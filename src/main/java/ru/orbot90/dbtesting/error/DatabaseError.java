package ru.orbot90.dbtesting.error;

/**
 * Exception thrown when there was an error while working with the database
 *
 * @author Iurii Plevako orbot90@gmail.com
 **/
public class DatabaseError extends RuntimeException {
    public DatabaseError(Throwable cause) {
        super(cause);
    }
}
