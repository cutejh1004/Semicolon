package com.Semicolon.pms.service;

import java.sql.SQLException;
import java.util.List;
import com.Semicolon.pms.dto.TaskDto;

public interface TaskService {
    // 일감 목록 조회
    List<TaskDto> getTaskList() throws SQLException;
    
    // 특정 일감 상세 정보 조회
    TaskDto getTaskById(String taskId) throws SQLException; 
    
    // 새 일감 등록
    void createNewTask(TaskDto task) throws SQLException;

    // 일감 수정
    void updateTask(TaskDto task) throws SQLException;
    
    // 일감 삭제
    void deleteTask(String taskId) throws SQLException;
    
    // 검색: 일감 제목으로 목록 조회
    List<TaskDto> getTaskListBySearch(String searchQuery) throws SQLException;
}