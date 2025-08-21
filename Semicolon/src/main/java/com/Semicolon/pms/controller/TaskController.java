package com.Semicolon.pms.controller;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.Semicolon.pms.dto.IssueDto;
import com.Semicolon.pms.dto.IssueReplyDTO;
import com.Semicolon.pms.dto.TaskDto;
import com.Semicolon.pms.dto.TaskReplyDTO;
import com.Semicolon.pms.service.TaskReplyService;
import com.Semicolon.pms.service.TaskService;
import com.Semicolon.cmnt.dto.MemberVO;
import com.Semicolon.command.PageMaker;


@Controller
@RequestMapping("/main/project") // URL 경로를 프로젝트 하위로 변경
public class TaskController {

    private final TaskService taskService;
    private final TaskReplyService taskReplyService;
    // 생성자 주입 방식으로 변경
    public TaskController(TaskService taskService, TaskReplyService taskReplyService) {
        this.taskService = taskService;
        this.taskReplyService = taskReplyService;
    }

    // 1. 일감 목록 조회 (페이지네이션 및 검색 추가)
    @GetMapping("/{projectId}/tasklist")
    public String getTaskList(@PathVariable("projectId") String projectId,
                              @RequestParam(value = "page", defaultValue = "1") int page,
                              @RequestParam(value = "perPageNum", defaultValue = "10") int perPageNum,
                              @RequestParam(value = "searchQuery", required = false) String searchQuery,
                              Model model) throws SQLException {

            PageMaker pageMaker = new PageMaker();
            pageMaker.setProjectId(projectId);
            pageMaker.setPage(page);
            pageMaker.setPerPageNum(perPageNum);
            pageMaker.setSearchQuery(searchQuery);

            pageMaker.setTotalCount(taskService.getTotalCount(pageMaker));
            
            List<TaskDto> taskList = taskService.getTaskList(pageMaker);

            model.addAttribute("taskList", taskList);
            model.addAttribute("pageMaker", pageMaker);
            model.addAttribute("projectId", projectId);

        return "organization/pms/task/tasklist"; // 뷰 경로
    }
    
    @PostMapping("/{projectId}/tasklist")
    @ResponseBody
    public ResponseEntity<Map<String, String>> createTask(@RequestBody TaskDto task, @PathVariable String projectId) {
        Map<String, String> response = new HashMap<>();
        try {
            task.setProjectId(projectId); // URL의 projectId를 DTO에 설정
            taskService.createNewTask(task);
            response.put("message", "일감이 성공적으로 등록되었습니다.");
            return new ResponseEntity<>(response, HttpStatus.CREATED); // 상태 코드를 201 Created로 변경
        } catch (SQLException e) {
            response.put("error", "일감 등록 중 오류가 발생했습니다.");
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
    @GetMapping("/{projectId}/tasklist/{taskId}")
    public String getTaskDetail(@PathVariable String projectId, @PathVariable String taskId, Model model) throws SQLException {
        TaskDto task = taskService.getTaskById(taskId);
        model.addAttribute("task", task);
        model.addAttribute("projectId", projectId);
        return "organization/pms/task/taskdetail";
    }


    @PutMapping("/task/update") // JSP의 fetch 경로와 일치
    @ResponseBody
    public ResponseEntity<Map<String, String>> updateTask(@RequestBody TaskDto task) {
        Map<String, String> response = new HashMap<>();
        try {
            taskService.updateTask(task);
            response.put("message", "일감이 성공적으로 수정되었습니다.");
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (SQLException e) {
            response.put("error", "일감 수정 중 오류 발생");
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // 3. 일감 상세 조회
    @DeleteMapping("/task/{taskId}") // JSP의 fetch 경로와 일치
    @ResponseBody
    public ResponseEntity<Map<String, String>> deleteTask(@PathVariable String taskId) {
        Map<String, String> response = new HashMap<>();
        try {
            taskService.deleteTask(taskId);
            response.put("message", "일감이 삭제되었습니다.");
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (SQLException e) {
            response.put("error", "일감 삭제 중 오류 발생");
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

 // 댓글 추가 (POST)
    @PostMapping("/taskReply") // JSP의 fetch 경로와 일치
    @ResponseBody
    public ResponseEntity<Map<String, String>> addTaskReply(@RequestBody TaskReplyDTO reply, HttpSession session) {
        Map<String, String> response = new HashMap<>();
        MemberVO loginUser = (MemberVO) session.getAttribute("loginUser");
        if (loginUser == null) {
            response.put("error", "로그인이 필요합니다.");
            return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
        }
        
        reply.setUserId(loginUser.getUser_id()); // 하드코딩된 사용자 ID를 실제 세션 정보로 변경
        
        try {
            taskReplyService.insertReply(reply);
            response.put("message", "댓글이 추가되었습니다.");
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (SQLException e) {
            response.put("error", "댓글 추가 중 오류 발생");
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // 댓글 삭제 (DELETE)
    @DeleteMapping("/task/reply/{replyNumber}") // JSP의 fetch 경로와 일치
    @ResponseBody
    public ResponseEntity<Map<String, String>> deleteReply(@PathVariable String replyNumber) {
        Map<String, String> response = new HashMap<>();
        try {
            taskReplyService.deleteReply(replyNumber);
            response.put("message", "댓글이 삭제되었습니다.");
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (SQLException e) {
            response.put("error", "댓글 삭제 중 오류 발생");
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}