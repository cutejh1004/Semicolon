package com.Semicolon.org.service;

import com.Semicolon.org.dto.ProjectOrgDTO;
import java.util.List;

public interface ProjectOrgService {
    void createProjectOrg(ProjectOrgDTO projectOrg);
    List<ProjectOrgDTO> getProjectOrgsByMemberId(String memberId);
}