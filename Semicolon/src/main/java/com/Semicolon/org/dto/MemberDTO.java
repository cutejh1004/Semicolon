package com.Semicolon.org.dto;

import java.sql.Timestamp;

// Engineer 테이블의 정보를 담는 DTO (Data Transfer Object)
public class MemberDTO {
    private int engId; // 엔지니어 ID (PK)
    private String userId; // 사용자 ID (Member 테이블의 ID와 연동)
    private String gitHubAddress; // 깃허브 주소
    private String major; // 전공
    private String career; // 경력
    private String historyProject; // 참여했던 프로젝트 이력
    private int orId; // 조직 ID

    public MemberDTO() {
    }

    public MemberDTO(int engId, String userId, String gitHubAddress, String major, String career, String historyProject, int orId) {
        this.engId = engId;
        this.userId = userId;
        this.gitHubAddress = gitHubAddress;
        this.major = major;
        this.career = career;
        this.historyProject = historyProject;
        this.orId = orId;
    }

    // Getter와 Setter
    public int getEngId() {
        return engId;
    }

    public void setEngId(int engId) {
        this.engId = engId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getGitHubAddress() {
        return gitHubAddress;
    }

    public void setGitHubAddress(String gitHubAddress) {
        this.gitHubAddress = gitHubAddress;
    }

    public String getMajor() {
        return major;
    }

    public void setMajor(String major) {
        this.major = major;
    }

    public String getCareer() {
        return career;
    }

    public void setCareer(String career) {
        this.career = career;
    }

    public String getHistoryProject() {
        return historyProject;
    }

    public void setHistoryProject(String historyProject) {
        this.historyProject = historyProject;
    }

    public int getOrId() {
        return orId;
    }

    public void setOrId(int orId) {
        this.orId = orId;
    }

    @Override
    public String toString() {
        return "EngineerDTO{" +
                "engId=" + engId +
                ", userId='" + userId + '\'' +
                ", gitHubAddress='" + gitHubAddress + '\'' +
                ", major='" + major + '\'' +
                ", career='" + career + '\'' +
                ", historyProject='" + historyProject + '\'' +
                ", orId=" + orId +
                '}';
    }
}
