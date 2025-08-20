<%-- WEB-INF/views/organization/pms/gantt/ganttchart.jsp --%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>간트 차트</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/common.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/ganttchart.css"> <%-- 간트 차트 전용 CSS --%>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.4/css/all.min.css">
    
    <script src="https://cdn.dhtmlx.com/gantt/edge/dhtmlxgantt.js"></script>
    <link href="https://cdn.dhtmlx.com/gantt/edge/dhtmlxgantt.css" rel="stylesheet">

    <style>
        /* 간트 차트가 표시될 컨테이너의 크기 정의 */
        #gantt_here {
            width: 100%;
            height: calc(100vh - 200px); /* 헤더, 푸터, 여백 제외한 높이 */
            box-sizing: border-box;
            border: 1px solid #e0e0e0;
            border-radius: 8px;
            overflow: hidden;
        }
    </style>
</head>
<body>
    <%@ include file="/WEB-INF/views/module/header.jsp" %>

    <div class="main-layout-container">
        <jsp:include page="/WEB-INF/views/commons/sidebar.jsp" />

        <div class="main-content">
            <h2 class="page-title">간트 차트</h2>

            <div class="content-area">
                <div class="gantt-controls">
                    <button class="add-task-btn" onclick="openCreateGanttTaskModal()">
                        <i class="fas fa-plus-circle"></i> 간트 항목 추가
                    </button>
                </div>
                
                <div id="gantt_here"></div>
            </div>
        </div>
    </div>

<button class="add-task-btn" onclick="openCreateGanttTaskModal()">간트 항목 추가</button>

<div id="createGanttTaskModal" class="modal">
    <div class="modal-content">
        <span class="close-button" onclick="closeCreateGanttTaskModal()">&times;</span>
        <h2>새 간트 항목 추가</h2>
        <label for="ganttTitle">항목 제목:</label>
        <input type="text" id="ganttTitle" required><br><br>

        <label for="ganttTaskId">관련 일감 ID (선택):</label>
        <input type="text" id="ganttTaskId"><br><br>

        <label for="ganttManagerId">담당자 ID:</label>
        <input type="text" id="ganttManagerId" required><br><br>

        <label for="ganttStartDate">시작일:</label>
        <input type="date" id="ganttStartDate" required><br><br>

        <label for="ganttEndDate">종료일:</label>
        <input type="date" id="ganttEndDate" required><br><br>

        <button onclick="addNewGanttTask()">확인</button>
    </div>
</div>

<script>
    // 모달 열기 함수
    function openCreateGanttTaskModal() {
        document.getElementById('createGanttTaskModal').style.display = 'block';
    }

    // 모달 닫기 함수
    function closeCreateGanttTaskModal() {
        document.getElementById('createGanttTaskModal').style.display = 'none';
    }

    // 새 간트 항목 추가 함수 (가장 중요한 부분)
    function addNewGanttTask() {
        const title = document.getElementById('ganttTitle').value;
        const taskId = document.getElementById('ganttTaskId').value;
        const managerId = document.getElementById('ganttManagerId').value;
        const startDate = document.getElementById('ganttStartDate').value;
        const endDate = document.getElementById('ganttEndDate').value;

        // 필수 입력 필드 유효성 검사
        if (!title || !managerId || !startDate || !endDate) {
            alert('모든 필수 항목을 입력해주세요.');
            return;
        }

        const newGanttData = {
            ganttTitle: title,
            taskId: taskId, // 일감 ID는 선택 사항
            ganttManagerId: managerId,
            ganttStartDate: startDate,
            ganttEndDate: endDate
        };

        fetch('${pageContext.request.contextPath}/main/gantt/add', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify(newGanttData),
        })
        .then(response => response.json())
        .then(data => {
            if (data.success) {
                alert('새 간트 항목이 성공적으로 추가되었습니다.');
                closeCreateGanttTaskModal(); // 모달 닫기
                location.reload(); // 페이지 새로고침하여 간트 차트 갱신
            } else {
                alert('간트 항목 추가 실패: ' + (data.error || '알 수 없는 오류'));
            }
        })
        .catch((error) => {
            console.error('Error:', error);
            alert('간트 항목 추가 중 네트워크 오류가 발생했습니다.');
        });
    }
</script>
</body>
</html>