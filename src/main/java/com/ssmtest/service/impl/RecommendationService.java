package com.ssmtest.service.impl;

import com.ssmtest.dao.BookDao;
import com.ssmtest.dao.UserBookDao;
import com.ssmtest.entity.Book;
import com.ssmtest.service.BookService;
import com.ssmtest.service.CollectionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

/**
 * 图书推荐服务实现类
 * 基于用户收藏数据和内容相似度进行个性化推荐
 */
@Service("userBookService")
public class RecommendationService {

    // 依赖注入部分
    @Autowired
    private UserBookDao userBookDao;          // 用户-书籍关系DAO
    @Autowired
    private BookDao bookDao;                  // 书籍数据访问DAO
    @Autowired
    private CollectionService collectionService; // 收藏服务
    @Autowired
    private BookService bookService;          // 基础书籍服务

    // 分类元数据
    private List<String> allCategories;       // 所有图书分类列表
    private Map<String, Integer> categoryIndexMap; // 分类名称到向量索引的映射

    // 缓存数据
    private Map<String, Integer> categoryTotalCollectMap; // 分类总收藏数缓存
    private Map<Integer, Integer> bookCollectCountCache = new ConcurrentHashMap<>(); // 书籍收藏数缓存
    private Map<Integer, Book> allBooksMap = new ConcurrentHashMap<>(); // 全量书籍数据缓存
    private Map<Integer, List<Double>> bookFeatureCache = new ConcurrentHashMap<>(); // 书籍特征向量缓存

    /**
     * 初始化方法（Bean生命周期初始化）
     * 加载分类元数据、收藏数据和全量书籍数据
     */
    @PostConstruct
    public void init() {
        initializeCategoryData();
        initializeCollectData();
        initializeAllBooks();
    }

    /**
     * 初始化分类元数据
     * 1. 加载全部分类数据
     * 2. 构建分类索引映射
     * 3. 初始化分类总收藏数统计
     */
    private void initializeCategoryData() {
        // 从数据库获取所有图书分类
        allCategories = bookDao.getAllCategories();
        // 构建分类名称到向量索引的映射（用于特征向量中的位置）
        categoryIndexMap = new ConcurrentHashMap<>(allCategories.size());
        for (int i = 0; i < allCategories.size(); i++) {
            categoryIndexMap.put(allCategories.get(i), i);
        }

        // 获取各分类总收藏数并缓存
        List<Map<String, Object>> categoryCollectData = bookDao.getCategoryTotalCollectCounts();
        categoryTotalCollectMap = categoryCollectData.stream()
                .collect(Collectors.toConcurrentMap(
                        e -> (String) e.get("category"),
                        e -> ((Long) e.get("totalCollect")).intValue()
                ));
    }

    /**
     * 初始化收藏数据
     * 加载所有书籍的收藏数量到缓存
     */
    private void initializeCollectData() {
        // 批量获取所有书籍的收藏计数
        List<Map<String, Object>> collectCounts = collectionService.getAllBookCollectCounts();
        // 转换为书籍ID到收藏数的映射
        collectCounts.forEach(e ->
                bookCollectCountCache.put(
                        (Integer) e.get("book_id"),
                        ((Long) e.get("count")).intValue()
                )
        );
    }

    /**
     * 初始化全量书籍数据
     * 1. 加载所有书籍到内存缓存
     * 2. 预计算每本书的特征向量
     */
    private void initializeAllBooks() {
        List<Book> books = bookDao.findAllBook();
        books.forEach(book -> {
            // 缓存书籍基本信息
            allBooksMap.put(book.getBook_id(), book);
            // 预计算并缓存特征向量
            bookFeatureCache.put(book.getBook_id(), computeFeatureVector(book));
        });
    }

    /**
     * 计算书籍特征向量
     * 特征工程：基于书籍分类的收藏比例构建特征向量
     * @param book 目标书籍对象
     * @return 归一化的特征向量（维度对应分类，值为该分类下本书收藏占比）
     */
    private List<Double> computeFeatureVector(Book book) {
        // 初始化全0向量（维度数等于分类总数）
        List<Double> vector = new ArrayList<>(Collections.nCopies(allCategories.size(), 0.0));
        String category = book.getCategory();

        if (category != null && categoryIndexMap.containsKey(category)) {
            int index = categoryIndexMap.get(category);
            // 获取本书收藏数和分类总收藏数
            int bookCollect = bookCollectCountCache.getOrDefault(book.getBook_id(), 0);
            int categoryCollect = categoryTotalCollectMap.getOrDefault(category, 0);

            // 计算特征值：本书收藏数占分类总收藏数的比例（防止除零）
            double value = categoryCollect == 0 ? 1.0 :
                    (double) bookCollect / categoryCollect;
            vector.set(index, value);
        }
        return vector;
    }

