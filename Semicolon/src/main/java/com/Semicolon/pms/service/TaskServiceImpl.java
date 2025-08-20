// com.Semicolon.pms.service.TaskServiceImpl.java
package com.Semicolon.pms.service;

import java.sql.SQLException;
import java.util.List;
import com.Semicolon.command.PageMaker;
import com.Semicolon.pms.dao.TaskDAO;
import com.Semicolon.pms.dao.TaskReplyDAO; // Task에 맞는 ReplyDAO로 가정
import com.Semicolon.pms.dto.TaskDto;
import com.Semicolon.pms.dto.TaskReplyDTO; // Task에 맞는 ReplyDto로 가정

public class TaskServiceImpl implements TaskService {

    private TaskDAO taskDAO;
    private TaskReplyDAO taskReplyDAO;
    
    public TaskServiceImpl(TaskDAO taskDAO, TaskReplyDAO taskReplyDAO) { 
        this.taskDAO = taskDAO;
        this.taskReplyDAO = taskReplyDAO;
    }
    
    @Override
    public List<TaskDto> getTaskList(PageMaker pageMaker) throws SQLException {
        return taskDAO.getTaskList(pageMaker);
    }

    @Override
    public int getTotalCount(PageMaker pageMaker) throws SQLException {
        return taskDAO.getTotalCountByProjectId(pageMaker);
    }

    @Override
    public TaskDto getTaskById(String taskId) throws SQLException {
        TaskDto task = taskDAO.getTaskById(taskId);
        if (task != null) {
            // 일감 상세 정보를 가져올 때 댓글 목록도 함께 가져옵니다.
            List<TaskReplyDTO> replies = taskReplyDAO.selectReplyList(taskId);
            task.setComments(replies);
        }
        return task;
    }

    @Override
    public void createNewTask(TaskDto task) throws SQLException {
        taskDAO.insertNewTask(task);
    }

    @Override
    public void updateTask(TaskDto task) throws SQLException {
        taskDAO.updateTask(task);
    }

    @Override
    public void deleteTask(String taskId) throws SQLException {
        // 일감을 삭제하기 전, 해당 일감에 달린 모든 댓글을 먼저 삭제합니다.
        taskReplyDAO.deleteRepliesByTaskId(taskId); // 메서드명은 ReplyDAO의 스펙에 따라 변경될 수 있습니다.
        taskDAO.deleteTask(taskId);
    }
}