package com.example.patients;

import com.example.patients.domain.Patient;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RequestMapping("/api")
public interface PatientService {

    @GetMapping("/")
    List<Patient> find(
            @RequestParam(name="name", required=false, defaultValue="") String name,
            @RequestParam(name="surName", required=false, defaultValue="") String surName);

    @GetMapping("/{id}")
    Optional<Patient> get(@PathVariable long id);

    @PostMapping("/save/{id}")
    void save(@RequestBody @Valid Patient patient, @PathVariable long id);

    @GetMapping("/delete/{id}")
    void delete(@PathVariable("id") long id);
}
