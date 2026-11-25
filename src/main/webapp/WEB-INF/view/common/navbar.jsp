<div class="box">
    <nav class="navbar">
        <div class="navbar-brand">
            <div class="is-flex is-align-items-center"><a href="${pageContext.request.contextPath}/"
                                                          class="is-italic has-text-weight-extrabold is-uppercase is-size-4 has-shadow">BookStore
                V1</a>
            </div>
        </div>

        <div class="navbar-menu">
            <div class="navbar-end">
                <a class="navbar-item" href="${pageContext.request.contextPath}/books">
                    Books
                </a>
                <c:choose>
                    <c:when test="${sessionScope.loginUser != null}">
                        <div class="navbar-item has-dropdown is-hoverable">
                            <a class="navbar-link">
                                <c:out value="${sessionScope.loginUser.username}"/>
                            </a>
                            <div class="navbar-dropdown">
                                <form action="${pageContext.request.contextPath}/logout" method="post">
                                    <input type="hidden" name="csrfToken" value="${sessionScope.csrfToken}"/>
                                    <button class="navbar-link navbar-item">Logout</button>
                                </form>
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
</div>