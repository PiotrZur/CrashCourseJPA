package com.capgemini.jstk.jpa.demo.persistence;

import com.capgemini.jstk.jpa.demo.persistence.entity.DoctorEntity;
import com.capgemini.jstk.jpa.demo.persistence.entity.MedicalTreatmentEntity;
import com.capgemini.jstk.jpa.demo.persistence.entity.PatientEntity;
import com.capgemini.jstk.jpa.demo.persistence.entity.Specialization;
import com.capgemini.jstk.jpa.demo.persistence.entity.TreatmentType;
import com.capgemini.jstk.jpa.demo.persistence.entity.VisitEntity;
import com.capgemini.jstk.jpa.demo.persistence.repository.DoctorRepository;
import com.capgemini.jstk.jpa.demo.persistence.repository.PersonRepository;
import com.capgemini.jstk.jpa.demo.persistence.repository.VisitRepository;
import com.google.common.collect.Sets;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class VisitRepositoryTest {

    @Autowired
    private PersonRepository personRepository;

    @Autowired
    private DoctorRepository doctorRepository;

    @Autowired
    private VisitRepository visitRepository;

    @Before
    public void loadData() {
        PatientEntity firstPatient = createPatient("Jan", "Kowalski", "1234567", "P-123", LocalDate.now().minusYears(20));
        PatientEntity secondPatient = createPatient("Marcin", "Kowalski", "9871236", "P-124", LocalDate.now().minusYears(15));
        personRepository.save(firstPatient);
        personRepository.save(secondPatient);

        DoctorEntity firstDoctor = createDoctor("Anna", "Nowak", "7654321", "D-321", Specialization.FAMILY_DOCTOR);
        DoctorEntity secondDoctor = createDoctor("Jakub", "Lis", "7654321", "D-322", Specialization.FAMILY_DOCTOR);
        personRepository.save(firstDoctor);
        personRepository.save(secondDoctor);

        VisitEntity firstVisit = createVisit("Hypochondria", LocalDateTime.of(2019, 2, 7, 12, 30),
                Sets.newHashSet(createMedicalTreatment("Heart ultrasound", TreatmentType.USG)), firstPatient, firstDoctor);
        VisitEntity seoncdVisit = createVisit("Broken forearm", LocalDateTime.of(2019, 2, 7, 12, 45),
                Sets.newHashSet(createMedicalTreatment("X-ray of the right forearm", TreatmentType.RTG)), secondPatient,
                secondDoctor);
        visitRepository.save(firstVisit);
        visitRepository.save(seoncdVisit);
    }

    @Test
    public void shouldFindByDoctor() {
        // given
        DoctorEntity doctor = doctorRepository.findByDoctorNumber("D-321");

        // when
        List<VisitEntity> visits = visitRepository.findByDoctor(doctor);

        // then
        assertThat(visits).hasSize(1);
        visits.forEach(v -> assertThat(v.getDoctor().getDoctorNumber()).isEqualTo("D-321"));
    }

    @Test
    public void shouldNotFindByDoctor() {
        // given
        DoctorEntity doctor = doctorRepository.findByDoctorNumber("P-123");

        // when
        List<VisitEntity> visits = visitRepository.findByDoctor(doctor);

        // then
        assertThat(visits).hasSize(0);
    }

    @Test
    public void shouldFindByTime() {
        // when
        List<VisitEntity> visits = visitRepository.findByTimeBetween(LocalDateTime.of(2019, 2, 7, 00, 00),
                LocalDateTime.of(2019, 2, 7, 23, 59));

        // then
        assertThat(visits).hasSize(2);
        visits.forEach(v -> assertThat(v.getTime()).isAfterOrEqualTo(LocalDateTime.of(2019, 2, 7, 00, 00)));
        visits.forEach(v -> assertThat(v.getTime()).isBeforeOrEqualTo(LocalDateTime.of(2019, 2, 7, 23, 59)));
    }

    @Test
    public void shouldNotFindByTime() {
        // when
        List<VisitEntity> visits = visitRepository.findByTimeBetween(LocalDateTime.of(2018, 2, 7, 00, 00),
                LocalDateTime.of(2018, 2, 7, 23, 59));

        // then
        assertThat(visits).hasSize(0);
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

    private VisitEntity createVisit(String description, LocalDateTime time, Set<MedicalTreatmentEntity> medicalTreatments,
                                    PatientEntity patient, DoctorEntity doctor) {
        VisitEntity visit = new VisitEntity();
        visit.setDescription(description);
        visit.setTime(time);
        visit.setMedicalTreatments(medicalTreatments);
        visit.setDoctor(doctor);
        visit.setPatient(patient);
        return visit;
    }

    private MedicalTreatmentEntity createMedicalTreatment(String description, TreatmentType type) {
        MedicalTreatmentEntity treatment = new MedicalTreatmentEntity();
        treatment.setDescription(description);
        treatment.setType(type);
        return treatment;
    }
}
