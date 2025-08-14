<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>LINKED - 조직 생성</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/common.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/createOrg.css">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.4/css/all.min.css">
</head>
<body>
    <%@ include file="/WEB-INF/views/module/header.jsp" %>
    <div class="main-layout-container">
        <jsp:include page="/WEB-INF/views/common/orgsidebar.jsp" />

        <div class="main-content">
            <div class="page-title">
                <h2>조직 생성</h2>
            </div>

            <div class="org-create-form-container">
                <form id="orgCreateForm" action="${pageContext.request.contextPath}/org/create" method="post">
                    <div class="form-grid-container">
                        <div class="form-group">
                            <label for="orName">조직 이름</label>
                            <input type="text" id="orName" name="orName" placeholder="SEMICOLON" required>
                        </div>
                        <div class="form-group">
                            <label for="orCategory">조직 카테고리</label>
                            <select id="orCategory" name="orCategory">
                                <option value="개발/디자인/마케팅" selected>개발/디자인/마케팅</option>
                                <option value="기획">기획</option>
                                <option value="운영">운영</option>
                            </select>
                        </div>
                        
                        <div class="form-group">
                            <label for="orIsPublic">공개 여부</label>
                            <select id="orIsPublic" name="orIsPublic">
                                <option value="공개" selected>공개</option>
                                <option value="비공개">비공개</option>
                            </select>
                        </div>

                        <div class="form-group full-width">
                            <label for="orIntroduce">조직 설명</label>
                            <textarea id="orIntroduce" name="orIntroduce" rows="4" placeholder="내용을 입력하세요."></textarea>
                        </div>

                        <div class="form-group">
                            <label for="memberInvite">멤버 초대</label>
                            <!-- Placeholder를 수정하여 쉼표 입력 방식을 안내합니다. -->
                            <input type="text" id="memberInvite" name="memberInvite" placeholder="멤버 ID를 쉼표(,)로 구분하여 입력하세요.">
                            <div id="invitedMembers" class="invited-members">
                            </div>
                        </div>

                        <div class="form-group">
                            <label for="startDate">활동 시작일</label>
                            <input type="date" id="startDate" name="startDate">
                        </div>

                        <div class="form-buttons">
                            <button type="submit" class="confirm-btn">확인</button>
                            <button type="button" class="cancel-btn">취소</button>
                        </div>
                    </div>
                </form>
            </div>
        </div>
    </div>
    <%@ include file="/WEB-INF/views/module/footer.jsp" %>
</body>
</html>
