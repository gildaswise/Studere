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
    private ToOne<Subject> relatedSubject;
    @Convert(converter = NotificationType.Converter.class, dbType = Integer.class) private NotificationType notificationType;

    public Appointment(){}

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

    public NotificationType getNotificationType() {
        return notificationType;
    }
    public void setNotificationType(NotificationType notificationType) {
        this.notificationType = notificationType;
    }

    public ToOne<Subject> getRelatedSubject() {
        return relatedSubject;
    }

    public void setRelatedSubject(ToOne<Subject> relatedSubject) {
        this.relatedSubject = relatedSubject;
    }

    public void setRelatedSubject(Subject relatedSubject) {
        this.relatedSubject.setAndPutTarget(relatedSubject);
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if(obj != null && obj instanceof Appointment) {
            Appointment other = (Appointment) obj;
            return other.getId().equals(this.getId()) &&
                   other.getTitle().equals(this.getTitle()) &&
                   other.getDescription().equals(this.getDescription()) &&
                   other.getDate().equals(this.getDate()) &&
                   other.getNotificationType().getTypeId() == this.getNotificationType().getTypeId();
                   // other.getRelatedSubject() == this.getRelatedSubject();
        }
        return false;
    }
}
