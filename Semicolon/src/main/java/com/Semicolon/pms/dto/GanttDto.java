package com.Semicolon.pms.dto;

import java.util.Date;

public class GanttDto {
    private int ganttId;
    private int taskId; // 변경대상아이디
    private String ganttTitle;
    private String ganttManagerId;
    private Date ganttStartDate;
    private Date ganttEndDate;
    private Date ganttRegDate;
    private Date ganttModifyDate;
    private int projectId; // 프로젝트아이디

    // Getters and Setters
    public int getGanttId() { return ganttId; }
    public void setGanttId(int ganttId) { this.ganttId = ganttId; }

    public int getTaskId() { return taskId; }
    public void setTaskId(int taskId) { this.taskId = taskId; }

    public String getGanttTitle() { return ganttTitle; }
    public void setGanttTitle(String ganttTitle) { this.ganttTitle = ganttTitle; }

    public String getGanttManagerId() { return ganttManagerId; }
    public void setGanttManagerId(String ganttManagerId) { this.ganttManagerId = ganttManagerId; }

    public Date getGanttStartDate() { return ganttStartDate; }
    public void setGanttStartDate(Date ganttStartDate) { this.ganttStartDate = ganttStartDate; }

    public Date getGanttEndDate() { return ganttEndDate; }
    public void setGanttEndDate(Date ganttEndDate) { this.ganttEndDate = ganttEndDate; }

    public Date getGanttRegDate() { return ganttRegDate; }
    public void setGanttRegDate(Date ganttRegDate) { this.ganttRegDate = ganttRegDate; }

    public Date getGanttModifyDate() { return ganttModifyDate; }
    public void setGanttModifyDate(Date ganttModifyDate) { this.ganttModifyDate = ganttModifyDate; }

    public int getProjectId() { return projectId; }
    public void setProjectId(int projectId) { this.projectId = projectId; }
}