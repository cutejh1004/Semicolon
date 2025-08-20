package com.Semicolon.cmnt.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.Semicolon.cmnt.command.MemberModifyCommand;
import com.Semicolon.cmnt.command.MemberRegistCommand;
import com.Semicolon.cmnt.dto.EngineerVO;
import com.Semicolon.cmnt.dto.MemberVO;
import com.Semicolon.cmnt.service.EngineerService;
import com.Semicolon.cmnt.service.MemberService;

@Controller
@RequestMapping("/member")
public class MemberController {

	@Autowired
	private MemberService service;
	@Autowired
	private EngineerService engineerService;

	@GetMapping("/main")
	public void main() {}


	
	@GetMapping("/regist")
	public void registForm() {
	}

	@GetMapping("/idCheck")
	@ResponseBody
	public String idCheck(String user_id) throws Exception {
		String result = "duplicated";
		MemberVO member = service.getMember(user_id);
		if (member == null)
			result = "";

		return result;
	}

	
	
	@PostMapping(value = "/regist", produces = "text/plain;charset=utf-8")
	public ModelAndView regist(MemberRegistCommand regCommand, ModelAndView mnv) {
		String url = "/member/regist_success";
		try {
			// DB 저장
			MemberVO member = regCommand.toMemberVO();
			service.regist(member);
			
			EngineerVO engineer = regCommand.toEngineerVO();
//			// 2. Engineer 저장 (major 입력이 있다면)
//	        if (regCommand.getMajor() != null && !regCommand.getMajor().isEmpty()) {
//	            EngineerVO engineer = regCommand.toEngineerVO();
//	            engineerService.registerEngineer(engineer);
//	        }
			engineerService.registerEngineer(engineer);
			
		}catch (Exception e) {
			url = "/error/500";
			e.printStackTrace();
		}
		
		mnv.setViewName(url);
		return mnv;
	}
	
	
	@GetMapping("/detail")
	public ModelAndView detail(String id, ModelAndView mnv) throws Exception {
		String url = "/member/detail";

		MemberVO member = service.getMember(id);

		mnv.addObject("member", member);
		mnv.setViewName(url);
		return mnv;
	}
	
	
	@GetMapping("/modify")
	public ModelAndView modifyForm(String id, ModelAndView mnv) throws Exception {
		String url = "/member/modify";
		
		MemberVO member = service.getMember(id);
		
		mnv.addObject("member", member);
		mnv.setViewName(url);
		return mnv;
	}
	
	@PostMapping(value = "/modify", produces = "text/plain;charset=utf-8")
	public ModelAndView modify(MemberModifyCommand modifyCommand, ModelAndView mnv) 
																	throws Exception {
		String url = "/member/modify_success";
		
		MemberVO member = modifyCommand.toMemberVO();
		
		// DB 수정
		service.modify(member);
		
		mnv.addObject("id",member.getUser_id());
		mnv.setViewName(url);
		return mnv;
	}
	
	@GetMapping(value = "/remove")
	public ModelAndView remove(String id,ModelAndView mnv) throws Exception {
		String url = "/member/remove_success";

		// db삭제
		service.remove(id);

		mnv.setViewName(url);
		return mnv;
	}

}