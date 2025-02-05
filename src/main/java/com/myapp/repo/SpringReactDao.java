package com.myapp.repo;

import com.myapp.entity.SpringReactEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

public interface SpringReactDao extends JpaRepository<SpringReactEntity, Long> {

    boolean existsByEmailId(@Param("Email_Id")String email_Id);
    boolean existsByMobile(@Param("Mobile") String mobile);

//  /  @Query(value = "SELECT s FROM SpringReactEntity s WHERE s.createdDate = :createdDate",
//            countQuery = "SELECT count(s) FROM SpringReactEntity s WHERE s.createdDate = :createdDate")
    Page<SpringReactEntity> findAll(Pageable page);

}
