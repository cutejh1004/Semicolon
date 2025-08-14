package com.Semicolon.org.dto;

import java.sql.Date;

// OrgDetailDTO는 ORG 테이블의 데이터를 담는 객체입니다.
public class OrgDetailDTO {

    private String orId; 
    private String orName;
    private String orCategory;
    private String orIsPublic;
    private String orIntroduce;
    private String orProject;
    private Date orCreateDate;
    private int totalDonation;
    private int donation;
    private int member;
    private String memberId;
    private String memberRole;
    private String memberStatus;
    private String orLinkAddress; // DB 컬럼명과 동일하게 필드명 수정

    // 기본 생성자
    public OrgDetailDTO() {
    }

    // 모든 필드를 포함하는 생성자
    public OrgDetailDTO(String orId, String orName, String orCategory, String orIsPublic,
                       String orIntroduce, String orProject, Date orCreateDate,
                       int totalDonation, int donation, int member, String memberId,
                       String memberRole, String memberStatus, String orLinkAddress) {
        this.orId = orId;
        this.orName = orName;
        this.orCategory = orCategory;
        this.orIsPublic = orIsPublic;
        this.orIntroduce = orIntroduce;
        this.orProject = orProject;
        this.orCreateDate = orCreateDate;
        this.totalDonation = totalDonation;
        this.donation = donation;
        this.member = member;
        this.memberId = memberId;
        this.memberRole = memberRole;
        this.memberStatus = memberStatus;
        this.orLinkAddress = orLinkAddress;
    }

    // Getter and Setter
    public String getOrId() { return orId; }
    public void setOrId(String orId) { this.orId = orId; }

    public String getOrName() { return orName; }
    public void setOrName(String orName) { this.orName = orName; }

    public String getOrCategory() { return orCategory; }
    public void setOrCategory(String orCategory) { this.orCategory = orCategory; }

    public String getOrIsPublic() { return orIsPublic; }
    public void setOrIsPublic(String orIsPublic) { this.orIsPublic = orIsPublic; }

    public String getOrIntroduce() { return orIntroduce; }
    public void setOrIntroduce(String orIntroduce) { this.orIntroduce = orIntroduce; }

    public String getOrProject() { return orProject; }
    public void setOrProject(String orProject) { this.orProject = orProject; }

    public Date getOrCreateDate() { return orCreateDate; }
    public void setOrCreateDate(Date orCreateDate) { this.orCreateDate = orCreateDate; }

    public int getTotalDonation() { return totalDonation; }
    public void setTotalDonation(int totalDonation) { this.totalDonation = totalDonation; }
    
    public int getDonation() { return donation; }
    public void setDonation(int donation) { this.donation = donation; }

    public int getMember() { return member; }
    public void setMember(int member) { this.member = member; }
    
    public String getMemberId() { return memberId; }
    public void setMemberId(String memberId) { this.memberId = memberId; }
    
    public String getMemberRole() { return memberRole; }
    public void setMemberRole(String memberRole) { this.memberRole = memberRole; }
    
    public String getMemberStatus() { return memberStatus; }
    public void setMemberStatus(String memberStatus) { this.memberStatus = memberStatus; }

    public String getOrLinkAddress() { return orLinkAddress; } // Getter/Setter 이름 수정
    public void setOrLinkAddress(String orLinkAddress) { this.orLinkAddress = orLinkAddress; } // Getter/Setter 이름 수정

    @Override
    public String toString() {
        return "OrgDetailDTO{" +
               "orId='" + orId + '\'' +
               ", orName='" + orName + '\'' +
               ", orCategory='" + orCategory + '\'' +
               ", orIsPublic='" + orIsPublic + '\'' +
               ", orIntroduce='" + orIntroduce + '\'' +
               ", orProject='" + orProject + '\'' +
               ", orCreateDate=" + orCreateDate +
               ", totalDonation=" + totalDonation +
               ", donation=" + donation +
               ", member=" + member +
               ", memberId='" + memberId + '\'' +
               ", memberRole='" + memberRole + '\'' +
               ", memberStatus='" + memberStatus + '\'' +
               ", orLinkAddress='" + orLinkAddress + '\'' +
               '}';
    }
}
