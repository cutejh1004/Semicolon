<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Gantt Chart</title>
    <%-- 공통 CSS 및 폰트 --%>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/common.css">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.4/css/all.min.css">
    
    <%-- 간트 차트 페이지 전용 CSS --%>
    <style>
        .main-content {
            display: flex;
            flex-direction: column;
            width: 100%;
        }
        .page-header {
            display: flex;
            justify-content: space-between;
            align-items: center;
            margin-bottom: 20px;
        }
        .page-title {
            font-size: 24px;
            font-weight: bold;
        }
        .add-chart-btn {
            background-color: #4CAF50; /* 이미지의 버튼 색상과 유사하게 */
            color: white;
            padding: 10px 15px;
            border: none;
            border-radius: 5px;
            cursor: pointer;
            font-size: 14px;
        }
        .add-chart-btn .fa-plus {
            margin-right: 8px;
        }
        .gantt-chart-container {
            width: 100%;
            height: 600px; /* 차트 높이, 필요에 따라 조정 */
            overflow-x: auto; /* 가로 스크롤 추가 */
        }
        /* Google Chart의 기본 스타일을 일부 오버라이드 */
        .gantt-chart-container text {
            font-family: 'Arial', sans-serif; /* 폰트 적용 */
            font-size: 13px;
        }
        .modal {
        display: none; /* 기본적으로 숨김 */
        position: fixed; /* 페이지 위에 고정 */
        z-index: 1000; /* 다른 요소 위에 표시 (높게 설정) */
        left: 0;
        top: 0;
        width: 100%;
        height: 100%;
        overflow: auto; /* 내용이 넘칠 경우 스크롤 */
        background-color: rgba(0,0,0,0.4); /* 배경을 어둡게 */
        padding-top: 60px; /* 상단에서 조금 내려오도록 */
    }
    .modal-content {
        background-color: #fefefe;
        margin: 5% auto; /* 상단에서 5%, 가운데 정렬 */
        padding: 30px;
        border: 1px solid #888;
        width: 80%; /* 폭 설정 */
        max-width: 600px; /* 최대 폭 */
        border-radius: 10px;
        position: relative;
        box-shadow: 0 4px 8px 0 rgba(0,0,0,0.2), 0 6px 20px 0 rgba(0,0,0,0.19);
        animation-name: animatetop;
        animation-duration: 0.4s;
    }
    /* 모달 등장 애니메이션 */
    @keyframes animatetop {
        from {top: -300px; opacity: 0}
        to {top: 0; opacity: 1}
    }
    .close-button {
        color: #aaa;
        float: right;
        font-size: 32px;
        font-weight: bold;
        cursor: pointer;
        line-height: 1; /* x 버튼 중앙 정렬 */
    }
    .close-button:hover,
    .close-button:focus {
        color: black;
        text-decoration: none;
    }
    .modal-content h2 {
        margin-top: 0;
        margin-bottom: 20px;
        font-size: 22px;
        color: #333;
    }
    .modal-content form label {
        display: block;
        margin-bottom: 5px;
        font-weight: bold;
        color: #555;
    }
    .modal-content form input[type="text"],
    .modal-content form input[type="date"],
    .modal-content form input[type="number"] {
        width: calc(100% - 20px);
        padding: 10px;
        margin-bottom: 15px;
        border: 1px solid #ddd;
        border-radius: 4px;
        font-size: 16px;
    }
    .save-button, .cancel-button {
        background-color: #4CAF50;
        color: white;
        padding: 10px 20px;
        border: none;
        border-radius: 5px;
        cursor: pointer;
        font-size: 16px;
        margin-right: 10px;
    }
    .cancel-button {
        background-color: #f44336;
    }
    .save-button:hover {
        background-color: #45a049;
    }
    .cancel-button:hover {
        background-color: #da190b;
    }
    </style>
</head>
<body>
    <%@ include file="/WEB-INF/views/module/header.jsp" %>

    <div class="main-layout-container">
        <%-- 사이드바는 요청 이미지와 같이 GANTT 메뉴가 활성화되었다고 가정합니다. --%>
        <jsp:include page="/WEB-INF/views/common/sidebar.jsp" />

        <div class="main-content">
            <div class="page-header">
                <h2 class="page-title">GANTT</h2>
                <button class="add-chart-btn">
                    <i class="fas fa-plus"></i> 차트/일정 추가
                </button>
            </div>

            <%-- 간트 차트가 렌더링될 컨테이너 --%>
            <div id="gantt_chart_div" class="gantt-chart-container"></div>
        </div>
    </div>
