package com.Semicolon.pms.dao;

import java.sql.SQLException;
import java.util.List;

import com.Semicolon.pms.dto.ReplyDto;

public interface ReplyDAO {
    // 이슈 ID(BNO)에 해당하는 댓글 목록을 조회하는 메서드
    List<ReplyDto> getRepliesByIssueId(String bno) throws SQLException;

    // 새 댓글을 등록하는 메서드
    void insertReply(ReplyDto reply) throws SQLException;

    // 특정 댓글을 삭제하는 메서드
    void deleteReply(int replyNumber) throws SQLException;

    // 이슈 ID(BNO)에 해당하는 모든 댓글을 삭제하는 메서드
    void deleteRepliesByIssueId(String bno) throws SQLException;
}