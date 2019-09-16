package com.capgemini.jstk.jpa.demo.persistence;

import com.capgemini.jstk.jpa.demo.persistence.entity.DoctorEntity;
import com.capgemini.jstk.jpa.demo.persistence.entity.PatientEntity;
import com.capgemini.jstk.jpa.demo.persistence.entity.PersonEntity;
import com.capgemini.jstk.jpa.demo.persistence.entity.Specialization;
import com.capgemini.jstk.jpa.demo.persistence.repository.PersonRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class PersonRepositoryTest {

    @Autowired
    private PersonRepository personRepository;

    @Before
    public void loadData() {
        PersonEntity firstPerson = createPatient("Jan", "Kowalski", "1234567", "P-123", LocalDate.now().minusYears(20));
        PersonEntity secondPerson = createPatient("Marcin", "Kowalski", "9871236", "P-124", LocalDate.now().minusYears(15));
        PersonEntity thirdPerson = createPatient("Maria", "Nowak", "7654321", "P-125", LocalDate.now().minusYears(35));
        PersonEntity fourthPerson = createDoctor("Jan", "Nowak", "7654321", "D-321", Specialization.CARDIOLOGIST);

        personRepository.save(firstPerson);
        personRepository.save(secondPerson);
        personRepository.save(thirdPerson);
        personRepository.save(fourthPerson);
    }

    @Test
    public void shouldFindByTelephoneNumber() {
        // when
        List<PersonEntity> result = personRepository.findByTelephoneNumber("7654321");

        // then
        assertThat(result).hasSize(2);
        assertThat(result.stream().allMatch(c -> c.getTelephoneNumber().equals("7654321"))).isTrue();
    }

    @Test
    public void shouldNotFindByTelephoneNumber() {
        // when
        List<PersonEntity> result = personRepository.findByTelephoneNumber("7654321321");

        // then
        assertThat(result).hasSize(0);
    }

    private PatientEntity createPatient(String firstName, String lastName, String telephoneNumber, String patientNumber,
                                        LocalDate dateOfBirth) {

        PatientEntity patient = new PatientEntity();
        patient.setFirstName(firstName);
        patient.setLastName(lastName);
        patient.setEmail(firstName + lastName + "@.st.com");
        patient.setTelephoneNumber(telephoneNumber);
        patient.setDateOfBirth(dateOfBirth);
        patient.setPatientNumber(patientNumber);
        return patient;
    }

    private DoctorEntity createDoctor(String firstName, String lastName, String telephoneNumber, String doctorNumber,
                                      Specialization specialization) {

        DoctorEntity doctor = new DoctorEntity();
        doctor.setFirstName(firstName);
        doctor.setLastName(lastName);
        doctor.setEmail(firstName + lastName + "@.st.com");
        doctor.setTelephoneNumber(telephoneNumber);
        doctor.setSpecialization(specialization);
        doctor.setDoctorNumber(doctorNumber);
        return doctor;
    }
}
