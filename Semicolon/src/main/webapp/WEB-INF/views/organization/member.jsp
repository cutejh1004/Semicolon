<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>팀원 목록</title>
    <!-- CSS 파일 연결 -->
    <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/common.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/member.css">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.4/css/all.min.css">
</head>
<body>
    <%@ include file="/WEB-INF/views/module/header.jsp" %>
    <div class="main-layout-container">
        <!-- orgsidebar.jsp를 재사용하여 기존 사이드바를 포함 -->
        <jsp:include page="/WEB-INF/views/common/orgsidebar.jsp" />

        <div class="main-content">
            <div class="member-list-container">
                <div class="member-list-header">
                    <h2>팀원 목록</h2>
                </div>
                
                <!-- 검색 및 필터링 바 추가 -->
                <div class="search-filter-bar">
                    <input type="text" id="member-search" placeholder="팀원 검색...">
                    <select id="role-filter">
                        <option value="">역할 전체</option>
                        <option value="PM">PM</option>
                        <option value="DEVELOPER">개발자</option>
                        <option value="DESIGNER">디자이너</option>
                    </select>
                    <select id="status-filter">
                        <option value="">상태 전체</option>
                        <option value="active">활동중</option>
                        <option value="away">휴식</option>
                    </select>
                </div>

                <table class="member-list-table">
                    <thead>
                        <tr>
                            <th>아이디</th>
                            <th>닉네임</th>
                            <th>역할</th>
                            <th>전공</th>
                            <th>상태</th>
                            <!-- PM에게만 보이는 관리 기능 열 -->
                            <c:if test="${sessionScope.currentUser.memberRole eq 'PM'}">
                                <th>관리</th>
                            </c:if>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach var="member" items="${members}">
                            <tr>
                                <td><a href="#">${member.userId}</a></td>
                                <td>${member.userId}</td>
                                <td>${member.memberRole}</td>
                                <td>${member.major}</td>
                                <td>
                                    <c:choose>
                                        <c:when test="${member.status eq 'active'}">
                                            <span class="status-badge status-active">활동중</span>
                                        </c:when>
                                        <c:when test="${member.status eq 'away'}">
                                            <span class="status-badge status-away">휴식</span>
                                        </c:when>
                                        <c:otherwise>
                                            <span class="status-badge status-unknown">알 수 없음</span>
                                        </c:otherwise>
                                    </c:choose>
                                </td>
                                <!-- PM에게만 보이는 관리 기능 버튼들 -->
                                <c:if test="${sessionScope.currentUser.memberRole eq 'PM'}">
                                    <td class="member-actions">
                                        <!-- 역할 변경 드롭다운 -->
                                        <select onchange="changeMemberRole('${member.userId}', this.value)">
                                            <option value="" disabled selected>역할 변경</option>
                                            <option value="PM">PM</option>
                                            <option value="DEVELOPER">개발자</option>
                                            <option value="DESIGNER">디자이너</option>
                                        </select>
                                        <!-- 추방 버튼 -->
                                        <button onclick="kickMember('${member.userId}')">추방</button>
                                    </td>
                                </c:if>
                            </tr>
                        </c:forEach>
                    </tbody>
                </table>
            </div>
        </div>
    </div>
    <%@ include file="/WEB-INF/views/module/footer.jsp" %>
    <script>
        // 권한에 따른 기능 실행 함수 (실제 로직은 서버와 통신해야 합니다)
        function changeMemberRole(userId, newRole) {
            if (confirm(`'${userId}' 님의 역할을 '${newRole}'(으)로 변경하시겠습니까?`)) {
                console.log(`Sending request to change role of ${userId} to ${newRole}`);
                // 여기에 AJAX 요청 또는 폼 제출 로직을 추가
            }
        }
        function kickMember(userId) {
            if (confirm(`'${userId}' 님을 팀에서 추방하시겠습니까?`)) {
                console.log(`Sending request to kick out ${userId}`);
                // 여기에 AJAX 요청 또는 폼 제출 로직을 추가
            }
        }
    </script>
</body>
</html>
