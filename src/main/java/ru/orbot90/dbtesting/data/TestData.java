package ru.orbot90.dbtesting.data;

import java.util.LinkedList;
import java.util.List;

/**
 * Test data abstraction, containing the data loaded from the test files.
 *
 * @author Iurii Plevako orbot90@gmail.com
 */
public class TestData {

    private List<TestDataUnit> testDataUnits = new LinkedList<>();

    /**
     * Add test data record.
     *
     * @param tableName - name of the table
     * @param columnNames - list of column names
     * @param values - list of values with the same order as column names
     */
    public void addRecord(String tableName, List<String> columnNames, List<String> values) {
        TestDataUnit unit = new TestDataUnit(tableName, columnNames, values);
        this.testDataUnits.add(unit);
    }

    /**
     * Get list of test data units
     */
    public List<TestDataUnit> getTestDataUnits() {
        return testDataUnits;
    }
}
