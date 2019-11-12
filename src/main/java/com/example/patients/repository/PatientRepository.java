package com.example.patients.repository;

import com.example.patients.domain.Patient;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PatientRepository extends CrudRepository<Patient, Long>{

    List<Patient> findByNameContainingIgnoreCaseAndSurNameContainingIgnoreCase(String name, String surName);

}
