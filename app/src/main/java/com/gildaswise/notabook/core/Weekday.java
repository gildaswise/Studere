package com.gildaswise.notabook.core;

import android.content.Context;

import com.gildaswise.notabook.R;

import org.threeten.bp.DayOfWeek;

import io.objectbox.converter.PropertyConverter;

/**
 * Created by Gildaswise on 04/05/2017.
 */

public enum Weekday implements DisplayableEnum {

    UNKNOWN(null, 0),
    SUNDAY(DayOfWeek.SUNDAY, R.string.sunday),
    MONDAY(DayOfWeek.MONDAY, R.string.monday),
    TUESDAY(DayOfWeek.TUESDAY, R.string.tuesday),
    WEDNESDAY(DayOfWeek.WEDNESDAY, R.string.wednesday),
    THURSDAY(DayOfWeek.THURSDAY, R.string.thursday),
    FRIDAY(DayOfWeek.FRIDAY, R.string.friday),
    SATURDAY(DayOfWeek.SATURDAY, R.string.saturday);

    private DayOfWeek dayOfWeek;
    private int stringRes;

    Weekday(DayOfWeek dayOfWeek, int stringRes) {
        this.dayOfWeek = dayOfWeek;
        this.stringRes = stringRes;
    }

    public static Weekday fromDayOfWeek(DayOfWeek dayOfWeek) {
        return valueOf(dayOfWeek.name());
    }

    public String getTranslatedName(Context context) {
        return context.getString(stringRes);
    }

    public DayOfWeek getDayOfWeek() {
        return dayOfWeek;
    }

    public int getStringRes() {
        return stringRes;
    }

    public static class Converter implements PropertyConverter<Weekday, String> {
        @Override
        public Weekday convertToEntityProperty(String databaseValue) {
            if (databaseValue == null) {return null;}
            for (Weekday role : Weekday.values()) {
                if (role.dayOfWeek.name() == databaseValue) {
                    return role;
                }
            }
            return Weekday.UNKNOWN;
        }

        @Override
        public String convertToDatabaseValue(Weekday entityProperty) {
            return entityProperty == null ? null : entityProperty.dayOfWeek.name();
        }
    }

}
