package ru.orbot90.dbtesting.validation;

import org.junit.Assert;
import org.junit.Test;

/**
 * @author Iurii Plevako orbot90@gmail.com
 **/
public class ValidationResultTest {

    @Test
    public void hasErrorsShouldReturnTrueWhenHasCountErrors() {
        ValidationResult validationResult = new ValidationResult();
        validationResult.addCountError("TableOne", 1, 2);
        Assert.assertTrue("hasErrors() returned false having count error", validationResult.hasErrors());
    }

    @Test
    public void hasErrorsShouldReturnTrueWhenHasExistsErrors() {
        ValidationResult validationResult = new ValidationResult();
        validationResult.addExistsError("TableOne" , "QUERY");
        Assert.assertTrue("hasErrors() returned false having exists error", validationResult.hasErrors());
    }

    @Test
    public void hasErrorsShouldReturnFalseWhenNoErrorsAdded() {
        ValidationResult validationResult = new ValidationResult();
        Assert.assertFalse("hasErrors() returned true when no errors added", validationResult.hasErrors());
    }

}
