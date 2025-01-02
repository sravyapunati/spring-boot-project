package com.myapp.service;

import com.myapp.dto.SpringReactDto;
import com.myapp.dto.SpringReactResponse;
import org.springframework.http.ResponseEntity;

import java.time.LocalDate;
import java.util.List;

public interface SpringReactService{

    ResponseEntity<String> saveUsers(SpringReactDto springReactDto);
    public ResponseEntity<List<SpringReactResponse>> getAllUsers(int startIndex, int endIndex, LocalDate createDate);

}
