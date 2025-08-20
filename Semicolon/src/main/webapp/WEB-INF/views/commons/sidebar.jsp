<%-- sidebar.jsp --%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<div class="sidebar-container">
    <ul class="sidebar-menu">
        <li><a href="${pageContext.request.contextPath}/main/project/{projectId}/tasklist"><i class="fas fa-tasks"></i> TASK</a></li>
        <li>
            <a id="issue-link" href="${pageContext.request.contextPath}/main/project/{projectId}/issuelist">
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
        <li><a href="${pageContext.request.contextPath}/main/budget">
                <i class="fas fa-chart-line"></i> BUDGET & PROGRESS</a></li>
        <li><a href="#"><i class="fas fa-file-alt"></i> REPORT</a></li>
        </ul>
</div>

<script>
    document.addEventListener('DOMContentLoaded', function() {
        const currentPath = window.location.pathname;
        const sidebarItems = document.querySelectorAll('.sidebar-menu li');

        sidebarItems.forEach(item => {
            const link = item.querySelector('a');
            if (!link) return;
            
            const href = link.getAttribute('href');
            if (!href) return;
            
            let isActive = false;

            if (href.includes('/tasklist') && currentPath.includes('/tasklist')) {
                isActive = true;
            } 
            else if (currentPath.startsWith(href)) {
                isActive = true;
            }
            
            if (href.includes('/issuelist') && currentPath.includes('/issuelist')) {
                isActive = true;
            } 
            else if (currentPath.startsWith(href)) {
                isActive = true;
            }

            if (isActive) {
                item.classList.add('active');
            } else {
                item.classList.remove('active');
            }
        });
    });
</script>

