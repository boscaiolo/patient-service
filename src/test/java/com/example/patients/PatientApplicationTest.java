package com.example.patients;

import com.example.patients.domain.Patient;
import com.example.patients.repository.PatientRepository;
import org.hamcrest.Matchers;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class PatientApplicationTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private PatientService patientService;

    @Autowired
    private PatientRepository patientRepository;

    @Test
    public void contextLoads() {
    }

    @Test
    public void whenAPatientExistFindReturnsMatchingSinglePatient() throws Exception {
        createSamplePatient();

        ResultActions result = mvc.perform(get("/api/")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content()
                        .contentTypeCompatibleWith(MediaType.APPLICATION_JSON));

        verifySamplePatient(result, "[0]");
    }

    @Test
    public void whenAPatientExistICanRetrievItByID() throws Exception {
        Patient patient = createSamplePatient();

        ResultActions result = mvc.perform(get("/api/" + patient.getId())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content()
                        .contentTypeCompatibleWith(MediaType.APPLICATION_JSON));

        verifySamplePatient(result, "");
    }

    private void verifySamplePatient(ResultActions result, String offset) throws Exception {
        result.andExpect(jsonPath("$" + offset + ".name", Matchers.is("name")))
                .andExpect(jsonPath("$" + offset + ".surName", Matchers.is("surname")))
                .andExpect(jsonPath("$" + offset + ".address", Matchers.is("address")))
                .andExpect(jsonPath("$" + offset + ".phoneNumber", Matchers.is("12345")))
                .andExpect(jsonPath("$" + offset + ".dob", Matchers.is("2019-11-12")));
    }

    private Patient createSamplePatient(){
        Patient patient = new Patient();
        patient.setName("name");
        patient.setSurName("surname");
        patient.setAddress("address");
        patient.setPhoneNumber("12345");
        patient.setDob(new Date(1573516800000L));

        return patientRepository.save(patient);
    }
}
