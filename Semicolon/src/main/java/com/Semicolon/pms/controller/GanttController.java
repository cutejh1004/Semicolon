package com.Semicolon.pms.controller;

import com.Semicolon.pms.dto.GanttDto;
import com.Semicolon.pms.service.GanttService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.sql.SQLException;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.ArrayList;
import java.util.HashMap;


@Controller
@RequestMapping("/main/gantt")
public class GanttController {

    @Autowired
    private GanttService ganttService;

    @GetMapping
    public ModelAndView ganttPage() {
        return new ModelAndView("organization/pms/gantt/gantt");
    }

    // 모든 간트 작업 가져오기 (필요시)
    @GetMapping("/all")
    @ResponseBody
    public List<Map<String, Object>> getAllGanttsForChart() {
        try {
            List<GanttDto> gantts = ganttService.getAllGantts();
            List<Map<String, Object>> tasks = new ArrayList<>();
            for (GanttDto g : gantts) {
                Map<String, Object> task = new HashMap<>();
                task.put("id", g.getGanttId());
                task.put("text", g.getGanttTitle()); // 간트 라이브러리에 따라 'title' 대신 'text' 사용
                task.put("start_date", g.getGanttStartDate()); // 간트 라이브러리에 따라 'start' 대신 'start_date' 사용
                task.put("end_date", g.getGanttEndDate()); // 간트 라이브러리에 따라 'end' 대신 'end_date' 사용
                task.put("duration", (g.getGanttEndDate().getTime() - g.getGanttStartDate().getTime()) / (1000 * 60 * 60 * 24)); // 일수 계산
                task.put("progress", 0.0); // 진행률 (필요시 추가)
                task.put("parent", 0); // 부모 태스크 (필요시 추가)
                // task.put("manager", g.getGanttManagerId()); // 담당자 정보
                task.put("project_id", g.getProjectId()); // 프로젝트 ID 포함
                tasks.add(task);
            }
            return tasks;
        } catch (SQLException e) {
            // 예외 로깅
            return Collections.emptyList();
        }
    }

    // 특정 프로젝트의 간트 작업 가져오기
    @GetMapping("/project/{projectId}")
    @ResponseBody
    public List<Map<String, Object>> getGanttsByProjectIdForChart(@PathVariable("projectId") int projectId) {
        try {
            List<GanttDto> gantts = ganttService.getGanttsByProjectId(projectId);
            List<Map<String, Object>> tasks = new ArrayList<>();
            for (GanttDto g : gantts) {
                Map<String, Object> task = new HashMap<>();
                task.put("id", g.getGanttId());
                task.put("text", g.getGanttTitle());
                task.put("start_date", g.getGanttStartDate());
                task.put("end_date", g.getGanttEndDate());
                task.put("duration", (g.getGanttEndDate().getTime() - g.getGanttStartDate().getTime()) / (1000 * 60 * 60 * 24));
                task.put("progress", 0.0);
                task.put("parent", 0);
                // task.put("manager", g.getGanttManagerId());
                task.put("project_id", g.getProjectId());
                tasks.add(task);
            }
            return tasks;
        } catch (SQLException e) {
            // 예외 로깅
            return Collections.emptyList();
        }
    }

    // 새 간트 작업 추가
    @PostMapping
    @ResponseBody
    public ResponseEntity<Map<String, String>> addGantt(@RequestBody GanttDto ganttDto) {
        try {
            ganttService.addGantt(ganttDto);
            return ResponseEntity.ok(Collections.singletonMap("message", "간트 작업이 성공적으로 추가되었습니다."));
        } catch (SQLException e) {
            return ResponseEntity.status(500).body(Collections.singletonMap("message", "간트 작업 추가 중 오류가 발생했습니다."));
        }
    }

    // 특정 간트 작업 가져오기 (상세보기용)
    @GetMapping("/{ganttId}")
    @ResponseBody
    public ResponseEntity<GanttDto> getGantt(@PathVariable("ganttId") int ganttId) {
        try {
            GanttDto gantt = ganttService.getGanttById(ganttId);
            if (gantt != null) {
                return ResponseEntity.ok(gantt);
            } else {
                return ResponseEntity.status(404).body(null);
            }
        } catch (SQLException e) {
            return ResponseEntity.status(500).body(null);
        }
    }

    // 간트 작업 수정
    @PutMapping("/update")
    @ResponseBody
    public ResponseEntity<Map<String, String>> updateGantt(@RequestBody GanttDto ganttDto) {
        try {
            ganttService.updateGantt(ganttDto);
            return ResponseEntity.ok(Collections.singletonMap("message", "간트 작업이 성공적으로 수정되었습니다."));
        } catch (SQLException e) {
            return ResponseEntity.status(500).body(Collections.singletonMap("message", "간트 작업 수정 중 오류가 발생했습니다."));
        }
    }

    // 간트 작업 삭제
    @DeleteMapping("/{ganttId}")
    @ResponseBody
    public ResponseEntity<Map<String, String>> deleteGantt(@PathVariable("ganttId") int ganttId) {
        try {
            ganttService.deleteGantt(ganttId);
            return ResponseEntity.ok(Collections.singletonMap("message", "간트 작업이 성공적으로 삭제되었습니다."));
        } catch (SQLException e) {
            return ResponseEntity.status(500).body(Collections.singletonMap("message", "간트 작업 삭제 중 오류가 발생했습니다."));
        }
    }
}