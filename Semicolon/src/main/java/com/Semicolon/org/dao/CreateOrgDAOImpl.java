package com.Semicolon.org.dao;

import com.Semicolon.org.dto.CreateOrgDTO;
import java.util.Map;
import org.mybatis.spring.SqlSessionTemplate; // 추가

public class CreateOrgDAOImpl implements CreateOrgDAO {

    // SqlSessionTemplate을 직접 주입받을 필드 선언
    private SqlSessionTemplate sqlSession;

    // XML에서 c:session-ref="sqlSession"으로 주입될 생성자
    public CreateOrgDAOImpl(SqlSessionTemplate sqlSession) {
        this.sqlSession = sqlSession;
    }

    @Override
    public void createOrganization(CreateOrgDTO organization) {
        sqlSession.insert("com.Semicolon.org.dao.CreateOrgDAO.createOrganization", organization);
    }

}