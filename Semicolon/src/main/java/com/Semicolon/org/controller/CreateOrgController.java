// src/main/java/com/Semicolon/org/controller/CreateOrgController.java
package com.Semicolon.org.controller;

import javax.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import com.Semicolon.org.dto.CreateOrgDTO;
import com.Semicolon.org.service.CreateOrgService;
import com.Semicolon.cmnt.dto.MemberVO;

@Controller
@RequestMapping("/org")
public class CreateOrgController {

    private CreateOrgService createOrgService;

    public CreateOrgController(CreateOrgService createOrgService) {
        this.createOrgService = createOrgService;
    }
    
    @GetMapping("/create")
    public String showCreateOrgPage() {
        return "organization/createOrg";
    }

    @PostMapping("/create")
    public String createOrganization(CreateOrgDTO org, HttpSession session) {
        
        MemberVO loginUser = (MemberVO) session.getAttribute("loginUser");

        if (loginUser == null) {
            return "redirect:/login";
        }

        org.setOrManagerId(loginUser.getUser_id());
        
        createOrgService.createOrganization(org);

        return "redirect:/org/detail/" + org.getOrId();
    }
}