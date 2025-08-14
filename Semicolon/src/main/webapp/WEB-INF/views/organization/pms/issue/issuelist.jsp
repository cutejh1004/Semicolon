<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>이슈 목록</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/common.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/issuelist.css">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.4/css/all.min.css">
</head>
<body>
    <%@ include file="/WEB-INF/views/module/header.jsp" %>

    <div class="main-layout-container">
        <jsp:include page="/WEB-INF/views/common/sidebar.jsp" />

        <div class="main-content">
            <h2 class="page-title">이슈</h2>

            <div class="content-area">
                <div class="issue-controls">
                    <div class="search-bar">
                        <i class="fas fa-search search-icon"></i>
                        <input type="text" id="searchInput" placeholder="이슈 검색" value="${searchQuery}">
                    </div>
                    <button class="create-issue-btn" onclick="openCreateIssueModal()">
                        <i class="fas fa-plus-circle"></i> 새 이슈
                    </button>
                </div>

                <div class="issue-list-container">
                    <table class="issue-table">
                        <thead>
                            <tr>
                                <th></th>
                                <th>이슈 명</th>
                                <th>상태</th>
                                <th>우선순위</th>
                                <th>담당자</th>
                            </tr>
                        </thead>
                        <tbody>
                            <c:choose><c:when test="${not empty issueList}">
                                <c:forEach var="issue" items="${issueList}">
                                    <c:set var="statusClass" value="${issue.issueStatus.replace(' ', '')}" />
                                    <c:set var="statusBadgeClass" value="${issue.issueStatus.replace(' ', '')}-badge" />
                                    <tr>
                                        <td><i class="status-icon fas fa-circle ${statusClass}"></i></td>
                                        <td>
                                            <a href="${pageContext.request.contextPath}/main/issue/${issue.issueId}" class="issue-title-link">
                                                ${issue.issueTitle}
                                            </a>
                                        </td>
                                        <td><span class="status-badge ${statusBadgeClass}">${issue.issueStatus}</span></td>
                                        <td>${issue.issueUrgency}</td>
                                        <td>${issue.issueManagerId}</td>
                                    </tr>
                                </c:forEach>
                            </c:when><c:otherwise>
                                <tr><td colspan="5" style="text-align: center; padding: 20px;">등록된 이슈가 없습니다.</td></tr>
                            </c:otherwise></c:choose>
                        </tbody>
                    </table>
                </div>

            </div>
        </div>
    </div>

    <div id="createIssueModal" class="modal">
        <h2>새 이슈 생성</h2>
        <div class="modal-content">
            <label for="newIssueTitle">이슈 이름</label>
            <input type="text" id="newIssueTitle" placeholder="이슈 제목을 입력하세요">

            <label for="newIssueContent">이슈 설명</label>
            <textarea id="newIssueContent" placeholder="상세 설명을 입력하세요"></textarea>

            <label for="statusSelect">진행도</label>
            <select id="statusSelect">
                <option value="">선택</option>
                <option value="진행 중">진행 중</option>
                <option value="완료">완료</option>
                <option value="검토 중">검토 중</option>
                <option value="대기 중">대기 중</option>
            </select>

            <label for="urgencySelect">우선순위</label>
            <select id="urgencySelect">
                <option value="">선택</option>
                <option value="Critical">Critical</option>
                <option value="High">High</option>
                <option value="Moderate">Moderate</option>
                <option value="Minor">Minor</option>
            </select>

            <label for="taskNameSelect">일감이름</label>
            <select id="taskNameSelect">
                <option value="">선택</option>
                <option value="Task-알파">Task-알파</option>
                <option value="Task-베타">Task-베타</option>
                <option value="Task-감마">Task-감마</option>
                <option value="Task-델타">Task-델타</option>
            </select>
        </div>
        <div class="modal-buttons">
            <button class="confirm-btn" onclick="addNewIssue()">확인</button>
            <button class="cancel-btn" onclick="closeCreateIssueModal()">취소</button>
        </div>
    </div>
    <div id="modalOverlay" class="modal-overlay"></div>

    <%@ include file="/WEB-INF/views/module/footer.jsp" %>

    <script>
        function openCreateIssueModal() {
            document.getElementById('createIssueModal').style.display = 'block';
            document.getElementById('modalOverlay').style.display = 'block';
            document.body.classList.add('modal-active');
        }

        function closeCreateIssueModal() {
            document.getElementById('createIssueModal').style.display = 'none';
            document.getElementById('modalOverlay').style.display = 'none';
            document.body.classList.remove('modal-active');
        }

        document.getElementById('modalOverlay').addEventListener('click', closeCreateIssueModal);

        // 검색 기능 JavaScript
        const searchInput = document.getElementById('searchInput');
        
        searchInput.addEventListener('keypress', function(event) {
            if (event.key === 'Enter') {
                performSearch();
            }
        });
        
        document.querySelector('.search-bar .search-icon').addEventListener('click', function() {
            performSearch();
        });

        function performSearch() {
            const searchQuery = searchInput.value;
            window.location.href = '${pageContext.request.contextPath}/main/issuelist?search=' + encodeURIComponent(searchQuery);
        }

        document.addEventListener('DOMContentLoaded', function() {
            const urlParams = new URLSearchParams(window.location.search);
            const searchQuery = urlParams.get('search');
            if (searchQuery) {
                searchInput.value = searchQuery;
            }

            var currentPath = window.location.pathname;
            var sidebarLinks = document.querySelectorAll('.sidebar-menu li a');

            sidebarLinks.forEach(function(link) {
                var linkPath = link.getAttribute('href');
                if (linkPath && currentPath.includes('/main/issuelist')) {
                    if (linkPath.includes('ISSUE')) {
                        link.parentElement.classList.add('active');
                    }
                }
            });
        });

        // 새 이슈를 추가하는 함수 (수정)
        function addNewIssue() {
            const issueTitle = document.getElementById('newIssueTitle').value;
            const issueContent = document.getElementById('newIssueContent').value;
            const issueStatus = document.getElementById('statusSelect').value;
            const issueUrgency = document.getElementById('urgencySelect').value;
            const taskName = document.getElementById('taskNameSelect').value;
            const taskId = "TSK-더미";
            const issueManagerId = "mimi";

        // 이전에 하드코딩한 projectId 값을 추가
            const projectId = "PROJECT-001"; // 또는 동적으로 가져올 수 있는 값
            
            if (!issueTitle || !issueContent || !issueStatus || !issueUrgency || !taskName) {
                alert('모든 필드를 입력해주세요.');
                return;
            }

            const newIssue = {
                issueTitle: issueTitle,
                issueContent: issueContent,
                issueStatus: issueStatus,
                issueUrgency: issueUrgency,
                taskName: taskName,
                taskId: taskId,
                issueManagerId: issueManagerId,
                projectId: projectId,
                issueCreatorId: "user-001"
            };

            fetch('${pageContext.request.contextPath}/main/issuelist', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json'
                },
                body: JSON.stringify(newIssue)
            })
            .then(response => {
                if (!response.ok) {
                    throw new Error('서버 응답이 실패했습니다.');
                }
                return response.json();
            })
            .then(data => {
                console.log('이슈가 성공적으로 저장되었습니다:', data);
                alert(data.message);
                location.reload();
            })
            .catch(error => {
                console.error('이슈 등록 중 오류 발생:', error);
                alert('이슈 등록 중 오류가 발생했습니다. 콘솔을 확인해주세요.');
            });

            closeCreateIssueModal();
            document.getElementById('newIssueTitle').value = '';
            document.getElementById('newIssueContent').value = '';
            document.getElementById('statusSelect').value = '';
            document.getElementById('urgencySelect').value = '';
            document.getElementById('taskNameSelect').value = '';
        }
    </script>
</body>
</html>