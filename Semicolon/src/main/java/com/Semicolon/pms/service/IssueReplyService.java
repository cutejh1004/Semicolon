// src/main/java/com/Semicolon/pms/service/IssueReplyService.java

package com.Semicolon.pms.service;

import java.util.List;
import com.Semicolon.pms.dto.IssueReplyDTO;

public interface IssueReplyService {

    List<IssueReplyDTO> getReplyList(String issueId) throws Exception;
    void registerReply(IssueReplyDTO dto) throws Exception;
    void modifyReply(IssueReplyDTO dto) throws Exception;
    void removeReply(String replyNumber) throws Exception; // int -> String
}