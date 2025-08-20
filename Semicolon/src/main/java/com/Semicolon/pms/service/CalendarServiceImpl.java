package com.Semicolon.pms.service;

import com.Semicolon.pms.dao.CalendarDAO;
import com.Semicolon.pms.dto.CalendarDto;
import java.sql.SQLException;
import java.util.List;

public class CalendarServiceImpl implements CalendarService {
    
    private CalendarDAO calendarDAO;

    public CalendarServiceImpl(CalendarDAO calendarDAO) {
        this.calendarDAO = calendarDAO;
    }

    @Override
    public void addCalendar(CalendarDto calendarDto) throws SQLException {
        calendarDAO.insertCalendar(calendarDto);
    }

    @Override
    public List<CalendarDto> getAllCalendars() throws SQLException {
        return calendarDAO.getAllCalendars();
    }

    @Override
    public CalendarDto getCalendarById(String calendarId) throws SQLException {
        return calendarDAO.getCalendarById(calendarId);
    }

    @Override
    public void updateCalendar(CalendarDto calendarDto) throws SQLException {
        calendarDAO.updateCalendar(calendarDto);
    }

    @Override
    public void deleteCalendar(String calendarId) throws SQLException {
        calendarDAO.deleteCalendar(calendarId);
    }
}