package com.Semicolon.pms.dto;

import java.util.Date;

public class CalendarDto {
    private int calendarId;
    private String calendarTitle;
    private String projectId;
    private String calendarContent;
    private Date calendarStartDate;
    private Date calendarEndDate;
    private Date calendarRegDate;
    private Date calendarModifyDate;
    
    // Getters and Setters
    public int getCalendarId() { return calendarId; }
    public void setCalendarId(int calendarId) { this.calendarId = calendarId; }
    
    public String getCalendarTitle() { return calendarTitle; }
    public void setCalendarTitle(String calendarTitle) { this.calendarTitle = calendarTitle; }
    
    public String getProjectId() { return projectId; }
    public void setProjectId(String projectId) { this.projectId = projectId; }
    
    public String getCalendarContent() { return calendarContent; }
    public void setCalendarContent(String calendarContent) { this.calendarContent = calendarContent; }
    
    public Date getCalendarStartDate() { return calendarStartDate; }
    public void setCalendarStartDate(Date calendarStartDate) { this.calendarStartDate = calendarStartDate; }
    
    public Date getCalendarEndDate() { return calendarEndDate; }
    public void setCalendarEndDate(Date calendarEndDate) { this.calendarEndDate = calendarEndDate; }
    
    public Date getCalendarRegDate() { return calendarRegDate; }
    public void setCalendarRegDate(Date calendarRegDate) { this.calendarRegDate = calendarRegDate; }
    
    public Date getCalendarModifyDate() { return calendarModifyDate; }
    public void setCalendarModifyDate(Date calendarModifyDate) { this.calendarModifyDate = calendarModifyDate; }
}