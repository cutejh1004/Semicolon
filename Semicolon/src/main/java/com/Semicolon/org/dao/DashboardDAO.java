package com.Semicolon.org.dao;
import java.util.List;
import com.Semicolon.org.dto.CrewRankingDto;
import com.Semicolon.org.dto.DashboardStatsDto;

public interface DashboardDAO {
    DashboardStatsDto getTotalProjectStats();
    List<CrewRankingDto> getQuarterlyBestCrews();
    List<CrewRankingDto> getMonthlyHotCrews();
}