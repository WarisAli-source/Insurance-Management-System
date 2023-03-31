package com.waris.insurance.controller;

import com.waris.insurance.dao.InsurancePolicyDao;
import com.waris.insurance.exception.NoSuchClientExistException;
import com.waris.insurance.service.impl.InsurancePolicyImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api")
public class InsurancePolicyController {
    @Autowired
    private InsurancePolicyImpl insurancePolicyService;

    @PostMapping("/insurancePolicy")
    public ResponseEntity<List<InsurancePolicyDao>> addInsuranceDetail(@RequestBody List<InsurancePolicyDao> insurancePolicyDaos) {
        List<InsurancePolicyDao> insurancePolicyDaoList = insurancePolicyService.saveInsurancePolicy(insurancePolicyDaos);
        log.info("Contact Added");
        return new ResponseEntity<>(insurancePolicyDaoList, HttpStatus.CREATED);
    }

    @GetMapping("/insurancePolicy")
    public ResponseEntity<List<InsurancePolicyDao>> getInsuranceDetails() {
        log.info("Contact Fetching");
        List<InsurancePolicyDao> parts = insurancePolicyService.getInsurancePolicy();
        if (parts.isEmpty()) {
            throw new NoSuchClientExistException("Contacts Not Found");
        }
        return new ResponseEntity<>(parts, HttpStatus.OK);
    }

    @GetMapping("/insurancePolicy/{id}")
    public ResponseEntity<InsurancePolicyDao> getClientDetailsById(@PathVariable String id) {

        InsurancePolicyDao insurancePolicyDao = insurancePolicyService.getInsurancePolicyById(id);
        if (insurancePolicyDao == null) {
            throw new NoSuchClientExistException("Client Not Found");
        }
        return new ResponseEntity<>(insurancePolicyDao, HttpStatus.OK);
    }

    @PutMapping("/insurancePolicy/{id}")
    public ResponseEntity<InsurancePolicyDao> updateClientDetails(@PathVariable String id, @RequestBody InsurancePolicyDao insurancePolicyDao) {
        InsurancePolicyDao insurancePolicy = insurancePolicyService.updateInsuranceById(id, insurancePolicyDao);
        if (insurancePolicy == null) {
            throw new NoSuchClientExistException("Client Not Found");
        }
        return new ResponseEntity<>(insurancePolicy, HttpStatus.OK);
    }

    @DeleteMapping("/insurancePolicy/{id}")
    public ResponseEntity<Void> deleteClientDetails(@PathVariable String id) {
        insurancePolicyService.deleteInsuranceById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
