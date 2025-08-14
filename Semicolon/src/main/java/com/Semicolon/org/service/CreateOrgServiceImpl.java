package com.Semicolon.org.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.Semicolon.org.dao.CreateOrgDAO;
import com.Semicolon.org.dto.CreateOrgDTO;

@Service("createOrgService")
public class CreateOrgServiceImpl implements CreateOrgService {

    @Autowired
    private CreateOrgDAO createOrgDAO;

    @Override
    @Transactional
    public void createOrganization(CreateOrgDTO organization) {
        createOrgDAO.createOrganization(organization);

        List<String> invitedMembers = organization.getInvitedMembers();
        if (invitedMembers != null && !invitedMembers.isEmpty()) {
            for (String memberId : invitedMembers) {
                Map<String, Object> params = new HashMap<>();
                params.put("orId", organization.getOrId());
                params.put("memberId", memberId);
                createOrgDAO.insertInvitedMember(params);
            }
        }
    }
}