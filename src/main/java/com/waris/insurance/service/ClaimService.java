package com.waris.insurance.service;

import com.waris.insurance.dao.ClaimDao;

import java.util.List;

public interface ClaimService {
    public List<ClaimDao> saveClaims(List<ClaimDao> claimDaoList);
    public List<ClaimDao> getClaims();
    public ClaimDao getClaimsById(String id);
    public ClaimDao updateClaimById(String id, ClaimDao claimDao);
    public void deleteClaimById(String id);

}
