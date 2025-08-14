package com.Semicolon.org.service;

import com.Semicolon.org.dto.OrgDetailDTO;
import java.util.List;

public interface OrgDetailService {

    OrgDetailDTO getOrgDetailByOrId(String orId);

    List<OrgDetailDTO> getAllOrgs();

    void createNewOrg(OrgDetailDTO orgDetailDTO);

    int updateOrg(OrgDetailDTO orgDetailDTO);
    
    int deleteOrg(String orId);
}
