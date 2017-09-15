package com.gildaswise.notabook.core;

import com.gildaswise.notabook.utils.DateUtils;

import java.util.Date;

import io.objectbox.annotation.Entity;
import io.objectbox.annotation.Id;
import io.objectbox.relation.ToOne;

/**
 * Created by Gildaswise on 17/05/2017.
 */

@Entity
public class Score {

    private static final Double DEFAULT_WEIGHT_VALUE = 1.0;

    @Id private Long id;
    private ToOne<Subject> subject;
    private Double value;
    private Double weight;
    private String description;
    private Date createdAt;
    private Date updatedAt;

    public Score(){}

    public Score(Subject subject, Double value, String description) {
        this.subject.setTarget(subject);
        this.value = value;
        this.description = description;
        this.weight = DEFAULT_WEIGHT_VALUE;
        this.createdAt = DateUtils.now();
        this.updatedAt = this.createdAt;
    }

    private void updateDate() {
        this.updatedAt = DateUtils.now();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getValue() {
        return value;
    }

    public void setValue(Double value) {
        this.value = value;
        updateDate();
    }

    public Double getWeight() {
        return weight;
    }

    public void setWeight(Double weight) {
        this.weight = weight;
        updateDate();
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
        updateDate();
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }

    public ToOne<Subject> getSubject() {
        return subject;
    }

    public void setSubject(ToOne<Subject> subject) {
        this.subject = subject;
    }
}
