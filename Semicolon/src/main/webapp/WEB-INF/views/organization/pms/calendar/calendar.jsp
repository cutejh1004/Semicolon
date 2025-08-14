<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>프로젝트 캘린더</title>
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/resources/css/common.css">
<link
	href='https://cdn.jsdelivr.net/npm/fullcalendar@6.1.18/main.min.css'
	rel='stylesheet' />


<link rel="stylesheet"
	href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.4/css/all.min.css">
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/resources/css/calendar.css">

<script
	src='https://cdn.jsdelivr.net/npm/fullcalendar@6.1.18/index.global.min.js'></script>

</head>
<body>
	<%@ include file="/WEB-INF/views/module/header.jsp"%>

	<div class="main-layout-container">
		<jsp:include page="/WEB-INF/views/common/sidebar.jsp" />

		<div class="main-content">
			<div class="page-header">
				<h2 class="page-title">캘린더</h2>
				<div class="action-buttons">
					<button class="btn btn-primary" onclick="openNewCalendarModal()">
						<i class="fas fa-plus"></i> 새 일정
					</button>
				</div>
			</div>

			<div id='calendar'></div>
		</div>
	</div>
	<%@ include file="/WEB-INF/views/module/footer.jsp"%>

	<div id="newCalendarModal" class="modal">
		<div class="modal-content">
			<h3>새 일정 생성</h3>
			<form id="newCalendarForm">
				<label for="new_title">일정 이름</label> <input type="text"
					id="new_title" name="calendarTitle" required> <label
					for="new_content">일정 설명</label>
				<textarea id="new_content" name="calendarContent" rows="3"></textarea>

				<label>기간 선택</label>
				<div class="date-range">
					<input type="date" id="new_start_date" name="calendarStartDate"
						required> ~ <input type="date" id="new_end_date"
						name="calendarEndDate" required>
				</div>

				<div class="modal-buttons">
					<button type="submit" class="confirm-btn">확인</button>
					<button type="button" class="cancel-btn"
						onclick="closeModal('newCalendarModal')">취소</button>
				</div>
			</form>
		</div>
	</div>

	<div id="detailCalendarModal" class="modal">
		<div class="modal-content">
			<h3>상세 일정</h3>
			<form id="detailCalendarForm">
				<input type="hidden" id="detail_id" name="calendarId"> <label
					for="detail_title">일정 이름</label> <input type="text"
					id="detail_title" name="calendarTitle" required> <label
					for="detail_content">일정 설명</label>
				<textarea id="detail_content" name="calendarContent" rows="3"></textarea>

				<label>기간 선택</label>
				<div class="date-range">
					<input type="date" id="detail_start_date" name="calendarStartDate"
						required> ~ <input type="date" id="detail_end_date"
						name="calendarEndDate" required>
				</div>

				<div class="modal-buttons">
					<button type="button" class="confirm-btn" id="updateCalendarBtn">수정</button>
					<button type="button" class="btn btn-danger" id="deleteCalendarBtn">삭제</button>
					<button type="button" class="cancel-btn"
						onclick="closeModal('detailCalendarModal')">취소</button>
				</div>
			</form>
		</div>
	</div>

	<div id="modalOverlay" class="modal-overlay"></div>

	<script>
    document.addEventListener('DOMContentLoaded', function() {
        var calendarEl = document.getElementById('calendar');

        // 1. 사용할 색상들을 배열로 정의
        var eventColors = ['#FAD291', '#B8E986', '#A1D9FF', '#FF9AA2', '#C7CEEA'];

        var calendar = new FullCalendar.Calendar(calendarEl, {
            initialView: 'dayGridMonth',
            locale: 'ko',
            headerToolbar: {
                left: 'prev,next today',
                center: 'title',
                right: ''
            },
            
            // 2. 이벤트 소스를 배열 대신 함수로 정의
            events: function(fetchInfo, successCallback, failureCallback) {
                fetch('${pageContext.request.contextPath}/main/calendar/all')
                .then(response => {
                    if (!response.ok) {
                        throw new Error('Network response was not ok');
                    }
                    return response.json();
                })
                .then(events => {
                    // 3. 서버에서 가져온 각 이벤트에 무작위 색상 적용
                    const coloredEvents = events.map(event => {
                        // 무작위로 색상 선택
                        const randomIndex = Math.floor(Math.random() * eventColors.length);
                        const randomColor = eventColors[randomIndex];
                        
                        // 이벤트 객체에 색상 속성 추가
                        return {
                            ...event, // 기존 이벤트 데이터 유지
                            backgroundColor: randomColor,
                            borderColor: randomColor // 선택 사항
                        };
                    });
                    successCallback(coloredEvents);
                })
                .catch(error => {
                    console.error('Error fetching events:', error);
                    failureCallback(error);
                });
            },
            
            // 일정 클릭 시 모달 띄우기
            eventClick: function(info) {
                // 서버에서 해당 일정의 상세 정보를 가져오는 로직
                fetch('${pageContext.request.contextPath}/main/calendar/' + info.event.id)
                .then(response => response.json())
                .then(data => {
                    document.getElementById('detail_id').value = data.calendarId;
                    document.getElementById('detail_title').value = data.calendarTitle;
                    document.getElementById('detail_content').value = data.calendarContent;
                    document.getElementById('detail_start_date').value = data.calendarStartDate;
                    document.getElementById('detail_end_date').value = data.calendarEndDate;
                    openModal('detailCalendarModal');
                });
            },
        });
        calendar.render();

        // 새 일정 생성 폼 제출 이벤트
        document.getElementById('newCalendarForm').addEventListener('submit', function(e) {
            e.preventDefault();
            const formData = new FormData(this);
            const data = Object.fromEntries(formData.entries());
            
            // TODO: projectId 값 설정 필요
            data.projectId = "P001"; // 임시 projectId

            fetch('${pageContext.request.contextPath}/main/calendar', {
                method: 'POST',
                headers: {'Content-Type': 'application/json'},
                body: JSON.stringify(data)
            })
            .then(response => response.json())
            .then(result => {
                alert(result.message);
                closeModal('newCalendarModal');
                calendar.refetchEvents(); // 캘린더 새로고침
            });
        });
        
        // 일정 수정 버튼 클릭 이벤트
        document.getElementById('updateCalendarBtn').addEventListener('click', function() {
            const form = document.getElementById('detailCalendarForm');
            const formData = new FormData(form);
            const data = Object.fromEntries(formData.entries());

            fetch('${pageContext.request.contextPath}/main/calendar/update', {
                method: 'PUT',
                headers: {'Content-Type': 'application/json'},
                body: JSON.stringify(data)
            })
            .then(response => response.json())
            .then(result => {
                alert(result.message);
                closeModal('detailCalendarModal');
                calendar.refetchEvents();
            });
        });
        
        // 일정 삭제 버튼 클릭 이벤트
        document.getElementById('deleteCalendarBtn').addEventListener('click', function() {
            if(confirm('정말로 이 일정을 삭제하시겠습니까?')) {
                const calendarId = document.getElementById('detail_id').value;
                fetch('${pageContext.request.contextPath}/main/calendar/' + calendarId, {
                    method: 'DELETE'
                })
                .then(response => response.json())
                .then(result => {
                    alert(result.message);
                    closeModal('detailCalendarModal');
                    calendar.refetchEvents();
                });
            }
        });
    });
    
    function openNewCalendarModal() {
        document.getElementById('newCalendarModal').style.display = 'block';
        document.getElementById('modalOverlay').style.display = 'block';
    }
    
    function openModal(modalId) {
        document.getElementById(modalId).style.display = 'block';
        document.getElementById('modalOverlay').style.display = 'block';
    }

    function closeModal(modalId) {
        document.getElementById(modalId).style.display = 'none';
        document.getElementById('modalOverlay').style.display = 'none';
    }
    
    document.getElementById('modalOverlay').addEventListener('click', function() {
        closeModal('newCalendarModal');
        closeModal('detailCalendarModal');
    });
</script>
	<!-- CREATE SEQUENCE CALENDAR_SEQ
		START WITH 1
		INCREMENT BY 1
		NOCACHE; sqldeveloper에서 시퀸스생성하기 필수 -->
</body>
</html>

