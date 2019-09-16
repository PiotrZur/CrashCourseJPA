package com.capgemini.jstk.jpa.demo.persistence.repository.impl;

import com.capgemini.jstk.jpa.demo.persistence.entity.PersonEntity;
import com.capgemini.jstk.jpa.demo.persistence.entity.QPersonEntity;
import com.capgemini.jstk.jpa.demo.persistence.repository.CustomizedPersonRepository;
import com.querydsl.jpa.impl.JPAQueryFactory;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

public class CustomizedPersonRepositoryImpl implements CustomizedPersonRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<PersonEntity> findByTelephoneNumber(String telephoneNumber) {
        QPersonEntity person = QPersonEntity.personEntity;
        return new JPAQueryFactory(entityManager).selectFrom(person)
                .where(person.telephoneNumber.eq(telephoneNumber))
                .fetch();
    }
}
