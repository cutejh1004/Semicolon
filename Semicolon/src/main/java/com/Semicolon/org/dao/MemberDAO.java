package com.Semicolon.org.dao;

import com.Semicolon.org.dto.MemberDTO;
import org.apache.ibatis.annotations.Param;
import java.util.List;
import java.util.Map;

// Member 테이블의 데이터 접근을 담당하는 DAO 인터페이스입니다.
// 이 인터페이스의 각 메서드는 MemberMapper.xml의 SQL 쿼리와 1:1로 매핑됩니다.
public interface MemberDAO {

    /**
     * engId를 기준으로 특정 회원의 상세 정보를 조회합니다.
     *
     * @param engId 조회할 엔지니어의 ID (PK)
     * @return 조회된 회원(엔지니어) 정보가 담긴 MemberDTO 객체
     */
    MemberDTO getMemberById(int engId);

    /**
     * engId를 기준으로 특정 회원을 삭제합니다.
     *
     * @param engId 삭제할 엔지니어의 ID
     */
    void deleteMember(int engId);

    /**
     * 특정 회원의 권한을 변경합니다.
     *
     * @param engId 변경할 엔지니어의 ID
     * @param newRole 새로 부여할 권한 (예: 'ROLE_ADMIN', 'ROLE_USER')
     * @return 업데이트된 행의 수
     */
    int updateMemberRole(@Param("engId") int engId, @Param("newRole") String newRole);

    /**
     * 다양한 필터를 적용하여 회원 목록을 검색합니다.
     * 필터 조건은 Map을 통해 동적으로 전달됩니다.
     *
     * @param filter 검색 조건을 담은 Map (예: "major", "career" 등)
     * @return 검색 조건에 맞는 회원 목록
     */
    List<MemberDTO> searchMembersByFilter(Map<String, Object> filter);
}
