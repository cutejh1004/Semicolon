// src/main/java/com/Semicolon/pms/service/IssueReplyServiceImpl.java

package com.Semicolon.pms.service;

import java.util.List;
import com.Semicolon.pms.dao.IssueReplyDAO;
import com.Semicolon.pms.dto.IssueReplyDTO;

public class IssueReplyServiceImpl implements IssueReplyService {

    private IssueReplyDAO issueReplyDAO;
    public void setIssueReplyDAO(IssueReplyDAO issueReplyDAO) {
        this.issueReplyDAO = issueReplyDAO;
    }

    @Override
    public List<IssueReplyDTO> getReplyList(String issueId) throws Exception {
        return issueReplyDAO.selectReplyList(issueId);
    }

    @Override
    public void registerReply(IssueReplyDTO dto) throws Exception {
        issueReplyDAO.insertReply(dto);
    }

    @Override
    public void modifyReply(IssueReplyDTO dto) throws Exception {
        issueReplyDAO.updateReply(dto);
    }

    @Override
    public void removeReply(String replyNumber) throws Exception { // int -> String
        issueReplyDAO.deleteReply(replyNumber);
    }
}