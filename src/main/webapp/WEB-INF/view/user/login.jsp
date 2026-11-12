<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>User Login</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/static/bootstrap.min.css">
</head>
<body class="container">

<h1>User Login</h1>

<c:if test="${not empty requestScope.errorMsg}">
    <div class="alert theme-danger" role="alert">
        <p><c:out value="${requestScope.errorMsg}"/></p>
    </div>
</c:if>

<form action="${pageContext.request.contextPath}/user/login" method="post">
    <input type="hidden" value="<c:out value='${sessionScope.csrfToken}'/>" name="csrfToken"/>
    <div class="mb-3">
        <label for="username" class="form-label">Username</label>
        <input type="text" class="form-control" id="username" name="username" required maxlength="50"
               autocomplete="username"
               value="<c:out value='${requestScope.username}'/>">

    </div>
    <div class="mb-3">
        <label for="password" class="form-label">Password</label>
        <input type="password" class="form-control" id="password" name="password" autocomplete="password"
               required>

    </div>
    <button type="submit" class="btn-solid theme-primary">Login</button>
</form>
</body>
</html>
