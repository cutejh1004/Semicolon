package com.Semicolon.org.dto;

import java.sql.Date;
import java.util.List;

public class CreateOrgDTO {
    private int orId;
    private String orName;
    private String orCategory;
    private String orIntroduce;
    private String orIsPublic;
    private Date startDate;
    private String memberId; // 생성자 ID
    private String memberRole; // 생성자 역할
    
    private List<String> invitedMembers;

    // Getter와 Setter
    public int getOrId() { return orId; }
    public void setOrId(int orId) { this.orId = orId; }
    public String getOrName() { return orName; }
    public void setOrName(String orName) { this.orName = orName; }
    public String getOrCategory() { return orCategory; }
    public void setOrCategory(String orCategory) { this.orCategory = orCategory; }
    public String getOrIntroduce() { return orIntroduce; }
    public void setOrIntroduce(String orIntroduce) { this.orIntroduce = orIntroduce; }
    public String getOrIsPublic() { return orIsPublic; }
    public void setOrIsPublic(String orIsPublic) { this.orIsPublic = orIsPublic; }
    public Date getStartDate() { return startDate; }
    public void setStartDate(Date startDate) { this.startDate = startDate; }
    public String getMemberId() { return memberId; }
    public void setMemberId(String memberId) { this.memberId = memberId; }
    public String getMemberRole() { return memberRole; }
    public void setMemberRole(String memberRole) { this.memberRole = memberRole; }
    public List<String> getInvitedMembers() { return invitedMembers; }
    public void setInvitedMembers(List<String> invitedMembers) { this.invitedMembers = invitedMembers; }
}