package com.waris.insurance.entity;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "claim")
public class Claim {

    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "org.hibernate.id.UUIDGenerator")
    private String id;
    private String claimNumber;
    private String description;
    private LocalDate claimDate;
    private String claimStatus;

    @ManyToOne
    @JoinColumn(name = "policy_id")
    private InsurancePolicy policy;

    public Claim(String claimNumber, String description, LocalDate claimDate, String claimStatus, InsurancePolicy policy) {
        this.claimNumber = claimNumber;
        this.description = description;
        this.claimDate = claimDate;
        this.claimStatus = claimStatus;
        this.policy = policy;
    }
}

