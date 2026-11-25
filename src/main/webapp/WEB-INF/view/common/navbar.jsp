<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<nav class="navbar">
    <div class="navbar-brand">
        <div class="is-flex is-align-items-center"><span
                class="is-italic has-text-weight-extrabold is-uppercase is-size-4 has-shadow">BookStore V1</span></div>
    </div>

    <div class="navbar-menu">
        <div class="navbar-end">
            <a class="navbar-item" href="${pageContext.request.contextPath}/books">
                Books
            </a>
            <c:choose>
                <c:when test="${loginUser != null}">
                    <div class="navbar-item has-dropdown is-hoverable">
                        <a class="navbar-link">
                            <c:out value="${loginUser.name}" />
                        </a>
                        <div class="navbar-dropdown">
                            <a class="navbar-item">Logout</a>
                        </div>
                    </div>
                </c:when>
                <c:otherwise>
                    <a class="navbar-item" href="${pageContext.request.contextPath}/login">
                        Login
                    </a>
                </c:otherwise>
            </c:choose>
        </div>
    </div>

</nav>