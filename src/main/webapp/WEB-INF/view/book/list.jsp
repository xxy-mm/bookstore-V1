<jsp:useBean id="pageResult" scope="request" type="org.example.bookstore.dto.PageResult"/>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html data-bs-theme="light">

<head>
    <meta charset="UTF-8">
    <title>图书列表</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/static/bootstrap.min.css">
</head>

<body class="container">

<h1>图书列表</h1>

<p>
    总记录数：${pageResult.totalRecords}
    当前页：${pageResult.pageNum}
    总页数：${pageResult.totalPages}
</p>

<table class="table">
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
            <td><a href="${pageContext.request.contextPath}/book/detail?id=${book.id}">
                    <c:out value="${book.title}"/>
            </td>
            <td>
                <c:out value="${book.author}"/>
            </td>
            <td>${book.price}</td>
            <td>
                <c:out value="${book.categoryName}"/>
            </td>
        </tr>
    </c:forEach>

    <c:if test="${empty pageResult.list}">
        <tr>
            <td colspan="5">暂无图书</td>
        </tr>
    </c:if>

    </tbody>
</table>
<div class="row">
    <!-- page navigation -->
    <div class="col">
        <nav aria-label="table pagination">
            <ul class="pagination">
                <!-- pre -->
                <c:choose>
                    <c:when test="${pageResult.pageNum > 1}">
                        <li class="page-item"><a
                                href="${pageContext.request.contextPath}/books?pageNum=${pageResult.pageNum - 1}&pageSize=${pageResult.pageSize}&category=${requestScope.categoryId}"
                                class="page-link">Previous</a></li>
                    </c:when>
                    <c:otherwise>
                        <li class="page-item"><span class="page-link disabled">Previous</span></li>
                    </c:otherwise>
                </c:choose>
                <!-- page -->
                <c:forEach begin="1" end="${pageResult.totalPages}" var="i">
                    <c:choose>
                        <c:when test="${i==pageResult.pageNum}">
                            <li class="page-item active">
                                <span class="page-link">${i}</span>
                            </li>
                        </c:when>
                        <c:otherwise>
                            <li class="page-item"><a class="page-link"
                                                     href="${pageContext.request.contextPath}/books?pageNum=${i}&pageSize=${pageResult.pageSize}&category=${requestScope.categoryId}">${i}</a>
                            </li>
                        </c:otherwise>
                    </c:choose>
                </c:forEach>
                <!-- next -->
                <c:choose>
                    <c:when test="${pageResult.pageNum < pageResult.totalPages}">
                        <li class="page-item"><a
                                href="${pageContext.request.contextPath}/books?pageNum=${pageResult.pageNum + 1}&pageSize=${pageResult.pageSize}&category=${requestScope.categoryId}"
                                class="page-link">Next</a></li>
                    </c:when>
                    <c:otherwise>
                        <li class="page-item"><span class="page-link disabled">Next</span></li>
                    </c:otherwise>
                </c:choose>
            </ul>
        </nav>
    </div>
    <%-- form --%>
    <div class="col">
        <form action="${pageContext.request.contextPath}/books" method="get">
            <input type="hidden" name="pageNum" value="1">
            <div class="form-field">
                <div class="row">
                    <label for="pageSize"
                           class="sm:col-1 col-form-label col-form-label-sm">Size</label>
                    <div class="sm:col-2">
                        <select name="pageSize" id="pageSize" class="form-control form-control-sm"
                                onchange="this.form.submit()">
                            <option value="5" <c:if test="${pageResult.pageSize == 5}">selected</c:if>>
                                5 条
                            </option>
                            <option value="10"
                                    <c:if test="${pageResult.pageSize == 10}">selected</c:if>
                            >
                                10 条
                            </option>
                            <option value="20"
                                    <c:if test="${pageResult.pageSize == 20}">selected</c:if>
                            >
                                20 条
                            </option>
                            <option value="50"
                                    <c:if test="${pageResult.pageSize == 50}">selected</c:if>
                            >
                                50 条
                            </option>
                        </select>
                    </div>
                </div>
            </div>
            <div class="form-field">
                <div class="row">
                    <label for="category" class="sm:col-1 col-form-label col-form-label-sm">Category</label>
                    <div class="sm:col-2">
                        <select name="category" id="category" class="form-control form-control-sm"
                                onchange="this.form.submit()">
                            <option value="" ${empty categoryId? 'selected':''}>All</option>
                            <jsp:useBean id="categories" scope="request" type="java.util.List"/>
                            <c:forEach items="${categories}" var="cate">
                                <c:choose>
                                    <c:when test="${categoryId == cate.id}">
                                        <option value="${cate.id}" selected><c:out value="${cate.name}"/></option>
                                    </c:when>
                                    <c:otherwise>
                                        <option value="${cate.id}"><c:out value="${cate.name}"/></option>
                                    </c:otherwise>
                                </c:choose>

                            </c:forEach>
                        </select>
                    </div>
                </div>
            </div>
        </form>
    </div>
</div>


</body>

</html>