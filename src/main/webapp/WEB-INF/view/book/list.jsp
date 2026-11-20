<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html>
<head>
    <%@ include file="../common/statics.jsp" %>
    <title>Book List</title>
</head>

<body>
<section class="section">
    <div class="container">
        <h1 class="title">Book List</h1>

        <p class="block">
            <span class="tag is-success">total books：${requestScope.pageResult.totalRecords}</span>
            <span class="tag is-link">current page：${requestScope.pageResult.pageNum}</span>
            <span class="tag is-success">total pages：${requestScope.pageResult.totalPages}</span>
        </p>
        <div class="table-container">
            <table class="table is-striped is-hoverable is-bordered is-fullwidth">
                <thead>
                <tr class="is-success">
                    <th>ID</th>
                    <th>书名</th>
                    <th>作者</th>
                    <th>价格</th>
                    <th>分类</th>
                </tr>
                </thead>

                <tbody>

                <c:forEach items="${requestScope.pageResult.list}" var="book">
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

                <c:if test="${empty requestScope.pageResult.list}">
                    <tr>
                        <td colspan="5">暂无图书</td>
                    </tr>
                </c:if>

                </tbody>
            </table>
        </div>
        <div class="level">
            <!-- page navigation -->
            <div class="level-left">
                <div class="level-item">
                    <nav class="pagination is-small">
                        <c:choose>
                            <c:when test="${requestScope.pageResult.pageNum > 1}">

                                <a
                                        href="${pageContext.request.contextPath}/books?pageNum=${requestScope.pageResult.pageNum - 1}&pageSize=${requestScope.pageResult.pageSize}&category=${requestScope.categoryId}"
                                        class="pagination-previous">Previous</a>
                            </c:when>
                            <c:otherwise>
                                <a href="#" class="pagination-previous is-disabled">Previous</a>
                            </c:otherwise>
                        </c:choose>

                        <c:choose>
                            <c:when test="${requestScope.pageResult.pageNum < requestScope.pageResult.totalPages}">
                                <a
                                        href="${pageContext.request.contextPath}/books?pageNum=${requestScope.pageResult.pageNum + 1}&pageSize=${requestScope.pageResult.pageSize}&category=${requestScope.categoryId}"
                                        class="pagination-next">Next page</a>
                            </c:when>
                            <c:otherwise>
                                <a class="pagination-next is-disabled">Next page</a>
                            </c:otherwise>
                        </c:choose>

                        <ul class="pagination-list">


                            <!-- page -->
                            <c:forEach begin="1" end="${requestScope.pageResult.totalPages}" var="i">
                                <c:choose>
                                    <c:when test="${i==requestScope.pageResult.pageNum}">
                                        <li>
                                            <a class="pagination-link is-current">${i}</a>
                                        </li>
                                    </c:when>
                                    <c:otherwise>
                                        <li><a class="pagination-link"
                                               href="${pageContext.request.contextPath}/books?pageNum=${i}&pageSize=${requestScope.pageResult.pageSize}&category=${requestScope.categoryId}">${i}</a>
                                        </li>
                                    </c:otherwise>
                                </c:choose>
                            </c:forEach>
                        </ul>
                    </nav>
                </div>
            </div>
            <%-- form --%>
            <div class="level-right">
                <div class="level-item">
                    <form action="${pageContext.request.contextPath}/books" method="get">
                        <input type="hidden" name="pageNum" value="1">

                        <div class="is-flex is-justify-content-flex-end is-gap-2">


                            <div class="field is-horizontal">
                                <div class="field-label is-small">
                                    <label for="pageSize"
                                           class="label">PageSize</label>
                                </div>
                                <div class="field-body">
                                    <div class="field is-narrow">
                                        <div class="control">
                                            <div class="select is-fullwidth is-small">
                                                <select name="pageSize" id="pageSize"
                                                        onchange="this.form.submit()">
                                                    <option value="5"
                                                            <c:if test="${requestScope.pageResult.pageSize == 5}">selected</c:if>>
                                                        5
                                                    </option>
                                                    <option value="10"
                                                            <c:if test="${requestScope.pageResult.pageSize == 10}">selected</c:if>
                                                    >
                                                        10
                                                    </option>
                                                    <option value="20"
                                                            <c:if test="${requestScope.pageResult.pageSize == 20}">selected</c:if>
                                                    >
                                                        20
                                                    </option>
                                                    <option value="50"
                                                            <c:if test="${requestScope.pageResult.pageSize == 50}">selected</c:if>
                                                    >
                                                        50
                                                    </option>
                                                </select>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>


                            <div class="field is-horizontal">
                                <div class="field-label is-small">
                                    <label for="category" class="label">Category</label>
                                </div>
                                <div class="field-body">
                                    <div class="field is-narrow">
                                        <div class="control">
                                            <div class="select is-fullwidth is-small">
                                                <select name="category" id="category"
                                                        onchange="this.form.submit()">
                                                    <option value="" ${empty requestScope.categoryId? 'selected':''}>All
                                                    </option>
                                                    <jsp:useBean id="categories" scope="request" type="java.util.List"/>
                                                    <c:forEach items="${categories}" var="cate">
                                                        <c:choose>
                                                            <c:when test="${requestScope.categoryId == cate.id}">
                                                                <option value="${cate.id}" selected><c:out
                                                                        value="${cate.name}"/></option>
                                                            </c:when>
                                                            <c:otherwise>
                                                                <option value="${cate.id}"><c:out
                                                                        value="${cate.name}"/></option>
                                                            </c:otherwise>
                                                        </c:choose>

                                                    </c:forEach>
                                                </select>
                                            </div>
                                        </div>
                                    </div>
                                </div>

                            </div>
                        </div>
                    </form>
                </div>
            </div>
        </div>
    </div>
</section>
</body>

</html>