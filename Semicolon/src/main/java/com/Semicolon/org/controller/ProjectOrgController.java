package com.Semicolon.org.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.Semicolon.org.service.ProjectOrgService;
import com.Semicolon.org.dto.ProjectOrgDTO;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/org")
public class ProjectOrgController {
    
    @Autowired
    private ProjectOrgService projectOrgService;

    @GetMapping("/myproject")
    public String myProjectList(@RequestParam(value = "search", required = false) String searchQuery, Model model) {
        
        List<Map<String, Object>> projectList = new ArrayList<>();
        projectList.add(Map.of(
            "projectId", "PJ-001",
            "projectName", "사내 그룹웨어 시스템 개발",
            "projectManagerId", "김철수",
            "projectStartDate", new Date(),
            "projectEndDate", new Date()
        ));
        projectList.add(Map.of(
            "projectId", "PJ-002",
            "projectName", "모바일 앱 UI/UX 개편",
            "projectManagerId", "이영희",
            "projectStartDate", new Date(),
            "projectEndDate", new Date()
        ));
        
        model.addAttribute("projectList", projectList);
        model.addAttribute("searchQuery", searchQuery);
        
        // myproject.jsp의 경로에 맞게 수정
        return "organization/myproject"; 
    }

    @PostMapping("/project")
    @ResponseBody
    public ResponseEntity<?> createProject(
            @RequestParam("projectName") String projectName,
            @RequestParam("projectLogo") MultipartFile projectLogo,
            @RequestParam("projectManager") String projectManager,
            @RequestParam("projectStartDate") String projectStartDate,
            @RequestParam("projectEndDate") String projectEndDate,
            @RequestParam("role") String role) {
        
        System.out.println("New Project Received: " + projectName);
        System.out.println("Logo file name: " + projectLogo.getOriginalFilename());

        return ResponseEntity.ok(Map.of("message", "프로젝트가 성공적으로 등록되었습니다."));
    }
    
    @GetMapping("/project/{projectId}")
    public String projectDetail(@PathVariable("projectId") String projectId, Model model) {
        System.out.println("Viewing details for project: " + projectId);
        return "organization/pms/task/tasklist";
    }

}