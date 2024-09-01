package org.learn.aws.start.controller;

import lombok.extern.slf4j.Slf4j;
import org.learn.aws.start.model.Account;
import org.learn.aws.start.model.Branch;
import org.learn.aws.start.service.AccountServices;
import org.learn.aws.start.service.BranchServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@CrossOrigin("*")
@Slf4j
public class Controller {

    @Autowired
    public AccountServices accountServices;

    @Autowired
    public BranchServices branchServices;

    @GetMapping("/")
    public String getMessage() {
        return "Hello World...!";
    }


    @GetMapping("/getUser")
    public ResponseEntity<Account> getAccount() {
        return new ResponseEntity<Account>(accountServices.createAccount(497, 10), HttpStatusCode.valueOf(200));
    }

//    @GetMapping("/branch/{id}")
//    public ResponseEntity<Branch> getBranch(@PathVariable("id") int branchId) {
//        log.info("Branch Id : " + branchId);
//        return new ResponseEntity<Branch>(accountServices.getBranchInfo(branchId), HttpStatusCode.valueOf(200));
//    }


    @GetMapping("/getbranchs")
    public ResponseEntity<List<Branch>> getBranchs() {
        return new ResponseEntity<>(branchServices.getAllBranch(), HttpStatusCode.valueOf(200));
    }


}
