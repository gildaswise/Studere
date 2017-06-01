package com.gildaswise.notabook.core.exception;

import com.gildaswise.notabook.R;

/**
 * Created by Gildaswise on 31/05/2017.
 */

public class EmptyStringException extends ErrorMessageException {

    @Override
    public int getMessageStringRes() {
        return R.string.error_invalid_fields;
    }

}
