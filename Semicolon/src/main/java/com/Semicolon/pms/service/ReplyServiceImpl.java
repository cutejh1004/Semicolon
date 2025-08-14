package com.Semicolon.pms.service;

import com.Semicolon.pms.dao.ReplyDAO;
import com.Semicolon.pms.dto.ReplyDto;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.List;

@Service
public class ReplyServiceImpl implements ReplyService {

    @Autowired
    private ReplyDAO replyDAO;

    @Override
    public List<ReplyDto> getRepliesByIssueId(String bno) throws SQLException {
        return replyDAO.getRepliesByIssueId(bno);
    }

    @Override
    public void addReply(ReplyDto replyDto) throws SQLException {
        replyDAO.insertReply(replyDto);
    }

    @Override
    public void deleteReply(int replyNumber) throws SQLException {
        replyDAO.deleteReply(replyNumber);
    }

    @Override
    public void deleteRepliesByIssueId(String bno) throws SQLException {
        replyDAO.deleteRepliesByIssueId(bno);
    }
}