package com.Semicolon.pms.service;

import java.sql.SQLException;
import java.util.List;

import com.Semicolon.pms.dto.IssueDto;

public interface IssueService {
	// 이슈 목록을 조회하는 메서드
		List<IssueDto> getIssueList() throws SQLException;
		
		IssueDto getIssueById(String issueId) throws SQLException; 
		
		// 새 이슈를 등록하는 메서드
		void createNewIssue(IssueDto issue) throws SQLException;
		// 이슈 수정 메소드 추가
	    void updateIssue(IssueDto issue) throws SQLException;
	    
	    // 이슈 삭제 메소드 추가
	    void deleteIssue(String issueId) throws SQLException;
	    
	    List<IssueDto> getIssueListBySearch(String searchQuery) throws SQLException;
	}