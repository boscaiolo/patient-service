package com.example.patients;

import com.example.patients.domain.Patient;
import com.example.patients.repository.PatientRepository;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.collection.IsCollectionWithSize.hasSize;

@RunWith(SpringRunner.class)
@SpringBootTest
public class PatientApplicationTest {

    @Autowired
    private PatientService patientService;

    @Autowired
    private PatientRepository patientRepository;

    @Test
    public void contextLoads() {
    }

    @Test
    public void whenAPatientExistICanRetrieveIt() {
        createSamplePatient();
        Patient patient = patientService.find("", "").get(0);
        verifySamplePatient(patient);
        Optional<Patient> patient1 = patientService.get(patient.getId());
        verifySamplePatient(patient1.get());
    }

    @Test
    @Ignore
    public void whenAPatientExistICanDeleteIt() {
        createSamplePatient();
        Patient patient = patientService.find("", "").get(0);
        verifySamplePatient(patient);
        patientService.delete(patient.getId());
        List<Patient> patients = patientService.find("", "");
        Assert.assertTrue(patients.isEmpty());
    }

    private void verifySamplePatient(Patient patient) {
        Assert.assertEquals("name", patient.getName());
        Assert.assertEquals("surname", patient.getSurName());
        Assert.assertEquals("address", patient.getAddress());
        Assert.assertEquals("12345", patient.getPhoneNumber());
        Assert.assertEquals(new Date(1573516800000L), patient.getDob());
    }

    private void createSamplePatient(){
        Patient patient = new Patient();
        patient.setName("name");
        patient.setSurName("surname");
        patient.setAddress("address");
        patient.setPhoneNumber("12345");
        patient.setDob(new Date(1573516800000L));

        patientRepository.save(patient);
    }
}
