package com.Semicolon.org.service;

import com.Semicolon.org.dao.Member1DAO;
import com.Semicolon.org.dto.Member1DTO;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

public class Member1ServiceImpl implements Member1Service {

    private final Member1DAO memberDAO;

    public Member1ServiceImpl(Member1DAO memberDAO) {
        this.memberDAO = memberDAO;
    }
    
    @Override
    public List<Member1DTO> getMemberList(String orId) {
        return memberDAO.getMemberListByOrgId(orId);
    }
    
    @Override
    @Transactional
    public int updateMemberRole(String userId, String newRole) {
        return memberDAO.updateMemberRole(userId, newRole);
    }
    
    @Override
    @Transactional
    public int removeMember(String userId, String orId) {
        // 추방 기능은 MEMBER 테이블의 ROLE을 'OUT' 등으로 변경하거나,
        // ENGINEER 테이블에서 해당 멤버의 OR_ID를 NULL로 변경하는 등의 로직으로 구현 가능합니다.
        // 여기서는 MEMBER 테이블의 ROLE을 'EX-MEMBER'로 변경하는 예시로 작성하겠습니다.
        return memberDAO.updateMemberRole(userId, "EX-MEMBER");
    }
}