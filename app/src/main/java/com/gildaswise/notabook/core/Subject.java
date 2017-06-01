package com.gildaswise.notabook.core;
import com.gildaswise.notabook.App;
import com.gildaswise.notabook.core.exception.EmptyStringException;
import com.gildaswise.notabook.utils.Constant;
import com.gildaswise.notabook.utils.DateUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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

/**
 * Created by Gildaswise on 17/05/2017.
 */

@Entity
public class Subject {

    public static final int MAX_NAME_LENGTH = 16;

    @Id private Long id;
    private String name;
    private String description;
    private boolean finished;
    private Double finalScore;
    private Date createdAt;
    @Relation (idProperty = "subjectId") private List<Score> scores;
    /** Used to resolve relations */
    @Internal
    @Generated(hash = 1307364262)
    transient BoxStore __boxStore;

    public Subject(String name, String description) {
        setName(name);
        setDescription(description);
        this.createdAt = DateUtils.now();
    }

    @Generated(hash = 1214828977)
    public Subject(Long id, String name, String description, boolean finished,
            Double finalScore, Date createdAt) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.finished = finished;
        this.finalScore = finalScore;
        this.createdAt = createdAt;
    }

    @Generated(hash = 1617906264)
    public Subject() {}

    public Double getAverage() {
        Double average = 0.0;
        Double totalWeight = 0.0;
        List<Score> scores = getScores();
        if(scores != null && scores.size() > 1) {
            for (Score score : scores) {
                average += score.getValue() * score.getWeight();
                totalWeight += score.getWeight();
            }
            App.log("Subject", "Calculating average for " + name + " = " + average + "/" + totalWeight);
            average = average / totalWeight;
        }
        return average;
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

    /**
     * To-many relationship, resolved on first access (and after reset).
     * Changes to to-many relations are not persisted, make changes to the target entity.
     */
    @Generated(hash = 1184192614)
    public List<Score> getScores() {
        if (scores == null) {
            final BoxStore boxStore = this.__boxStore;
            if (boxStore == null) {
                throw new DbDetachedException();
            }
            Box<Score> box = boxStore.boxFor(Score.class);
            int targetTypeId = boxStore.getEntityTypeIdOrThrow(Score.class);
            List<Score> scoresNew = box.getBacklinkEntities(targetTypeId, Score_.subjectId, id);
            synchronized (this) {
                if (scores == null) {
                    scores = scoresNew;
                }
            }
        }
        return scores;
    }

    /** Resets a to-many relationship, making the next get call to query for a fresh result. */
    @Generated(hash = 1202883178)
    public synchronized void resetScores() {
        scores = null;
    }

    /**
     * Removes entity from its object box. Entity must attached to an entity context.
     */
    @Generated(hash = 1152428473)
    public void remove() {
        if (__boxStore == null) {
            throw new DbDetachedException();
        }
        __boxStore.boxFor(Subject.class).remove(this);
    }

    /**
     * Puts the entity in its object box.
     * Entity must attached to an entity context.
     */
    @Generated(hash = 1657112668)
    public void put() {
        if (__boxStore == null) {
            throw new DbDetachedException();
        }
        __boxStore.boxFor(Subject.class).put(this);
    }
}
