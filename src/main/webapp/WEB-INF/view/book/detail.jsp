
<%@page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:useBean id="book" scope="request" type="org.example.bookstore.entity.Book"/>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
    <meta charset="UTF-8">
    <title><c:out value="${book.title}"/> - 图书详情</title>
</head>
<body>
    <h1><c:out value="${book.title}"/></h1>

    <div>
        <img src="${book.cover}" alt="<c:out value="${book.title}"/>" width="200">
    </div>

    <p>作者：<c:out value="${book.author}"/></p>

    <p>出版社：
    <c:out value="${book.publisher}"/></p>

    <p>ISBN：${book.isbn}</p>

    <p>价格：￥${book.price}</p>

    <p>库存：${book.stock}</p>

    <p>图书简介：<c:out value="${book.description}"/></p>

    1<%= request.getContextPath() %>

    2${param.name}

    3${pageContext.request.contextPath}
</body>
</html>
