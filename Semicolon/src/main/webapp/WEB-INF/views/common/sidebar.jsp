<%-- sidebar.jsp --%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<div class="sidebar-container">
    <ul class="sidebar-menu">
        <li><a href="${pageContext.request.contextPath}/main/tasklist"><i class="fas fa-tasks"></i> TASK</a></li>
        <li>
            <a href="${pageContext.request.contextPath}/main/issuelist">
                <i class="fas fa-bug"></i> ISSUE
            </a>
        </li>
        <li><a href="${pageContext.request.contextPath}/main/gantt"><i class="fas fa-chart-bar"></i> GANTT</a></li>
        <li>
            <a href="${pageContext.request.contextPath}/main/calendar">
                <i class="fas fa-calendar-alt"></i> CALENDAR
            </a>
        </li>
        <li><a href="#"><i class="fas fa-handshake"></i> MEETING</a></li>
        <li><a href="#"><i class="fas fa-chart-line"></i> BUDGET & PROGRESS</a></li>
        <li><a href="#"><i class="fas fa-file-alt"></i> REPORT</a></li>
        </ul>
</div>

<script>
    document.addEventListener('DOMContentLoaded', function() {
        var currentPath = window.location.pathname;
        var sidebarLinks = document.querySelectorAll('.sidebar-menu li');

        var calendarPath = '${pageContext.request.contextPath}/main/calendar';
        var issuePath = '${pageContext.request.contextPath}/main/issuelist';
        var taskPath = '${pageContext.request.contextPath}/main/tasklist';
        var ganttPath = '${pageContext.request.contextPath}/main/gantt';
        
        sidebarLinks.forEach(function(li) {
            var link = li.querySelector('a');
            if (link) {
                var href = link.getAttribute('href');
                if (href) {
                	if (currentPath.endsWith(href)) {
                        li.classList.add('active');
                    } else {
                        li.classList.remove('active');
                    }
                }
            }
        });
    });
</script>