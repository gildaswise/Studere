package com.gildaswise.notabook.core;

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
public class ScheduleItem {

    @Id private Long id;
    @Convert(converter = Weekday.Converter.class, dbType = String.class) private Weekday weekday;
    private ToOne<Notification> notification;
    private Date startingAt;
    private Date endingAt;
    private int color;

    public ScheduleItem(){}

    public ScheduleItem(Weekday weekday, Date startingAt, Date endingAt, int color) {
        this.weekday = weekday;
        this.startingAt = startingAt;
        this.endingAt = endingAt;
        this.color = color;
        this.notification.setAndPutTarget(new Notification(startingAt));
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Weekday getWeekday() {
        return weekday;
    }

    public void setWeekday(Weekday weekday) {
        this.weekday = weekday;
    }

    public Date getStartingAt() {
        return startingAt;
    }

    public void setStartingAt(Date startingAt) {
        this.startingAt = startingAt;
    }

    public Date getEndingAt() {
        return endingAt;
    }

    public void setEndingAt(Date endingAt) {
        this.endingAt = endingAt;
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }

    public ToOne<Notification> getNotification() {
        return notification;
    }

    public void setNotification(ToOne<Notification> notification) {
        this.notification = notification;
    }
}
