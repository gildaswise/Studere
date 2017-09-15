package com.gildaswise.notabook.core;
import com.gildaswise.notabook.App;
import com.gildaswise.notabook.core.exception.EmptyStringException;
import com.gildaswise.notabook.utils.Constant;
import com.gildaswise.notabook.utils.DateUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import io.objectbox.annotation.Backlink;
import io.objectbox.annotation.Entity;
import io.objectbox.annotation.Id;
import io.objectbox.annotation.Generated;
import io.objectbox.annotation.Relation;
import io.objectbox.annotation.Transient;
import io.objectbox.Box;
import io.objectbox.BoxStore;
import io.objectbox.annotation.apihint.Internal;
import io.objectbox.exception.DbDetachedException;
import io.objectbox.exception.DbException;
import io.objectbox.relation.ToMany;

/**
 * Created by Gildaswise on 17/05/2017.
 */

@Entity
public class Subject {

    public static final int MAX_NAME_LENGTH = 16;
    public static final double DEFAULT_AVERAGE = -1.0;

    @Id private Long id;
    @Backlink(to = "subjectId") private ToMany<Score> scores;
    private String name;
    private String description;
    private boolean finished;
    private Double finalScore;
    private Date createdAt;

    public Subject(){}

    public Subject(String name, String description) {
        setName(name);
        setDescription(description);
        this.createdAt = DateUtils.now();
        this.finalScore = 0.0;
    }

    public static Double getAverage(List<Score> scores) {
        Double average = DEFAULT_AVERAGE;
        Double totalWeight = 0.0;
        if(scores != null && scores.size() > 1) {
            average = 0.0;
            for (Score score : scores) {
                App.log("Subject", "Added " + score.getValue() + " to total");
                average += score.getValue() * score.getWeight();
                App.log("Subject", "Total: " + average);
                totalWeight += Math.round(1.0 * score.getWeight());
                App.log("Subject", "Weight: " + totalWeight);
            }
            App.log("Subject", "Calculating average = " + average + "/" + totalWeight);
            average = average / totalWeight;
        }
        return average;
    }

    public Double getAverage() {
        return getAverage(getScores());
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        if(name.isEmpty()) throw new EmptyStringException();
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        if(description.isEmpty()) throw new EmptyStringException();
        this.description = description;
    }

    public boolean isFinished() {
        return finished;
    }

    public void setFinished(boolean finished) {
        this.finished = finished;
    }

    public Double getFinalScore() {
        return finalScore;
    }

    public void setFinalScore(Double finalScore) {
        this.finalScore = finalScore;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public boolean getFinished() {
        return finished;
    }

    public List<Score> getScores() {
        return scores;
    }

    @Override
    public boolean equals(Object obj) {
        if(obj != null && obj instanceof Subject) {
            Subject other = (Subject) obj;
            return other.getId().equals(this.getId()) &&
                   other.getName().equals(this.getName()) &&
                   other.getDescription().equals(this.getDescription()) &&
                   other.getFinished() == this.getFinished() &&
                   other.getCreatedAt().equals(this.getCreatedAt()) &&
                   other.getFinalScore() == this.getFinalScore();
        }
        return false;
    }
}
