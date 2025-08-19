package com.Semicolon.org.dto;

import java.sql.Date;
import java.util.List;

public class ProjectOrgDTO {
    private int projectId;
    private String projectName;
    private String projectCategory;
    private String projectIntroduce;
    private String projectIsPublic;
    private Date projectCreateDate;
    private Date projectEndDate;
    private String projectLogo;
    
    // 생성자 및 멤버 정보
    private String memberId;
    private String memberRole;
    private List<String> invitedMembers;

    // Getters and Setters
    public int getProjectId() { return projectId; }
    public void setProjectId(int projectId) { this.projectId = projectId; }
    public String getProjectName() { return projectName; }
    public void setProjectName(String projectName) { this.projectName = projectName; }
    public String getProjectCategory() { return projectCategory; }
    public void setProjectCategory(String projectCategory) { this.projectCategory = projectCategory; }
    public String getProjectIntroduce() { return projectIntroduce; }
    public void setProjectIntroduce(String projectIntroduce) { this.projectIntroduce = projectIntroduce; }
    public String getProjectIsPublic() { return projectIsPublic; }
    public void setProjectIsPublic(String projectIsPublic) { this.projectIsPublic = projectIsPublic; }
    public Date getProjectCreateDate() { return projectCreateDate; }
    public void setProjectCreateDate(Date projectCreateDate) { this.projectCreateDate = projectCreateDate; }
    public Date getProjectEndDate() { return projectEndDate; }
    public void setProjectEndDate(Date projectEndDate) { this.projectEndDate = projectEndDate; }
    public String getProjectLogo() { return projectLogo; }
    public void setProjectLogo(String projectLogo) { this.projectLogo = projectLogo; }
    public String getMemberId() { return memberId; }
    public void setMemberId(String memberId) { this.memberId = memberId; }
    public String getMemberRole() { return memberRole; }
    public void setMemberRole(String memberRole) { this.memberRole = memberRole; }
    public List<String> getInvitedMembers() { return invitedMembers; }
    public void setInvitedMembers(List<String> invitedMembers) { this.invitedMembers = invitedMembers; }
}