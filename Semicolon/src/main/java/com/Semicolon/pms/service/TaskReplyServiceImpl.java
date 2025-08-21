// com.Semicolon.pms.service.TaskReplyServiceImpl.java

package com.Semicolon.pms.service;

import java.sql.SQLException;
import java.util.List;
import com.Semicolon.pms.dao.TaskReplyDAO;
import com.Semicolon.pms.dto.IssueReplyDTO;
import com.Semicolon.pms.dto.TaskReplyDTO;

public class TaskReplyServiceImpl implements TaskReplyService {

    private TaskReplyDAO taskReplyDAO;
    public void setTaskReplyDAO(TaskReplyDAO taskReplyDAO) {
        this.taskReplyDAO = taskReplyDAO;
    }

    @Override
    public List<TaskReplyDTO> selectReplyList(String issueId) throws SQLException {
        return taskReplyDAO.selectReplyList(issueId);
    }

    @Override
    public void insertReply(TaskReplyDTO dto) throws SQLException {
        taskReplyDAO.insertReply(dto);
    }

    @Override
    public void updateReply(TaskReplyDTO dto) throws SQLException {
        taskReplyDAO.updateReply(dto);
    }

    @Override
    public void deleteReply(String replyNumber) throws SQLException {
        taskReplyDAO.deleteReply(replyNumber);
    }
}