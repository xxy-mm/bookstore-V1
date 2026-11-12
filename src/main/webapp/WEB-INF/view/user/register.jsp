<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>User Register</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/static/bootstrap.min.css">
</head>
<body class="container">

<h1>User Register</h1>

<c:if test="${not empty requestScope.errorMsg}">
    <div class="alert theme-danger" role="alert">
        <p><c:out value="${requestScope.errorMsg}"/></p>
    </div>
</c:if>

<form action="${pageContext.request.contextPath}/user/register" method="post">
    <input type="hidden" value="<c:out value='${sessionScope.csrfToken}'/>" name="csrfToken"/>
    <div class="mb-3">
        <label for="username" class="form-label">Username</label>
        <input type="text" class="form-control" id="username" name="username"
               value="<c:out value='${requestScope.username}'/>"
               aria-describedby="usernameHelp" maxlength="50" required>
        <div id="usernameHelp" class="form-text">Max length 50 characters.</div>
    </div>
    <div class="mb-3">
        <label for="password" class="form-label">Password</label>
        <input type="password" class="form-control" id="password" name="password" aria-describedby="passwordHelp"
               minlength="8" required>
        <div id="passwordHelp" class="form-text">At least 8 characters.</div>
    </div>
    <div class="mb-3">
        <label for="confirmPassword" class="form-label">Confirm Password</label>
        <input type="password" class="form-control" id="confirmPassword" name="confirmPassword"
               minlength="8" required
               aria-describedby="confirmPasswordHelp">
        <div id="confirmPasswordHelp" class="form-text">Must as same as the password.</div>
    </div>
    <div class="mb-3">
        <label for="nickname" class="form-label">Nickname</label>
        <input type="text" class="form-control" id="nickname" name="nickname"
               maxlength="50"
               value="<c:out value='${requestScope.nickname}'/>">
    </div>

    <div class="mb-3">
        <label for="email" class="form-label">Email</label>
        <input type="email" class="form-control" id="email" maxlength="50" name="email"
               value="<c:out value='${requestScope.email}'/>">
    </div>

    <div class="mb-3">
        <label for="phone" class="form-label">Phone</label>
        <input type="text" class="form-control" id="phone" maxlength="20" name="phone"
               value="<c:out value='${requestScope.phone}'/>">
    </div>

    <button type="submit" class="btn-solid theme-primary">Submit</button>
</form>
</body>
</html>
