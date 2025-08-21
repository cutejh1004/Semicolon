package com.Semicolon.org.service;

import java.util.List;
import com.Semicolon.org.dto.CrewRankingDto;
import com.Semicolon.org.dto.DashboardStatsDto;

public interface DashboardService {
    DashboardStatsDto getTotalProjectStats();
    List<CrewRankingDto> getQuarterlyBestCrews();
    List<CrewRankingDto> getMonthlyHotCrews();
}