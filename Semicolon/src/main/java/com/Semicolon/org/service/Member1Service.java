package com.Semicolon.org.service;

import com.Semicolon.org.dto.Member1DTO;
import java.util.List;

public interface Member1Service {
    
    List<Member1DTO> getMemberList(String orId);
    
    int updateMemberRole(String userId, String newRole);
    
    int removeMember(String userId, String orId);
}