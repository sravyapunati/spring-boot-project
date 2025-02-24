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
import org.springframework.data.domain.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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
        springReactDto.setDesignation("SoftwareEngineer");
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
        when(springReactDao.existsByEmailId(springReactDto.getEmailId())).thenReturn(true);
        ResponseEntity<String> response = springReactServiceImpl.saveUsers(springReactDto);
        assertEquals(400,response.getStatusCodeValue());
        assertEquals("Email Id already exists",response.getBody());
        verify(springReactDao, times(1)).existsByEmailId(springReactDto.getEmailId());
    }

    @Test
    public void testSaveUsers_MobileFailure() {
        when(springReactDao.existsByMobile(springReactDto.getMobile())).thenReturn(true);
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
        entity.setDesignation("Software Engineer");
        entityList.add(entity);
        Pageable page = PageRequest.of(0,1,Sort.by("id").descending());
        Page<SpringReactEntity> pageList = new PageImpl<>(entityList,page,entityList.size());
        when(springReactDao.findAll(page)).thenReturn(pageList);
        ResponseEntity<PaginationResult> resp = springReactServiceImpl.getAllUsers(springReactDto.getStartIndex(),springReactDto.getEndIndex());
        assertEquals(200,resp.getStatusCodeValue());
        assertEquals(1,resp.getBody().getTotalRecords());
        assertNotNull(resp);
        verify(springReactDao, times(1)).findAll(page);
    }

    @Test
    public void testUsersList_Failure(){
        List<SpringReactEntity> entityList= new ArrayList<>();
        LocalDate date = LocalDate.now();
        Pageable page = PageRequest.of(0,1, Sort.by("id").descending());
        Page<SpringReactEntity> mockPage = new PageImpl<>(entityList, page, entityList.size());
        when(springReactDao.findAll(page)).thenReturn(mockPage);
        ResponseEntity<PaginationResult> resp = springReactServiceImpl.getAllUsers(springReactDto.getStartIndex(),springReactDto.getEndIndex());
        assertEquals(204,resp.getStatusCodeValue());
        verify(springReactDao, times(1)).findAll(page);
    }

    @Test
    public void testUserGet_Onsuccess(){
        SpringReactEntity entity = new SpringReactEntity();
        when(springReactDao.existsById(1L)).thenReturn(true);
        when(springReactDao.findById(1L)).thenReturn(Optional.of(entity));
        ResponseEntity<SpringReactResponse> resp = springReactServiceImpl.getUser(1L);
        assertNotNull(resp);
        assertEquals(200,resp.getStatusCodeValue());
    }

    @Test
    public void testUserGet_Onfailure(){
        SpringReactEntity entity = new SpringReactEntity();
        when(springReactDao.existsById(1L)).thenReturn(false);
        ResponseEntity<SpringReactResponse> resp = springReactServiceImpl.getUser(1L);
        assertEquals(404,resp.getStatusCodeValue());
    }

    @Test
    public void testUserUpdateon_Success(){
        SpringReactEntity entity = new SpringReactEntity();
        when(springReactDao.findById(1L)).thenReturn(Optional.of(entity));
        Optional<SpringReactEntity> opt= springReactDao.findById(1L);
        ResponseEntity<String> resp = springReactServiceImpl.updateUsers(1L,"sravya","punati","software");
        assertEquals(200,resp.getStatusCodeValue());
        assertEquals("Updated successfully",resp.getBody());
        assertEquals(opt.isPresent(),true);
    }

    @Test
    public void testUserUpdateon_Failure(){
        SpringReactEntity entity = new SpringReactEntity();
        Optional<SpringReactEntity> opt= springReactDao.findById(1L);
        ResponseEntity<String> resp = springReactServiceImpl.updateUsers(1L,"sravya","punati","software");
        assertEquals(404,resp.getStatusCodeValue());
        assertEquals(opt.isPresent(),false);
    }
}