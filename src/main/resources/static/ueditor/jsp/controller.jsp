<%@ page language="java" contentType="text/html; charset=utf-8" import="com.baidu.ueditor.ActionEnter" pageEncoding="utf-8" %>
<%@ page trimDirectiveWhitespaces="true" %>
<%
    request.setCharacterEncoding("utf-8");
    response.setHeader("Content-Type", "text/html");
    String rootPath = application.getRealPath("/");
    if (rootPath.toLowerCase().contains("temp")) {
        rootPath = java.net.URLDecoder.decode(application.getResource("static/ueditor/").getPath(), "UTF-8");
        rootPath = rootPath.replace("/static/ueditor/", "/");
        System.out.println(rootPath);
    }
    out.write(new ActionEnter(request, rootPath).exec());
%>