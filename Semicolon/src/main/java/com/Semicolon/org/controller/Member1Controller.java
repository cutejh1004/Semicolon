package com.Semicolon.org.controller;

import com.Semicolon.org.dto.Member1DTO;
import com.Semicolon.org.service.Member1Service;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/org")
public class Member1Controller {

    private final Member1Service memberService;

    public Member1Controller(Member1Service memberService) {
        this.memberService = memberService;
    }

    /**
     * 특정 조직의 멤버 목록 페이지를 보여줍니다.
     */
    @GetMapping("/members")
    public String showOrgMembers(@RequestParam(value = "orId", required = false, defaultValue = "0") String orId, Model model) {
        List<Member1DTO> memberList = memberService.getMemberList(orId);
        model.addAttribute("memberList", memberList);
        return "organization/member1";
    }

    /**
     * PM이 멤버의 역할을 변경합니다. (PM 권한 확인 필요)
     */
    @PostMapping("/updateRole")
    @ResponseBody
    public Map<String, Object> updateMemberRole(@RequestParam("userId") String userId,
                                                @RequestParam("newRole") String newRole,
                                                HttpSession session) {
        Map<String, Object> response = new HashMap<>();
        // 실제 PM 권한 확인 로직 (예: 세션에서 ROLE 가져와서 "PM"인지 확인)
        String currentRole = (String) session.getAttribute("ROLE");
        if (!"PM".equals(currentRole)) {
            response.put("success", false);
            response.put("message", "권한이 없습니다.");
            return response;
        }

        int result = memberService.updateMemberRole(userId, newRole);
        if (result > 0) {
            response.put("success", true);
            response.put("message", "권한이 성공적으로 변경되었습니다.");
        } else {
            response.put("success", false);
            response.put("message", "권한 변경에 실패했습니다.");
        }
        return response;
    }

    /**
     * PM이 멤버를 조직에서 추방합니다. (PM 권한 확인 필요)
     */
    @PostMapping("/removeMember")
    @ResponseBody
    public Map<String, Object> removeMemberFromOrg(@RequestParam("userId") String userId,
                                                   @RequestParam("orId") String orId, // int -> String으로 변경
                                                   HttpSession session) {
        Map<String, Object> response = new HashMap<>();
        // 실제 PM 권한 확인 로직 (예: 세션에서 ROLE 가져와서 "PM"인지 확인)
        String currentRole = (String) session.getAttribute("ROLE");
        if (!"PM".equals(currentRole)) {
            response.put("success", false);
            response.put("message", "권한이 없습니다.");
            return response;
        }

        int result = memberService.removeMember(userId, orId); // 파라미터 타입 변경
        if (result > 0) {
            response.put("success", true);
            response.put("message", "멤버가 성공적으로 추방되었습니다.");
        } else {
            response.put("success", false);
            response.put("message", "멤버 추방에 실패했습니다.");
        }
        return response;
    }
}