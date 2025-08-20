package com.Semicolon.pms.dao;

import java.sql.SQLException;
import java.util.List;

import org.mybatis.spring.SqlSessionTemplate;

import com.Semicolon.pms.dto.CalendarDto;

public class CalendarDAOImpl implements CalendarDAO {

    private SqlSessionTemplate sqlSession;

    public CalendarDAOImpl(SqlSessionTemplate sqlSession) {
        this.sqlSession = sqlSession;
    }

    @Override
    public void insertCalendar(CalendarDto calendarDto) throws SQLException {
        sqlSession.insert("com.Semicolon.pms.dao.CalendarDAO.insertCalendar", calendarDto);
    }

    @Override
    public List<CalendarDto> getAllCalendars() throws SQLException {
        return sqlSession.selectList("com.Semicolon.pms.dao.CalendarDAO.getAllCalendars");
    }

    @Override
    public CalendarDto getCalendarById(String calendarId) throws SQLException {
        return sqlSession.selectOne("com.Semicolon.pms.dao.CalendarDAO.getCalendarById", calendarId);
    }

    @Override
    public void updateCalendar(CalendarDto calendarDto) throws SQLException {
        sqlSession.update("com.Semicolon.pms.dao.CalendarDAO.updateCalendar", calendarDto);
    }

    @Override
    public void deleteCalendar(String calendarId) throws SQLException {
        sqlSession.delete("com.Semicolon.pms.dao.CalendarDAO.deleteCalendar", calendarId);
    }
}