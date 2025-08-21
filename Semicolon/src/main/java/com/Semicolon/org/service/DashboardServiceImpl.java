package com.Semicolon.org.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.Semicolon.org.dao.DashboardDAO;
import com.Semicolon.org.dto.CrewRankingDto;
import com.Semicolon.org.dto.DashboardStatsDto;


public class DashboardServiceImpl implements DashboardService {


    private DashboardDAO dashboardDAO;
    
    public DashboardServiceImpl(DashboardDAO dashboardDAO) {
    	this.dashboardDAO = dashboardDAO;
    }

    @Override
    public DashboardStatsDto getTotalProjectStats() {
        return dashboardDAO.getTotalProjectStats();
    }

    @Override
    public List<CrewRankingDto> getQuarterlyBestCrews() {
        return dashboardDAO.getQuarterlyBestCrews();
    }

    @Override
    public List<CrewRankingDto> getMonthlyHotCrews() {
        return dashboardDAO.getMonthlyHotCrews();
    }
}