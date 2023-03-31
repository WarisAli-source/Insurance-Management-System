package com.waris.insurance.dao;

import com.waris.insurance.entity.InsurancePolicy;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
public class ClaimDao {

    private Long id;
    private String claimNumber;
    private String description;
    private LocalDate claimDate;
    private String claimStatus;

    @ManyToOne
    @JoinColumn(name = "policy_id")
    private InsurancePolicy policy;

    public ClaimDao(String claimNumber, String description, LocalDate claimDate, String claimStatus, InsurancePolicy policy) {
        this.claimNumber = claimNumber;
        this.description = description;
        this.claimDate = claimDate;
        this.claimStatus = claimStatus;
        this.policy = policy;
    }
}
