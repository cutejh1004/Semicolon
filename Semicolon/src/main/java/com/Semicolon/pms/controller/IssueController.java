package com.Semicolon.pms.controller;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpSession; // HttpSession import
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.Semicolon.cmnt.dto.MemberVO; // MemberVO import
import com.Semicolon.pms.dto.IssueDto;
import com.Semicolon.pms.dto.IssueReplyDTO; // IssueReplyDTO import
import com.Semicolon.pms.dto.TaskDto;
import com.Semicolon.pms.service.IssueReplyService; // IssueReplyService import
import com.Semicolon.pms.service.IssueService;
import com.Semicolon.pms.service.TaskService;
import com.Semicolon.command.PageMaker;

@Controller
@RequestMapping("/main/project") // 기본 경로를 /main으로 변경하여 URL 일관성 확보
public class IssueController {

    private final IssueService issueService;
    private final TaskService taskService;
    private final IssueReplyService issueReplyService; // 댓글 처리를 위한 서비스

    public IssueController(IssueService issueService, TaskService taskService, IssueReplyService issueReplyService) {
        this.issueService = issueService;
        this.taskService = taskService;
        this.issueReplyService = issueReplyService;
    }

    // 이슈 목록 페이지
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
        
        // 3. 이제 this.taskService로 정상적으로 접근 가능
        List<TaskDto> taskList = this.taskService.getTaskListByProjectId(projectId);

        model.addAttribute("issueList", issueList);
        model.addAttribute("pageMaker", pageMaker);
        model.addAttribute("projectId", projectId);
        model.addAttribute("taskList", taskList);

        return "organization/pms/issue/issuelist";
    }

    // 이슈 상세 페이지
    @GetMapping("/{projectId}/issuelist/{issueId}")
    public String getIssueDetail(@PathVariable String projectId, @PathVariable String issueId, Model model) throws SQLException {
        IssueDto issue = issueService.getIssueById(issueId);
        model.addAttribute("issue", issue);
        model.addAttribute("projectId", projectId);
        return "organization/pms/issue/issuedetail";
    }

    // --- API 메소드 (JavaScript fetch와 연동) ---

    // 이슈 수정 (PUT)
    @PutMapping("/issue/update") // JSP의 fetch 경로와 일치
    @ResponseBody
    public ResponseEntity<Map<String, String>> updateIssue(@RequestBody IssueDto issue) {
        Map<String, String> response = new HashMap<>();
        try {
            issueService.updateIssue(issue);
            response.put("message", "이슈가 성공적으로 수정되었습니다.");
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (SQLException e) {
            response.put("error", "이슈 수정 중 오류 발생");
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // 이슈 삭제 (DELETE)
    @DeleteMapping("/issue/{issueId}") // JSP의 fetch 경로와 일치
    @ResponseBody
    public ResponseEntity<Map<String, String>> deleteIssue(@PathVariable String issueId) {
        Map<String, String> response = new HashMap<>();
        try {
            issueService.deleteIssue(issueId);
            response.put("message", "이슈가 삭제되었습니다.");
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (SQLException e) {
            response.put("error", "이슈 삭제 중 오류 발생");
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // 댓글 추가 (POST)
    @PostMapping("/reply") // JSP의 fetch 경로와 일치
    @ResponseBody
    public ResponseEntity<Map<String, String>> addReply(@RequestBody IssueReplyDTO reply, HttpSession session) {
        Map<String, String> response = new HashMap<>();
        MemberVO loginUser = (MemberVO) session.getAttribute("loginUser");
        if (loginUser == null) {
            response.put("error", "로그인이 필요합니다.");
            return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
        }
        
        reply.setUserId(loginUser.getUser_id()); // 하드코딩된 사용자 ID를 실제 세션 정보로 변경
        
        try {
            issueReplyService.insertReply(reply);
            response.put("message", "댓글이 추가되었습니다.");
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (SQLException e) {
            response.put("error", "댓글 추가 중 오류 발생");
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // 댓글 삭제 (DELETE)
    @DeleteMapping("/issue/reply/{replyNumber}") // JSP의 fetch 경로와 일치
    @ResponseBody
    public ResponseEntity<Map<String, String>> deleteReply(@PathVariable String replyNumber) {
        Map<String, String> response = new HashMap<>();
        try {
            issueReplyService.deleteReply(replyNumber);
            response.put("message", "댓글이 삭제되었습니다.");
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (SQLException e) {
            response.put("error", "댓글 삭제 중 오류 발생");
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}