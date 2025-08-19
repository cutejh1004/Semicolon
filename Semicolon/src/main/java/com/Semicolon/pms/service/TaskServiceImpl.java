// com.Semicolon.pms.service.TaskServiceImpl.java
package com.Semicolon.pms.service;

import java.sql.SQLException;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional; // [추가] 트랜잭션 어노테이션 임포트

import com.Semicolon.pms.dao.TaskDAO;
import com.Semicolon.pms.dao.ReplyDAO;
import com.Semicolon.pms.dto.TaskDto;
import com.Semicolon.pms.dto.ReplyDto;
import com.Semicolon.pms.dto.GanttDto;           // [추가] GanttDto 임포트
import com.Semicolon.pms.service.GanttService;   // [추가] GanttService 임포트

@Service("taskService")
public class TaskServiceImpl implements TaskService {

    @Autowired
    private TaskDAO taskDAO;

    @Autowired
    private ReplyDAO replyDAO;

    @Autowired
    private GanttService ganttService; // [추가] GanttService 의존성 주입

    @Override
    public List<TaskDto> getTaskList() throws SQLException {
        return taskDAO.getTaskList();
    }

    @Override
    public List<TaskDto> getTaskListBySearch(String searchQuery) throws SQLException {
        return taskDAO.getTaskListBySearch(searchQuery);
    }

    @Override
    @Transactional // [수정] 일감과 간트 생성을 하나의 트랜잭션으로 처리
    public void createNewTask(TaskDto task) throws SQLException {
        if (task.getTaskId() == null || task.getTaskId().isEmpty()) {
            task.setTaskId(UUID.randomUUID().toString()); // UUID 생성 및 설정
        }
        // 1. 일감 생성
        taskDAO.insertNewTask(task);
        
        // 2. [추가] 생성된 일감 정보를 바탕으로 간트 항목 생성
        GanttDto gantt = new GanttDto();
        gantt.setProjectId(task.getProjectId());
        gantt.setTaskId(task.getTaskId());
        gantt.setGanttTitle(task.getTaskTitle());
        gantt.setGanttManagerId(task.getTaskManagerId());
        gantt.setGanttStartDate(task.getTaskStartDate());
        gantt.setGanttEndDate(task.getTaskEndDate());

        ganttService.createNewGantt(gantt);
    }

    @Override
    public TaskDto getTaskById(String taskId) throws SQLException {
        TaskDto task = taskDAO.getTaskById(taskId);
        if (task != null) {
            List<ReplyDto> replies = replyDAO.getRepliesByIssueId(taskId);
            task.setComments(replies);
        }
        return task;
    }

    @Override
    @Transactional // [수정] 일감과 간트 수정을 하나의 트랜잭션으로 처리
    public void updateTask(TaskDto task) throws SQLException {
        // 1. 일감 정보 수정
        taskDAO.updateTask(task);
        
        // 2. [추가] 관련된 간트 항목 정보도 함께 수정
        GanttDto gantt = new GanttDto();
        gantt.setTaskId(task.getTaskId());
        gantt.setGanttTitle(task.getTaskTitle());
        gantt.setGanttManagerId(task.getTaskManagerId());
        gantt.setGanttStartDate(task.getTaskStartDate());
        gantt.setGanttEndDate(task.getTaskEndDate());

        ganttService.updateGanttByTask(gantt);
    }

    @Override
    @Transactional // [수정] 일감, 댓글, 간트 삭제를 하나의 트랜잭션으로 처리
    public void deleteTask(String taskId) throws SQLException {
        // 1. 관련된 댓글 먼저 삭제
        replyDAO.deleteRepliesByIssueId(taskId);
        
        // 2. [추가] 관련된 간트 항목 삭제
        ganttService.deleteGanttByTaskId(taskId);
        
        // 3. 마지막으로 일감 자체를 삭제
        taskDAO.deleteTask(taskId);
    }
}