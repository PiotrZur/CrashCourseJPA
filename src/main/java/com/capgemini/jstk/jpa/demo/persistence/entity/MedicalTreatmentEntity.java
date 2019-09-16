package com.capgemini.jstk.jpa.demo.persistence.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;

@Entity
@Table(name = "MEDICAL_TREATMENT")
public class MedicalTreatmentEntity extends AbstractEntity {

    @Column(nullable = false)
    private String description;

    @Enumerated(EnumType.STRING)
    private TreatmentType type;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public TreatmentType getType() {
        return type;
    }

    public void setType(TreatmentType type) {
        this.type = type;
    }
}
