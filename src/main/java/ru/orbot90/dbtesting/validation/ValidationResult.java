package ru.orbot90.dbtesting.validation;

import java.util.Collections;
import java.util.List;

/**
 * Abstraction for the results of the data validation.
 * Contains the information about any validation errors if they were found,
 * otherwise the method hasErrors() returns false and getValidationErrors returns an empty collection
 *
 * @author Iurii Plevako orbot90@gmail.com
 */
public class ValidationResult {

    /**
     * @return true if any validation errors exist
     */
    public boolean hasErrors() {

        // TODO: implement
        return false;
    }

    /**
     * Get the found validation errors
     *
     * @return Collections with the errors. Empty collection if there are no errors
     */
    public List<String> getValidationErrors() {
        // TODO: implement
        return Collections.emptyList();
    }
}
