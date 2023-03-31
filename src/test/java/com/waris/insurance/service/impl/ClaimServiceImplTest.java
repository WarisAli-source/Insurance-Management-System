package com.waris.insurance.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.waris.insurance.dao.ClaimDao;
import com.waris.insurance.entity.Claim;
import com.waris.insurance.entity.Client;
import com.waris.insurance.entity.InsurancePolicy;
import com.waris.insurance.exception.NoSuchClientExistException;
import com.waris.insurance.repository.ClaimRepository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Disabled;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ContextConfiguration(classes = {ClaimServiceImpl.class})
@ExtendWith(SpringExtension.class)
class ClaimServiceImplTest {
    @MockBean
    private ClaimRepository claimRepository;

    @Autowired
    private ClaimServiceImpl claimServiceImpl;

    @Test
    void testSaveClaims() {
        assertThrows(NoSuchClientExistException.class, () -> claimServiceImpl.saveClaims(new ArrayList<>()));
    }


    @Test
    void testSaveClaims2() {
        when(claimRepository.saveAll((Iterable<Claim>) any())).thenReturn(new ArrayList<>());

        ArrayList<ClaimDao> claimDaoList = new ArrayList<>();
        claimDaoList.add(new ClaimDao());
        List<ClaimDao> actualSaveClaimsResult = claimServiceImpl.saveClaims(claimDaoList);
        assertSame(claimDaoList, actualSaveClaimsResult);
        assertEquals(1, actualSaveClaimsResult.size());
        verify(claimRepository).saveAll((Iterable<Claim>) any());
    }

    @Test
    void testGetClaims() {
        when(claimRepository.findAll()).thenReturn(new ArrayList<>());
        assertThrows(NoSuchClientExistException.class, () -> claimServiceImpl.getClaims());
        verify(claimRepository).findAll();
    }

    @Test
    void testGetClaims2() {
        Client client = new Client();
        client.setAddress("42 Main St");
        client.setContactInformation("Getting Claims");
        client.setDateOfBirth(LocalDate.ofEpochDay(1L));
        client.setId("42");
        client.setName("Getting Claims");

        InsurancePolicy insurancePolicy = new InsurancePolicy();
        insurancePolicy.setClient(client);
        insurancePolicy.setCoverageAmount(BigDecimal.valueOf(42L));
        insurancePolicy.setEndDate(LocalDate.ofEpochDay(1L));
        insurancePolicy.setId("42");
        insurancePolicy.setPolicyNumber("42");
        insurancePolicy.setPremium(BigDecimal.valueOf(42L));
        insurancePolicy.setStartDate(LocalDate.ofEpochDay(1L));
        insurancePolicy.setType("Getting Claims");

        Claim claim = new Claim();
        claim.setClaimDate(LocalDate.ofEpochDay(1L));
        claim.setClaimNumber("42");
        claim.setClaimStatus("Getting Claims");
        claim.setDescription("The characteristics of someone or something");
        claim.setId("42");
        claim.setPolicy(insurancePolicy);

        ArrayList<Claim> claimList = new ArrayList<>();
        claimList.add(claim);
        when(claimRepository.findAll()).thenReturn(claimList);
        assertEquals(1, claimServiceImpl.getClaims().size());
        verify(claimRepository).findAll();
    }


    @Test
    void testGetClaims3() {
        when(claimRepository.findAll()).thenThrow(new NoSuchClientExistException("Getting Claims"));
        assertThrows(NoSuchClientExistException.class, () -> claimServiceImpl.getClaims());
        verify(claimRepository).findAll();
    }


