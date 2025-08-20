package com.Semicolon.org.dao;
import org.mybatis.spring.SqlSessionTemplate;
import com.Semicolon.org.dto.OrgDetailDTO;
public class OrgDetailDAOImpl implements OrgDetailDAO {
    private SqlSessionTemplate sqlSession;
    public OrgDetailDAOImpl(SqlSessionTemplate sqlSession) { this.sqlSession = sqlSession; }
    @Override
    public String selectOrgIdByUserId(String userId) {
        return sqlSession.selectOne("com.Semicolon.org.dao.OrgDetailDAO.selectOrgIdByUserId", userId);
    }
    
    @Override
    public OrgDetailDTO selectOrgById(String orId) {
        return sqlSession.selectOne("com.Semicolon.org.dao.OrgDetailDAO.selectOrgById", orId);
    }
    @Override
    public void updateOrg(OrgDetailDTO org) {
        sqlSession.update("com.Semicolon.org.dao.OrgDetailDAO.updateOrg", org);
    }
    @Override
    public void deleteOrg(String orId) {
        sqlSession.delete("com.Semicolon.org.dao.OrgDetailDAO.deleteOrg", orId);
    }
}