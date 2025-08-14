<%-- /WEB-INF/views/module/header.jsp --%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<header class="header">
    <div class="header-left">
        <img src="${pageContext.request.contextPath}/resources/images/logo.png" alt="LINKED" class="logo">
    </div>
    <div class="header-right">
        <div class="header-right-top">
            <a href="${pageContext.request.contextPath}/logout">Logout</a>
            <a href="${pageContext.request.contextPath}/mypage">My Page</a>
        </div>
        <div class="menu">
            <a href="${pageContext.request.contextPath}/community">COMMUNITY</a>
            <a href="${pageContext.request.contextPath}/org/main">ORGANIZATION</a>
            <a href="${pageContext.request.contextPath}/crowdfunding">CROWD FUNDING</a>
        </div>
    </div>
</header>