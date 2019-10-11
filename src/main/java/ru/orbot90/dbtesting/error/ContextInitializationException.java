package ru.orbot90.dbtesting.error;

/**
 * Exception to be thrown when any error happens while initializing DBTestingContext
 *
 * @author Iurii Plevako orbot90@gmail.com
 */
public class ContextInitializationException extends RuntimeException {

    public ContextInitializationException(String message) {
        super(message);
    }

    public ContextInitializationException(String message, Throwable cause) {
        super(message, cause);
    }
}
