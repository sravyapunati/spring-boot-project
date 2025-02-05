package com.myapp.controller;

import com.myapp.dto.PaginationResult;
import com.myapp.dto.SpringReactDto;
import com.myapp.dto.SpringReactResponse;
import com.myapp.entity.SpringReactEntity;
import com.myapp.service.SpringReactService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class SpringReactControllerTest {
    @InjectMocks
    private SpringReactController springReactController;
    @Mock
    private SpringReactService springReactService;

    private SpringReactDto springReactDto;
    private SpringReactEntity springReactEntity;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        // Initialize dto and entity for tests
        springReactDto = new SpringReactDto();
        springReactDto.setFirstName("John");
        springReactDto.setLastName("Doe");
        springReactDto.setEmailId("john.doe@example.com");
        springReactDto.setMobile("1234567890");

        springReactEntity = new SpringReactEntity();
        springReactEntity.setFirstName(springReactDto.getFirstName());
        springReactEntity.setLastName(springReactDto.getLastName());
        springReactEntity.setEmailId(springReactDto.getEmailId());
        springReactEntity.setMobile(springReactDto.getMobile());
        springReactEntity.setCreatedDate(LocalDate.now());
    }

    @Test
    void create_Success(){
        when(springReactController.createUsers(springReactDto)).thenReturn(ResponseEntity.ok().body("Users saved successfully"));
        ResponseEntity<String> response = springReactController.createUsers(springReactDto);
        assertEquals(200,response.getStatusCodeValue());
        assertEquals("Users saved successfully",response.getBody());
    }
    @Test
    void list_success(){
        List<SpringReactResponse> responseList = new ArrayList<>();
        LocalDate date = LocalDate.now();
        PaginationResult paginationResult = new PaginationResult();
        ResponseEntity<PaginationResult> mockResponse = ResponseEntity.ok(paginationResult); // Simulate success response
        when(springReactController.getAllUsers(0,10)).thenReturn(mockResponse);
        ResponseEntity<PaginationResult> response = springReactController.getAllUsers(0,10);
        assertEquals(200,response.getStatusCodeValue());

    }

    @Test
    void get_success(){
        SpringReactResponse response = new SpringReactResponse();
        ResponseEntity<SpringReactResponse> resp = ResponseEntity.ok(response);
        when(springReactController.getUser(1L)).thenReturn(resp);
        ResponseEntity<SpringReactResponse> res = springReactController.getUser(1L);
        assertEquals(resp,res);
    }

    @Test
    void update_Success(){
        when(springReactController.updateUser(1L,springReactDto)).thenReturn(ResponseEntity.ok().body("Updated successfullyy"));
        ResponseEntity<String> response = springReactController.updateUser(1L,springReactDto);
        assertEquals(200,response.getStatusCodeValue());
        assertEquals("Updated successfullyy",response.getBody());
    }

}
