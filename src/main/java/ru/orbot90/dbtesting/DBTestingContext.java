package ru.orbot90.dbtesting;

import ru.orbot90.dbtesting.data.DatabaseMediator;
import ru.orbot90.dbtesting.data.SqlPreparer;
import ru.orbot90.dbtesting.data.TestData;
import ru.orbot90.dbtesting.data.TestDataInitializer;
import ru.orbot90.dbtesting.validation.ValidationResult;

import javax.sql.DataSource;
import java.util.Collection;
import java.util.List;

/**
 * The main context class of the DBTesting framework.
 * All interactions with the framework must be performed through the instances of this class
 *
 * @author Iurii Plevako, orbot90@gmail.com
 */
public class DBTestingContext {

    private TestData initTestData;
    private TestData validationTestData;
    private final DatabaseMediator databaseMediator;
    private final SqlPreparer sqlPreparer = new SqlPreparer();

    DBTestingContext(DataFilesType dataFilesType,
                     Collection<String> initFilesLocations,
                     Collection<String> validationFilesLocations,
                     DataSource dataSource, TestDataFilesLocationType locationType) {
        this.initializeTestData(initFilesLocations, validationFilesLocations, dataFilesType, locationType);
        this.databaseMediator = new DatabaseMediator(dataSource);
    }

    public static DBTestingContextBuilder builder() {
        return new DBTestingContextBuilder();
    }

    /**
     * Uploads initial data from the init files to the database.
     */
    public void uploadInitData() {
        List<String> insertQueries = this.sqlPreparer.prepareInsertQueries(this.initTestData);
        databaseMediator.insertData(insertQueries);
    }

    /**
     * Validates the data in the database using the passed validation files.
     *
     * @return ValidationResult with the detailed description of the errors.
     * If there are no errors during validation, the returned ValidationResult instance
     * must return false when the method "hasErrors()" is called.
     * This method is never expected to return null or throw any exceptions
     */
    public ValidationResult validateData() {
        // TODO: implement
        return null;
    }

    private void initializeTestData(Collection<String> initFilesLocations,
                                    Collection<String> validationFilesLocations, DataFilesType dataFilesType,
                                    TestDataFilesLocationType locationType) {
        TestDataInitializer testDataInitializer = new TestDataInitializer(dataFilesType, locationType);
        this.initTestData = testDataInitializer.initTestData(initFilesLocations);
        this.validationTestData = testDataInitializer.initTestData(validationFilesLocations);
    }

}
