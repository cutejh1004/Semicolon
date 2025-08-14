<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>일감 목록</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/common.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/issuelist.css"> <%-- CSS 파일명은 재사용 --%>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.4/css/all.min.css">
</head>
<body>
    <%@ include file="/WEB-INF/views/module/header.jsp" %>

    <div class="main-layout-container">
        <jsp:include page="/WEB-INF/views/common/sidebar.jsp" />

        <div class="main-content">
            <h2 class="page-title">일감</h2>

            <div class="content-area">
                <div class="issue-controls">
                    <div class="search-bar">
                        <i class="fas fa-search search-icon"></i>
                        <input type="text" id="searchInput" placeholder="일감 검색" value="${searchQuery}">
                    </div>
                    <button class="create-issue-btn" onclick="openCreateTaskModal()">
                        <i class="fas fa-plus-circle"></i> 새 일감
                    </button>
                </div>

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
            </div>
        </div>
    </div>

    <%-- 새 일감 생성 모달 --%>
    <div id="createTaskModal" class="modal">
        <div class="modal-content">
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
        <div class="modal-buttons">
            <button class="confirm-btn" onclick="addNewTask()">확인</button>
            <button class="cancel-btn" onclick="closeCreateTaskModal()">취소</button>
        </div>
    </div>
    <div id="modalOverlay" class="modal-overlay"></div>

    <%@ include file="/WEB-INF/views/module/footer.jsp" %>

    <script>
        function openCreateTaskModal() {
            document.getElementById('createTaskModal').style.display = 'block';
            document.getElementById('modalOverlay').style.display = 'block';
        }

        function closeCreateTaskModal() {
            document.getElementById('createTaskModal').style.display = 'none';
            document.getElementById('modalOverlay').style.display = 'none';
        }

        document.getElementById('modalOverlay').addEventListener('click', closeCreateTaskModal);

        // 검색 기능
        const searchInput = document.getElementById('searchInput');
        searchInput.addEventListener('keypress', e => e.key === 'Enter' && performSearch());
        document.querySelector('.search-icon').addEventListener('click', performSearch);

        function performSearch() {
            const query = searchInput.value;
            window.location.href = '${pageContext.request.contextPath}/main/tasklist?search=' + encodeURIComponent(query);
        }

        // 새 일감 추가 함수
        function addNewTask() {
            const newTask = {
                // projectId는 세션 또는 다른 방식으로 가져와야 합니다. 여기서는 예시 값을 사용합니다.
                projectId: "PROJECT-001", 
                taskTitle: document.getElementById('newTaskTitle').value,
                taskDescription: document.getElementById('newTaskDescription').value,
                taskManagerId: document.getElementById('newTaskManagerId').value,
                taskStartDate: document.getElementById('newTaskStartDate').value,
                taskEndDate: document.getElementById('newTaskEndDate').value,
                taskStatus: document.getElementById('newStatusSelect').value,
                taskUrgency: document.getElementById('newUrgencySelect').value
            };

            if (!newTask.taskTitle || !newTask.taskManagerId || !newTask.taskStatus || !newTask.taskUrgency) {
                alert('제목, 담당자, 상태, 중요도는 필수 입력 항목입니다.');
                return;
            }

            fetch('${pageContext.request.contextPath}/main/task', { // POST URL 변경
                method: 'POST',
                headers: { 'Content-Type': 'application/json' },
                body: JSON.stringify(newTask)
            })
            .then(response => response.ok ? response.json() : Promise.reject('서버 응답 실패'))
            .then(data => {
                alert(data.message || '일감이 성공적으로 등록되었습니다.');
                location.reload();
            })
            .catch(error => {
                console.error('Error:', error);
                alert('일감 등록 중 오류가 발생했습니다.');
            });
        }
    </script>
</body>
</html>