package com.Semicolon.org.controller;

import com.Semicolon.org.dto.MemberDTO;
import com.Semicolon.org.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/member")
public class MemberController {

    // MemberService를 주입받아 사용합니다.
    @Autowired
    private MemberService memberService;

    /**
     * 특정 회원의 상세 프로필 페이지를 보여주는 메서드.
     * @param memberId 조회할 회원의 ID
     * @param model 뷰로 데이터를 전달하기 위한 Model 객체
     * @return 회원 프로필 페이지의 뷰 이름
     */
    @GetMapping("/profile")
    public String viewMemberProfile(@RequestParam("memberId") int memberId, Model model) {
        try {
            // MemberService를 통해 특정 memberId에 해당하는 회원 정보를 가져옵니다.
            MemberDTO member = memberService.getMemberById(memberId);

            // 가져온 회원 정보를 "member"라는 이름으로 모델에 추가합니다.
            // 이렇게 추가된 데이터는 뷰(HTML)에서 사용할 수 있습니다.
            model.addAttribute("member", member);
        } catch (Exception e) {
            // 예외 발생 시 에러 메시지를 모델에 추가합니다.
            model.addAttribute("errorMessage", "회원 정보를 불러오는 중 오류가 발생했습니다.");
            // 에러 페이지로 리다이렉트하거나, 현재 페이지에서 에러를 표시할 수 있습니다.
            return "errorPage"; // 예시로 에러 페이지를 반환합니다.
        }

        // 'member-profile.html' 뷰를 반환하여 해당 페이지를 보여줍니다.
        return "member-profile";
    }

    // 다른 컨트롤러 메서드들을 추가할 수 있습니다.
    // 예를 들어, 회원 목록을 보여주는 메서드, 정보 수정 메서드 등.
}
