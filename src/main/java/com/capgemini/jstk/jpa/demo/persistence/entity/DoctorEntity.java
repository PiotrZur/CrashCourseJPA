package com.capgemini.jstk.jpa.demo.persistence.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

@NamedQueries({
        @NamedQuery(name = "DoctorEntity.findAll", query = "SELECT d FROM DoctorEntity d"),
        @NamedQuery(name = "DoctorEntity.deleteByLastName", query = "DELETE FROM DoctorEntity d WHERE d.lastName = :lastName")
})
@Entity
@Table(name = "DOCTOR")
@PrimaryKeyJoinColumn(name = "doctor_id", referencedColumnName = "id")
public class DoctorEntity extends PersonEntity {

    @Column(nullable = false)
    private String doctorNumber;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Specialization specialization;

    public String getDoctorNumber() {
        return doctorNumber;
    }

    public void setDoctorNumber(String doctorNumber) {
        this.doctorNumber = doctorNumber;
    }

    public Specialization getSpecialization() {
        return specialization;
    }

    public void setSpecialization(Specialization specialization) {
        this.specialization = specialization;
    }
}
