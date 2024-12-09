<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<%response.setStatus(200);%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>404 - Page not found!</title>
    <link rel="stylesheet" href="${ctx}/static/css/error.css">
</head>
<body>
<!-- partial:index.partial.html -->
<div class="error__container">
    <div class="error__code">
        <p>4</p>
        <p>0</p>
        <p>4</p>
    </div>
    <div class="error__title">Page Not Found</div>
    <div class="error__description">
        We can't seem to find that page. It might have been removed or doesn't
        exist anymore.
    </div>
    <button class="action" onclick="window.location.href='${ctx}'">Go Home</button>
</div>
<!-- partial -->
</body>
</html>
