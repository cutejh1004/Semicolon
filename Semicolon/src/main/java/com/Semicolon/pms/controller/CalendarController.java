package com.Semicolon.pms.controller;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.Semicolon.pms.dto.CalendarDto;
import com.Semicolon.pms.service.CalendarService;

@Controller
@RequestMapping("/main/calendar")
public class CalendarController {

    private CalendarService calendarService;

    public CalendarController(CalendarService calendarService) {
        this.calendarService = calendarService;
    }

    @GetMapping
    public ModelAndView calendarPage() {
        return new ModelAndView("organization/pms/calendar/calendar");
    }

    @GetMapping("/all")
    @ResponseBody
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

    @PostMapping
    @ResponseBody
    public ResponseEntity<Map<String, String>> addCalendar(@RequestBody CalendarDto calendarDto) {
        try {
            // 프로젝트 ID를 임시로 하드코딩
            calendarDto.setProjectId("P123");
            calendarService.addCalendar(calendarDto);
            return ResponseEntity.ok(Collections.singletonMap("message", "일정이 성공적으로 추가되었습니다."));
        } catch (SQLException e) {
            return ResponseEntity.status(500).body(Collections.singletonMap("message", "일정 추가 중 오류가 발생했습니다."));
        }
    }
    
    @GetMapping("/{calendarId}")
    @ResponseBody
    public ResponseEntity<CalendarDto> getCalendar(@PathVariable("calendarId") String calendarId) {
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

    @PutMapping("/update")
    @ResponseBody
    public ResponseEntity<Map<String, String>> updateCalendar(@RequestBody CalendarDto calendarDto) {
        try {
            // 프로젝트 ID를 임시로 하드코딩
            calendarDto.setProjectId("P123");
            calendarService.updateCalendar(calendarDto);
            return ResponseEntity.ok(Collections.singletonMap("message", "일정이 성공적으로 수정되었습니다."));
        } catch (SQLException e) {
            return ResponseEntity.status(500).body(Collections.singletonMap("message", "일정 수정 중 오류가 발생했습니다."));
        }
    }

    @DeleteMapping("/{calendarId}")
    @ResponseBody
    public ResponseEntity<Map<String, String>> deleteCalendar(@PathVariable("calendarId") String calendarId) {
        try {
            calendarService.deleteCalendar(calendarId);
            return ResponseEntity.ok(Collections.singletonMap("message", "일정이 성공적으로 삭제되었습니다."));
        } catch (SQLException e) {
            return ResponseEntity.status(500).body(Collections.singletonMap("message", "일정 삭제 중 오류가 발생했습니다."));
        }
    }
}