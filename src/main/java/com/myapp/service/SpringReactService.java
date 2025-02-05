package com.myapp.service;

import com.myapp.dto.PaginationResult;
import com.myapp.dto.SpringReactDto;
import com.myapp.dto.SpringReactResponse;
import org.springframework.http.ResponseEntity;

public interface SpringReactService{

    ResponseEntity<String> saveUsers(SpringReactDto springReactDto);
    ResponseEntity<PaginationResult> getAllUsers(int startIndex, int endIndex);
    ResponseEntity<SpringReactResponse> getUser(Long id);
    ResponseEntity<String> updateUsers(Long id, String firstName,String lastName, String designation);
}
