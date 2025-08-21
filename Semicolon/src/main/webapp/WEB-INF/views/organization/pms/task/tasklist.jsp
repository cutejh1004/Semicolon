<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>일감 목록</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/common.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/tasklist.css"> <%-- CSS 파일명은 재사용 --%>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.4/css/all.min.css">
</head>
<body>
    <%@ include file="/WEB-INF/views/module/header.jsp" %>

    <div class="main-layout-container">
        <jsp:include page="/WEB-INF/views/commons/sidebar.jsp" />

        <div class="main-content">
            <h2 class="page-title">일감</h2>

            <div class="content-area">
                <%-- ▼▼▼▼▼ 수정된 부분 ▼▼▼▼▼ --%>
                <form id="searchForm" action="${pageContext.request.contextPath}/main/tasklist" method="GET">
                    <input type='hidden' name="page" value="${pageMaker.page}" />
                    <input type='hidden' name="perPageNum" value="${pageMaker.perPageNum}" />
                    <input type='hidden' name="searchType" value="${pageMaker.searchType}" />
                    <input type='hidden' name="keyword" value="${pageMaker.keyword}" />

                    <div class="issue-controls">
                        <div class="search-bar">
                            <i class="fas fa-search search-icon"></i>
                            <input type="text" name="keyword" id="searchInput" placeholder="일감 검색" value="${pageMaker.keyword}">
                        </div>
                        <button type="button" class="create-issue-btn" onclick="openCreateTaskModal()">
                            <i class="fas fa-plus-circle"></i> 새 일감
                        </button>
                    </div>
                </form>
                <%-- ▲▲▲▲▲ 수정된 부분 ▲▲▲▲▲ --%>

                <div class="issue-list-container">
                    <table class="issue-table">
                        <thead>
                            <tr>
                                <th></th>
                                <th>일감 제목</th>
                                <th>상태</th>
                                <th>중요도</th>
                                <th>담당자</th>
                                <th>시작일</th>
                                <th>종료일</th>
                            </tr>
                        </thead>
                        <tbody>
                            <c:choose>
                                <c:when test="${not empty taskList}">
                                    <c:forEach var="task" items="${taskList}">
                                        <c:set var="statusClass" value="${task.taskStatus.replace(' ', '')}" />
                                        <c:set var="statusBadgeClass" value="${task.taskStatus.replace(' ', '')}-badge" />
                                        <tr>
                                            <td><i class="status-icon fas fa-circle ${statusClass}"></i></td>
                                            <td>
                                                <a href="${pageContext.request.contextPath}/main/task/${task.taskId}" class="issue-title-link">
                                                    ${task.taskTitle}
                                                </a>
                                            </td>
                                            <td><span class="status-badge ${statusBadgeClass}">${task.taskStatus}</span></td>
                                            <td>${task.taskUrgency}</td>
                                            <td>${task.taskManagerId}</td>
                                            <td>${task.taskStartDate}</td>
                                            <td>${task.taskEndDate}</td>
                                        </tr>
                                    </c:forEach>
                                </c:when>
                                <c:otherwise>
                                    <tr><td colspan="7" style="text-align: center; padding: 20px;">등록된 일감이 없습니다.</td></tr>
                                </c:otherwise>
                            </c:choose>
                        </tbody>
                    </table>
                </div>
                
                <%-- 페이지네이션 모듈 포함 (pageMaker 객체가 컨트롤러로부터 전달되어야 함) --%>
                <%@ include file="/WEB-INF/views/module/pagination.jsp" %>
            </div>
        </div>
    </div>

    <%-- 새 일감 생성 모달 --%>
    <div id="createTaskModal" class="custom-modal">
        <div class="custom-modal-content">
            <h2>새 일감 생성</h2>
            <label for="newTaskTitle">일감 제목</label>
            <input type="text" id="newTaskTitle" placeholder="일감 제목을 입력하세요">

            <label for="newTaskDescription">일감 설명</label>
            <textarea id="newTaskDescription" placeholder="상세 설명을 입력하세요"></textarea>

            <label for="newTaskManagerId">담당자</label>
            <input type="text" id="newTaskManagerId" placeholder="담당자 ID를 입력하세요">

            <label for="newTaskStartDate">시작일</label>
            <input type="date" id="newTaskStartDate">

            <label for="newTaskEndDate">종료일</label>
            <input type="date" id="newTaskEndDate">

            <label for="newStatusSelect">진행 상태</label>
            <select id="newStatusSelect">
                <option value="">선택</option>
                <option value="진행 중">진행 중</option>
                <option value="완료">완료</option>
                <option value="검토 중">검토 중</option>
                <option value="대기 중">대기 중</option>
            </select>

            <label for="newUrgencySelect">중요도</label>
            <select id="newUrgencySelect">
                <option value="">선택</option>
                <option value="Critical">Critical</option>
                <option value="High">High</option>
                <option value="Moderate">Moderate</option>
                <option value="Minor">Minor</option>
            </select>
        </div>
        <div class="custom-modal-buttons">
            <button class="custom-confirm-btn" onclick="addNewTask()">확인</button>
            <button class="custom-cancel-btn" onclick="closeCreateTaskModal()">취소</button>
        </div>
    </div>
    <div id="modalOverlay" class="custom-modal-overlay"></div>

    <%@ include file="/WEB-INF/views/module/footer.jsp" %>

    <script>
    	const currentProjectId = "${projectId}"; 
    
    	function openCreateTaskModal() {
    	    console.log('Attempting to open modal...'); // Add this line
    	    const taskModal = document.getElementById('createTaskModal');
    	    const modalOverlay = document.getElementById('modalOverlay');

    	    if (taskModal && modalOverlay) {
    	        taskModal.style.display = 'block';
    	        modalOverlay.style.display = 'block';
    	        document.body.classList.add('modal-active');
    	        console.log('Modal and overlay display set to block. Body class added.'); // Add this line
    	    } else {
    	        console.error('Error: createTaskModal or modalOverlay element not found!'); // Add this line
    	    }
    	}

    	function closeCreateTaskModal() {
    	    console.log('closeCreateTaskModal called!'); // Add this
    	    document.getElementById('createTaskModal').style.display = 'none';
    	    document.getElementById('modalOverlay').style.display = 'none';
    	    document.body.classList.remove('modal-active');
    	}

        document.getElementById('modalOverlay').addEventListener('click', closeCreateTaskModal);

        // ▼▼▼▼▼ 수정된 부분 ▼▼▼▼▼
        const searchForm = document.getElementById('searchForm');
        const searchInput = document.getElementById('searchInput');

        // 검색 아이콘 클릭 시
        document.querySelector('.search-icon').addEventListener('click', () => search_list(1));

        // Enter 키 입력 시
        searchInput.addEventListener('keypress', function(event) {
        	if (event.key === 'Enter') {
            	search_list(1);
        	}
    	});
    
    	document.querySelector('.search-bar .search-icon').addEventListener('click', function() {
        	search_list(1);
    	});

        // 검색 실행 함수
        function search_list(page) {
        	jobForm.searchQuery.value = searchInput.value;
            jobForm.page.value = page;
            jobForm.submit();
        }

        // 사이드바 활성화
        document.addEventListener('DOMContentLoaded', function() {
            const currentPath = window.location.pathname;
            const sidebarLinks = document.querySelectorAll('.sidebar-menu li a');

            sidebarLinks.forEach(function(link) {
                const linkPath = link.getAttribute('href');
                if (linkPath && currentPath.includes('/main/project')) {
                    if (linkPath.includes('tasklist')) { // URL에 'task'가 포함된 경우
                        link.parentElement.classList.add('active');
                    }
                }
            });
        });

        function addNewTask() {
        	const taskTitle = document.getElementById('newTaskTitle').value;
            const taskDescription = document.getElementById('newTaskDescription').value;
            const taskManagerId = document.getElementById('newTaskManagerId').value;
            const taskStartDate = document.getElementById('newTaskStartDate').value;
            const taskEndDate = document.getElementById('newTaskEndDate').value;
            const taskStatus = document.getElementById('newStatusSelect').value;
            const taskUrgency = document.getElementById('newUrgencySelect').value;
        	
        	const projectId = currentProjectId;
        	
            if (!newTask.taskTitle || !newTask.taskManagerId || !newTask.taskStatus || !newTask.taskUrgency) {
                alert('제목, 담당자, 상태, 중요도는 필수 입력 항목입니다.');
                return;
            }

            const newTask = {
                    taskTitle: taskTitle,
                    taskDescription: taskDescription,
                    taskManagerId: taskManagerId,
                    taskStartDate: taskStartDate,
                    taskEndDate: taskEndDate,
                    [cite_start]taskStatus: taskStatus, [cite: 43]
                    taskUrgency: taskUrgency,
                    projectId: projectId
                };

            if (!newTask.taskTitle || !newTask.taskManagerId || !newTask.taskStatus || !newTask.taskUrgency) {
                alert('제목, 담당자, 상태, 중요도는 필수 입력 항목입니다.');
                [cite_start]return; [cite: 42]
            }
            
            [cite_start]const postUrl = `${pageContext.request.contextPath}/main/project/${projectId}/tasklist`; [cite: 44]
            
            fetch(postUrl, {
                method: 'POST',
                headers: { 'Content-Type': 'application/json' },
                body: JSON.stringify(newTask)
            [cite_start]}) [cite: 45]
            .then(response => {
                if (!response.ok) {
                    if (response.status === 404) {
                        [cite_start]throw new Error('404 (Not Found): 요청된 URL을 찾을 수 없습니다. URL 경로를 확인해주세요.'); [cite: 45, 46]
                    }
                    throw new Error(`서버 응답이 실패했습니다. 상태 코드: ${response.status}`);
                }
                return response.json();
            })
            [cite_start].then(data => { [cite: 47]
                console.log('일감이 성공적으로 등록되었습니다.', data);
                if(data.message) { // 성공 메시지가 있을 경우
                    alert(data.message);
                    closeCreateTaskModal();
                    [cite_start]location.reload(); [cite: 48]
                } else { // 오류 메시지가 있을 경우
                    [cite_start]alert(data.error || '알 수 없는 오류가 발생했습니다.'); [cite: 49]
                }
            })
            .catch(error => {
                console.error('일감 등록 중 오류 발생:', error);
                alert(`일감 등록 중 오류가 발생했습니다: ${error.message}`);
                closeCreateTaskModal();
            [cite_start]}); [cite: 50]
        }
     </script>
 </body>
 </html>