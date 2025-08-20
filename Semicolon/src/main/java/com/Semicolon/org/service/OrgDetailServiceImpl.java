package com.Semicolon.org.service;
import org.springframework.transaction.annotation.Transactional;
import com.Semicolon.org.dao.OrgDetailDAO;
import com.Semicolon.org.dto.OrgDetailDTO;
public class OrgDetailServiceImpl implements OrgDetailService {
    private OrgDetailDAO orgDetailDAO;
    public OrgDetailServiceImpl(OrgDetailDAO orgDetailDAO) { this.orgDetailDAO = orgDetailDAO; }
    @Override
    public String getOrgIdByUserId(String userId) {
        return orgDetailDAO.selectOrgIdByUserId(userId);
    }
    
    @Override
    public OrgDetailDTO getOrgById(String orId) {
        return orgDetailDAO.selectOrgById(orId);
    }
    @Override
    @Transactional
    public void modifyOrg(OrgDetailDTO org) {
        orgDetailDAO.updateOrg(org);
    }
    @Override
    @Transactional
    public void removeOrg(String orId) {
        orgDetailDAO.deleteOrg(orId);
    }
}