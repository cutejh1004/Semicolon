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
    @RequestMapping(value = "/{taskId}", method = RequestMethod.GET)
    public ResponseEntity<List<TaskReplyDTO>> list(@PathVariable("taskId") String taskId) {
        ResponseEntity<List<TaskReplyDTO>> entity = null;
        try {
            List<TaskReplyDTO> replies = taskReplyService.selectReplyList(taskId);
            entity = new ResponseEntity<>(replies, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            entity = new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return entity;
    }

    // 2. 새 댓글 등록
    @RequestMapping(value = "", method = RequestMethod.POST)
    public ResponseEntity<String> register(@RequestBody TaskReplyDTO dto) {
        ResponseEntity<String> entity = null;
        try {
            // 참고: 실제 구현 시 세션 등에서 작성자 정보를 가져와 dto에 설정해야 합니다.
            taskReplyService.insertReply(dto);
            entity = new ResponseEntity<>("SUCCESS", HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            entity = new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
        return entity;
    }

    // 3. 댓글 수정
    @RequestMapping(value = "/{replyNumber}", method = RequestMethod.PUT)
    public ResponseEntity<String> update(@PathVariable("replyNumber") String replyNumber, @RequestBody TaskReplyDTO dto) {
        ResponseEntity<String> entity = null;
        try {
            dto.setReplyNumber(replyNumber);
            taskReplyService.updateReply(dto);
            entity = new ResponseEntity<>("SUCCESS", HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            entity = new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
        return entity;
    }

    // 4. 댓글 삭제
    @RequestMapping(value = "/{replyNumber}", method = RequestMethod.DELETE)
    public ResponseEntity<String> remove(@PathVariable("replyNumber") String replyNumber) {
        ResponseEntity<String> entity;
        try {
            taskReplyService.deleteReply(replyNumber);
            entity = new ResponseEntity<>("SUCCESS", HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            entity = new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
        return entity;
    }
}