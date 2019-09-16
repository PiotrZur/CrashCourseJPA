package com.capgemini.jstk.jpa.demo.persistence.repository;

import com.capgemini.jstk.jpa.demo.persistence.entity.PersonEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PersonRepository extends JpaRepository<PersonEntity, Long>, CustomizedPersonRepository {
}
