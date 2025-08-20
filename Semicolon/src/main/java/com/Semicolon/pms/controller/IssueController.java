// src/main/java/com/Semicolon/pms/controller/IssueController.java
package com.Semicolon.pms.controller;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.Semicolon.pms.dto.IssueDto;
import com.Semicolon.pms.service.IssueService;
import com.Semicolon.command.PageMaker;

@Controller
@RequestMapping("/main/project")
public class IssueController {

    private IssueService issueService;

    public IssueController(IssueService issueService) {
        this.issueService = issueService;
    }

    @GetMapping("/{projectId}/issuelist")
    public String getIssueList(@PathVariable("projectId") String projectId,
                               @RequestParam(value = "page", defaultValue = "1") int page,
                               @RequestParam(value = "perPageNum", defaultValue = "10") int perPageNum,
                               @RequestParam(value = "searchQuery", required = false) String searchQuery,
                               Model model) throws SQLException {
        
        PageMaker pageMaker = new PageMaker();
        pageMaker.setProjectId(projectId);
        pageMaker.setPage(page);
        pageMaker.setPerPageNum(perPageNum);
        pageMaker.setSearchQuery(searchQuery);

        pageMaker.setTotalCount(issueService.getTotalCount(pageMaker));
        
        List<IssueDto> issueList = issueService.getIssueList(pageMaker);
        
        model.addAttribute("issueList", issueList);
        model.addAttribute("pageMaker", pageMaker);
        model.addAttribute("projectId", projectId);

        return "organization/pms/issue/issuelist"; // 뷰 경로 확인 필요
    }

    // 새 이슈 등록 (POST)
    @PostMapping("/{projectId}/issuelist")
    @ResponseBody
    public ResponseEntity<Map<String, String>> createIssue(@PathVariable("projectId") String projectId,
                                                           @RequestBody IssueDto issue) {
        Map<String, String> response = new HashMap<>();
        try {
            issue.setProjectId(projectId);
            issueService.createNewIssue(issue);
            response.put("message", "이슈가 성공적으로 등록되었습니다.");
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (SQLException e) {
            e.printStackTrace();
            response.put("message", "이슈 등록 중 오류가 발생했습니다.");
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // 이슈 상세 조회
    @GetMapping("/{projectId}/issuelist/{issueId}")
    public String getIssueDetail(@PathVariable String projectId, @PathVariable String issueId, Model model) throws SQLException {
        IssueDto issue = issueService.getIssueById(issueId);
        model.addAttribute("issue", issue);
        model.addAttribute("projectId", projectId);
        return "organization/pms/issue/issuedetail"; // 상세 페이지 뷰 경로
    }

    // 이슈 수정 (실제로는 API 형태로 PUT을 사용하는 것이 더 적합)
    @PostMapping("/{projectId}/issuelist/update")
    public String updateIssue(IssueDto issue) throws SQLException {
        issueService.updateIssue(issue);
        return "redirect:/main/project/" + issue.getProjectId() + "/issuelist/" + issue.getIssueId();
    }

    // 이슈 삭제 (실제로는 API 형태로 DELETE를 사용하는 것이 더 적합)
    @PostMapping("/{projectId}/issuelist/delete")
    public String deleteIssue(@RequestParam String issueId, @RequestParam String projectId) throws SQLException {
        issueService.deleteIssue(issueId);
        return "redirect:/main/project/" + projectId + "/issuelist";
    }
}