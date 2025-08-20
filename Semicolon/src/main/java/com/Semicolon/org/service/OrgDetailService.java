package com.Semicolon.org.service;
import com.Semicolon.org.dto.OrgDetailDTO;
public interface OrgDetailService {
	String getOrgIdByUserId(String userId);
    OrgDetailDTO getOrgById(String orId);
    void modifyOrg(OrgDetailDTO org);
    void removeOrg(String orId);
}