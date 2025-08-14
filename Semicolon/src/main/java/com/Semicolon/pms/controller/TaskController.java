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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.Semicolon.pms.dto.TaskDto;
import com.Semicolon.pms.service.TaskService;

@Controller
@RequestMapping("/main")
public class TaskController {

    @Autowired
    private TaskService taskService;

    // 일감 목록 페이지 (GET /main/tasklist)
    @GetMapping("/tasklist")
    public String getTaskList(@RequestParam(value = "search", required = false) String searchQuery, Model model) {
        try {
            List<TaskDto> taskList;

            if (searchQuery != null && !searchQuery.isEmpty()) {
                // 검색어가 있을 경우
                taskList = taskService.getTaskListBySearch(searchQuery);
                model.addAttribute("searchQuery", searchQuery);
            } else {
                // 검색어가 없을 경우
                taskList = taskService.getTaskList();
            }

            model.addAttribute("taskList", taskList);
        } catch (SQLException e) {
            e.printStackTrace();
            model.addAttribute("errorMsg", "일감 목록을 불러오는 중 오류가 발생했습니다.");
            return "errorPage"; // 에러 페이지 경로
        }
        return "organization/pms/task/tasklist"; // JSP 뷰 경로
    }

    // 새 일감 등록 (POST /main/task)
    @PostMapping("/task")
    @ResponseBody
    public Map<String, String> createTask(@RequestBody TaskDto newTask) {
        try {
            taskService.createNewTask(newTask);
            return Collections.singletonMap("message", "새 일감이 성공적으로 등록되었습니다.");
        } catch (SQLException e) {
            e.printStackTrace();
            return Collections.singletonMap("error", "일감 등록 중 오류가 발생했습니다.");
        }
    }
    
    // 일감 상세 페이지 (GET /main/task/{taskId})
    @GetMapping("/task/{taskId}")
    public String getTaskDetail(@PathVariable("taskId") String taskId, Model model) {
        try {
            TaskDto task = taskService.getTaskById(taskId);
            model.addAttribute("task", task); // JSP에서 사용할 'task' 객체
        } catch (SQLException e) {
            e.printStackTrace();
            model.addAttribute("errorMsg", "일감 상세 정보를 불러오는 중 오류가 발생했습니다.");
            return "errorPage";
        }
        return "organization/pms/task/taskdetail"; // JSP 뷰 경로
    }
    
    // 일감 수정 (PUT /main/task)
    @PutMapping("/task")
    @ResponseBody
    public Map<String, String> updateTask(@RequestBody TaskDto taskDto) {
        try {
            taskService.updateTask(taskDto);
            return Collections.singletonMap("message", "일감이 성공적으로 수정되었습니다.");
        } catch (SQLException e) {
            e.printStackTrace();
            return Collections.singletonMap("error", "일감 수정 중 오류가 발생했습니다.");
        }
    }
    
    // 일감 삭제 (DELETE /main/task/{taskId})
    @DeleteMapping("/task/{taskId}")
    @ResponseBody
    public Map<String, String> deleteTask(@PathVariable String taskId) {
        try {
            taskService.deleteTask(taskId);
            return Collections.singletonMap("message", "일감이 성공적으로 삭제되었습니다.");
        } catch (SQLException e) {
            e.printStackTrace();
            return Collections.singletonMap("error", "일감 삭제 중 오류가 발생했습니다.");
        }
    }
}





//CREATE SEQUENCE TASK_SEQ
//START WITH 1       -- 시작값
//INCREMENT BY 1     -- 증가값
//NOCACHE            -- 캐시 미사용 (테스트용)
//NOCYCLE;           -- 최대값 도달 후 다시 시작 안 함