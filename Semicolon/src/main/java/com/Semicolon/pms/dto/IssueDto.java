package com.Semicolon.pms.dto;

import java.util.Date;
import java.util.List; // List를 사용하기 위해 import 합니다.

public class IssueDto {
    private String issueId;
    private String projectId;
    private String issueTitle;
    private String issueContent;
    private String taskId;
    private String taskName;
    private String issueStatus;
    private String issueCreatorId;
    private Date issueRegDate;
    private Date issueModifyDate;
    private Date issueDeleteDate;
    private String issueManagerId;
    private String issueUrgency;
    
    // 이 부분에 댓글 목록 필드를 추가합니다.
    private List<ReplyDto> comments;

    // 기본 생성자
    public IssueDto() {
    }

    // 모든 필드를 포함하는 생성자
    public IssueDto(String issueId, String projectId, String issueTitle, String issueContent, String taskId, String taskName, String issueStatus, String issueCreatorId, Date issueRegDate, Date issueModifyDate, Date issueDeleteDate, String issueManagerId, String issueUrgency) {
        this.issueId = issueId;
        this.projectId = projectId;
        this.issueTitle = issueTitle;
        this.issueContent = issueContent;
        this.taskId = taskId;
        this.taskName = taskName;
        this.issueStatus = issueStatus;
        this.issueCreatorId = issueCreatorId;
        this.issueRegDate = issueRegDate;
        this.issueModifyDate = issueModifyDate;
        this.issueDeleteDate = issueDeleteDate;
        this.issueManagerId = issueManagerId;
        this.issueUrgency = issueUrgency;
    }

    // Getter와 Setter
    public String getIssueId() {
        return issueId;
    }

    public void setIssueId(String issueId) {
        this.issueId = issueId;
    }

    public String getProjectId() {
        return projectId;
    }

    public void setProjectId(String projectId) {
        this.projectId = projectId;
    }

    public String getIssueTitle() {
        return issueTitle;
    }

    public void setIssueTitle(String issueTitle) {
        this.issueTitle = issueTitle;
    }

    public String getIssueContent() {
        return issueContent;
    }

    public void setIssueContent(String issueContent) {
        this.issueContent = issueContent;
    }

    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }
    
    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public String getIssueStatus() {
        return issueStatus;
    }

    public void setIssueStatus(String issueStatus) {
        this.issueStatus = issueStatus;
    }

    public String getIssueCreatorId() {
        return issueCreatorId;
    }

    public void setIssueCreatorId(String issueCreatorId) {
        this.issueCreatorId = issueCreatorId;
    }

    public Date getIssueRegDate() {
        return issueRegDate;
    }

    public void setIssueRegDate(Date issueRegDate) {
        this.issueRegDate = issueRegDate;
    }

    public Date getIssueModifyDate() {
        return issueModifyDate;
    }

    public void setIssueModifyDate(Date issueModifyDate) {
        this.issueModifyDate = issueModifyDate;
    }

    public Date getIssueDeleteDate() {
        return issueDeleteDate;
    }

    public void setIssueDeleteDate(Date issueDeleteDate) {
        this.issueDeleteDate = issueDeleteDate;
    }

    public String getIssueManagerId() {
        return issueManagerId;
    }

    public void setIssueManagerId(String issueManagerId) {
        this.issueManagerId = issueManagerId;
    }

    public String getIssueUrgency() {
        return issueUrgency;
    }

    public void setIssueUrgency(String issueUrgency) {
        this.issueUrgency = issueUrgency;
    }

    // 새롭게 추가된 comments 필드에 대한 Getter와 Setter
    public List<ReplyDto> getComments() {
        return comments;
    }

    public void setComments(List<ReplyDto> comments) {
        this.comments = comments;
    }
}