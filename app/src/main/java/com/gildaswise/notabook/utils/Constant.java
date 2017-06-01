package com.gildaswise.notabook.utils;

import com.gildaswise.notabook.core.Subject;

/**
 * Created by Gildaswise on 10/05/2017.
 */

public abstract class Constant {

    public static final String STRING_EMPTY = "";
    public static final String STRING_SPACE = " ";
    public static final String STRING_SUSPENSION_POINTS = "…";

    public static String shorten(String str) {
        return (str.length() > Subject.MAX_NAME_LENGTH) ? str.substring(0, Subject.MAX_NAME_LENGTH - 2) + Constant.STRING_SUSPENSION_POINTS : str;
    }

}
