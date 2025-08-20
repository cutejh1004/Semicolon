package com.Semicolon.org.dao;

import com.Semicolon.org.dto.CreateOrgDTO;
import java.util.Map;

public interface CreateOrgDAO {
    void createOrganization(CreateOrgDTO organization);
}
//CREATE SEQUENCE ORGANIZATION_SEQ
//START WITH 1
//INCREMENT BY 1
//NOCACHE;  시퀀스 DB에 추가