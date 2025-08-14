package com.Semicolon.pms.controller;

import java.sql.SQLException;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam; // RequestParam import 추가
import org.springframework.web.bind.annotation.ResponseBody;

import com.Semicolon.pms.dto.IssueDto;
import com.Semicolon.pms.service.IssueService;

@Controller
@RequestMapping("/main")
public class IssueController {

    @Autowired
    private IssueService issueService;

    // 이슈 목록 페이지 (GET /main/issuelist)
    // 검색 기능을 추가하여 searchQuery 파라미터를 받습니다.
    @GetMapping("/issuelist")
    public String getIssueList(@RequestParam(value = "search", required = false) String searchQuery, Model model) {
        try {
            List<IssueDto> issueList;

            if (searchQuery != null && !searchQuery.isEmpty()) {
                // 검색어가 있을 경우, 검색 로직을 수행
                issueList = issueService.getIssueListBySearch(searchQuery);
                model.addAttribute("searchQuery", searchQuery); // 검색어 값을 뷰로 전달
            } else {
                // 검색어가 없을 경우, 전체 목록을 조회
                issueList = issueService.getIssueList();
            }

            model.addAttribute("issueList", issueList);
        } catch (SQLException e) {
            e.printStackTrace();
            model.addAttribute("errorMsg", "이슈 목록을 불러오는 중 오류가 발생했습니다.");
            return "errorPage";
        }
        return "organization/pms/issue/issuelist";
    }

    // 새 이슈 등록 (POST /main/issuelist)
    @PostMapping("/issuelist")
    @ResponseBody
    public Map<String, String> createIssue(@RequestBody IssueDto newIssue) {
        try {
            issueService.createNewIssue(newIssue);
            return Collections.singletonMap("message", "새 이슈가 성공적으로 등록되었습니다.");
        } catch (SQLException e) {
            e.printStackTrace();
            return Collections.singletonMap("error", "이슈 등록 중 오류가 발생했습니다.");
        }
    }
    
    // =========================================================================
    // 추가된 코드: 이슈 상세 페이지를 보여주는 메소드
    // =========================================================================
    @GetMapping("/issue/{issueId}")
    public String getIssueDetail(@PathVariable("issueId") String issueId, Model model) {
        try {
            // issueId를 사용하여 DB에서 이슈 상세 정보를 조회
            IssueDto issue = issueService.getIssueById(issueId);
            model.addAttribute("issue", issue); // JSP에서 사용할 'issue' 객체
        } catch (SQLException e) {
            e.printStackTrace();
            model.addAttribute("errorMsg", "이슈 상세 정보를 불러오는 중 오류가 발생했습니다.");
            return "errorPage"; // 오류 페이지로 리다이렉트
        }

        // JSP 파일 경로를 반환. WEB-INF/views/organization/issue/issuedetail.jsp
        return "organization/pms/issue/issuedetail";
    }
    
    @PutMapping("/issue/update")
    @ResponseBody
    public Map<String, String> updateIssue(@RequestBody IssueDto issueDto) {
        try {
            issueService.updateIssue(issueDto);
            return Collections.singletonMap("message", "이슈가 성공적으로 수정되었습니다.");
        } catch (SQLException e) {
            e.printStackTrace();
            return Collections.singletonMap("error", "이슈 수정 중 오류가 발생했습니다.");
        }
    }
    
    @DeleteMapping("/issue/{issueId}")
    @ResponseBody
    public Map<String, String> deleteIssue(@PathVariable String issueId) {
        try {
            issueService.deleteIssue(issueId);
            return Collections.singletonMap("message", "이슈가 성공적으로 삭제되었습니다.");
        } catch (SQLException e) {
            e.printStackTrace();
            return Collections.singletonMap("error", "이슈 삭제 중 오류가 발생했습니다.");
        }
    }
}