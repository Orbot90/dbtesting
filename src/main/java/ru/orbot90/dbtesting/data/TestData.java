package ru.orbot90.dbtesting.data;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * Test data abstraction, containing the data loaded from the test files.
 *
 * @author Iurii Plevako orbot90@gmail.com
 */
public class TestData {

    private Map<String, List<TestDataUnit>> testDataUnits = new HashMap<>();

    /**
     * Add test data record.
     *
     * @param tableName - name of the table
     * @param columnNames - list of column names
     * @param values - list of values with the same order as column names
     */
    public void addRecord(String tableName, List<String> columnNames, List<String> values) {
        TestDataUnit unit = new TestDataUnit(columnNames, values);
        List<TestDataUnit> records = this.testDataUnits.computeIfAbsent(tableName, key -> new LinkedList<>());
        records.add(unit);
    }

    /**
     * Get test data units
     */
    public Map<String, List<TestDataUnit>> getTestDataUnits() {
        return testDataUnits;
    }
}
