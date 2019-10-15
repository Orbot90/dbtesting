package ru.orbot90.dbtesting.validation;

import java.util.LinkedList;
import java.util.List;

/**
 * Abstraction for the results of the data validation.
 * Contains the information about any validation errors if they were found,
 * otherwise the method hasErrors() returns false and getValidationErrors returns an empty collection
 *
 * @author Iurii Plevako orbot90@gmail.com
 */
public class ValidationResult {

    private List<String> countError;
    private List<String> existsError;

    /**
     * @return true if any validation errors exist
     */
    public boolean hasErrors() {
        return countError != null || existsError != null;
    }

    /**
     * Get the found validation errors
     *
     * @return Collections with the errors. Empty collection if there are no errors
     */
    public List<String> getValidationErrors() {
        List<String> errors = new LinkedList<>();
        errors.addAll(countError);
        errors.addAll(existsError);
        return errors;
    }

    /**
     * Add count error to the validation result.
     *
     * @param tableName the name of the validated table
     * @param expectedCount the expected count of records in the validated table
     * @param actualCount the actual count of records in the validated table
     */
    public void addCountError(String tableName, int expectedCount, int actualCount) {
        if (this.countError == null) {
            this.countError = new LinkedList<>();
        }
        countError.add("The count of records in the table \"" + tableName + "\" is wrong. Actual number is: \"" +
                actualCount + "\". Expected count: \"" + expectedCount + "\"");
    }

    /**
     * Add exists error to the validation result
     *
     * @param tableName the name of the validated table
     * @param query the exists query that returned false while validating
     */
    public void addExistsError(String tableName, String query) {
        if (this.existsError == null) {
            this.existsError = new LinkedList<>();
        }

        existsError.add("In the table \"" + tableName + "\" was not found a record with query: " + query);
    }
}
