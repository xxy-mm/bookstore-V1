<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<html>
<head>
    <%@ include file="../common/statics.jsp" %>
    <title>User Register</title>
</head>

<body>
<section class="section">
    <div class="container">
        <h1 class="is-size-4 has-text-weight-bold has-text-centered pb-4">User Register</h1>
        <c:if test="${not empty requestScope.errorMsg}">
            <div class="box">
                <p class="has-text-danger"><c:out value="${requestScope.errorMsg}"/></p>
            </div>
        </c:if>
        <c:if test="${not empty requestScope.successMsg}">
            <div class="notification is-success">
                <p>${requestScope.successMsg}</p>
                <p>Redirecting to login page in <span id="countdown">3</span> seconds...</p>
            </div>
        </c:if>

        <form action="${pageContext.request.contextPath}/register" method="post" class="box">
            <input type="hidden" value="<c:out value='${sessionScope.csrfToken}'/>" name="csrfToken"/>
            <div class="field">
                <label for="username" class="label">Username</label>
                <input type="text" class="input" id="username" name="username"
                       value="<c:out value='${requestScope.username}'/>"
                       maxlength="50" required>
            </div>
            <div class="field">
                <label for="password" class="label">Password</label>
                <input type="password" class="input" id="password" name="password"
                       minlength="8" required>
            </div>
            <div class="field">
                <label for="confirmPassword" class="label">Confirm Password</label>
                <input type="password" class="input" id="confirmPassword" name="confirmPassword"
                       minlength="8" required
                >
            </div>
            <div class="field">
                <label for="nickname" class="label">Nickname</label>
                <input type="text" class="input" id="nickname" name="nickname"
                       maxlength="50"
                       value="<c:out value='${requestScope.nickname}'/>">
            </div>

            <div class="field">
                <label for="email" class="label">Email</label>
                <input type="email" class="input" id="email" maxlength="50" name="email"
                       value="<c:out value='${requestScope.email}'/>">
            </div>

            <div class="field">
                <label for="phone" class="label">Phone</label>
                <input type="text" class="input" id="phone" maxlength="20" name="phone"
                       value="<c:out value='${requestScope.phone}'/>">
            </div>

            <div class="field">
                <div class="is-flex">

                    <button type="submit" class="button is-link ml-auto">Submit</button>
                </div>
            </div>
        </form>
    </div>
</section>

<script>
    <c:if test="${requestScope.successMsg != null}">
    let seconds = 3;

    const timer = setInterval(() => {
        seconds--;

        document.getElementById("countdown").textContent = seconds + '';

        if (seconds <= 0) {
            clearInterval(timer);
            window.location.href =
                "${pageContext.request.contextPath}/login";
        }
    }, 1000);
    </c:if>
</script>
</body>
</html>
