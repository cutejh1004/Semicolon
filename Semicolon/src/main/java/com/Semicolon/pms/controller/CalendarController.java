package com.Semicolon.pms.controller;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller; // @Controller로 변경
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody; // @ResponseBody import
import org.springframework.web.servlet.ModelAndView;

import com.Semicolon.pms.dto.CalendarDto;
import com.Semicolon.pms.service.CalendarService;

@Controller 
@RequestMapping("/main/calendar")
public class CalendarController {

    @Autowired
    private CalendarService calendarService;

    @GetMapping
    public ModelAndView calendarPage() {
        return new ModelAndView("organization/pms/calendar/calendar");
    }

    // 모든 일정 가져오기 (FullCalendar용)
    @GetMapping("/all")
    @ResponseBody // API 메서드에 @ResponseBody 추가
    public List<Map<String, Object>> getAllCalendarsForFullCalendar() {
        try {
            List<CalendarDto> calendars = calendarService.getAllCalendars();
            
            List<Map<String, Object>> events = new ArrayList<>();
            for (CalendarDto c : calendars) {
                Map<String, Object> event = new HashMap<>();
                event.put("id", c.getCalendarId());
                event.put("title", c.getCalendarTitle());
                event.put("start", c.getCalendarStartDate());
                event.put("end", c.getCalendarEndDate());
                events.add(event);
            }
            return events;
        } catch (SQLException e) {
            return Collections.emptyList();
        }
    }

    // 새 일정 추가
    @PostMapping
    @ResponseBody // API 메서드에 @ResponseBody 추가
    public ResponseEntity<Map<String, String>> addCalendar(@RequestBody CalendarDto calendarDto) {
        try {
            calendarService.addCalendar(calendarDto);
            return ResponseEntity.ok(Collections.singletonMap("message", "일정이 성공적으로 추가되었습니다."));
        } catch (SQLException e) {
            return ResponseEntity.status(500).body(Collections.singletonMap("message", "일정 추가 중 오류가 발생했습니다."));
        }
    }
    
    // 특정 일정 가져오기 (상세보기용)
    @GetMapping("/{calendarId}")
    @ResponseBody // API 메서드에 @ResponseBody 추가
    public ResponseEntity<CalendarDto> getCalendar(@PathVariable("calendarId") int calendarId) {
        try {
            CalendarDto calendar = calendarService.getCalendarById(calendarId);
            if (calendar != null) {
                return ResponseEntity.ok(calendar);
            } else {
                return ResponseEntity.status(404).body(null);
            }
        } catch (SQLException e) {
            return ResponseEntity.status(500).body(null);
        }
    }

    // 일정 수정
    @PutMapping("/update")
    @ResponseBody // API 메서드에 @ResponseBody 추가
    public ResponseEntity<Map<String, String>> updateCalendar(@RequestBody CalendarDto calendarDto) {
        try {
            calendarService.updateCalendar(calendarDto);
            return ResponseEntity.ok(Collections.singletonMap("message", "일정이 성공적으로 수정되었습니다."));
        } catch (SQLException e) {
            return ResponseEntity.status(500).body(Collections.singletonMap("message", "일정 수정 중 오류가 발생했습니다."));
        }
    }

    // 일정 삭제
    @DeleteMapping("/{calendarId}")
    @ResponseBody // API 메서드에 @ResponseBody 추가
    public ResponseEntity<Map<String, String>> deleteCalendar(@PathVariable("calendarId") int calendarId) {
        try {
            calendarService.deleteCalendar(calendarId);
            return ResponseEntity.ok(Collections.singletonMap("message", "일정이 성공적으로 삭제되었습니다."));
        } catch (SQLException e) {
            return ResponseEntity.status(500).body(Collections.singletonMap("message", "일정 삭제 중 오류가 발생했습니다."));
        }
    }
}