// src/main/java/com/Semicolon/pms/service/IssueReplyService.java

package com.Semicolon.pms.service;

import java.sql.SQLException;
import java.util.List;

import com.Semicolon.pms.dto.TaskReplyDTO;

public interface TaskReplyService {

    List<TaskReplyDTO> selectReplyList(String issueId) throws SQLException;
    void insertReply(TaskReplyDTO dto) throws SQLException;
    void updateReply(TaskReplyDTO dto) throws SQLException;
    void deleteReply(String replyNumber) throws SQLException;
}