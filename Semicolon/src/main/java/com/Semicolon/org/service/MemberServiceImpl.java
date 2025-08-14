package com.Semicolon.org.service;

import com.Semicolon.org.dao.MemberDAO;
import com.Semicolon.org.dto.MemberDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

/**
 * MemberService 인터페이스를 구현하는 클래스입니다.
 * 이 클래스에서 실질적인 비즈니스 로직을 처리합니다.
 * @Service 어노테이션을 사용하여 Spring 컨테이너에 서비스 빈으로 등록합니다.
 */
@Service
public class MemberServiceImpl implements MemberService {

    // MemberDAO에 대한 의존성 주입 (Dependency Injection)
    // @Autowired를 통해 Spring이 자동으로 MemberDAO 객체를 주입해줍니다.
    private final MemberDAO memberDAO;

    @Autowired
    public MemberServiceImpl(MemberDAO memberDAO) {
        this.memberDAO = memberDAO;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public MemberDTO getMemberById(int engId) {
        return memberDAO.getMemberById(engId);
    }

    /**
     * {@inheritDoc}
     *
     * @Transactional 어노테이션은 이 메서드가 데이터베이스 트랜잭션 내에서 실행되도록 보장합니다.
     * 만약 여러 DB 작업이 있다면, 하나라도 실패하면 전체가 롤백됩니다.
     */
    @Override
    @Transactional
    public void deleteMember(int engId) {
        memberDAO.deleteMember(engId);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional
    public int updateMemberRole(int engId, String newRole) {
        return memberDAO.updateMemberRole(engId, newRole);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<MemberDTO> searchMembersByFilter(Map<String, Object> filter) {
        return memberDAO.searchMembersByFilter(filter);
    }
}
