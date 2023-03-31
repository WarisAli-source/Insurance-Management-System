package com.waris.insurance.dao;

import com.waris.insurance.entity.Client;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
public class InsurancePolicyDao {

    private Long id;
    private String policyNumber;
    private String type;
    private BigDecimal coverageAmount;
    private BigDecimal premium;
    private LocalDate startDate;
    private LocalDate endDate;

    @ManyToOne
    @JoinColumn(name = "client_id")
    private Client client;

    public InsurancePolicyDao(String policyNumber, String type, BigDecimal coverageAmount, BigDecimal premium, LocalDate startDate, LocalDate endDate, Client client) {
        this.policyNumber = policyNumber;
        this.type = type;
        this.coverageAmount = coverageAmount;
        this.premium = premium;
        this.startDate = startDate;
        this.endDate = endDate;
        this.client = client;
    }
}

