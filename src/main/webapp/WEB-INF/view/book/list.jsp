<%--
  Created by IntelliJ IDEA.
  User: xxy_m
  Date: 2026/11/9
  Time: 21:06
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c"
           uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>图书列表</title>

    <style>
        table {
            border-collapse: collapse;
            width: 800px;
        }

        th, td {
            border: 1px solid #ccc;
            padding: 10px;
            text-align: left;
        }

        .pagination {
            margin-top: 20px;
        }

        .pagination a,
        .pagination span {
            display: inline-block;
            padding: 6px 12px;
            margin-right: 5px;
            border: 1px solid #ccc;
            text-decoration: none;
        }

        .pagination .current {
            background: #337ab7;
            color: white;
        }

        .pagination .disabled {
            color: #999;
        }
    </style>
</head>

<body>

<h1>图书列表</h1>

<p>
    总记录数：${pageResult.totalRecords}
    当前页：${pageResult.pageNum}
    总页数：${pageResult.totalPages}
</p>

<table>
    <thead>
    <tr>
        <th>ID</th>
        <th>书名</th>
        <th>作者</th>
        <th>价格</th>
        <th>分类</th>
    </tr>
    </thead>

    <tbody>

    <c:forEach items="${pageResult.list}" var="book">
        <tr>
            <td>${book.id}</td>
            <td><a href="${pageContext.request.contextPath}/book?id=${book.id}"><c:out value="${book.title}"/></td>
            <td><c:out value="${book.author}"/></td>
            <td>${book.price}</td>
            <td><c:out value="${book.categoryName}"/></td>
        </tr>
    </c:forEach>

    <c:if test="${empty pageResult.list}">
        <tr>
            <td colspan="5">暂无图书</td>
        </tr>
    </c:if>

    </tbody>
</table>

<!-- 分页导航 -->
<div class="pagination">

    <!-- 上一页 -->
    <c:choose>
        <c:when test="${pageResult.pageNum > 1}">
            <a href="${pageContext.request.contextPath}/books?page=${pageResult.pageNum - 1}&pageSize=${pageResult.pageSize}">
                上一页
            </a>
        </c:when>
        <c:otherwise>
            <span class="disabled">上一页</span>
        </c:otherwise>
    </c:choose>

    <!-- 页码 -->
    <c:forEach begin="1"
               end="${pageResult.totalPages}"
               var="i">

        <c:choose>
            <c:when test="${i == pageResult.pageNum}">
                <span class="current">${i}</span>
            </c:when>

            <c:otherwise>
                <a href="${pageContext.request.contextPath}/books?page=${i}&pageSize=${pageResult.pageSize}">
                    ${i}
                </a>
            </c:otherwise>
        </c:choose>

    </c:forEach>

    <!-- 下一页 -->
    <c:choose>
        <c:when test="${pageResult.pageNum < pageResult.totalPages}">
            <a href="${pageContext.request.contextPath}/books?page=${pageResult.pageNum + 1}&pageSize=${pageResult.pageSize}">
                下一页
            </a>
        </c:when>
        <c:otherwise>
            <span class="disabled">下一页</span>
        </c:otherwise>
    </c:choose>
    <form action="${pageContext.request.contextPath}/books"
          method="get">

        <input type="hidden" name="page" value="1">

        <label for="pageSize">每页显示：</label>

        <select name="pageSize" id="pageSize"
                onchange="this.form.submit()">
<option value="2"
                    <c:if test="${pageResult.pageSize == 2}">selected</c:if>>
                    2 条
                </option>
             <option value="5"
                    <c:if test="${pageResult.pageSize == 5}">selected</c:if>>
                    5 条
                </option>

                <option value="10"
                    <c:if test="${pageResult.pageSize == 10}">selected</c:if>>
                    10 条
                </option>

                <option value="20"
                    <c:if test="${pageResult.pageSize == 20}">selected</c:if>>
                    20 条
                </option>

                <option value="50"
                    <c:if test="${pageResult.pageSize == 50}">selected</c:if>>
                    50 条
                </option>

        </select>

    </form>
</div>


</body>
</html>


