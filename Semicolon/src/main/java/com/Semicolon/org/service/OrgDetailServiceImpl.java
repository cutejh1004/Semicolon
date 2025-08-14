package com.Semicolon.org.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.Semicolon.org.dao.OrgDetailDAO;
import com.Semicolon.org.dto.OrgDetailDTO;

@Service
public class OrgDetailServiceImpl implements OrgDetailService {

    @Autowired
    private OrgDetailDAO orgDetailDAO;

    @Override
    public OrgDetailDTO getOrgDetailByOrId(String orId) {
        return orgDetailDAO.selectOrgDetailByOrId(orId);
    }

    @Override
    public List<OrgDetailDTO> getAllOrgs() {
        return orgDetailDAO.selectAllOrgs();
    }

    @Override
    @Transactional
    public void createNewOrg(OrgDetailDTO orgDetailDTO) {
        orgDetailDAO.insertNewOrg(orgDetailDTO);
    }

    @Override
    @Transactional
    public int updateOrg(OrgDetailDTO orgDetailDTO) {
        return orgDetailDAO.updateOrg(orgDetailDTO);
    }

    @Override
    @Transactional
    public int deleteOrg(String orId) {
        return orgDetailDAO.deleteOrg(orId);
    }
}
