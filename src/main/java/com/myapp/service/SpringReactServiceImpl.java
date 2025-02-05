package com.myapp.service;
import com.myapp.dto.PaginationResult;
import com.myapp.dto.SpringReactDto;
import com.myapp.dto.SpringReactResponse;
import com.myapp.entity.SpringReactEntity;
import com.myapp.repo.SpringReactDao;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
public class SpringReactServiceImpl implements SpringReactService{
    @Autowired
    private SpringReactDao springReactDao;

    private static final Logger log = LogManager.getLogger(SpringReactServiceImpl.class);

    @Override
    public ResponseEntity<String> saveUsers(SpringReactDto springReactDto) {
        SpringReactEntity entity = new SpringReactEntity();
        entity.setFirstName(springReactDto.getFirstName());
        entity.setLastName(springReactDto.getLastName());
        entity.setEmailId(springReactDto.getEmailId());
        entity.setMobile(springReactDto.getMobile());
        LocalDate currentDate = LocalDate.now();
        //SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        entity.setCreatedDate(currentDate);
        if(springReactDao.existsByEmailId(springReactDto.getEmailId())){
            log.error("Email Id already exists");
           // return ResponseEntity.status(HttpStatus.OK).body("Email Id already exists");
           return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Email Id already exists");
        }
        if(springReactDao.existsByMobile(springReactDto.getMobile())){
            log.error("Mobile already exists");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Mobile already exists");
        }
        log.info("Users saved successfully");
        springReactDao.save(entity);
        return ResponseEntity.status(HttpStatus.OK).body("Users saved successfully");
    }

    @Override
    public ResponseEntity<PaginationResult> getAllUsers(int startIndex, int endIndex, LocalDate createDate) {
       // System.out.println("Page sizes startindex "+springReactDto.getStartIndex()+"endindex " +springReactDto.getEndIndex());
        PaginationResult pagination = new PaginationResult();
        List<SpringReactResponse> reactResponse = new ArrayList<>();
     //   int pageSize = endIndex-startIndex;
        Pageable pageable = PageRequest.of(startIndex,endIndex);

        log.info("Page size"+startIndex,endIndex);
        Page<SpringReactEntity> entity = springReactDao.findAllByCreatedDate(createDate,pageable);

        System.out.println("total size"+entity.getTotalElements());
        List<SpringReactResponse> responses = null;
        if(entity!=null && !entity.isEmpty()){
            reactResponse = entity.stream().map(resp-> {
                SpringReactResponse response = new SpringReactResponse();
                response.setId(resp.getId());
                response.setFirstName(resp.getFirstName());
                response.setLastName(resp.getLastName());
                response.setEmailId(resp.getEmailId());
                response.setMobile(resp.getMobile());
                response.setCreatedDate(resp.getCreatedDate());
                return response;
            }).collect(Collectors.toList());
            pagination.setResponseList(reactResponse);
            pagination.setTotalRecords(entity.getTotalElements());
            return ResponseEntity.ok().body(pagination);
        }else{
            return ResponseEntity.noContent().build();
        }
    }

    @Override
    public ResponseEntity<SpringReactResponse> getUser(Long id) {
        boolean isIdExists = springReactDao.existsById(id);
        System.out.println("isIdExists-"+isIdExists);
        if(isIdExists){
            Optional<SpringReactEntity> entity = springReactDao.findById(id);
            SpringReactResponse response = new SpringReactResponse();
            response.setId(id);
            response.setFirstName(entity.get().getFirstName());
            response.setLastName(entity.get().getLastName());
            response.setMobile(entity.get().getMobile());
            response.setEmailId(entity.get().getEmailId());
            response.setCreatedDate(entity.get().getCreatedDate());
            return ResponseEntity.ok(response);
        }else {
            return ResponseEntity.notFound().build();
        }
    }

    @Override
    public ResponseEntity<String> updateUsers(Long id,String firstName, String lastName) {
       // SpringReactEntity entity = new SpringReactEntity();
        Optional<SpringReactEntity> entity = springReactDao.findById(id);
        if (entity.isPresent()){
            entity.get().setFirstName(firstName);
            entity.get().setId(id);
            entity.get().setLastName(lastName);
            entity.get().setUpdatedDate(LocalDate.now());
            springReactDao.save(entity.get());
            return ResponseEntity.status(HttpStatus.OK).body("Updated successfully");
        }else{
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

    }
}