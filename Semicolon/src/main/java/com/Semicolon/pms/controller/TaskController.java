// com.Semicolon.pms.controller.TaskController.java

package com.Semicolon.pms.controller;

import com.Semicolon.command.PageMaker;
import com.Semicolon.pms.dto.TaskDto;
import com.Semicolon.pms.service.TaskService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/main/project") // URL 경로를 프로젝트 하위로 변경
public class TaskController {

    private final TaskService taskService;

    // 생성자 주입 방식으로 변경
    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    // 1. 일감 목록 조회 (페이지네이션 및 검색 추가)
    @GetMapping("/{projectId}/tasklist")
    public String getTaskList(@PathVariable("projectId") String projectId,
                              @RequestParam(value = "page", defaultValue = "1") int page,
                              @RequestParam(value = "perPageNum", defaultValue = "10") int perPageNum,
                              @RequestParam(value = "searchQuery", required = false) String searchQuery,
                              Model model) {
        try {
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

        } catch (SQLException e) {
            e.printStackTrace();
            // 에러 처리 로직 (예: 에러 페이지로 리디렉션)
        }
        return "organization/pms/task/tasklist"; // 뷰 경로
    }

    // 2. 새 일감 등록
    @PostMapping("/{projectId}/tasklist")
    @ResponseBody
    public ResponseEntity<Map<String, String>> createTask(@PathVariable String projectId, @RequestBody TaskDto task) {
        Map<String, String> response = new HashMap<>();
        try {
            task.setProjectId(projectId);
            taskService.createNewTask(task);
            response.put("message", "일감이 성공적으로 등록되었습니다.");
            return new ResponseEntity<>(response, HttpStatus.CREATED); // 상태 코드 201 CREATED
        } catch (SQLException e) {
            e.printStackTrace();
            response.put("message", "일감 등록 중 오류가 발생했습니다.");
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // 3. 일감 상세 조회
    @GetMapping("/{projectId}/tasklist/{taskId}")
    public String getTaskDetail(@PathVariable String projectId, @PathVariable String taskId, Model model) {
        try {
            TaskDto task = taskService.getTaskById(taskId);
            model.addAttribute("task", task);
            model.addAttribute("projectId", projectId);
        } catch (SQLException e) {
            e.printStackTrace();
            // 에러 처리 로직
        }
        return "organization/pms/task/taskdetail"; // 상세 페이지 뷰 경로
    }

    // 4. 일감 수정 (RESTful API 방식)
    @PutMapping("/{projectId}/tasklist/{taskId}")
    @ResponseBody
    public ResponseEntity<String> updateTask(@PathVariable String taskId, @RequestBody TaskDto task) {
        try {
            task.setTaskId(taskId);
            taskService.updateTask(task);
            return new ResponseEntity<>("SUCCESS", HttpStatus.OK);
        } catch (SQLException e) {
            e.printStackTrace();
            return new ResponseEntity<>("FAIL", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // 5. 일감 삭제 (RESTful API 방식)
    @DeleteMapping("/{projectId}/tasklist/{taskId}")
    @ResponseBody
    public ResponseEntity<String> deleteTask(@PathVariable String taskId) {
        try {
            taskService.deleteTask(taskId);
            return new ResponseEntity<>("SUCCESS", HttpStatus.OK);
        } catch (SQLException e) {
            e.printStackTrace();
            return new ResponseEntity<>("FAIL", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}