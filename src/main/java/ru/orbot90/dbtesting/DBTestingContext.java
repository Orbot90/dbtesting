package ru.orbot90.dbtesting;

import ru.orbot90.dbtesting.data.*;
import ru.orbot90.dbtesting.validation.ValidationResult;

import javax.sql.DataSource;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Optional;

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
    private TestData removeTestData;

    DBTestingContext(DataFilesType dataFilesType,
                     Collection<String> initFilesLocations,
                     Collection<String> validationFilesLocations,
                     Collection<String> dataRemoveFilesLocations,
                     DataSource dataSource, TestDataFilesLocationType locationType) {
        this.initializeTestData(initFilesLocations, validationFilesLocations,
                dataRemoveFilesLocations, dataFilesType, locationType);
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
        ValidationResult validationResult = new ValidationResult();
        Map<String, List<TestDataUnit>> testDataUnits = this.validationTestData.getTestDataUnits();
        for (Map.Entry<String, List<TestDataUnit>> entry : testDataUnits.entrySet()) {
            String tableName = entry.getKey();
            List<TestDataUnit> testDataUnitList = entry.getValue();

            String countSql = this.sqlPreparer.prepareCountQuery(tableName);
            int count = databaseMediator.performCountQuery(countSql);
            int expectedCount = testDataUnitList.size();
            if (count != expectedCount) {
                validationResult.addCountError(tableName, expectedCount, count);
            }

            List<String> selectQueries = this.sqlPreparer.prepareSelectQueries(tableName, testDataUnitList);

            Map<String, Boolean> existsMap = this.databaseMediator.runSelectQueriesAndCheckExistence(selectQueries);

            for (Map.Entry<String, Boolean> existsEntry : existsMap.entrySet()) {
                if (!existsEntry.getValue()) {
                    validationResult.addExistsError(tableName, existsEntry.getKey());
                }
            }
        }
        return validationResult;
    }

    public void removeData() {
        List<String> deleteQueries = this.sqlPreparer.prepareDeleteQueries(this.removeTestData);
        this.databaseMediator.performDeleteQueries(deleteQueries);
    }

    private void initializeTestData(Collection<String> initFilesLocations,
                                    Collection<String> validationFilesLocations,
                                    Collection<String> dataRemoveFilesLocations, DataFilesType dataFilesType,
                                    TestDataFilesLocationType locationType) {
        TestDataInitializer testDataInitializer = new TestDataInitializer(dataFilesType, locationType);
        this.initTestData = Optional.ofNullable(initFilesLocations)
                .map(testDataInitializer::initTestData)
                .orElse(null);
        this.validationTestData = Optional.ofNullable(validationFilesLocations)
                .map(testDataInitializer::initTestData)
                .orElse(null);
        this.removeTestData = Optional.ofNullable(dataRemoveFilesLocations)
                .map(testDataInitializer::initTestData)
                .orElse(null);
    }

}
