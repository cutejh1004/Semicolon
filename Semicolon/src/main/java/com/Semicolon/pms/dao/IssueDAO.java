package com.Semicolon.pms.dao;

import java.sql.SQLException;
import java.util.List;

import com.Semicolon.pms.dto.IssueDto;

public interface IssueDAO {
    // 이슈 목록을 조회하는 메서드.
    List<IssueDto> getIssueList() throws SQLException;

    // 검색 기능: 이슈 제목으로 이슈 목록을 조회하는 메서드 추가
    List<IssueDto> getIssueListBySearch(String searchQuery) throws SQLException;
    
    // 새 이슈를 등록하는 메서드.
    void insertNewIssue(IssueDto issue) throws SQLException;
    
    // 추가: 특정 이슈 상세 정보를 가져오는 메소드 선언
    IssueDto getIssueById(String issueId) throws SQLException;
    
    // 이슈 수정 메소드 추가
    void updateIssue(IssueDto issue) throws SQLException;
    
    // 이슈 삭제 메소드 추가
    void deleteIssue(String issueId) throws SQLException;
}