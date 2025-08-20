// src/main/java/com/Semicolon/org/controller/OrgDetailController.java
package com.Semicolon.org.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.Semicolon.cmnt.dto.MemberVO;
import com.Semicolon.org.dto.OrgDetailDTO;
import com.Semicolon.org.service.OrgDetailService;

@Controller
@RequestMapping("/org")
public class OrgDetailController {

	private OrgDetailService orgDetailService;

	public OrgDetailController(OrgDetailService orgDetailService) {
		this.orgDetailService = orgDetailService;
	}

	@GetMapping("/detail")
	public String orgDetail(HttpSession session, Model model) {

		MemberVO loginUser = (MemberVO) session.getAttribute("loginUser");
		if (loginUser == null) {
			return "redirect:/login";
		}

		// OrgDetailService를 이용해 사용자의 조직 ID 조회
		String orId = orgDetailService.getOrgIdByUserId(loginUser.getUser_id());

		if (orId == null) {
			return "redirect:/org/create"; // 소속된 조직이 없으면 생성 페이지로
		}

		OrgDetailDTO org = orgDetailService.getOrgById(orId);

		model.addAttribute("org", org);

		return "organization/orgDetail";
	}

	@PostMapping("/update")
	@ResponseBody
	public ResponseEntity<Map<String, String>> updateOrg(@RequestBody OrgDetailDTO org) {
		Map<String, String> response = new HashMap<>();
		try {
			orgDetailService.modifyOrg(org);
			response.put("status", "success");
			response.put("message", "조직 정보가 성공적으로 업데이트되었습니다.");
			return new ResponseEntity<>(response, HttpStatus.OK);
		} catch (Exception e) {
			response.put("status", "error");
			response.put("message", "업데이트 중 오류가 발생했습니다.");
			return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PostMapping("/delete")
	@ResponseBody
	public ResponseEntity<Map<String, String>> deleteOrg(@RequestBody Map<String, String> payload) {
		Map<String, String> response = new HashMap<>();
		try {
			String orId = payload.get("orId");
			orgDetailService.removeOrg(orId);
			response.put("status", "success");
			response.put("message", "조직이 삭제되었습니다.");
			return new ResponseEntity<>(response, HttpStatus.OK);
		} catch (Exception e) {
			response.put("status", "error");
			response.put("message", "삭제 중 오류가 발생했습니다.");
			return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}