<div id="addCalendarModal" class="modal">
    <div class="modal-content">
        <span class="close-button">&times;</span>
        <h2>새 일정 추가</h2>
        <form id="addCalendarForm">
            <label for="modalTaskTitle">제목:</label>
            <input type="text" id="modalTaskTitle" name="taskTitle" required><br><br>

            <label for="modalStartDate">시작일:</label>
            <input type="date" id="modalStartDate" name="startDate"><br><br>

            <label for="modalEndDate">종료일:</label>
            <input type="date" id="modalEndDate" name="endDate"><br><br>

            <label for="modalTaskManagerId">담당자 ID:</label>
            <input type="text" id="modalTaskManagerId" name="taskManagerId"><br><br>

            <label for="modalPercentComplete">완료율 (%):</label>
            <input type="number" id="modalPercentComplete" name="percentComplete" min="0" max="100" value="0"><br><br>
            
            <button type="submit" class="save-button">저장</button>
            <button type="button" class="cancel-button">취소</button>
        </form>
    </div>
</div>
    <%@ include file="/WEB-INF/views/module/footer.jsp" %>

    <%-- Google Charts 라이브러리 로드 --%>
    <script type="text/javascript" src="https://www.gstatic.com/charts/loader.js"></script>
    
<script type="text/javascript">
    // Google Charts 라이브러리 로드 및 'gantt' 패키지 준비
    google.charts.load('current', {'packages':['gantt']});
    google.charts.setOnLoadCallback(drawChart);

    // 문서가 완전히 로드된 후 실행될 코드
    document.addEventListener('DOMContentLoaded', function() {
        const addChartBtn = document.querySelector('.add-chart-btn');
        const addModal = document.getElementById('addCalendarModal');
        const closeModalBtn = document.querySelector('.close-button');
        const cancelButton = addModal.querySelector('.cancel-button'); // 취소 버튼 추가
        const addCalendarForm = document.getElementById('addCalendarForm');

        // "차트/일정 추가" 버튼 클릭 이벤트
        if (addChartBtn) {
            addChartBtn.addEventListener('click', function() {
                addModal.style.display = 'block'; // 모달 열기
                // 모달이 열릴 때 폼 필드 초기화 (선택 사항)
                addCalendarForm.reset(); 
            });
        } else {
            console.error("'.add-chart-btn' 요소를 찾을 수 없습니다.");
        }

        // 모달 닫기 버튼 (x) 클릭 이벤트
        if (closeModalBtn) {
            closeModalBtn.addEventListener('click', function() {
                addModal.style.display = 'none'; // 모달 닫기
            });
        }
        
        // 모달 내의 "취소" 버튼 클릭 이벤트
        if (cancelButton) {
            cancelButton.addEventListener('click', function() {
                addModal.style.display = 'none'; // 모달 닫기
            });
        }

        // 모달 외부 클릭 시 닫기
        window.addEventListener('click', function(event) {
            if (event.target == addModal) {
                addModal.style.display = 'none';
            }
        });

        // 폼 제출 이벤트 처리 (예시: AJAX를 통해 서버로 데이터 전송)
        if (addCalendarForm) {
            addCalendarForm.addEventListener('submit', function(event) {
                event.preventDefault(); // 기본 폼 제출 동작 방지

                // 폼 데이터 가져오기
                const taskTitle = document.getElementById('modalTaskTitle').value;
                const startDate = document.getElementById('modalStartDate').value;
                const endDate = document.getElementById('modalEndDate').value;
                const taskManagerId = document.getElementById('modalTaskManagerId').value;
                const percentComplete = parseInt(document.getElementById('modalPercentComplete').value);

                // 간단한 유효성 검사 (필요에 따라 더 복잡하게 구현)
                if (!taskTitle || !startDate || !endDate) {
                    alert('제목, 시작일, 종료일은 필수 입력 사항입니다.');
                    return;
                }

                // 여기에 서버로 데이터를 전송하는 AJAX 요청 로직을 구현합니다.
                // 예시: fetch API 사용 (최신 웹 표준)
                // 실제 엔드포인트와 요청 방식 (POST 등)에 맞게 수정해야 합니다.
                fetch('${pageContext.request.contextPath}/api/addTask', { // 예시: 실제 API 엔드포인트로 변경 필요
                    method: 'POST',
                    headers: {
                        'Content-Type': 'application/json'
                    },
                    body: JSON.stringify({
                        taskTitle: taskTitle,
                        taskStartDate: startDate,
                        taskEndDate: endDate,
                        taskManagerId: taskManagerId,
                        percentComplete: percentComplete
                        // taskId는 서버에서 생성될 것입니다.
                        // dependencies, resource 등 다른 필드도 필요하면 추가
                    })
                })
                .then(response => {
                    if (!response.ok) {
                        return response.json().then(error => { throw new Error(error.message || '서버 오류 발생'); });
                    }
                    return response.json();
                })
                .then(data => {
                    alert('일정이 성공적으로 추가되었습니다! (ID: ' + data.taskId + ')'); // 서버 응답에 따라 메시지 변경
                    addModal.style.display = 'none'; // 모달 닫기
                    // 차트를 새로고침하여 추가된 일정을 반영합니다.
                    // 실제 애플리케이션에서는 서버에서 taskList를 다시 가져와야 합니다.
                    // 여기서는 간단히 drawChart()를 다시 호출하지만,
                    // 이는 전체 데이터를 다시 로드하는 비효율적인 방법일 수 있습니다.
                    // 실제로는 서버에서 업데이트된 taskList를 JSON으로 받아와서 data.addRows()를 통해 추가하는 것이 좋습니다.
                    location.reload(); // 페이지 전체를 새로고침하여 데이터 다시 로드 (간단한 해결책)
                })
                .catch(error => {
                    console.error('일정 추가 중 오류 발생:', error);
                    alert('일정 추가에 실패했습니다: ' + error.message);
                });
            });
        }
    });


    function drawChart() {
        var data = new google.visualization.DataTable();
        // 데이터 테이블의 컬럼 정의
        data.addColumn('string', 'Task ID');
        data.addColumn('string', 'Task Name');
        data.addColumn('string', 'Resource'); // 담당자 또는 리소스
        data.addColumn('date', 'Start Date');
        data.addColumn('date', 'End Date');
        data.addColumn('number', 'Duration');
        data.addColumn('number', 'Percent Complete');
        data.addColumn('string', 'Dependencies');

        var rows = [];

        // taskList가 비어있는지 확인
        <c:if test="${empty taskList}">
            console.log("taskList가 비어있습니다. 더미 데이터를 추가하여 날짜 축을 표시합니다.");
            var today = new Date();
            var dummyStartDate = new Date(today.getFullYear(), today.getMonth(), 1);
            var dummyEndDate = new Date(today.getFullYear(), today.getMonth() + 1, 0);

            rows.push([
                'dummyTask',          
                ' ',                  
                null,                 
                dummyStartDate,       
                dummyEndDate,         
                null,                 
                0,                    
                null                  
            ]);
        </c:if>

        // 실제 taskList 데이터를 JavaScript 배열로 변환
        <c:forEach var="task" items="${taskList}" varStatus="status">
            var startDate;
            <c:choose>
                <c:when test="${task.taskStartDate ne null}">
                    startDate = new Date('<fmt:formatDate value="${task.taskStartDate}" pattern="yyyy-MM-dd" />');
                </c:when>
                <c:otherwise>
                    startDate = null;
                </c:otherwise>
            </c:choose>

            var endDate;
            <c:choose>
                <c:when test="${task.taskEndDate ne null}">
                    endDate = new Date('<fmt:formatDate value="${task.taskEndDate}" pattern="yyyy-MM-dd" />');
                </c:when>
                <c:otherwise>
                    endDate = null;
                </c:otherwise>
            </c:choose>

            var percentComplete;
            <c:choose>
                <c:when test="${task.taskStatus eq 'COMPLETED'}">
                    percentComplete = 100;
                </c:when>
                <c:otherwise>
                    percentComplete = 0;
                </c:otherwise>
            </c:choose>
            
            rows.push([
                '${task.taskId}',
                '${task.taskTitle}',
                '${task.taskManagerId}',
                startDate,
                endDate,
                null,
                percentComplete,
                null
            ]);
        </c:forEach>
        
        console.log("최종 차트 데이터 행 수:", rows.length);
        data.addRows(rows);
        
        var options = {
            height: 500,
            gantt: {
                trackHeight: 30,
                barHeight: 20,
                criticalPathEnabled: false,
                arrow: {
                    angle: 100,
                    width: 2,
                    color: '#777',
                    radius: 0
                },
                labelStyle: {
                    fontName: 'Arial',
                    fontSize: 12,
                    color: '#757575'
                },
                palette: [
                    { "color": "#f6c26a", "dark": "#b0884a", "light": "#fce0a7" },
                    { "color": "#ffd666", "dark": "#b39542", "light": "#ffe7a4" },
                    { "color": "#93c47d", "dark": "#698a58", "light": "#b8d9ad" },
                    { "color": "#6fa8dc", "dark": "#4e769b", "light": "#a7c9e9" },
                    { "color": "#8e7cc3", "dark": "#645789", "light": "#b5a8d9" },
                    { "color": "#c27ba0", "dark": "#895670", "light": "#d8a6c0" }
                ]
            }
        };

        var chart = new google.visualization.Gantt(document.getElementById('gantt_chart_div'));
        chart.draw(data, options);
    }
</script>
</body>
</html>