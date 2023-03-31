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
@Table(name = "client")
public class Client {

    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = "id")
    private String id;
    private String name;
    private LocalDate dateOfBirth;
    private String address;
    private String contactInformation;

    public Client(String name, LocalDate dateOfBirth, String address, String contactInformation) {
        this.name = name;
        this.dateOfBirth = dateOfBirth;
        this.address = address;
        this.contactInformation = contactInformation;
    }
}