    /**
     * 核心推荐方法
     * @param userId 目标用户ID
     * @param page 分页页码
     * @param pageSize 分页大小
     * @return 推荐结果列表（按相似度和热度排序）
     */
    public List<Book> recommendBooks(int userId, int page, int pageSize) {
        // 获取用户已收藏书籍ID集合（优化为单次查询）
        Set<Integer> collectedIds = new HashSet<>(userBookDao.getBooksByUserId(userId));

        // 无收藏时的降级策略：返回过滤后的热门书籍
        if (collectedIds.isEmpty()) {
            return getFilteredHotBooks(collectedIds, page, pageSize);
        }

        // 批量获取用户收藏书籍详情（减少数据库访问）
        List<Book> collectedBooks = bookDao.getBooksByIds(new ArrayList<>(collectedIds));
//        System.out.println(collectedBooks);
        // 构建用户兴趣向量（基于收藏书籍的分类分布）
        List<Double> userVector = buildUserVector(collectedBooks);

        // 并行计算所有书籍与用户的相似度
        Map<Book, Double> similarityMap = allBooksMap.values().parallelStream()
                .collect(Collectors.toConcurrentMap(
                        book -> book,
                        book -> computeSimilarity(userVector, bookFeatureCache.get(book.getBook_id()))
                ));

        // 复合排序和分页处理
        return similarityMap.entrySet().stream()
                .sorted((e1, e2) -> {
                    // 第一优先级：相似度降序
                    int cmp = Double.compare(e2.getValue(), e1.getValue());
                    if (cmp != 0) return cmp;
                    // 第二优先级：收藏数降序（解决相似度相同的情况）
                    return Integer.compare(
                            bookCollectCountCache.getOrDefault(e2.getKey().getBook_id(), 0),
                            bookCollectCountCache.getOrDefault(e1.getKey().getBook_id(), 0)
                    );
                })
                .map(Map.Entry::getKey)
                .skip((page - 1) * pageSize)  // 分页跳转
                .limit(pageSize)               // 分页限制
                .collect(Collectors.toList());
    }

    /**
     * 获取过滤后的热门书籍（降级推荐策略）
     * @param excludeIds 需要排除的书籍ID集合
     * @param page 分页页码
     * @param pageSize 分页大小
     * @return 按收藏数降序排列的热门书籍
     */
    private List<Book> getFilteredHotBooks(Set<Integer> excludeIds, int page, int pageSize) {
        // 获取基础热门书籍数据
        List<Book> hotBooks = bookService.getGuessLikeBooks(1, Integer.MAX_VALUE).stream()
                .filter(book -> !excludeIds.contains(book.getBook_id()))  // 过滤已收藏
                .sorted((b1, b2) -> Integer.compare(          // 按收藏数降序排序
                        bookCollectCountCache.getOrDefault(b2.getBook_id(), 0),
                        bookCollectCountCache.getOrDefault(b1.getBook_id(), 0)
                ))
                .collect(Collectors.toList());

        return paginate(hotBooks, page, pageSize);
    }

    /**
     * 构建用户兴趣向量
     * @param collectedBooks 用户收藏的书籍列表
     * @return 归一化的用户兴趣向量（各维度表示对应分类的收藏比例）
     */
    private List<Double> buildUserVector(List<Book> collectedBooks) {
        // 统计各分类的收藏次数
        Map<String, Long> categoryCount = collectedBooks.stream()
                .filter(b -> b.getCategory() != null)
                .collect(Collectors.groupingBy(
                        Book::getCategory,
                        Collectors.counting()
                ));

        // 转换为比例向量并进行归一化
        return allCategories.stream()
                .map(c -> (double) categoryCount.getOrDefault(c, 0L) / collectedBooks.size())
                .collect(Collectors.toList());
    }

    /**
     * 计算余弦相似度
     * @param userVec 用户兴趣向量
     * @param bookVec 书籍特征向量
     * @return 两个向量的余弦相似度值（范围[-1,1]）
     */
    private double computeSimilarity(List<Double> userVec, List<Double> bookVec) {
        double dotProduct = 0.0;  // 点积
        double userMagnitude = 0.0; // 用户向量模长
        double bookMagnitude = 0.0; // 书籍向量模长

        for (int i = 0; i < userVec.size(); i++) {
            double u = userVec.get(i);
            double b = bookVec.get(i);
            dotProduct += u * b;
            userMagnitude += u * u;
            bookMagnitude += b * b;
        }

        // 处理零向量情况
        if (userMagnitude == 0 || bookMagnitude == 0) {
            return 0.0;
        }
        return dotProduct / (Math.sqrt(userMagnitude) * Math.sqrt(bookMagnitude));
    }

    /**
     * 通用分页工具方法
     * @param list 待分页的列表
     * @param page 目标页码（从1开始）
     * @param pageSize 每页大小
     * @return 分页后的子列表
     */
    private List<Book> paginate(List<Book> list, int page, int pageSize) {
        if (page < 1 || pageSize < 1) return Collections.emptyList();
        int start = (page - 1) * pageSize;
        return start >= list.size() ? Collections.emptyList() :
                list.subList(start, Math.min(start + pageSize, list.size()));
    }
}