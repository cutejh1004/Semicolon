package com.Semicolon.pms.dto;

import java.util.Date; // REGDATE, UPDATEDATE 컬럼을 위해 java.util.Date를 import 합니다.

public class ReplyDto {
    private int replyNumber;        // REPLYNUMBER
    private String bno;             // BNO (게시글 번호, 이슈 ID와 매핑)
    private String userId;          // USERID (댓글 작성자 ID)
    private String replyContent;    // REPLYCONTENT (댓글 내용)
    private Date regDate;           // REGDATE (등록일)
    private Date updateDate;        // UPDATEDATE (수정일)
    private String engId;           // ENG_ID
    private String groupId;         // GROUPID

    // 기본 생성자
    public ReplyDto() {
    }
    
    // Getter 및 Setter
    public int getReplyNumber() {
        return replyNumber;
    }

    public void setReplyNumber(int replyNumber) {
        this.replyNumber = replyNumber;
    }

    public String getBno() {
        return bno;
    }

    public void setBno(String bno) {
        this.bno = bno;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getReplyContent() {
        return replyContent;
    }

    public void setReplyContent(String replyContent) {
        this.replyContent = replyContent;
    }

    public Date getRegDate() {
        return regDate;
    }

    public void setRegDate(Date regDate) {
        this.regDate = regDate;
    }

    public Date getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }
    
    public String getEngId() {
        return engId;
    }

    public void setEngId(String engId) {
        this.engId = engId;
    }

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    // toString() 메소드 (선택 사항)
    @Override
    public String toString() {
        return "ReplyDto{" +
               "replyNumber=" + replyNumber +
               ", bno='" + bno + '\'' +
               ", userId='" + userId + '\'' +
               ", replyContent='" + replyContent + '\'' +
               ", regDate=" + regDate +
               ", updateDate=" + updateDate +
               ", engId='" + engId + '\'' +
               ", groupId='" + groupId + '\'' +
               '}';
    }
}