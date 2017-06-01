package com.gildaswise.notabook.core;

import com.gildaswise.notabook.utils.DateUtils;

import java.util.Date;

import io.objectbox.annotation.Entity;
import io.objectbox.annotation.Id;
import io.objectbox.annotation.Generated;

/**
 * Created by Gildaswise on 17/05/2017.
 */

@Entity
public class Notification {

    @Id private Long id;
    private Date trigger;

    public Notification(Date trigger) {
        this.trigger = trigger;
    }

    @Generated(hash = 904321292)
    public Notification(Long id, Date trigger) {
        this.id = id;
        this.trigger = trigger;
    }

    @Generated(hash = 1855225820)
    public Notification() {
    }

    public Long getId() {
        return id;
    }

    public Date getTrigger() {
        return trigger;
    }

    public void setTrigger(Date trigger) {
        this.trigger = trigger;
    }

    public boolean isSent() {
        return DateUtils.now().after(trigger);
    }

    public void sent(boolean isSent, int plusDays) {
        if (isSent) {
            setTrigger(DateUtils.toDate(DateUtils.toLocalDateTime(this.trigger).plusDays(plusDays)));
        }
    }

    public void setId(Long id) {
        this.id = id;
    }

}
