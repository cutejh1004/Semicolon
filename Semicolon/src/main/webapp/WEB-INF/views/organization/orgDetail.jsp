<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>LINKED - 나의 조직 상세</title>
    <!-- CSS 파일 연결 -->
    <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/common.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/orgDetail.css">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.4/css/all.min.css">
</head>
<body>
    <%@ include file="/WEB-INF/views/module/header.jsp" %>
    <div class="main-layout-container">
        <!-- orgsidebar.jsp를 재사용하여 기존 사이드바를 포함 -->
        <jsp:include page="/WEB-INF/views/common/orgsidebar.jsp" />

        <div class="main-content">
            <div class="page-title-container">
                <h2>조직 상세페이지</h2>
                <div class="page-buttons">
                    <!-- '조직 편집' 버튼 클릭 시 모달 열기 함수 호출 -->
                    <button type="button" class="create-crew-btn" onclick="openModal('editOrgModal')">
                        <i class="fas fa-plus"></i> 조직 편집
                    </button>
                    <!-- '조직 삭제' 버튼 -->
                    <button type="button" class="delete-crew-btn" onclick="deleteOrg('${org.orId}')">
                        <i class="fas fa-trash"></i> 조직 삭제
                    </button>
                </div>
            </div>

            <div class="org-detail-form-container">
                <form id="orgDetailForm" action="#" method="post">
                    <!-- 그리드 레이아웃을 사용하여 폼 필드 배치 -->
                    <div class="form-grid-container">
                        <div class="form-group">
                            <label for="orgName">조직이름</label>
                            <input type="text" id="orgName" name="orgName" value="${org.orName}" readonly>
                        </div>
                        <div class="form-group">
                            <label for="orgManager">담당자</label>
                            <input type="text" id="orgManager" name="orgManager" value="${org.memberId}" readonly>
                        </div>
                        <div class="form-group">
                            <label for="orgIsPublic">공개 여부</label>
                            <select id="orgIsPublic" name="orgIsPublic" disabled>
                                <option value="공개" ${org.orIsPublic eq '공개' ? 'selected' : ''}>공개</option>
                                <option value="비공개" ${org.orIsPublic eq '비공개' ? 'selected' : ''}>비공개</option>
                            </select>
                        </div>
                        
                        <div class="form-group full-width">
                            <label for="crewIntro">조직 소개</label>
                            <textarea id="crewIntro" name="crewIntro" rows="4" readonly>${org.orIntroduce}</textarea>
                        </div>

                        <div class="form-group">
                            <label for="currentProject">진행중인 프로젝트</label>
                            <input type="text" id="currentProject" name="currentProject" value="${org.orProject}" readonly>
                        </div>
                        <div class="form-group">
                            <label for="completedProject">완료된 프로젝트</label>
                            <input type="text" id="completedProject" name="completedProject" value="${org.orProject}" readonly>
                        </div>
                        
                        <div class="form-group">
                            <label for="crewStartDate">조직 생성일</label>
                            <input type="text" id="crewStartDate" name="crewStartDate" value="${org.orCreateDate}" readonly>
                        </div>
                        <div class="form-group">
                            <label for="totalFunding">누적 펀딩 금액</label>
                            <input type="text" id="totalFunding" name="totalFunding" value="${org.totalDonation}원" readonly>
                        </div>

                        <div class="form-group">
                            <label for="teamMembers">팀원 현황</label>
                            <input type="text" id="teamMembers" name="teamMembers" value="${org.member}명" readonly>
                        </div>
                        <div class="form-group">
                            <label for="support">지원하러가기</label>
                            <input type="text" id="support" name="support" value="${org.orLinkAddress}" placeholder="여기에 지원 링크를 넣을 수 있습니다." readonly>
                        </div>
                    </div>
                </form>
            </div>
        </div>
    </div>
    
    <!-- 조직 편집 모달창 -->
    <div id="editOrgModal" class="modal">
        <h2>조직 편집</h2>
        <form id="editOrgForm">
            <div class="modal-form-grid">
                <div class="form-group">
                    <label for="modalOrgName">조직 이름</label>
                    <input type="text" id="modalOrgName" name="orgName" value="${org.orName}">
                </div>
                <div class="form-group">
                    <label for="modalOrgIsPublic">공개 여부</label>
                    <select id="modalOrgIsPublic" name="orgIsPublic">
                        <option value="공개" ${org.orIsPublic eq '공개' ? 'selected' : ''}>공개</option>
                        <option value="비공개" ${org.orIsPublic eq '비공개' ? 'selected' : ''}>비공개</option>
                    </select>
                </div>
                <div class="form-group full-width">
                    <label for="modalCrewIntro">조직 소개</label>
                    <textarea id="modalCrewIntro" name="crewIntro" rows="4">${org.orIntroduce}</textarea>
                </div>
            </div>
            <div class="modal-buttons">
                <button type="button" class="confirm-btn" onclick="saveChanges()">저장</button>
                <button type="button" class="cancel-btn" onclick="closeModal('editOrgModal')">취소</button>
            </div>
        </form>
    </div>
    <!-- 모달창 외부 영역을 클릭했을 때 모달을 닫기 위한 오버레이 -->
    <div id="modal-overlay" class="modal-overlay"></div>

    <%@ include file="/WEB-INF/views/module/footer.jsp" %>

    <script>
        var orgId = '${org.orId}';

        // 모달 열기
        function openModal(modalId) {
            const modal = document.getElementById(modalId);
            const overlay = document.getElementById('modal-overlay');

            // 기존 폼의 값들을 모달 폼으로 복사
            document.getElementById('modalOrgName').value = document.getElementById('orgName').value;
            document.getElementById('modalOrgIsPublic').value = document.getElementById('orgIsPublic').value;
            document.getElementById('modalCrewIntro').value = document.getElementById('crewIntro').value;

            modal.style.display = "block";
            overlay.style.display = "block";
            document.body.classList.add('modal-active');
        }
        
        // 모달 닫기
        function closeModal(modalId) {
            const modal = document.getElementById(modalId);
            const overlay = document.getElementById('modal-overlay');
            modal.style.display = "none";
            overlay.style.display = "none";
            document.body.classList.remove('modal-active');
        }

        // 변경 사항 저장 (이 부분은 AJAX 통신을 통해 서버에 데이터 전송)
        function saveChanges() {
            var newOrgName = document.getElementById('modalOrgName').value;
            var newOrgIsPublic = document.getElementById('modalOrgIsPublic').value;
            var newCrewIntro = document.getElementById('modalCrewIntro').value;

            // TODO: AJAX를 사용하여 서버에 데이터 업데이트 요청 보내기
            // 예시: $.ajax({
            //     url: '/org/update',
            //     method: 'POST',
            //     data: {
            //         orId: orgId,
            //         orName: newOrgName,
            //         orIsPublic: newOrgIsPublic,
            //         orIntroduce: newCrewIntro
            //     },
            //     success: function(response) {
            //         alert('조직 정보가 성공적으로 업데이트되었습니다.');
            //         location.reload();
            //     },
            //     error: function(error) {
            //         alert('업데이트 중 오류가 발생했습니다.');
            //     }
            // });

            alert('조직 ID: ' + orgId + '의 정보가 업데이트 요청되었습니다.');
            console.log('업데이트할 정보:', {
                orName: newOrgName,
                orIsPublic: newOrgIsPublic,
                orIntroduce: newCrewIntro
            });
            closeModal('editOrgModal');
        }

        // 조직 삭제 버튼 클릭 시 실행될 함수 (기존과 동일)
        function deleteOrg(orId) {
            if (confirm('정말로 이 조직을 삭제하시겠습니까?')) {
                var form = document.createElement('form');
                form.setAttribute('method', 'post');
                form.setAttribute('action', '${pageContext.request.contextPath}/org/delete');
                
                var hiddenField = document.createElement('input');
                hiddenField.setAttribute('type', 'hidden');
                hiddenField.setAttribute('name', 'orId');
                hiddenField.setAttribute('value', orId);
                
                form.appendChild(hiddenField);
                document.body.appendChild(form);
                
                alert('조직 ID: ' + orId + '에 대한 삭제 요청이 전송되었습니다.');
            }
        }
        
        // 사용자가 모달 외부를 클릭하면 모달 닫기 (오버레이 클릭 시)
        document.getElementById('modal-overlay').addEventListener('click', function() {
            closeModal('editOrgModal');
        });
    </script>
</body>
</html>
