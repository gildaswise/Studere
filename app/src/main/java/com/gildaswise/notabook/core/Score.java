package com.gildaswise.notabook.core;

import com.gildaswise.notabook.utils.DateUtils;

import java.util.Date;

import io.objectbox.BoxStore;
import io.objectbox.annotation.Entity;
import io.objectbox.annotation.Generated;
import io.objectbox.annotation.Id;
import io.objectbox.annotation.Relation;
import io.objectbox.annotation.apihint.Internal;
import io.objectbox.exception.DbDetachedException;
import io.objectbox.relation.ToOne;
import io.objectbox.Box;
import io.objectbox.exception.DbException;

/**
 * Created by Gildaswise on 17/05/2017.
 */

@Entity
public class Score {

    private static final Double DEFAULT_WEIGHT_VALUE = 1.0;

    @Id private Long id;
    @Relation private Subject subject;
    private Double value;
    private Double weight;
    private String description;
    private Date createdAt;
    private Date updatedAt;

    long subjectId;

    /** Used to resolve relations */
    @Internal
    @Generated(1307364262)
    transient BoxStore __boxStore;

    @Internal
    @Generated(511840344)
    transient ToOne<Subject> subjectToOne = new ToOne<>(this, Score_.subject);

    public Score(Subject subject, Double value, String description) {
        setSubject(subject);
        this.value = value;
        this.description = description;
        this.weight = DEFAULT_WEIGHT_VALUE;
        this.createdAt = DateUtils.now();
        this.updatedAt = this.createdAt;
    }

    @Generated(863362717)
    @Internal
    /** This constructor was generated by ObjectBox and may change any time. */
    public Score(Long id, Double value, Double weight, String description, Date createdAt,
            Date updatedAt, long subjectId) {
        this.id = id;
        this.value = value;
        this.weight = weight;
        this.description = description;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.subjectId = subjectId;
    }

    @Generated(226049941)
    public Score() {
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

    public long getSubjectId() {
        return subjectId;
    }

    public void setSubjectId(long subjectId) {
        this.subjectId = subjectId;
    }

    /** To-one relationship, resolved on first access. */
    @Generated(477515299)
    public Subject getSubject() {
        subject = subjectToOne.getTarget(this.subjectId);
        return subject;
    }

    /** Set the to-one relation including its ID property. */
    @Generated(33345778)
    public void setSubject(Subject subject) {
        subjectToOne.setTarget(subject);
        this.subject = subject;
    }
}
