package com.Semicolon.org.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.Semicolon.org.service.OrgDetailService;
import com.Semicolon.org.dto.OrgDetailDTO;

@Controller
@RequestMapping("/org")
public class OrgDetailController {
    
    @Autowired
    private OrgDetailService orgDetailService;

    // 조직 상세 페이지로 이동하는 메서드
    // URL: /org/detail?orId=ABCDEF
    @GetMapping("/detail")
    public String orgDetail(@RequestParam(value = "orId", required = false) String orId, Model model) {
        
        OrgDetailDTO org;

        // DB에 데이터가 없을 경우 임시로 테스트 데이터를 생성
        if (orId == null) {
            org = new OrgDetailDTO();
            org.setOrId("TEMP_ORG_ID");
            org.setOrName("임시 조직명");
            org.setOrIntroduce("테스트를 위한 임시 조직입니다.");
        } else {
            // DB에서 실제 데이터 조회
            org = orgDetailService.getOrgDetailByOrId(orId);
        }

        if (org != null) {
            model.addAttribute("org", org);
        } else {
            return "redirect:/error";
        }
        
        return "organization/orgDetail";
    }

    @PostMapping("/update")
    public String updateOrg(OrgDetailDTO orgDetailDTO, RedirectAttributes redirectAttributes) {
        int result = orgDetailService.updateOrg(orgDetailDTO);
        
        if (result > 0) {
            redirectAttributes.addAttribute("orId", orgDetailDTO.getOrId());
            return "redirect:/org/detail";
        } else {
            return "redirect:/error";
        }
    }

    @PostMapping("/delete")
    public String deleteOrg(@RequestParam("orId") String orId) {
        int result = orgDetailService.deleteOrg(orId);
        
        if (result > 0) {
            return "redirect:/org/list";
        } else {
            return "redirect:/error";
        }
    }
}
