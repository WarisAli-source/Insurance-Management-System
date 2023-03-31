package com.waris.insurance.service.impl;

import com.waris.insurance.dao.ClaimDao;
import com.waris.insurance.entity.Claim;
import com.waris.insurance.exception.NoSuchClientExistException;
import com.waris.insurance.repository.ClaimRepository;
import com.waris.insurance.service.ClaimService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
public class ClaimServiceImpl implements ClaimService   {
    
    @Autowired
    private ClaimRepository claimRepository;

    public List<ClaimDao> saveClaims(List<ClaimDao> claimDaoList) {
        log.info("Adding Claims");
        if (claimDaoList.isEmpty()) {throw new NoSuchClientExistException("No Client Available");
        }
        List<Claim> claimList = new ArrayList<Claim>();
        for (ClaimDao claimDao : claimDaoList) {
            Claim claim = new Claim();
            BeanUtils.copyProperties(claimDao, claim);
            claimList.add(claim);
        }
        claimRepository.saveAll(claimList);
        return claimDaoList;

    }

    public List<ClaimDao> getClaims() {

        log.info("Getting Claims");
        List<Claim> claims = claimRepository.findAll();
        if (claims.isEmpty()) { throw new NoSuchClientExistException("Clients not Found :" + claims);
        }
        else {
            List<ClaimDao> claimDaos = new ArrayList<ClaimDao>();
            for (Claim claim : claims) {
                ClaimDao claimDao = new ClaimDao();
                BeanUtils.copyProperties(claim, claimDao);
                claimDaos.add(claimDao);
            }
            return claimDaos;
        }
    }

    public ClaimDao getClaimsById(String id) {
        Claim claim = claimRepository.findById(id).orElseThrow(() -> new NoSuchClientExistException("No Claim Found for ID:" + id));
        log.info("Claim Fetched By id:" + id);
        ClaimDao claimDao = new ClaimDao();
        BeanUtils.copyProperties(claim, claimDao);
        return claimDao;
    }

    public ClaimDao updateClaimById(String id, ClaimDao claimDao) {

        Claim claim = claimRepository.findById(id).orElseThrow(() -> new NoSuchClientExistException("No Claim Found for ID:" + id));
        log.info("Claim Fetched By id:" + id);
        BeanUtils.copyProperties(claimDao, claim);
        claimRepository.save(claim);
        return claimDao;
    }

    public void deleteClaimById(String id) {
        claimRepository.findById(id).orElseThrow(() -> new NoSuchClientExistException("No Claim Found for ID:" + id));
        log.error("Deleting Claim By ID" + id);
        claimRepository.deleteById(id);
    }
}
