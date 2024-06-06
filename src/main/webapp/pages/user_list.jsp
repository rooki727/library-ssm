
<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<h1>查询所有用户</h1>

<%--    ${adminList}--%>

<c:forEach items="${adminList}" var="admin">
    ${admin.userName}
    ${admin.password}
</c:forEach>

</body>
</html>
