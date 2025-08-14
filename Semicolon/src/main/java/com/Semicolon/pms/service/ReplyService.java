package com.Semicolon.pms.service;

import java.sql.SQLException;
import java.util.List;

import com.Semicolon.pms.dto.ReplyDto;

public interface ReplyService {
    List<ReplyDto> getRepliesByIssueId(String bno) throws SQLException;
    void addReply(ReplyDto replyDto) throws SQLException;
    void deleteReply(int replyNumber) throws SQLException;
    void deleteRepliesByIssueId(String bno) throws SQLException;
}