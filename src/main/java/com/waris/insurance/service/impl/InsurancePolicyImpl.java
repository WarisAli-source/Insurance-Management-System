package com.waris.insurance.service.impl;

import com.waris.insurance.dao.InsurancePolicyDao;
import com.waris.insurance.entity.InsurancePolicy;
import com.waris.insurance.exception.NoSuchClientExistException;
import com.waris.insurance.repository.InsurancePolicyRepository;
import com.waris.insurance.service.InsurancePolicyService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
public class InsurancePolicyImpl implements InsurancePolicyService {


    @Autowired
    private InsurancePolicyRepository insurancePolicyRepository;

    public List<InsurancePolicyDao> saveInsurancePolicy(List<InsurancePolicyDao> insurancePolicyDaos) {
        log.info("Adding Insurance Policy");
        if (insurancePolicyDaos.isEmpty()) {throw new NoSuchClientExistException("No Insurance Available");
        }
        List<InsurancePolicy> parts = new ArrayList<InsurancePolicy>();
        for (InsurancePolicyDao insurancePolicyDao : insurancePolicyDaos) {
            InsurancePolicy insurancePolicy = new InsurancePolicy();
            BeanUtils.copyProperties(insurancePolicyDao, insurancePolicy);
            parts.add(insurancePolicy);
        }
        insurancePolicyRepository.saveAll(parts);
        return insurancePolicyDaos;
    }


    public List<InsurancePolicyDao> getInsurancePolicy() {
        log.info("Getting Policy");
        List<InsurancePolicy> policies = insurancePolicyRepository.findAll();
        if (policies.isEmpty()) { throw new NoSuchClientExistException("Insurance not Found :" + policies);
        }
        else {
            List<InsurancePolicyDao> insurancePolicyDaos = new ArrayList<InsurancePolicyDao>();
            for (InsurancePolicy insurancePolicy : policies) {
                InsurancePolicyDao policyDao = new InsurancePolicyDao();
                BeanUtils.copyProperties(insurancePolicy, policyDao);
                insurancePolicyDaos.add(policyDao);
            }
            return insurancePolicyDaos;
        }
    }

    public InsurancePolicyDao getInsurancePolicyById(String id) {
        InsurancePolicy insurancePolicy = insurancePolicyRepository.findById(id).orElseThrow(() -> new NoSuchClientExistException("No Insurance Found for ID:" + id));
        log.info("Insurance Policy Fetched By id:" + id);
        InsurancePolicyDao insurancePolicyDao = new InsurancePolicyDao();
        BeanUtils.copyProperties(insurancePolicy, insurancePolicyDao);
        return insurancePolicyDao;

    }

    public InsurancePolicyDao updateInsuranceById(String id, InsurancePolicyDao insurancePolicyDao) {
        InsurancePolicy insurancePolicy = insurancePolicyRepository.findById(id).orElseThrow(() -> new NoSuchClientExistException("No Insurance Found for ID:" + id));
        log.info("Insurance Policy Fetched By id:" + id);
        BeanUtils.copyProperties(insurancePolicyDao, insurancePolicy);
        insurancePolicyRepository.save(insurancePolicy);
        return insurancePolicyDao;
    }

    public void deleteInsuranceById(String id) {
        InsurancePolicy insurancePolicy = insurancePolicyRepository.findById(id).orElseThrow(() -> new NoSuchClientExistException("No Insurance Found for ID:" + id));
        insurancePolicyRepository.delete(insurancePolicy);
    }
}
