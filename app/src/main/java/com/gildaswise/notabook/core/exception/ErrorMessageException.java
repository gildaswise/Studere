package com.gildaswise.notabook.core.exception;

/**
 * Created by Gildaswise on 31/05/2017.
 */

public abstract class ErrorMessageException extends RuntimeException {

    public abstract int getMessageStringRes();

}
