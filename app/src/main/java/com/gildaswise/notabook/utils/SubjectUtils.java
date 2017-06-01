package com.gildaswise.notabook.utils;

import android.content.Context;
import android.preference.PreferenceManager;

import com.gildaswise.notabook.R;
import com.gildaswise.notabook.core.Subject;

/**
 * Created by Gildaswise on 04/05/2017.
 */

public class SubjectUtils {

    public static String getSubjectStatus(Context context, Subject subject) {
        Double defaultAverage = Double.valueOf(PreferenceManager.getDefaultSharedPreferences(context).getString("default_average", "7.0"));
        Double subjectAverage = subject.getAverage();
        String message;
        if (subjectAverage == 0.0) {message = context.getString(R.string.subject_status_none_available);}
        else {
            message = context.getString(
                      (subject.getFinished()) ?
                      (subjectAverage >= defaultAverage) ? R.string.subject_status_finished_passed : R.string.subject_status_finished_passed_false :
                      (subjectAverage < defaultAverage) ? R.string.subject_status_not_finished_passed_false : R.string.subject_status_not_finished_passed);
        }
        return message;
    }

}
