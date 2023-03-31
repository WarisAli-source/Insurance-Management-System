package com.waris.insurance.dao;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor

public class ClientDao {

    private String id;
    private String name;
    private LocalDate dateOfBirth;
    private String address;
    private String contactInformation;

    public ClientDao(String name, LocalDate dateOfBirth, String address, String contactInformation) {
        this.name = name;
        this.dateOfBirth = dateOfBirth;
        this.address = address;
        this.contactInformation = contactInformation;
    }
}
