// com.Semicolon.pms.controller.TaskReplyController.java

package com.Semicolon.pms.controller;

import com.Semicolon.pms.dto.TaskReplyDTO;
import com.Semicolon.pms.service.TaskReplyService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/task-replies") // Issue 댓글(/replies)과 경로가 겹치지 않도록 변경
public class TaskReplyController {

    private final TaskReplyService taskReplyService;

    // 생성자 주입
    public TaskReplyController(TaskReplyService taskReplyService) {
        this.taskReplyService = taskReplyService;
    }

    // 1. 특정 일감의 댓글 목록 조회
    @GetMapping("/{taskId}")
    public ResponseEntity<List<TaskReplyDTO>> list(@PathVariable("taskId") String taskId) {
        ResponseEntity<List<TaskReplyDTO>> entity;
        try {
            List<TaskReplyDTO> replies = taskReplyService.getReplyList(taskId);
            entity = new ResponseEntity<>(replies, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            entity = new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return entity;
    }

    // 2. 새 댓글 등록
    @PostMapping("")
    public ResponseEntity<String> register(@RequestBody TaskReplyDTO dto) {
        ResponseEntity<String> entity;
        try {
            // 참고: 실제 구현 시 세션 등에서 작성자 정보를 가져와 dto에 설정해야 합니다.
            taskReplyService.registerReply(dto);
            entity = new ResponseEntity<>("SUCCESS", HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            entity = new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
        return entity;
    }

    // 3. 댓글 수정
    @RequestMapping(value = "/{replyNumber}", method = {RequestMethod.PUT, RequestMethod.PATCH})
    public ResponseEntity<String> update(@PathVariable("replyNumber") String replyNumber, @RequestBody TaskReplyDTO dto) {
        ResponseEntity<String> entity;
        try {
            dto.setReplyNumber(replyNumber);
            taskReplyService.modifyReply(dto);
            entity = new ResponseEntity<>("SUCCESS", HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            entity = new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
        return entity;
    }

    // 4. 댓글 삭제
    @DeleteMapping("/{replyNumber}")
    public ResponseEntity<String> remove(@PathVariable("replyNumber") String replyNumber) {
        ResponseEntity<String> entity;
        try {
            taskReplyService.removeReply(replyNumber);
            entity = new ResponseEntity<>("SUCCESS", HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            entity = new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
        return entity;
    }
}