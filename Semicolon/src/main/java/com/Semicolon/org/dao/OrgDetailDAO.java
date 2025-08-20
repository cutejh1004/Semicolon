package com.Semicolon.org.dao;
import com.Semicolon.org.dto.OrgDetailDTO;
public interface OrgDetailDAO {
	String selectOrgIdByUserId(String userId);
    OrgDetailDTO selectOrgById(String orId);
    void updateOrg(OrgDetailDTO org);
    void deleteOrg(String orId);
}