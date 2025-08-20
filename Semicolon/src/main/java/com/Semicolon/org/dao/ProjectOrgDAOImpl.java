package com.Semicolon.org.dao;

import com.Semicolon.org.dto.ProjectOrgDTO;
import java.util.List;
import java.util.Map;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class ProjectOrgDAOImpl implements ProjectOrgDAO {

    private final SqlSessionTemplate sqlSession;

    // 생성자 주입
    public ProjectOrgDAOImpl(SqlSessionTemplate sqlSession) {
        this.sqlSession = sqlSession;
    }
    
    @Override
    public void createProjectOrg(ProjectOrgDTO projectOrg) {
        // "ProjectOrgMapper.createProjectOrg"는 MyBatis 매퍼 파일에 정의된
        // 프로젝트 조직 생성 쿼리의 ID를 가정합니다.
        sqlSession.insert("ProjectOrgMapper.createProjectOrg", projectOrg);
    }
    
    @Override
    public void insertInvitedMember(Map<String, Object> params) {
        // "ProjectOrgMapper.insertInvitedMember"는 MyBatis 매퍼 파일에 정의된
        // 초대된 멤버 삽입 쿼리의 ID를 가정합니다.
        sqlSession.insert("ProjectOrgMapper.insertInvitedMember", params);
    }
    
    @Override
    public List<ProjectOrgDTO> getProjectOrgsByMemberId(String memberId) {
        // "ProjectOrgMapper.getProjectOrgsByMemberId"는 MyBatis 매퍼 파일에 정의된
        // 멤버 ID로 프로젝트 조직 목록을 조회하는 쿼리의 ID를 가정합니다.
        return sqlSession.selectList("ProjectOrgMapper.getProjectOrgsByMemberId", memberId);
    }
}