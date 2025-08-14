package com.Semicolon.pms.dao;

import java.sql.SQLException;
import java.util.List;

import com.Semicolon.pms.dto.CalendarDto;

public interface CalendarDAO {
    void insertCalendar(CalendarDto calendarDto) throws SQLException;
    List<CalendarDto> getAllCalendars() throws SQLException;
    CalendarDto getCalendarById(int calendarId) throws SQLException;
    void updateCalendar(CalendarDto calendarDto) throws SQLException;
    void deleteCalendar(int calendarId) throws SQLException;
}