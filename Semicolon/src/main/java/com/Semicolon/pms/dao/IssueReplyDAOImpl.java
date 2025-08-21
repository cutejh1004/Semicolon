// src/main/java/com/Semicolon/pms/dao/IssueReplyDAOImpl.java

package com.Semicolon.pms.dao;
import java.sql.SQLException;
import java.util.List;
import org.apache.ibatis.session.SqlSession;
import com.Semicolon.pms.dto.IssueReplyDTO;

public class IssueReplyDAOImpl implements IssueReplyDAO {

    private SqlSession sqlSession;
    public void setSqlSession(SqlSession sqlSession) {
        this.sqlSession = sqlSession;
    }

    private static final String NAMESPACE = "com.Semicolon.pms.mappers.IssueReply-Mapper";

    @Override
    public List<IssueReplyDTO> selectReplyList(String issueId) throws SQLException {
        return sqlSession.selectList(NAMESPACE + ".selectReplyList", issueId);
    }

    @Override
    public void insertReply(IssueReplyDTO dto) throws SQLException {
        sqlSession.insert(NAMESPACE + ".insertReply", dto);
    }

    @Override
    public void updateReply(IssueReplyDTO dto) throws SQLException {
        sqlSession.update(NAMESPACE + ".updateReply", dto);
    }

    @Override
    public void deleteReply(String replyNumber) throws SQLException {
        sqlSession.delete(NAMESPACE + ".deleteReply", replyNumber);
    }
    
    @Override
    public void deleteRepliesByIssueId(String issueId) throws SQLException {
        sqlSession.delete(NAMESPACE + ".deleteRepliesByIssueId", issueId);
    }
}