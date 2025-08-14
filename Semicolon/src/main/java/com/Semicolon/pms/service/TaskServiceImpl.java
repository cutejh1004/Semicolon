package com.Semicolon.pms.service;

import java.sql.SQLException;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.Semicolon.pms.dao.TaskDAO;
import com.Semicolon.pms.dao.ReplyDAO;
import com.Semicolon.pms.dto.TaskDto;
import com.Semicolon.pms.dto.ReplyDto;

@Service("taskService")
public class TaskServiceImpl implements TaskService {
    
    @Autowired
    private TaskDAO taskDAO;
    
    @Autowired
    private ReplyDAO replyDAO;

    @Override
    public List<TaskDto> getTaskList() throws SQLException {
        return taskDAO.getTaskList();
    }
    
    @Override
    public List<TaskDto> getTaskListBySearch(String searchQuery) throws SQLException {
        return taskDAO.getTaskListBySearch(searchQuery);
    }

    @Override
    public void createNewTask(TaskDto task) throws SQLException {
    	if (task.getTaskId() == null || task.getTaskId().isEmpty()) {
            task.setTaskId(UUID.randomUUID().toString()); // UUID 생성 및 설정
        }
        taskDAO.insertNewTask(task);
    }
    
    @Override
    public TaskDto getTaskById(String taskId) throws SQLException {
        // 1. 일감 정보를 먼저 가져옵니다.
        TaskDto task = taskDAO.getTaskById(taskId);

        // 2. 일감 정보가 존재하면 해당 일감의 댓글 목록을 가져와 DTO에 설정합니다.
        if (task != null) {
            List<ReplyDto> replies = replyDAO.getRepliesByIssueId(taskId); // bno(게시글 번호)로 댓글 조회
            task.setComments(replies);
        }

        return task;
    }
    
    @Override
    public void updateTask(TaskDto task) throws SQLException {
        taskDAO.updateTask(task);
    }
    
    @Override
    public void deleteTask(String taskId) throws SQLException {
        // 일감 삭제 전, 관련된 댓글을 먼저 삭제합니다. (외래키 제약조건)
        replyDAO.deleteRepliesByIssueId(taskId); // bno(게시글 번호)로 댓글 삭제
        taskDAO.deleteTask(taskId);
    }
}