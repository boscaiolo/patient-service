package com.example.patients;

import com.example.patients.domain.GP;
import com.example.patients.domain.Patient;
import com.example.patients.repository.GPRepository;
import com.example.patients.repository.PatientRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.hamcrest.Matchers;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.util.Date;
import java.util.Optional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class PatientApplicationTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private PatientService patientService;

    @Autowired
    private PatientRepository patientRepository;

    @Autowired
    private GPRepository gpRepository;

    @Test
    public void contextLoads() {
    }

    @Test
    public void whenAPatientExistFindReturnsMatchingSinglePatient() throws Exception {
        patientRepository.save(createSamplePatient());

        ResultActions result = mvc.perform(get("/api/")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content()
                        .contentTypeCompatibleWith(MediaType.APPLICATION_JSON));

        verifySamplePatient(result, "[0]");

        patientRepository.deleteAll();
    }

    @Test
    public void whenAPatientExistICanRetrievItByID() throws Exception {
        Patient patient = createSamplePatient();
        patient = patientRepository.save(patient);

        ResultActions result = mvc.perform(get("/api/" + patient.getId())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content()
                        .contentTypeCompatibleWith(MediaType.APPLICATION_JSON));

        verifySamplePatient(result, "");
        patientRepository.deleteAll();
    }

    @Test
    public void whenAPatientExistICanDeleteItByID() throws Exception {
        Patient patient = createSamplePatient();
        patient = patientRepository.save(patient);

        mvc.perform(get("/api/delete/" + patient.getId())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        Assert.assertEquals(Optional.empty(), patientRepository.findById(patient.getId()));
    }

    @Test
    public void whenAPatientExistICanUpdateIt() throws Exception {
        Patient patient = new Patient();
        patient.setName("John");
        patient.setSurName("Smith");
        patient.setAddress("Dundee");
        patient.setPhoneNumber("0000");

        patient = patientRepository.save(patient);

        mvc.perform(post("/api/save/" + patient.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(createSamplePatient())))
                .andExpect(status().isOk());

        ResultActions result = mvc.perform(get("/api/")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content()
                        .contentTypeCompatibleWith(MediaType.APPLICATION_JSON));

        verifySamplePatient(result, "[0]");
        patientRepository.deleteAll();
    }

    @Test
    public void whenAPatientExistICanAssignItToexistingGP() throws Exception {
        Patient patient = createSamplePatient();
        patient = patientRepository.save(patient);

        GP gp = new GP();
        gp.setName("dr");
        gp.setSurName("bob");
        gpRepository.save(gp);

        patient.setGp(gp);

        mvc.perform(post("/api/save/" + patient.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(patient)))
                .andExpect(status().isOk());


        patient = patientRepository.findById(patient.getId()).get();
        Assert.assertEquals("dr", patient.getGp().getName());
        Assert.assertEquals("bob", patient.getGp().getSurName());

        patientRepository.deleteAll();
    }

    @Test
    public void iCanCreateANewPatient() throws Exception {
        Assert.assertFalse(patientRepository.findAll().iterator().hasNext());

        Patient patient = createSamplePatient();

        mvc.perform(post("/api/save/")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(patient)))
                .andExpect(status().isOk());

        ResultActions result = mvc.perform(get("/api/")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content()
                        .contentTypeCompatibleWith(MediaType.APPLICATION_JSON));

        verifySamplePatient(result, "[0]");
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

        return patient;
    }
}
