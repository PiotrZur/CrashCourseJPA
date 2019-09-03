package com.capgemini.jstk.jpa.demo.persistence;

import com.capgemini.jstk.jpa.demo.persistence.entity.DoctorEntity;
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
import java.util.List;

import static org.assertj.core.api.Assertions.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class DoctorQueriesTest {

    @PersistenceContext
    private EntityManager entityManager;

    @Before
    public void init() {
        DoctorEntity firstDoctor = createDoctor("James", "Bond", "999-999", "D-999", Specialization.FAMILY_DOCTOR);
        DoctorEntity secondDoctor = createDoctor("Hannibal", "Lecter", "888-888", "D-888", Specialization.PSYCHIATRIST);
        DoctorEntity thirdDoctor = createDoctor("Jack", "Sparrow", "777-777", "D-777", Specialization.NEUROLOGIST);
        DoctorEntity fourthDoctor = createDoctor("Sarah", "Connor", "987987", "D-666", Specialization.CARDIOLOGIST);

        entityManager.persist(firstDoctor);
        entityManager.persist(secondDoctor);
        entityManager.persist(thirdDoctor);
        entityManager.persist(fourthDoctor);
    }

    @Test
    public void shouldFindAllDoctors() {
        // when
        List<DoctorEntity> result = entityManager.createNamedQuery("DoctorEntity.findAll", DoctorEntity.class)
                .getResultList();

        // then
        assertThat(result).hasSize(4);
    }

    @Test
    public void shouldDeleteDoctorWithGivenLastName() {
        // given
        String lastName = "Bond";

        // when
        entityManager.createNamedQuery("DoctorEntity.deleteByLastName")
                .setParameter("lastName", lastName)
                .executeUpdate();

        // then
        List<DoctorEntity> doctors = entityManager.createNamedQuery("DoctorEntity.findAll", DoctorEntity.class).getResultList();
        assertThat(doctors)
                .as("Verify number of rows after delete")
                .hasSize(3);
        assertThat(doctors)
                .as("Verify doctors list contains only acceptable doctors")
                .filteredOn(doctor -> lastName.equals(doctor.getLastName()))
                .isEmpty();
    }

    private DoctorEntity createDoctor(String firstName, String lastName, String telephoneNumber, String doctorNumber, Specialization specialization) {
        DoctorEntity doctor = new DoctorEntity();
        doctor.setFirstName(firstName);
        doctor.setLastName(lastName);
        doctor.setTelephoneNumber(telephoneNumber);
        doctor.setDoctorNumber(doctorNumber);
        doctor.setSpecialization(specialization);
        return doctor;
    }
}
