package com.Semicolon.pms.service;

import com.Semicolon.pms.dao.GanttDAO;
import com.Semicolon.pms.dto.GanttDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.List;

@Service
public class GanttServiceImpl implements GanttService {

    @Autowired
    private GanttDAO ganttDAO;

    @Override
    public void addGantt(GanttDto ganttDto) throws SQLException {
        ganttDAO.insertGantt(ganttDto);
    }

    @Override
    public List<GanttDto> getAllGantts() throws SQLException {
        return ganttDAO.getAllGantts();
    }

    @Override
    public GanttDto getGanttById(int ganttId) throws SQLException {
        return ganttDAO.getGanttById(ganttId);
    }

    @Override
    public void updateGantt(GanttDto ganttDto) throws SQLException {
        ganttDAO.updateGantt(ganttDto);
    }

    @Override
    public void deleteGantt(int ganttId) throws SQLException {
        ganttDAO.deleteGantt(ganttId);
    }

    @Override
    public List<GanttDto> getGanttsByProjectId(int projectId) throws SQLException {
        return ganttDAO.getGanttsByProjectId(projectId);
    }
}