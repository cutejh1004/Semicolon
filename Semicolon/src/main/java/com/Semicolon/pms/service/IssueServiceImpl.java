package com.Semicolon.pms.service;

import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.Semicolon.pms.dao.IssueDAO;
import com.Semicolon.pms.dao.ReplyDAO;
import com.Semicolon.pms.dto.IssueDto;
import com.Semicolon.pms.dto.ReplyDto;

@Service("issueService")
public class IssueServiceImpl implements IssueService {
	
	@Autowired
	private IssueDAO issueDAO;
	
	@Autowired // ReplyDAO 주입
    private ReplyDAO replyDAO;

	@Override
	public List<IssueDto> getIssueList() throws SQLException {
		return issueDAO.getIssueList();
	}
	
    // 검색 기능 구현
    @Override
    public List<IssueDto> getIssueListBySearch(String searchQuery) throws SQLException {
        return issueDAO.getIssueListBySearch(searchQuery);
    }

	@Override
	public void createNewIssue(IssueDto issue) throws SQLException {
		issueDAO.insertNewIssue(issue);
	}
	
	@Override
    public IssueDto getIssueById(String issueId) throws SQLException {
        // 1. 이슈 정보를 먼저 가져옵니다.
        IssueDto issue = issueDAO.getIssueById(issueId);

        // 2. 이슈 정보가 존재하면 해당 이슈의 댓글 목록을 가져와서 Dto에 설정합니다.
        if (issue != null) {
            List<ReplyDto> replies = replyDAO.getRepliesByIssueId(issueId);
            issue.setComments(replies); // IssueDto의 setComments() 메소드 사용
        }

        return issue;
    }
	
	// 이슈 수정 메소드 구현
    @Override
    public void updateIssue(IssueDto issue) throws SQLException {
        issueDAO.updateIssue(issue);
    }
    
    // 이슈 삭제 메소드 구현
    @Override
    public void deleteIssue(String issueId) throws SQLException {
        // 이슈 삭제 전에 댓글을 먼저 삭제하여 외래키 제약조건 오류가 발생하지 않도록 합니다.
        // ReplyDAO에 deleteRepliesByIssueId(issueId) 메서드가 구현되어 있다고 가정합니다.
        replyDAO.deleteRepliesByIssueId(issueId);
        issueDAO.deleteIssue(issueId);
    }
}