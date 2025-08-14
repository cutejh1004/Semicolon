package com.Semicolon.org.controller;

import java.util.Arrays;
import java.util.List;

import javax.servlet.http.HttpSession; // HttpSession을 사용하기 위해 import 추가

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.Semicolon.org.dto.CreateOrgDTO;
import com.Semicolon.org.service.CreateOrgService;

@Controller
@RequestMapping("/org")
public class CreateOrgController {

    @Autowired
    private CreateOrgService createOrgService;

    @GetMapping("/create")
    public String showCreateOrgPage() {
        return "organization/createOrg";
    }

    @PostMapping("/create")
    public ModelAndView createOrganization(
            CreateOrgDTO organization,
            @RequestParam(value = "memberInvite", required = false) String memberInviteStr,
            HttpSession session // 세션 객체를 파라미터로 추가
    ) {
        String publicStatus = "공개".equals(organization.getOrIsPublic()) ? "Y" : "N";
        organization.setOrIsPublic(publicStatus);
        
        // **수정**: 로그인한 사용자 ID를 세션에서 가져와 DTO에 설정합니다.
        // 세션에 "userId"라는 이름으로 사용자 ID가 저장되어 있다고 가정합니다.
        String loggedInUserId = (String) session.getAttribute("userId");
        if (loggedInUserId != null) {
            organization.setMemberId(loggedInUserId);
        } else {
            // 로그인 정보가 없을 경우, 로그인 페이지로 리다이렉트하거나 오류 처리
            ModelAndView errorMav = new ModelAndView("redirect:/login");
            return errorMav;
        }

        organization.setMemberRole("관리자");

        List<String> invitedMembersList = null;
        if (memberInviteStr != null && !memberInviteStr.isEmpty()) {
            invitedMembersList = Arrays.asList(memberInviteStr.split(","));
        }
        organization.setInvitedMembers(invitedMembersList);

        createOrgService.createOrganization(organization);

        ModelAndView mav = new ModelAndView("redirect:/org/myorg");
        return mav;
    }
}
