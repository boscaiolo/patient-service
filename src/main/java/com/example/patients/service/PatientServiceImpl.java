package com.example.patients.service;

import com.example.patients.PatientService;
import com.example.patients.domain.Patient;
import com.example.patients.repository.PatientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
public class PatientServiceImpl implements PatientService {

    @Autowired
    private PatientRepository patientRepository;

    @Override
    public List<Patient> find(String name, String surName) {
        return patientRepository.findByNameContainingIgnoreCaseAndSurNameContainingIgnoreCase(name, surName);
    }

    @Override
    public Optional<Patient> get(long id) {
        return patientRepository.findById(id);
    }

    @Override
    public void save(@Valid Patient patient, Optional<Long> id) {
        if (id.isPresent()){
            patient.setId(id.get());
        }
        patientRepository.save(patient);
    }

    @Override
    public void delete(long id) {
        patientRepository.deleteById(id);
    }
}
