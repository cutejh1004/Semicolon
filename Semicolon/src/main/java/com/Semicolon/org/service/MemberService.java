package com.Semicolon.org.service;

import com.Semicolon.org.dto.MemberDTO;
import java.util.List;
import java.util.Map;

/**
 * 회원(엔지니어) 관리를 위한 비즈니스 로직을 정의하는 서비스 인터페이스입니다.
 * 이 인터페이스는 실제 구현체와 분리되어 역할과 책임을 명확히 합니다.
 */
public interface MemberService {

    /**
     * 특정 엔지니어의 상세 정보를 조회합니다.
     *
     * @param engId 조회할 엔지니어의 고유 ID
     * @return 조회된 엔지니어의 정보가 담긴 MemberDTO 객체
     */
    MemberDTO getMemberById(int engId);

    /**
     * 특정 엔지니어의 정보를 시스템에서 삭제합니다.
     *
     * @param engId 삭제할 엔지니어의 고유 ID
     */
    void deleteMember(int engId);

    /**
     * 특정 엔지니어의 권한(Role)을 변경합니다.
     *
     * @param engId 변경할 엔지니어의 고유 ID
     * @param newRole 새로 지정할 권한 문자열 (예: "ROLE_ADMIN")
     * @return 성공적으로 변경된 엔지니어의 수 (일반적으로 1)
     */
    int updateMemberRole(int engId, String newRole);

    /**
     * 다양한 필터(예: 전공, 경력)를 사용하여 엔지니어 목록을 검색합니다.
     *
     * @param filter 검색 조건을 담은 Map 객체
     * @return 검색 조건에 맞는 엔지니어 목록
     */
    List<MemberDTO> searchMembersByFilter(Map<String, Object> filter);
}
