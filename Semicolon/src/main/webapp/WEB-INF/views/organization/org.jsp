<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>LINKED - 메인 페이지</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/common.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/dashboard.css"> <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.4/css/all.min.css">
</head>
<body>
    <%@ include file="/WEB-INF/views/module/header.jsp" %>
    <div class="main-layout-container">
        <jsp:include page="/WEB-INF/views/commons/orgsidebar.jsp" />

        <div class="main-content">
            <div class="dashboard-container">
                <div class="dashboard-section top-section">
                    <div class="dashboard-box best-crew-box">
                        <h3 class="box-title">분기별 프로젝트 베스트 크루</h3>
                        <div class="list-container">
                            <c:forEach var="crew" items="${quarterlyBestCrews}" varStatus="status">
                                <div class="list-item">
                                    <div class="ranking-icon-container">
                                        <c:choose>
                                            <c:when test="${status.index == 0}"><i class="fas fa-medal first-rank"></i></c:when>
                                            <c:when test="${status.index == 1}"><i class="fas fa-medal second-rank"></i></c:when>
                                            <c:when test="${status.index == 2}"><i class="fas fa-medal third-rank"></i></c:when>
                                            <c:otherwise><span class="ranking-number">${status.index + 1}</span></c:otherwise>
                                        </c:choose>
                                    </div>
                                    <div class="crew-name">${crew.crewName}</div>
                                    <div class="project-count">${crew.projectCount}</div>
                                </div>
                            </c:forEach>
                        </div>
                    </div>
                    
                    <div class="dashboard-box total-projects-box">
                        <h3 class="box-title">전체 조직 프로젝트수/완료율</h3>
                        <div class="total-projects-info">
                            <div class="info-card">
                                <span class="card-label">TOTAL PROJECTS</span>
                                <span class="card-value">${totalProjects}</span>
                            </div>
                            <div class="info-card">
                                <span class="card-label">COMPLETED PROJECTS</span>
                                <span class="card-value">${completedProjects}</span>
                            </div>
                        </div>
                    </div>
                </div>

                <div class="dashboard-section bottom-section">
                    <div class="dashboard-box hot-crew-box">
                        <h3 class="box-title">이번 달 가장 핫한 크루</h3>
                        <div class="list-container">
                            <c:forEach var="crew" items="${monthlyHotCrews}" varStatus="status">
                                <div class="list-item">
                                    <div class="ranking-icon-container">
                                        <c:choose>
                                            <c:when test="${status.index == 0}"><i class="fas fa-medal first-rank"></i></c:when>
                                            <c:when test="${status.index == 1}"><i class="fas fa-medal second-rank"></i></c:when>
                                            <c:when test="${status.index == 2}"><i class="fas fa-medal third-rank"></i></c:when>
                                            <c:otherwise><span class="ranking-number">${status.index + 1}</span></c:otherwise>
                                        </c:choose>
                                    </div>
                                    <div class="crew-name">${crew.crewName}</div>
                                    <div class="member-count">${crew.memberCount}명</div>
                                </div>
                            </c:forEach>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
    <%@ include file="/WEB-INF/views/module/footer.jsp" %>
</body>
</html>