package com.Semicolon.org.controller;

import com.Semicolon.org.dto.ProjectOrgDTO;
import com.Semicolon.org.service.ProjectOrgService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import java.util.Arrays;
import java.util.List;

@Controller
@RequestMapping("/org") // Base URL is now /org
public class ProjectOrgController {

    @Autowired
    private ProjectOrgService projectOrgService;

    @GetMapping("/create") // Full URL: /org/create
    public String showCreateProjectPage() {
        return "project/createProject";
    }

    @PostMapping("/create") // Full URL: /org/create
    public ModelAndView createProjectOrg(
            ProjectOrgDTO projectOrg,
            @RequestParam(value = "memberInvite", required = false) String memberInviteStr,
            HttpSession session
    ) {
        // 공개 여부 설정
        String publicStatus = "공개".equals(projectOrg.getProjectIsPublic()) ? "Y" : "N";
        projectOrg.setProjectIsPublic(publicStatus);
        
        // 세션에서 사용자 ID 가져오기
        String loggedInUserId = (String) session.getAttribute("userId");
        if (loggedInUserId == null) {
            return new ModelAndView("redirect:/login");
        }
        projectOrg.setMemberId(loggedInUserId);
        projectOrg.setMemberRole("관리자");

        // 초대 멤버 목록 처리
        if (memberInviteStr != null && !memberInviteStr.isEmpty()) {
            projectOrg.setInvitedMembers(Arrays.asList(memberInviteStr.split(",")));
        }

        projectOrgService.createProjectOrg(projectOrg);

        return new ModelAndView("redirect:/org/myproject"); // 리다이렉트 URL 변경
    }

    @GetMapping("/myproject") // Full URL: /org/myproject
    public ModelAndView showMyProjectsPage(HttpSession session) {
        String loggedInUserId = (String) session.getAttribute("userId");
        if (loggedInUserId == null) {
            return new ModelAndView("redirect:/login");
        }

        ModelAndView mav = new ModelAndView("project/myProjectList");
        List<ProjectOrgDTO> myProjectList = projectOrgService.getProjectOrgsByMemberId(loggedInUserId);
        mav.addObject("myProjectList", myProjectList);

        return mav;
    }
}