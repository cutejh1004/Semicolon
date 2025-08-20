// com.Semicolon.pms.dao.TaskDAO.java

package com.Semicolon.pms.dao;

import java.sql.SQLException;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.Semicolon.command.PageMaker;
import com.Semicolon.pms.dto.TaskDto;

public interface TaskDAO {
    // 일감 목록 조회 (검색 조건 포함)
    List<TaskDto> getTaskList(@Param("pageMaker") PageMaker pageMaker) throws SQLException;

    // 특정 프로젝트의 전체 일감 수를 조회 (검색 조건 포함)
    int getTotalCountByProjectId(@Param("pageMaker") PageMaker pageMaker) throws SQLException;
    
    // 새 일감 등록
    void insertNewTask(TaskDto task) throws SQLException;
    
    // 일감 상세 정보 조회
    TaskDto getTaskById(@Param("taskId") String taskId) throws SQLException;
    
    // 일감 수정
    void updateTask(TaskDto task) throws SQLException;
    
    // 일감 삭제
    void deleteTask(@Param("taskId") String taskId) throws SQLException;
}