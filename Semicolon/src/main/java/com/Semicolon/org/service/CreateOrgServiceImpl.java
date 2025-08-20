package com.Semicolon.org.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.Semicolon.org.dao.CreateOrgDAO;
import com.Semicolon.org.dto.CreateOrgDTO;

public class CreateOrgServiceImpl implements CreateOrgService {

    private CreateOrgDAO createOrgDAO;

    // XML에서 c:createOrgDAO-ref="createOrgDAO"로 주입될 생성자
    public CreateOrgServiceImpl(CreateOrgDAO createOrgDAO) {
        this.createOrgDAO = createOrgDAO;
    }

    @Override
    public void createOrganization(CreateOrgDTO organization) {
        createOrgDAO.createOrganization(organization);
    
    }
}