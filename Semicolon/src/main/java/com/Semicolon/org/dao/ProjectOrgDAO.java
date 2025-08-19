package com.Semicolon.org.dao;

import com.Semicolon.org.dto.ProjectOrgDTO;
import java.util.List;
import java.util.Map;

public interface ProjectOrgDAO {
    void createProjectOrg(ProjectOrgDTO projectOrg);
    void insertInvitedMember(Map<String, Object> params);
    List<ProjectOrgDTO> getProjectOrgsByMemberId(String memberId);
}