<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<html>
<head>
    <%@ include file="../common/statics.jsp" %>
    <title>User Login</title>
</head>
<body>
<section class="section">
    <div class="container">
        <h2 class="is-size-4 has-text-weight-bold is-flex is-justify-content-center is-align-items-center">Login</h2>
        <c:if test="${not empty requestScope.errorMsg}">
            <div class="notification is-danger">
                <c:out value="${requestScope.errorMsg}"/>
            </div>
        </c:if>
        <form method="post" action="${pageContext.request.contextPath}/login">
            <input type="hidden" name="csrfToken" value="${sessionScope.csrfToken}">
            <div class="field">
                <label class="label" for="username">Username</label>
                <div class="control">
                    <input class="input" type="text" id="username" name="username">
                </div>
            </div>
            <div class="field">
                <label class="label" for="password">Password</label>
                <div class="control">
                    <input class="input" type="password" id="password" name="password">
                </div>
            </div>
            <div class="field">
                <p>Don't have a username? <a href="${pageContext.request.contextPath}/register">Register here.</a></p>
                <input type="submit" value="Login" class="is-link button is-float-right">
            </div>
        </form>
    </div>
</section>
</body>
</html>
