package com.myapp.service;

import com.myapp.dto.PaginationResult;
import com.myapp.dto.SpringReactDto;
import com.myapp.dto.SpringReactResponse;
import com.myapp.entity.SpringReactEntity;
import com.myapp.repo.SpringReactDao;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class SpringReactServiceImplTest {

    @InjectMocks
    private SpringReactServiceImpl springReactServiceImpl; // Testing the implementation

    @Mock
    private SpringReactDao springReactDao;

    private SpringReactDto springReactDto;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);

        // Initialize DTO for tests
        springReactDto = new SpringReactDto();
        springReactDto.setFirstName("John");
        springReactDto.setLastName("Doe");
        springReactDto.setEmailId("john.doe@example.com");
        springReactDto.setMobile("1234567890");
        springReactDto.setStartIndex(0);
        springReactDto.setEndIndex(1);
    }

    @Test
    public void testSaveUsers_Success() {
        // Mock DAO behavior
        when(springReactDao.existsByEmailId(springReactDto.getEmailId())).thenReturn(false);
        when(springReactDao.existsByMobile(springReactDto.getMobile())).thenReturn(false);
        ResponseEntity<String> response = springReactServiceImpl.saveUsers(springReactDto);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Users saved successfully", response.getBody());
        verify(springReactDao, times(1)).existsByEmailId(springReactDto.getEmailId());
        verify(springReactDao, times(1)).existsByMobile(springReactDto.getMobile());

    }

    @Test
    public void testSaveUsers_EmailFailure() {
        lenient().when(springReactDao.existsByEmailId(springReactDto.getEmailId())).thenReturn(true);
        ResponseEntity<String> response = springReactServiceImpl.saveUsers(springReactDto);
        assertEquals(400,response.getStatusCodeValue());
        assertEquals("Email Id already exists",response.getBody());
        verify(springReactDao, times(1)).existsByEmailId(springReactDto.getEmailId());
    }

    @Test
    public void testSaveUsers_MobileFailure() {
        lenient().when(springReactDao.existsByMobile(springReactDto.getMobile())).thenReturn(true);
        ResponseEntity<String> response = springReactServiceImpl.saveUsers(springReactDto);
        assertEquals(400,response.getStatusCodeValue());
        assertEquals("Mobile already exists",response.getBody());
        verify(springReactDao, times(1)).existsByMobile(springReactDto.getMobile());
    }

    @Test
    public void testUsersList_Success(){
        List<SpringReactEntity> entityList= new ArrayList<>();
        LocalDate date = LocalDate.now();
        SpringReactEntity entity = new SpringReactEntity();
        entity.setId(1L);
        entity.setFirstName("Sravya");
        entity.setLastName("Punati");
        entity.setEmailId("sravya26punati@gmail.com");
        entity.setMobile("99121211211");
        entity.setCreatedDate(date);
        entityList.add(entity);
        Pageable page = PageRequest.of(0,10);
        Page<SpringReactEntity> pageList = new PageImpl<>(entityList);
        when(springReactDao.findAllByCreatedDate(date,page)).thenReturn(pageList);
        ResponseEntity<List<SpringReactResponse>> resp = springReactServiceImpl.getAllUsers(0,10,date);
        assertEquals(200,resp.getStatusCodeValue());
        assertEquals(1,resp.getBody().size());
        assertNotNull(resp);
    }

    @Test
    public void testUsersList_Failure(){
        List<SpringReactEntity> entityList= new ArrayList<>();
        LocalDate date = LocalDate.now();
        Pageable page = PageRequest.of(0,10);
        Page pageInfo = new PageImpl(entityList);
        when(springReactDao.findAllByCreatedDate(date,page)).thenReturn(pageInfo);
        ResponseEntity<List<SpringReactResponse>> resp = springReactServiceImpl.getAllUsers(0,10,date);
        assertEquals(204,resp.getStatusCodeValue());
    }
}