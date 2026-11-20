<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <%@ include file="WEB-INF/view/common/statics.jsp" %>
    <title>Home</title>
</head>
<body>
<nav class="navbar">
    <div class="navbar-brand">
        <div class="is-flex is-align-items-center"><span
                class="is-italic has-text-weight-extrabold is-uppercase is-size-4 has-shadow">BookStore V1</span></div>
    </div>
    <c:if test="${sessionScope.user!=null}">
        <div class="navbar-menu">
            <div class="navbar-end">
                <a class="navbar-item" href="${pageContext.request.contextPath}/books">
                    Books
                </a>
            </div>
        </div>
    </c:if>
</nav>

<div class="is-flex is-align-items-center is-justify-content-center mt-6">
    <c:choose>

        <c:when test="${sessionScope.user != null}">
            <h1>Welcome to BooStore V1, ${sessionScope.user.username}</h1>
        </c:when>
        <c:otherwise>
            <h1 class="is-flex is-align-items-center is-size-4">Welcome to BookStore V1, please&nbsp;<a
                    class="is-underlined" href="${pageContext.request.contextPath}/login">login</a>
            </h1>
        </c:otherwise>
    </c:choose>
</div>

</body>
</html>