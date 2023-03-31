package com.waris.insurance.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.waris.insurance.dao.InsurancePolicyDao;
import com.waris.insurance.entity.Client;
import com.waris.insurance.entity.InsurancePolicy;
import com.waris.insurance.exception.NoSuchClientExistException;
import com.waris.insurance.repository.InsurancePolicyRepository;

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

@ContextConfiguration(classes = {InsurancePolicyImpl.class})
@ExtendWith(SpringExtension.class)
class InsurancePolicyImplTest {
    @Autowired
    private InsurancePolicyImpl insurancePolicyImpl;

    @MockBean
    private InsurancePolicyRepository insurancePolicyRepository;


    @Test
    void testSaveInsurancePolicy() {
        assertThrows(NoSuchClientExistException.class, () -> insurancePolicyImpl.saveInsurancePolicy(new ArrayList<>()));
    }

    @Test
    void testSaveInsurancePolicy2() {
        when(insurancePolicyRepository.saveAll((Iterable<InsurancePolicy>) any())).thenReturn(new ArrayList<>());

        ArrayList<InsurancePolicyDao> insurancePolicyDaoList = new ArrayList<>();
        insurancePolicyDaoList.add(new InsurancePolicyDao());
        List<InsurancePolicyDao> actualSaveInsurancePolicyResult = insurancePolicyImpl
                .saveInsurancePolicy(insurancePolicyDaoList);
        assertSame(insurancePolicyDaoList, actualSaveInsurancePolicyResult);
        assertEquals(1, actualSaveInsurancePolicyResult.size());
        verify(insurancePolicyRepository).saveAll((Iterable<InsurancePolicy>) any());
    }


    @Test
    void testSaveInsurancePolicy4() {
        when(insurancePolicyRepository.saveAll((Iterable<InsurancePolicy>) any()))
                .thenThrow(new NoSuchClientExistException("Adding Insurance Policy"));

        ArrayList<InsurancePolicyDao> insurancePolicyDaoList = new ArrayList<>();
        insurancePolicyDaoList.add(new InsurancePolicyDao());
        assertThrows(NoSuchClientExistException.class,
                () -> insurancePolicyImpl.saveInsurancePolicy(insurancePolicyDaoList));
        verify(insurancePolicyRepository).saveAll((Iterable<InsurancePolicy>) any());
    }


    @Test
    void testGetInsurancePolicy() {
        when(insurancePolicyRepository.findAll()).thenReturn(new ArrayList<>());
        assertThrows(NoSuchClientExistException.class, () -> insurancePolicyImpl.getInsurancePolicy());
        verify(insurancePolicyRepository).findAll();
    }


    @Test
    void testGetInsurancePolicy2() {
        Client client = new Client();
        client.setAddress("42 Main St");
        client.setContactInformation("Getting Policy");
        client.setDateOfBirth(LocalDate.ofEpochDay(1L));
        client.setId("42");
        client.setName("Getting Policy");

        InsurancePolicy insurancePolicy = new InsurancePolicy();
        insurancePolicy.setClient(client);
        insurancePolicy.setCoverageAmount(BigDecimal.valueOf(42L));
        insurancePolicy.setEndDate(LocalDate.ofEpochDay(1L));
        insurancePolicy.setId("42");
        insurancePolicy.setPolicyNumber("42");
        insurancePolicy.setPremium(BigDecimal.valueOf(42L));
        insurancePolicy.setStartDate(LocalDate.ofEpochDay(1L));
        insurancePolicy.setType("Getting Policy");

        ArrayList<InsurancePolicy> insurancePolicyList = new ArrayList<>();
        insurancePolicyList.add(insurancePolicy);
        when(insurancePolicyRepository.findAll()).thenReturn(insurancePolicyList);
        assertEquals(1, insurancePolicyImpl.getInsurancePolicy().size());
        verify(insurancePolicyRepository).findAll();
    }

    @Test
    void testGetInsurancePolicy3() {
        when(insurancePolicyRepository.findAll()).thenThrow(new NoSuchClientExistException("Getting Policy"));
        assertThrows(NoSuchClientExistException.class, () -> insurancePolicyImpl.getInsurancePolicy());
        verify(insurancePolicyRepository).findAll();
    }


