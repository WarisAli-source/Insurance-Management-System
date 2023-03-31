package com.waris.insurance.service;

import com.waris.insurance.dao.InsurancePolicyDao;

import java.util.List;

public interface InsurancePolicyService {
    public List<InsurancePolicyDao> saveInsurancePolicy(List<com.waris.insurance.dao.InsurancePolicyDao> insurancePolicyDaos);
    public List<InsurancePolicyDao> getInsurancePolicy();
    public InsurancePolicyDao getInsurancePolicyById(String id);
    public InsurancePolicyDao updateInsuranceById(String id, InsurancePolicyDao insurancePolicyDao);
    public void deleteInsuranceById(String id);

}
