package com.waris.insurance.controller;

import com.waris.insurance.dao.ClaimDao;
import com.waris.insurance.dao.ClientDao;
import com.waris.insurance.exception.NoSuchClientExistException;
import com.waris.insurance.service.impl.ClaimServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
public class ClaimController {
    @Autowired
    private ClaimServiceImpl claimService;


    @PostMapping("/claims")
    public ResponseEntity<List<ClaimDao>> addClaimDetail(@RequestBody List<ClaimDao> clientDaos) {

        List<ClaimDao> claimDao = claimService.saveClaims(clientDaos);
        log.info("Contact Added");
        return new ResponseEntity<>(claimDao, HttpStatus.CREATED);
    }

    @GetMapping("/claims")
    public ResponseEntity<List<ClaimDao>> getClaimDetails() {
        log.info("Claims Fetching");
        List<ClaimDao> claimDaoList = claimService.getClaims();
        if (claimDaoList.isEmpty()) {
            throw new NoSuchClientExistException("Claims Not Found");
        }
        return new ResponseEntity<>(claimDaoList, HttpStatus.OK);
    }

    @GetMapping("/claims/{id}")
    public ResponseEntity<ClaimDao> getClaimDetailsById(@PathVariable String id) {

        ClaimDao claimDao = claimService.getClaimsById(id);
        if (claimDao == null) {
            throw new NoSuchClientExistException("Claim Not Found");
        }
        return new ResponseEntity<>(claimDao, HttpStatus.OK);
    }

    @PutMapping("/claims/{id}")
    public ResponseEntity<ClaimDao> updateClaimDetails(@PathVariable String id, @RequestBody ClaimDao claimDao) {
        ClaimDao claimDaoList = claimService.updateClaimById(id, claimDao);
        if (claimDaoList == null) {
            throw new NoSuchClientExistException("Claim Not Found");
        }
        return new ResponseEntity<>(claimDaoList, HttpStatus.OK);
    }

    @DeleteMapping("/claims/{id}")
    public ResponseEntity<Void> deleteClaimDetails(@PathVariable String id) {
        claimService.deleteClaimById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
