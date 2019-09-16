package com.capgemini.jstk.jpa.demo.persistence.repository;

import com.capgemini.jstk.jpa.demo.persistence.entity.PersonEntity;

import java.util.List;

public interface CustomizedPersonRepository {

    List<PersonEntity> findByTelephoneNumber(String telephoneNumber);
}
