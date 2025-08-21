package com.Semicolon.org.dao;

import java.util.List;

import org.mybatis.spring.SqlSessionTemplate;

import com.Semicolon.org.dto.CrewRankingDto;
import com.Semicolon.org.dto.DashboardStatsDto;

public class DashboardDAOImpl implements DashboardDAO {
    private final SqlSessionTemplate sqlSession;

    public DashboardDAOImpl(SqlSessionTemplate sqlSession) {
        this.sqlSession = sqlSession;
    }

    @Override
    public DashboardStatsDto getTotalProjectStats() {
        return sqlSession.selectOne("dashboard.getTotalProjectStats");
    }

    @Override
    public List<CrewRankingDto> getQuarterlyBestCrews() {
        return sqlSession.selectList("dashboard.getQuarterlyBestCrews");
    }

    @Override
    public List<CrewRankingDto> getMonthlyHotCrews() {
        return sqlSession.selectList("dashboard.getMonthlyHotCrews");
    }
}
