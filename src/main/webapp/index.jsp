<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <%@ include file="WEB-INF/view/common/statics.jsp" %>
    <title>Home</title>
</head>
<body>
<%@include file="WEB-INF/view/common/navbar.jsp"%>

<div class="is-flex is-align-items-center is-justify-content-center mt-6">
    <c:choose>

        <c:when test="${sessionScope.loginUser != null}">
            <h1>Welcome to BooStore V1, ${sessionScope.loginUser.username}</h1>
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