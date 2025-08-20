// src/main/java/com/Semicolon/pms/controller/IssueReplyController.java

package com.Semicolon.pms.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.Semicolon.pms.dto.IssueReplyDTO;
import com.Semicolon.pms.service.IssueReplyService;

@RestController
@RequestMapping("/replies")
public class IssueReplyController {

    private final IssueReplyService issueReplyService;

    public IssueReplyController(IssueReplyService issueReplyService) {
        this.issueReplyService = issueReplyService;
    }

    @RequestMapping(value = "/{issueId}", method = RequestMethod.GET)
    public ResponseEntity<List<IssueReplyDTO>> list(@PathVariable("issueId") String issueId) {
        // 이 부분을 아래 코드로 채워주세요.
        ResponseEntity<List<IssueReplyDTO>> entity = null;
        try {
            // 서비스 계층을 호출하여 댓글 목록을 가져옵니다.
            List<IssueReplyDTO> replies = issueReplyService.getReplyList(issueId);
            // 성공 시, 댓글 목록과 HTTP 상태 코드 200 (OK)를 함께 반환합니다.
            entity = new ResponseEntity<>(replies, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            // 실패 시, HTTP 상태 코드 400 (Bad Request)를 반환합니다.
            entity = new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return entity;
    }

    @RequestMapping(value = "", method = RequestMethod.POST)
    public ResponseEntity<String> register(@RequestBody IssueReplyDTO dto) {
        ResponseEntity<String> entity = null;
        try {
            // 로그인 세션에서 userId, engId, groupId를 가져와 설정해야 합니다.
            // 예: dto.setUserId((String) session.getAttribute("loginUser_id"));
            // 예: dto.setEngId((String) session.getAttribute("loginUser_engId"));
            // 예: dto.setGroupId((String) session.getAttribute("loginUser_groupId"));
            issueReplyService.registerReply(dto);
            entity = new ResponseEntity<>("SUCCESS", HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            entity = new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
        return entity;
    }

    @RequestMapping(value = "/{replyNumber}", method = {RequestMethod.PUT, RequestMethod.PATCH})
    public ResponseEntity<String> update(@PathVariable("replyNumber") String replyNumber, @RequestBody IssueReplyDTO dto) { // int -> String
        ResponseEntity<String> entity = null;
        try {
            dto.setReplyNumber(replyNumber);
            issueReplyService.modifyReply(dto);
            entity = new ResponseEntity<>("SUCCESS", HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            entity = new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
        return entity;
    }

    @RequestMapping(value = "/{replyNumber}", method = RequestMethod.DELETE)
    public ResponseEntity<String> remove(@PathVariable("replyNumber") String replyNumber) { // int -> String
        ResponseEntity<String> entity = null;
        try {
            issueReplyService.removeReply(replyNumber);
            entity = new ResponseEntity<>("SUCCESS", HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            entity = new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
        return entity;
    }
}