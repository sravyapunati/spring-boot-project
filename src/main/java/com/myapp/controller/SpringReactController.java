package com.myapp.controller;

import com.myapp.dto.PaginationResult;
import com.myapp.dto.SpringReactDto;
import com.myapp.dto.SpringReactResponse;
import com.myapp.service.SpringReactService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin("*")
@RestController
@RequestMapping(path = SpringReactController.BASEPATH, produces = "application/json")
public class SpringReactController {

    @Autowired
    private SpringReactService service;

    public static final String BASEPATH = "v1/users";
    private static final Logger log = LogManager.getLogger(SpringReactController.class);

    @PostMapping(path = "/create")
    public ResponseEntity<String> createUsers(@RequestBody SpringReactDto dto) {
        log.debug("Received request to create user: {}", dto);
        return service.saveUsers(dto);
    }

    @GetMapping("/get")
    public ResponseEntity<PaginationResult> getAllUsers(@RequestParam int startIndex, @RequestParam int endIndex){
        log.debug("fetch users");
        return service.getAllUsers(startIndex,endIndex);
    }

    @GetMapping("/getUser/{id}")
    public ResponseEntity<SpringReactResponse> getUser(@PathVariable Long id ){
        log.debug("fetch user");
        return service.getUser(id);
    }
     @PutMapping("/update/{id}")
    public ResponseEntity<String> updateUser(@PathVariable Long id,@RequestBody SpringReactDto dto){
         log.debug("update user");
         return service.updateUsers(id,dto.getFirstName(),dto.getLastName(), dto.getDesignation());
     }

}

