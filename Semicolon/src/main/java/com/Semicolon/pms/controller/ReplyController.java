package com.Semicolon.pms.controller;

import java.sql.SQLException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.Semicolon.pms.dto.ReplyDto;
import com.Semicolon.pms.service.ReplyService;

@Controller
@RequestMapping("/main")
public class ReplyController {

    @Autowired
    private ReplyService replyService;

    // 댓글 추가
    @PostMapping("/reply")
    @ResponseBody
    public Map<String, String> addReply(@RequestBody ReplyDto replyDto) {
        try {
            // null 값으로 인한 오류 방지를 위해, engId와 groupId에 기본값 설정
            if (replyDto.getEngId() == null) {
                replyDto.setEngId("");
            }
            if (replyDto.getGroupId() == null) {
                replyDto.setGroupId("");
            }

            replyService.addReply(replyDto);
            return Collections.singletonMap("message", "댓글이 성공적으로 등록되었습니다.");
        } catch (SQLException e) {
            e.printStackTrace();
            return Collections.singletonMap("error", "댓글 등록 중 데이터베이스 오류가 발생했습니다.");
        } catch (Exception e) {
            e.printStackTrace();
            return Collections.singletonMap("error", "댓글 등록 중 알 수 없는 오류가 발생했습니다.");
        }
    }
    
    // 댓글 삭제
    // URL: DELETE /main/issue/reply/{replyNumber}
    @DeleteMapping("/pms/issue/reply/{replyNumber}")
    @ResponseBody
    public Map<String, String> deleteReply(@PathVariable("replyNumber") int replyNumber) {
        Map<String, String> response = new HashMap<>();
        try {
            replyService.deleteReply(replyNumber);
            response.put("message", "댓글이 성공적으로 삭제되었습니다.");
        } catch (SQLException e) {
            e.printStackTrace();
            response.put("error", "댓글 삭제 중 데이터베이스 오류가 발생했습니다.");
        } catch (Exception e) {
            e.printStackTrace();
            response.put("error", "댓글 삭제 중 알 수 없는 오류가 발생했습니다.");
        }
        return response;
    }
}