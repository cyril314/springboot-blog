<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>${pageTitle}-Powered by java1234</title>
    <link rel="stylesheet" href="${ctx}/static/bootstrap3/css/bootstrap.min.css">
    <link rel="stylesheet" href="${ctx}/static/bootstrap3/css/bootstrap-theme.min.css">
    <link rel="stylesheet" href="${ctx}/static/css/blog.css">
    <link href="${ctx}/favicon.ico" rel="SHORTCUT ICON">
    <script src="${ctx}/static/bootstrap3/js/jquery-1.11.2.min.js"></script>
    <script src="${ctx}/static/bootstrap3/js/bootstrap.min.js"></script>
    <style type="text/css">
        body {
            padding-top: 10px;
            padding-bottom: 40px;
        }
    </style>
</head>
<body>
<div class="container">
    <jsp:include page="/views/foreground/common/head.jsp"/>

    <jsp:include page="/views/foreground/common/menu.jsp"/>

    <div class="row">
        <div class="col-md-9">
            <jsp:include page="${mainPage }"></jsp:include>
        </div>

        <div class="col-md-3">
            <div class="data_list">
                <div class="data_list_title">
                    <img src="${ctx}/static/images/user_icon.png"/>
                    博主信息
                </div>
                <div class="user_image">
                    <img src="${ctx}/static/userImages/${blogger.imagename }"/>
                </div>
                <div class="nickName">${blogger.nickname }</div>
                <div class="userSign">(${blogger.sign })</div>
            </div>

            <div class="data_list">
                <div class="data_list_title">
                    <img src="${ctx}/static/images/byType_icon.png"/>
                    按日志类别
                </div>
                <div class="datas">
                    <ul>
                        <c:forEach var="blogTypeCount" items="${blogTypeCountList }">
                            <li><span><a
                                    href="${ctx}/index.html?typeId=${blogTypeCount.id }">${blogTypeCount.typeName }(${blogTypeCount.blogCount })</a></span>
                            </li>
                        </c:forEach>
                    </ul>
                </div>
            </div>

            <div class="data_list">
                <div class="data_list_title">
                    <img src="${ctx}/static/images/byDate_icon.png"/>
                    按日志日期
                </div>
                <div class="datas">
                    <ul>
                        <c:forEach var="blogCount" items="${blogCountList }">
                            <li><span><a
                                    href="${ctx}/index.html?releaseDateStr=${blogCount.releaseDateStr }">${blogCount.releaseDateStr }(${blogCount.blogCount })</a></span>
                            </li>
                        </c:forEach>
                    </ul>
                </div>
            </div>

            <div class="data_list">
                <div class="data_list_title">
                    <img src="${ctx}/static/images/link_icon.png"/>
                    友情链接
                </div>
                <div class="datas">
                    <ul>
                        <c:forEach var="link" items="${linkList }">
                            <li><span><a href="${link.linkUrl }" target="_blank">${link.linkName }</a></span></li>
                        </c:forEach>
                    </ul>
                </div>
            </div>
        </div>
    </div>
    <jsp:include page="/views/foreground/common/foot.jsp"/>
</div>
</body>
</html>