    @Test
    void testGetClaimsById() {
        Client client = new Client();
        client.setAddress("42 Main St");
        client.setContactInformation("Contact Information");
        client.setDateOfBirth(LocalDate.ofEpochDay(1L));
        client.setId("42");
        client.setName("Name");

        InsurancePolicy insurancePolicy = new InsurancePolicy();
        insurancePolicy.setClient(client);
        insurancePolicy.setCoverageAmount(BigDecimal.valueOf(42L));
        insurancePolicy.setEndDate(LocalDate.ofEpochDay(1L));
        insurancePolicy.setId("42");
        insurancePolicy.setPolicyNumber("42");
        insurancePolicy.setPremium(BigDecimal.valueOf(42L));
        insurancePolicy.setStartDate(LocalDate.ofEpochDay(1L));
        insurancePolicy.setType("Type");

        Claim claim = new Claim();
        claim.setClaimDate(LocalDate.ofEpochDay(1L));
        claim.setClaimNumber("42");
        claim.setClaimStatus("Claim Status");
        claim.setDescription("The characteristics of someone or something");
        claim.setId("42");
        claim.setPolicy(insurancePolicy);
        Optional<Claim> ofResult = Optional.of(claim);
        when(claimRepository.findById((String) any())).thenReturn(ofResult);
        ClaimDao actualClaimsById = claimServiceImpl.getClaimsById("42");
        assertEquals("1970-01-02", actualClaimsById.getClaimDate().toString());
        InsurancePolicy policy = actualClaimsById.getPolicy();
        assertSame(insurancePolicy, policy);
        assertEquals("42", actualClaimsById.getClaimNumber());
        assertEquals("The characteristics of someone or something", actualClaimsById.getDescription());
        assertEquals("Claim Status", actualClaimsById.getClaimStatus());
        assertEquals("42", policy.getPremium().toString());
        assertEquals("42", policy.getCoverageAmount().toString());
        verify(claimRepository).findById((String) any());
    }

    @Test
    void testGetClaimsById2() {
        when(claimRepository.findById((String) any())).thenReturn(Optional.empty());
        assertThrows(NoSuchClientExistException.class, () -> claimServiceImpl.getClaimsById("42"));
        verify(claimRepository).findById((String) any());
    }


    @Test
    void testGetClaimsById3() {
        when(claimRepository.findById((String) any())).thenThrow(new NoSuchClientExistException("Msg"));
        assertThrows(NoSuchClientExistException.class, () -> claimServiceImpl.getClaimsById("42"));
        verify(claimRepository).findById((String) any());
    }


    @Test
    void testUpdateClaimById() {
        Client client = new Client();
        client.setAddress("42 Main St");
        client.setContactInformation("Contact Information");
        client.setDateOfBirth(LocalDate.ofEpochDay(1L));
        client.setId("42");
        client.setName("Name");

        InsurancePolicy insurancePolicy = new InsurancePolicy();
        insurancePolicy.setClient(client);
        insurancePolicy.setCoverageAmount(BigDecimal.valueOf(42L));
        insurancePolicy.setEndDate(LocalDate.ofEpochDay(1L));
        insurancePolicy.setId("42");
        insurancePolicy.setPolicyNumber("42");
        insurancePolicy.setPremium(BigDecimal.valueOf(42L));
        insurancePolicy.setStartDate(LocalDate.ofEpochDay(1L));
        insurancePolicy.setType("Type");

        Claim claim = new Claim();
        claim.setClaimDate(LocalDate.ofEpochDay(1L));
        claim.setClaimNumber("42");
        claim.setClaimStatus("Claim Status");
        claim.setDescription("The characteristics of someone or something");
        claim.setId("42");
        claim.setPolicy(insurancePolicy);
        Optional<Claim> ofResult = Optional.of(claim);

        Client client1 = new Client();
        client1.setAddress("42 Main St");
        client1.setContactInformation("Contact Information");
        client1.setDateOfBirth(LocalDate.ofEpochDay(1L));
        client1.setId("42");
        client1.setName("Name");

        InsurancePolicy insurancePolicy1 = new InsurancePolicy();
        insurancePolicy1.setClient(client1);
        insurancePolicy1.setCoverageAmount(BigDecimal.valueOf(42L));
        insurancePolicy1.setEndDate(LocalDate.ofEpochDay(1L));
        insurancePolicy1.setId("42");
        insurancePolicy1.setPolicyNumber("42");
        insurancePolicy1.setPremium(BigDecimal.valueOf(42L));
        insurancePolicy1.setStartDate(LocalDate.ofEpochDay(1L));
        insurancePolicy1.setType("Type");

        Claim claim1 = new Claim();
        claim1.setClaimDate(LocalDate.ofEpochDay(1L));
        claim1.setClaimNumber("42");
        claim1.setClaimStatus("Claim Status");
        claim1.setDescription("The characteristics of someone or something");
        claim1.setId("42");
        claim1.setPolicy(insurancePolicy1);
        when(claimRepository.save((Claim) any())).thenReturn(claim1);
        when(claimRepository.findById((String) any())).thenReturn(ofResult);
        ClaimDao claimDao = new ClaimDao();
        assertSame(claimDao, claimServiceImpl.updateClaimById("42", claimDao));
        verify(claimRepository).save((Claim) any());
        verify(claimRepository).findById((String) any());
    }


