package com.Semicolon.org.service;

import com.Semicolon.org.dao.ProjectOrgDAO;
import com.Semicolon.org.dto.ProjectOrgDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service("projectOrgService")
public class ProjectOrgServiceImpl implements ProjectOrgService {

    @Autowired
    private ProjectOrgDAO projectOrgDAO;

    @Override
    @Transactional
    public void createProjectOrg(ProjectOrgDTO projectOrg) {
        // 1. 프로젝트 생성
        projectOrgDAO.createProjectOrg(projectOrg);

        // 2. 생성자를 멤버로 추가 (필요시)
        // (별도의 PROJECT_MEMBER 테이블이 있다면 여기에 추가하는 로직 구현)

        // 3. 다른 멤버 초대
        List<String> invitedMembers = projectOrg.getInvitedMembers();
        if (invitedMembers != null && !invitedMembers.isEmpty()) {
            for (String memberId : invitedMembers) {
                Map<String, Object> params = new HashMap<>();
                // DTO에서 projectId를 가져와야 함
                params.put("projectId", projectOrg.getProjectId()); 
                params.put("memberId", memberId);
                projectOrgDAO.insertInvitedMember(params);
            }
        }
    }

    @Override
    public List<ProjectOrgDTO> getProjectOrgsByMemberId(String memberId) {
        return projectOrgDAO.getProjectOrgsByMemberId(memberId);
    }
}