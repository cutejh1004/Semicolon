package com.Semicolon.pms.service;

import java.sql.SQLException;
import java.util.List;

import com.Semicolon.pms.dto.CalendarDto;

public interface CalendarService {
    void addCalendar(CalendarDto calendarDto) throws SQLException;
    List<CalendarDto> getAllCalendars() throws SQLException;
    CalendarDto getCalendarById(String calendarId) throws SQLException;
    void updateCalendar(CalendarDto calendarDto) throws SQLException;
    void deleteCalendar(String calendarId) throws SQLException;
}