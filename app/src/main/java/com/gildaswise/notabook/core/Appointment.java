package com.gildaswise.notabook.core;

import com.gildaswise.notabook.core.exception.EmptyStringException;
import com.gildaswise.notabook.core.exception.EmptyTitleException;
import com.gildaswise.notabook.core.exception.InvalidDateException;
import com.gildaswise.notabook.utils.DateUtils;

import org.threeten.bp.LocalDateTime;

import java.util.Date;

import io.objectbox.annotation.Convert;
import io.objectbox.annotation.Entity;
import io.objectbox.annotation.Id;
import io.objectbox.annotation.Relation;
import io.objectbox.annotation.Generated;
import io.objectbox.Box;
import io.objectbox.BoxStore;
import io.objectbox.annotation.apihint.Internal;
import io.objectbox.exception.DbDetachedException;
import io.objectbox.exception.DbException;
import io.objectbox.relation.ToOne;

/**
 * Created by Gildaswise on 17/05/2017.
 */

@Entity
public class Appointment {

    @Id private Long id;
    private Date date;
    private String title;
    private String description;
    @Relation private Subject relatedSubject;
    @Convert(converter = NotificationType.Converter.class, dbType = Integer.class) private NotificationType notificationType;

    long relatedSubjectId;
    /** Used to resolve relations */
    @Internal
    @Generated(hash = 1307364262)
    transient BoxStore __boxStore;
    @Generated(hash = 1458127967)
    public Appointment(Long id, Date date, String title, String description, NotificationType notificationType,
            long relatedSubjectId) {
        this.id = id;
        this.date = date;
        this.title = title;
        this.description = description;
        this.notificationType = notificationType;
        this.relatedSubjectId = relatedSubjectId;
    }
    @Generated(hash = 1660940633)
    public Appointment() {
    }

    public Appointment(Date date) {
        this.date = date;
        this.notificationType = NotificationType.THIRTY_MINUTES_BEFORE;
    }

    public Date getNotificationDate() {
        return DateUtils.toDate(DateUtils.toLocalDateTime(getDate()).minusMinutes(getNotificationType().getMinutes()));
    }

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        if(DateUtils.toLocalDateTime(date).isBefore(LocalDateTime.now().minusMinutes(1))) throw new InvalidDateException();
        this.date = date;
    }

    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        if(title.isEmpty()) throw new EmptyTitleException();
        this.title = title;
    }
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public long getRelatedSubjectId() {
        return relatedSubjectId;
    }
    public void setRelatedSubjectId(long relatedSubjectId) {
        this.relatedSubjectId = relatedSubjectId;
    }
    @Internal
    @Generated(hash = 1887831834)
    private transient ToOne<Appointment, Subject> relatedSubject__toOne;
    /** See {@link io.objectbox.relation.ToOne} for details. */
    @Generated(hash = 867755661)
    public synchronized ToOne<Appointment, Subject> getRelatedSubject__toOne() {
        if (relatedSubject__toOne == null) {
            relatedSubject__toOne = new ToOne<>(this, Appointment_.relatedSubjectId,
                    Subject.class);
        }
        return relatedSubject__toOne;
    }
    /** To-one relationship, resolved on first access. */
    @Generated(hash = 1995020336)
    public Subject getRelatedSubject() {
        relatedSubject = getRelatedSubject__toOne()
                .getTarget(this.relatedSubjectId);
        return relatedSubject;
    }
    /** Set the to-one relation including its ID property. */
    @Generated(hash = 347334636)
    public void setRelatedSubject(Subject relatedSubject) {
        getRelatedSubject__toOne().setTarget(relatedSubject);
        this.relatedSubject = relatedSubject;
    }
    /**
     * Removes entity from its object box. Entity must attached to an entity context.
     */
    @Generated(hash = 1993486799)
    public void remove() {
        if (__boxStore == null) {
            throw new DbDetachedException();
        }
        __boxStore.boxFor(Appointment.class).remove(this);
    }
    /**
     * Puts the entity in its object box.
     * Entity must attached to an entity context.
     */
    @Generated(hash = 1459575529)
    public void put() {
        if (__boxStore == null) {
            throw new DbDetachedException();
        }
        __boxStore.boxFor(Appointment.class).put(this);
    }
    public NotificationType getNotificationType() {
        return notificationType;
    }
    public void setNotificationType(NotificationType notificationType) {
        this.notificationType = notificationType;
    }

}
