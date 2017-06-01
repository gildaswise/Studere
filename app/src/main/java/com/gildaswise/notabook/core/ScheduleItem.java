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
    @Relation private Notification notification;
    private Date startingAt;
    private Date endingAt;
    private int color;
    long notificationId;
    /** Used to resolve relations */
    @Internal
    @Generated(hash = 1307364262)
    transient BoxStore __boxStore;
    @Internal
    @Generated(hash = 1671419484)
    private transient ToOne<ScheduleItem, Notification> notification__toOne;

    public ScheduleItem(Weekday weekday, Date startingAt, Date endingAt, int color) {
        this.weekday = weekday;
        this.startingAt = startingAt;
        this.endingAt = endingAt;
        this.color = color;
        this.notification = new Notification(startingAt);
    }

    @Generated(hash = 1162182273)
    public ScheduleItem(Long id, Weekday weekday, Date startingAt, Date endingAt, int color,
            long notificationId) {
        this.id = id;
        this.weekday = weekday;
        this.startingAt = startingAt;
        this.endingAt = endingAt;
        this.color = color;
        this.notificationId = notificationId;
    }

    @Generated(hash = 620347724)
    public ScheduleItem() {
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

    public long getNotificationId() {
        return notificationId;
    }

    public void setNotificationId(long notificationId) {
        this.notificationId = notificationId;
    }

    /** See {@link io.objectbox.relation.ToOne} for details. */
    @Generated(hash = 1566694664)
    public synchronized ToOne<ScheduleItem, Notification> getNotification__toOne() {
        if (notification__toOne == null) {
            notification__toOne = new ToOne<>(this, ScheduleItem_.notificationId, Notification.class);
        }
        return notification__toOne;
    }

    /** To-one relationship, resolved on first access. */
    @Generated(hash = 1188992942)
    public Notification getNotification() {
        notification = getNotification__toOne().getTarget(this.notificationId);
        return notification;
    }

    /** Set the to-one relation including its ID property. */
    @Generated(hash = 2097482369)
    public void setNotification(Notification notification) {
        getNotification__toOne().setTarget(notification);
        this.notification = notification;
    }

    /**
     * Removes entity from its object box. Entity must attached to an entity context.
     */
    @Generated(hash = 906144106)
    public void remove() {
        if (__boxStore == null) {
            throw new DbDetachedException();
        }
        __boxStore.boxFor(ScheduleItem.class).remove(this);
    }

    /**
     * Puts the entity in its object box.
     * Entity must attached to an entity context.
     */
    @Generated(hash = 316098109)
    public void put() {
        if (__boxStore == null) {
            throw new DbDetachedException();
        }
        __boxStore.boxFor(ScheduleItem.class).put(this);
    }
}
