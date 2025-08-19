// com.Semicolon.pms.controller.GanttController.java
package com.Semicolon.pms.controller;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.Collections;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.Semicolon.pms.dto.GanttDto;
import com.Semicolon.pms.service.GanttService;

@Controller
@RequestMapping("/main")
public class GanttController {

    @Autowired
    private GanttService ganttService;

    // 간트 차트 페이지 (GET /main/gantt 또는 /main/gantt/{projectId})
    @GetMapping({"/gantt", "/gantt/{projectId}"})
    public String getGanttPage(@RequestParam(value = "search", required = false) String projectId, Model model) {
        String currentProjectId = projectId != null ? projectId : "PJT001"; // 기본값 설정
        // TODO: 로그인된 사용자의 프로젝트 목록에서 선택된 프로젝트를 가져오거나, 기본 프로젝트 설정

        try {
            List<GanttDto> ganttData = ganttService.getGanttDataByProjectId(currentProjectId);
            model.addAttribute("ganttData", ganttData); // JSP로 간트 데이터 전달
            model.addAttribute("currentProjectId", currentProjectId);
        } catch (SQLException e) {
            e.printStackTrace();
            model.addAttribute("errorMsg", "간트 차트 데이터를 불러오는 중 오류가 발생했습니다.");
            return "errorPage"; // 오류 페이지로 리다이렉트
        }
        return "organization/pms/gantt/ganttchart"; // 간트 차트 JSP 뷰 경로
    }

    // 새 간트 항목 추가 API (POST /main/gantt/add)
    @PostMapping("/gantt/add")
    @ResponseBody // JSON 응답을 위해 추가
    public Map<String, Object> addGanttTask(@RequestBody GanttDto ganttDto) {
        Map<String, Object> response = Collections.singletonMap("success", false);
        try {
            int result = ganttService.createNewGantt(ganttDto); // GanttService 호출
            if (result > 0) {
                response = Collections.singletonMap("success", true);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            // 오류 발생 시 상세 메시지 전달
            response = Collections.singletonMap("error", "간트 항목 추가 중 오류 발생: " + e.getMessage());
        }
        return response;
    }
}