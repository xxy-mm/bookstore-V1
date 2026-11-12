<%@page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html lang="zh-CN">
<head>
    <meta charset="UTF-8">
    <title><c:out value="${requestScope.book.title}"/> - 图书详情</title>
</head>
<body>
<h1><c:out value="${requestScope.book.title}"/></h1>

<div>
    <img src="${requestScope.book.cover}" alt="<c:out value="${requestScope.book.title}"/>" width="200">
</div>

<p>作者：<c:out value="${requestScope.book.author}"/></p>

<p>出版社：
    <c:out value="${requestScope.book.publisher}"/></p>

<p>ISBN：${requestScope.book.isbn}</p>

<p>价格：￥${requestScope.book.price}</p>

<p>库存：${requestScope.book.stock}</p>

<p>图书简介：<c:out value="${requestScope.book.description}"/></p>

</body>
</html>
