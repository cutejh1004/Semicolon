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

    <%@ include file="/WEB-INF/views/module/footer.jsp" %>

    <%-- Google Charts 라이브러리 로드 --%>
    <script type="text/javascript" src="https://www.gstatic.com/charts/loader.js"></script>
    
    <script type="text/javascript">
        // Google Charts 라이브러리 로드 및 'gantt' 패키지 준비
        google.charts.load('current', {'packages':['gantt']});
        google.charts.setOnLoadCallback(drawChart);

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

            // JSP의 taskList 데이터를 JavaScript 배열로 변환
            // JSTL의 forEach를 사용하여 서버 사이드에서 데이터를 생성
            data.addRows([
                <c:forEach var="task" items="${taskList}" varStatus="status">
                    [
                        '${task.taskId}',      // Task ID
                        '${task.taskTitle}',   // Task Name
                        '${task.taskManagerId}',// Resource (담당자)
                        new Date('<fmt:formatDate value="${task.taskStartDate}" pattern="yyyy-MM-dd" />'), // Start Date
                        new Date('<fmt:formatDate value="${task.taskEndDate}" pattern="yyyy-MM-dd" />'),   // End Date
                        null, // Duration (null이면 자동 계산)
                        0,    // Percent Complete (여기서는 0으로 고정)
                        null  // Dependencies (종속성, 필요시 설정)
                    ]
                    <c:if test="${not status.last}">,</c:if> // 마지막 요소에는 쉼표(,)를 추가하지 않음
                </c:forEach>
            ]);

            // 간트 차트의 옵션 설정
            var options = {
                height: 500, // 차트의 높이
                gantt: {
                    trackHeight: 30,
                    barHeight: 20,
                    criticalPathEnabled: false, // 크리티컬 패스 비활성화
                    arrow: { // 종속성 화살표 스타일
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
                    // 요청 이미지의 색상 팔레트와 유사하게 설정
                    palette: [
                        { "color": "#f6c26a", "dark": "#b0884a", "light": "#fce0a7" }, // Research
                        { "color": "#ffd666", "dark": "#b39542", "light": "#ffe7a4" }, // Wireframe
                        { "color": "#93c47d", "dark": "#698a58", "light": "#b8d9ad" }, // Prototype
                        { "color": "#6fa8dc", "dark": "#4e769b", "light": "#a7c9e9" }, // Development
                        { "color": "#8e7cc3", "dark": "#645789", "light": "#b5a8d9" }, // Quality Assurance
                        { "color": "#c27ba0", "dark": "#895670", "light": "#d8a6c0" }  // Wrap-up
                    ]
                }
            };

            // 차트를 그릴 DOM 요소를 가져와서 chart 객체 생성
            var chart = new google.visualization.Gantt(document.getElementById('gantt_chart_div'));

            // 설정된 옵션과 데이터로 차트 그리기
            chart.draw(data, options);
        }
    </script>
</body>
</html>