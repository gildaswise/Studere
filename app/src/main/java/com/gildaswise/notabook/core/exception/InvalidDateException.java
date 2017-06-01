package com.gildaswise.notabook.core.exception;

import com.gildaswise.notabook.R;

/**
 * Created by Gildaswise on 01/06/2017.
 */

public class InvalidDateException extends ErrorMessageException {

    @Override
    public int getMessageStringRes() {
        return R.string.error_invalid_date;
    }

}
