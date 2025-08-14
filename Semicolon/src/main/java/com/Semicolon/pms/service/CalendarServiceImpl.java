package com.Semicolon.pms.service;

import com.Semicolon.pms.dao.CalendarDAO;
import com.Semicolon.pms.dto.CalendarDto;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.List;

@Service
public class CalendarServiceImpl implements CalendarService {
    
    @Autowired
    private CalendarDAO calendarDAO;

    @Override
    public void addCalendar(CalendarDto calendarDto) throws SQLException {
        calendarDAO.insertCalendar(calendarDto);
    }

    @Override
    public List<CalendarDto> getAllCalendars() throws SQLException {
        return calendarDAO.getAllCalendars();
    }

    @Override
    public CalendarDto getCalendarById(int calendarId) throws SQLException {
        return calendarDAO.getCalendarById(calendarId);
    }

    @Override
    public void updateCalendar(CalendarDto calendarDto) throws SQLException {
        calendarDAO.updateCalendar(calendarDto);
    }

    @Override
    public void deleteCalendar(int calendarId) throws SQLException {
        calendarDAO.deleteCalendar(calendarId);
    }
}