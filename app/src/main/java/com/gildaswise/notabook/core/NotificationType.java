package com.gildaswise.notabook.core;

import com.gildaswise.notabook.R;

import io.objectbox.converter.PropertyConverter;

/**
 * Created by Gildaswise on 27/05/2017.
 */

public enum NotificationType {

    UNKNOWN(0, 0, 0),
    NO_NOTIFICATION(1, R.string.appointment_notification_option_1, 0),
    TEN_MINUTES_BEFORE(2, R.string.appointment_notification_option_2, 10),
    THIRTY_MINUTES_BEFORE(3, R.string.appointment_notification_option_3, 30),
    ONE_HOUR_BEFORE(4, R.string.appointment_notification_option_4, 60);

    private int typeId, stringRes, minutes;

    NotificationType(int typeId, int stringRes, int minutes) {
        this.typeId = typeId;
        this.stringRes = stringRes;
        this.minutes = minutes;
    }

    public int getTypeId() {
        return typeId;
    }

    public int getStringRes() {
        return stringRes;
    }

    public int getMinutes() {
        return minutes;
    }

    public static class Converter implements PropertyConverter<NotificationType, Integer> {

        @Override
        public NotificationType convertToEntityProperty(Integer databaseValue) {
            if (databaseValue == null) {return null;}
            for (NotificationType role : NotificationType.values()) {
                if (role.typeId == databaseValue) {
                    return role;
                }
            }
            return NotificationType.UNKNOWN;
        }

        @Override
        public Integer convertToDatabaseValue(NotificationType entityProperty) {
            return entityProperty == null ? null : entityProperty.typeId;
        }

    }

}
