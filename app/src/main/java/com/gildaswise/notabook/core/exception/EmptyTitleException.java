package com.gildaswise.notabook.core.exception;

import com.gildaswise.notabook.R;

/**
 * Created by Gildaswise on 31/05/2017.
 */

public class EmptyTitleException extends ErrorMessageException {

    @Override
    public int getMessageStringRes() {
        return R.string.error_empty_title;
    }

}
