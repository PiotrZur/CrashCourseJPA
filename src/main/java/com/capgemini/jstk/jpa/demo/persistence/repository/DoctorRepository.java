package com.capgemini.jstk.jpa.demo.persistence.repository;

import com.capgemini.jstk.jpa.demo.persistence.entity.DoctorEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DoctorRepository extends JpaRepository<DoctorEntity, Long> {

    DoctorEntity findByDoctorNumber(String doctorNumber);
}
