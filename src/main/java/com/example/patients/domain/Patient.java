package com.example.patients.domain;

import com.example.patients.domain.GP;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.Date;

@Entity
@Table(name = "patients")
public class Patient {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Name is mandatory")
    @Size(max = 100)
    private String name;

    @NotBlank(message = "Surname is mandatory")
    @Size(max = 100)
    private String surName;

    @Temporal(TemporalType.DATE)
    private Date dob;

    @NotBlank(message = "Phone number is mandatory")
    @Size(max = 100)
    private String phoneNumber;

    @NotBlank(message = "Address is mandatory")
    @Size(max = 100)
    private String address;

    @ManyToOne
    @JoinColumn(name="gp_id")
    private GP gp;

    public GP getGp() {
        return gp;
    }

    public void setGp(GP gp) {
        this.gp = gp;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurName() {
        return surName;
    }

    public void setSurName(String surName) {
        this.surName = surName;
    }

    public Date getDob() {
        return dob;
    }

    public void setDob(Date dob) {
        this.dob = dob;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
}