    @Test
    void testGetInsurancePolicyById() {
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
        BigDecimal valueOfResult = BigDecimal.valueOf(42L);
        insurancePolicy.setPremium(valueOfResult);
        insurancePolicy.setStartDate(LocalDate.ofEpochDay(1L));
        insurancePolicy.setType("Type");
        Optional<InsurancePolicy> ofResult = Optional.of(insurancePolicy);
        when(insurancePolicyRepository.findById((String) any())).thenReturn(ofResult);
        InsurancePolicyDao actualInsurancePolicyById = insurancePolicyImpl.getInsurancePolicyById("42");
        assertSame(client, actualInsurancePolicyById.getClient());
        assertEquals("Type", actualInsurancePolicyById.getType());
        BigDecimal coverageAmount = actualInsurancePolicyById.getCoverageAmount();
        assertEquals(valueOfResult, coverageAmount);
        assertEquals("42", actualInsurancePolicyById.getPolicyNumber());
        assertEquals("1970-01-02", actualInsurancePolicyById.getEndDate().toString());
        BigDecimal premium = actualInsurancePolicyById.getPremium();
        assertEquals(coverageAmount, premium);
        assertEquals("1970-01-02", actualInsurancePolicyById.getStartDate().toString());
        assertEquals("42", premium.toString());
        assertEquals("42", coverageAmount.toString());
        verify(insurancePolicyRepository).findById((String) any());
    }


    @Test
    void testGetInsurancePolicyById2() {
        when(insurancePolicyRepository.findById((String) any())).thenReturn(Optional.empty());
        assertThrows(NoSuchClientExistException.class, () -> insurancePolicyImpl.getInsurancePolicyById("42"));
        verify(insurancePolicyRepository).findById((String) any());
    }


    @Test
    void testUpdateInsuranceById() {
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
        Optional<InsurancePolicy> ofResult = Optional.of(insurancePolicy);

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
        when(insurancePolicyRepository.save((InsurancePolicy) any())).thenReturn(insurancePolicy1);
        when(insurancePolicyRepository.findById((String) any())).thenReturn(ofResult);
        InsurancePolicyDao insurancePolicyDao = new InsurancePolicyDao();
        assertSame(insurancePolicyDao, insurancePolicyImpl.updateInsuranceById("42", insurancePolicyDao));
        verify(insurancePolicyRepository).save((InsurancePolicy) any());
        verify(insurancePolicyRepository).findById((String) any());
    }


    @Test
    void testUpdateInsuranceById2() {
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
        Optional<InsurancePolicy> ofResult = Optional.of(insurancePolicy);
        when(insurancePolicyRepository.save((InsurancePolicy) any())).thenThrow(new NoSuchClientExistException("Msg"));
        when(insurancePolicyRepository.findById((String) any())).thenReturn(ofResult);
        assertThrows(NoSuchClientExistException.class,
                () -> insurancePolicyImpl.updateInsuranceById("42", new InsurancePolicyDao()));
        verify(insurancePolicyRepository).save((InsurancePolicy) any());
        verify(insurancePolicyRepository).findById((String) any());
    }


    @Test
    void testUpdateInsuranceById3() {
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
        when(insurancePolicyRepository.save((InsurancePolicy) any())).thenReturn(insurancePolicy);
        when(insurancePolicyRepository.findById((String) any())).thenReturn(Optional.empty());
        assertThrows(NoSuchClientExistException.class,
                () -> insurancePolicyImpl.updateInsuranceById("42", new InsurancePolicyDao()));
        verify(insurancePolicyRepository).findById((String) any());
    }


    @Test
    void testDeleteInsuranceById() {
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
        Optional<InsurancePolicy> ofResult = Optional.of(insurancePolicy);
        doNothing().when(insurancePolicyRepository).delete((InsurancePolicy) any());
        when(insurancePolicyRepository.findById((String) any())).thenReturn(ofResult);
        insurancePolicyImpl.deleteInsuranceById("42");
        verify(insurancePolicyRepository).findById((String) any());
        verify(insurancePolicyRepository).delete((InsurancePolicy) any());
    }


    @Test
    void testDeleteInsuranceById2() {
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
        Optional<InsurancePolicy> ofResult = Optional.of(insurancePolicy);
        doThrow(new NoSuchClientExistException("Msg")).when(insurancePolicyRepository).delete((InsurancePolicy) any());
        when(insurancePolicyRepository.findById((String) any())).thenReturn(ofResult);
        assertThrows(NoSuchClientExistException.class, () -> insurancePolicyImpl.deleteInsuranceById("42"));
        verify(insurancePolicyRepository).findById((String) any());
        verify(insurancePolicyRepository).delete((InsurancePolicy) any());
    }

    @Test
    void testDeleteInsuranceById3() {
        doNothing().when(insurancePolicyRepository).delete((InsurancePolicy) any());
        when(insurancePolicyRepository.findById((String) any())).thenReturn(Optional.empty());
        assertThrows(NoSuchClientExistException.class, () -> insurancePolicyImpl.deleteInsuranceById("42"));
        verify(insurancePolicyRepository).findById((String) any());
    }
}

