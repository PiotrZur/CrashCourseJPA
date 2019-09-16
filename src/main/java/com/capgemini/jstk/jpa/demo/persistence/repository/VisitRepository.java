package com.capgemini.jstk.jpa.demo.persistence.repository;

import com.capgemini.jstk.jpa.demo.persistence.entity.DoctorEntity;
import com.capgemini.jstk.jpa.demo.persistence.entity.VisitEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface VisitRepository extends JpaRepository<VisitEntity, Long> {

    List<VisitEntity> findByTimeBetween(LocalDateTime fromTime, LocalDateTime toTime);

    List<VisitEntity> findByDoctor(DoctorEntity doctor);
}
