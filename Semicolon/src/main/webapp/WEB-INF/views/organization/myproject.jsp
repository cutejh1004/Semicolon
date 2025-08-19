<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>프로젝트 목록</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/common.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/issuelist.css">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.4/css/all.min.css">
</head>
<body>
    <%@ include file="/WEB-INF/views/module/header.jsp" %>

    <div class="main-layout-container">
        <jsp:include page="/WEB-INF/views/common/orgsidebar.jsp" />

        <div class="main-content">
            <h2 class="page-title">프로젝트</h2>

            <div class="content-area">
                <div class="issue-controls">
                    <div class="search-bar">
                        <i class="fas fa-search search-icon"></i>
                        <input type="text" id="searchInput" placeholder="프로젝트 검색" value="${searchQuery}">
                    </div>
                    <button class="create-issue-btn" onclick="openCreateProjectModal()">
                        <i class="fas fa-plus-circle"></i> 새 프로젝트
                    </button>
                </div>

                <div class="issue-list-container">
                    <table class="issue-table">
                        <thead>
                            <tr>
                                <th></th>
                                <th>프로젝트 제목</th>
                                <th>상태</th>
                                <th>담당자</th>
                                <th>시작일</th>
                                <th>종료일</th>
                            </tr>
                        </thead>
                        <tbody>
                            <c:choose>
                                <c:when test="${not empty projectList}">
                                    <c:forEach var="project" items="${projectList}">
                                        <c:set var="statusClass" value="${project.projectStatus.replace(' ', '')}" />
                                        <c:set var="statusBadgeClass" value="${project.projectStatus.replace(' ', '')}-badge" />
                                        <tr>
                                            <td><i class="status-icon fas fa-circle ${statusClass}"></i></td>
                                            <td>
                                                <a href="${pageContext.request.contextPath}/main/project/${project.projectId}" class="issue-title-link">
                                                    ${project.projectName}
                                                </a>
                                            </td>
                                            <td><span class="status-badge ${statusBadgeClass}">${project.projectStatus}</span></td>
                                            <td>${project.projectManagerId}</td>
                                            <td><fmt:formatDate value="${project.projectStartDate}" pattern="yyyy-MM-dd"/></td>
                                            <td><fmt:formatDate value="${project.projectEndDate}" pattern="yyyy-MM-dd"/></td>
                                        </tr>
                                    </c:forEach>
                                </c:when>
                                <c:otherwise>
                                    <tr><td colspan="6" style="text-align: center; padding: 20px;">참여 중인 프로젝트가 없습니다.</td></tr>
                                </c:otherwise>
                            </c:choose>
                        </tbody>
                    </table>
                </div>
            </div>
        </div>
    </div>

    <%-- New Project Creation Modal --%>
    <div id="createProjectModal" class="modal">
        <div class="modal-content">
            <h2>새 프로젝트 생성</h2>
            
            <label for="newProjectName">프로젝트 제목</label>
            <input type="text" id="newProjectName" placeholder="프로젝트 제목을 입력하세요">

            <label for="newProjectLogo">프로젝트 로고</label>
            <input type="file" id="newProjectLogo" accept="image/*">

            <label for="newProjectManagerId">담당자</label>
            <input type="text" id="newProjectManagerId" placeholder="담당자 ID를 입력하세요">

            <label for="newProjectStartDate">시작일</label>
            <input type="date" id="newProjectStartDate">

            <label for="newProjectEndDate">종료일</label>
            <input type="date" id="newProjectEndDate">

            <label for="newProjectRole">권한</label>
            <select id="newProjectRole">
                <option value="">선택</option>
                <option value="PM">PM (Project Manager)</option>
                <option value="Member">Member</option>
            </select>
        </div>
        <div class="modal-buttons">
            <button class="confirm-btn" onclick="addNewProject()">확인</button>
            <button class="cancel-btn" onclick="closeCreateProjectModal()">취소</button>
        </div>
    </div>
    <div id="modalOverlay" class="modal-overlay"></div>

    <%@ include file="/WEB-INF/views/module/footer.jsp" %>

    <script>
        function openCreateProjectModal() {
            document.getElementById('createProjectModal').style.display = 'block';
            document.getElementById('modalOverlay').style.display = 'block';
        }

        function closeCreateProjectModal() {
            document.getElementById('createProjectModal').style.display = 'none';
            document.getElementById('modalOverlay').style.display = 'none';
        }

        document.getElementById('modalOverlay').addEventListener('click', closeCreateProjectModal);

        const searchInput = document.getElementById('searchInput');
        searchInput.addEventListener('keypress', e => e.key === 'Enter' && performSearch());
        document.querySelector('.search-icon').addEventListener('click', performSearch);

        function performSearch() {
            const query = searchInput.value;
            window.location.href = '${pageContext.request.contextPath}/main/projectlist?search=' + encodeURIComponent(query);
        }

        function addNewProject() {
            const projectName = document.getElementById('newProjectName').value;
            const projectLogoInput = document.getElementById('newProjectLogo');
            const projectManagerId = document.getElementById('newProjectManagerId').value;
            const projectStartDate = document.getElementById('newProjectStartDate').value;
            const projectEndDate = document.getElementById('newProjectEndDate').value;
            const projectRole = document.getElementById('newProjectRole').value;

            if (!projectName || !projectManagerId || projectLogoInput.files.length === 0 || !projectStartDate || !projectEndDate || !projectRole) {
                alert('모든 필수 항목(제목, 로고, 담당자, 시작일, 종료일, 권한)을 입력해주세요.');
                return;
            }

            const formData = new FormData();
            formData.append('projectName', projectName);
            formData.append('projectLogo', projectLogoInput.files[0]);
            formData.append('projectManager', projectManagerId);
            formData.append('projectStartDate', projectStartDate);
            formData.append('projectEndDate', projectEndDate);
            formData.append('role', projectRole);
            
            fetch('${pageContext.request.contextPath}/main/project', {
                method: 'POST',
                body: formData // JSON.stringify가 아닌 FormData 객체를 전송
                // FormData 사용 시 'Content-Type' 헤더는 브라우저가 자동으로 설정하므로 명시하지 않습니다.
            })
            .then(response => {
                if (response.ok) {
                    return response.json();
                }
                return Promise.reject('서버 응답에 실패했습니다.');
            })
            .then(data => {
                alert(data.message || '프로젝트가 성공적으로 등록되었습니다.');
                location.reload();
            })
            .catch(error => {
                console.error('Error:', error);
                alert('프로젝트 등록 중 오류가 발생했습니다.');
            });
        }
    </script>
</body>
</html>