package com.waris.insurance.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "insurance_policy")
public class InsurancePolicy {
    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "org.hibernate.id.UUIDGenerator")
    private String id;
    private String policyNumber;
    private String type;
    private BigDecimal coverageAmount;
    private BigDecimal premium;
    private LocalDate startDate;
    private LocalDate endDate;

    @ManyToOne
    @JoinColumn(name = "client_id")
    private Client client;

    public InsurancePolicy(String policyNumber, String type, BigDecimal coverageAmount, BigDecimal premium, LocalDate startDate, LocalDate endDate, Client client) {
        this.policyNumber = policyNumber;
        this.type = type;
        this.coverageAmount = coverageAmount;
        this.premium = premium;
        this.startDate = startDate;
        this.endDate = endDate;
        this.client = client;
    }
}