    @Test
    void testUpdateClaimById2() {
        Client client = new Client();
        client.setAddress("42 Main St");
        client.setContactInformation("Contact Information");
        client.setDateOfBirth(LocalDate.ofEpochDay(1L));
        client.setId("42");
        client.setName("Name");

        InsurancePolicy insurancePolicy = new InsurancePolicy();
        insurancePolicy.setClient(client);
        insurancePolicy.setCoverageAmount(BigDecimal.valueOf(42L));
        insurancePolicy.setEndDate(LocalDate.ofEpochDay(1L));
        insurancePolicy.setId("42");
        insurancePolicy.setPolicyNumber("42");
        insurancePolicy.setPremium(BigDecimal.valueOf(42L));
        insurancePolicy.setStartDate(LocalDate.ofEpochDay(1L));
        insurancePolicy.setType("Type");

        Claim claim = new Claim();
        claim.setClaimDate(LocalDate.ofEpochDay(1L));
        claim.setClaimNumber("42");
        claim.setClaimStatus("Claim Status");
        claim.setDescription("The characteristics of someone or something");
        claim.setId("42");
        claim.setPolicy(insurancePolicy);
        Optional<Claim> ofResult = Optional.of(claim);
        when(claimRepository.save((Claim) any())).thenThrow(new NoSuchClientExistException("Msg"));
        when(claimRepository.findById((String) any())).thenReturn(ofResult);
        assertThrows(NoSuchClientExistException.class, () -> claimServiceImpl.updateClaimById("42", new ClaimDao()));
        verify(claimRepository).save((Claim) any());
        verify(claimRepository).findById((String) any());
    }


    @Test
    void testUpdateClaimById3() {
        Client client = new Client();
        client.setAddress("42 Main St");
        client.setContactInformation("Contact Information");
        client.setDateOfBirth(LocalDate.ofEpochDay(1L));
        client.setId("42");
        client.setName("Name");

        InsurancePolicy insurancePolicy = new InsurancePolicy();
        insurancePolicy.setClient(client);
        insurancePolicy.setCoverageAmount(BigDecimal.valueOf(42L));
        insurancePolicy.setEndDate(LocalDate.ofEpochDay(1L));
        insurancePolicy.setId("42");
        insurancePolicy.setPolicyNumber("42");
        insurancePolicy.setPremium(BigDecimal.valueOf(42L));
        insurancePolicy.setStartDate(LocalDate.ofEpochDay(1L));
        insurancePolicy.setType("Type");

        Claim claim = new Claim();
        claim.setClaimDate(LocalDate.ofEpochDay(1L));
        claim.setClaimNumber("42");
        claim.setClaimStatus("Claim Status");
        claim.setDescription("The characteristics of someone or something");
        claim.setId("42");
        claim.setPolicy(insurancePolicy);
        when(claimRepository.save((Claim) any())).thenReturn(claim);
        when(claimRepository.findById((String) any())).thenReturn(Optional.empty());
        assertThrows(NoSuchClientExistException.class, () -> claimServiceImpl.updateClaimById("42", new ClaimDao()));
        verify(claimRepository).findById((String) any());
    }

    @Test
    void testDeleteClaimById() {
        Client client = new Client();
        client.setAddress("42 Main St");
        client.setContactInformation("Contact Information");
        client.setDateOfBirth(LocalDate.ofEpochDay(1L));
        client.setId("42");
        client.setName("Name");

        InsurancePolicy insurancePolicy = new InsurancePolicy();
        insurancePolicy.setClient(client);
        insurancePolicy.setCoverageAmount(BigDecimal.valueOf(42L));
        insurancePolicy.setEndDate(LocalDate.ofEpochDay(1L));
        insurancePolicy.setId("42");
        insurancePolicy.setPolicyNumber("42");
        insurancePolicy.setPremium(BigDecimal.valueOf(42L));
        insurancePolicy.setStartDate(LocalDate.ofEpochDay(1L));
        insurancePolicy.setType("Type");

        Claim claim = new Claim();
        claim.setClaimDate(LocalDate.ofEpochDay(1L));
        claim.setClaimNumber("42");
        claim.setClaimStatus("Claim Status");
        claim.setDescription("The characteristics of someone or something");
        claim.setId("42");
        claim.setPolicy(insurancePolicy);
        Optional<Claim> ofResult = Optional.of(claim);
        doNothing().when(claimRepository).deleteById((String) any());
        when(claimRepository.findById((String) any())).thenReturn(ofResult);
        claimServiceImpl.deleteClaimById("42");
        verify(claimRepository).findById((String) any());
        verify(claimRepository).deleteById((String) any());
    }
}

