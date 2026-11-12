<%@ page contentType="text/html;charset=UTF-8" %>

<html>
<head>
    <title>Bookstore</title>
</head>
<body>
<h1>Bookstore V1</h1>
<h2>Hello ${sessionScope.user.username}</h2>
<a href="${pageContext.request.contextPath}/books">books</a>
</body>
</html>