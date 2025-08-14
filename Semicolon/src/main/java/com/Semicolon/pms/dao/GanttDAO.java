package com.Semicolon.pms.dao;

import com.Semicolon.pms.dto.GanttDto;
import java.sql.SQLException;
import java.util.List;

public interface GanttDAO {
    void insertGantt(GanttDto ganttDto) throws SQLException;
    List<GanttDto> getAllGantts() throws SQLException;
    GanttDto getGanttById(int ganttId) throws SQLException;
    void updateGantt(GanttDto ganttDto) throws SQLException;
    void deleteGantt(int ganttId) throws SQLException;
    List<GanttDto> getGanttsByProjectId(int projectId) throws SQLException; // 프로젝트별 간트 가져오기
}