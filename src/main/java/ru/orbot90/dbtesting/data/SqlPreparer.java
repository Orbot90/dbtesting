package ru.orbot90.dbtesting.data;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * Service for preparing sql for queries and updates.
 *
 * @author Iurii Plevako orbot90@gmail.com
 **/
public class SqlPreparer {

    /**
     * Prepares sql for insertion of test data
     *
     * @param testData - test data initialized from the files
     * @return list of sql updates.
     */
    public List<String> prepareInsertQueries(TestData testData) {

        List<String> inserts = new LinkedList<>();
        Map<String, List<TestDataUnit>> testDataUnitsMap = testData.getTestDataUnits();
        for (Map.Entry<String, List<TestDataUnit>> entry : testDataUnitsMap.entrySet()) {
            String tableName = entry.getKey();
            List<TestDataUnit> unitsList = entry.getValue();
            for (TestDataUnit unit : unitsList) {
                StringBuilder queryBuilder = new StringBuilder("INSERT INTO ")
                        .append(tableName)
                        .append(" (");

                for (String column : unit.getHeaders()) {
                    queryBuilder.append(column)
                            .append(",");
                }
                queryBuilder = new StringBuilder(queryBuilder.subSequence(0, queryBuilder.length() - 1));
                queryBuilder.append(")")
                        .append(" VALUES (");

                for (String value : unit.getValues()) {
                    queryBuilder.append("'")
                            .append(value)
                            .append("'")
                            .append(",");
                }
                queryBuilder = new StringBuilder(queryBuilder.subSequence(0, queryBuilder.length() - 1));
                queryBuilder.append(")");

                inserts.add(queryBuilder.toString());
            }
        }
        return inserts;
    }

    /**
     * Prepare query to count records in table
     *
     * @param tableName the name of the validated table
     * @return sql query for counting records in the table
     */
    public String prepareCountQuery(String tableName) {
        return "SELECT COUNT(*) FROM " + tableName;
    }

    /**
     * Prepare select queries from the test data units list
     *
     * @param tableName the name of the validated table
     * @param testDataUnitList list of test data units for validation
     * @return list of sql select queries
     */
    public List<String> prepareSelectQueries(String tableName, List<TestDataUnit> testDataUnitList) {
        List<String> selectQueries = new LinkedList<>();
        for (TestDataUnit unit : testDataUnitList) {
            StringBuilder queryBuilder = new StringBuilder("SELECT * FROM " + tableName + " WHERE ");
            List<String> values = unit.getValues();
            List<String> headers = unit.getHeaders();

            Iterator<String> valuesIterator = values.iterator();
            Iterator<String> headersIterator = headers.iterator();

            while (valuesIterator.hasNext()) {
                String nextValue = valuesIterator.next();
                String nextHeader = headersIterator.next();

                if (nextValue == null) {
                    queryBuilder.append(nextHeader + " is null");
                } else {
                    queryBuilder.append(nextHeader + " = " + nextValue);
                }

                queryBuilder.append(" AND ");
            }

            selectQueries.add(queryBuilder.subSequence(0, queryBuilder.lastIndexOf(" AND")).toString());
        }
        return selectQueries;
    }
}
