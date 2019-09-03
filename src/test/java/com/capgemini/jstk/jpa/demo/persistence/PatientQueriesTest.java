package com.capgemini.jstk.jpa.demo.persistence;

import com.capgemini.jstk.jpa.demo.persistence.entity.DoctorEntity;
import com.capgemini.jstk.jpa.demo.persistence.entity.PatientEntity;
import com.capgemini.jstk.jpa.demo.persistence.entity.Specialization;
import org.assertj.core.api.Assertions;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class PatientQueriesTest {

    @PersistenceContext
    private EntityManager entityManager;

    @Before
    public void loadData() {
        PatientEntity firstPatient = createPatient("James", "Bond", "999-999", "D-999", LocalDate.of(1920, 11, 11));
        PatientEntity secondPatient = createPatient("Hannibal", "Lecter", "888-888", "D-888", LocalDate.of(1933, 1, 20));
        PatientEntity thirdPatient = createPatient("Jack", "Connor", "777-777", "D-777", LocalDate.of(1690, 3, 12));
        PatientEntity fourthPatient = createPatient("Sarah", "Connor", "987987", "D-666", LocalDate.of(1984, 7, 19));

        entityManager.persist(firstPatient);
        entityManager.persist(secondPatient);
        entityManager.persist(thirdPatient);
        entityManager.persist(fourthPatient);
    }

    @Test
    public void shouldFindPatientByGivenLastName() {
        // given
        String lastName = "Connor";

        // when
        List<PatientEntity> result = entityManager.createNamedQuery("PatientEntity.findByLastName", PatientEntity.class)
                .setParameter("lastName", lastName)
                .getResultList();

        // then
        assertThat(result)
                .as("Verify that lists contains exactly one element")
                .hasSize(2);
        assertThat(result)
                .as("Verify that list only contains patients with the given last name")
                .filteredOn(patient -> lastName.equals(patient.getLastName()))
                .hasSameSizeAs(result);

    }

    private PatientEntity createPatient(String firstName, String lastName, String telephoneNumber, String patientNumber, LocalDate dateOfBirth) {
        PatientEntity patient = new PatientEntity();
        patient.setFirstName(firstName);
        patient.setLastName(lastName);
        patient.setTelephoneNumber(telephoneNumber);
        patient.setPatientNumber(patientNumber);
        patient.setDateOfBirth(dateOfBirth);
        return patient;
    }
}
