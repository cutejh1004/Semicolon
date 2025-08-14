package com.Semicolon.org.dao;

import com.Semicolon.org.dto.OrgDetailDTO;
import java.util.List;

public interface OrgDetailDAO {
    
    OrgDetailDTO selectOrgDetailByOrId(String orId);
    
    List<OrgDetailDTO> selectAllOrgs();

    void insertNewOrg(OrgDetailDTO orgDetailDTO);
    
    int updateOrg(OrgDetailDTO orgDetailDTO);
    
    int deleteOrg(String orId);
}
