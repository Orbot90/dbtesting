package ru.orbot90.dbtesting.data;

import java.util.LinkedList;
import java.util.List;

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
        List<TestDataUnit> units = testData.getTestDataUnits();
        List<String> inserts = new LinkedList<>();
        for (TestDataUnit unit : units) {
            StringBuilder queryBuilder = new StringBuilder("INSERT INTO ")
                    .append(unit.getTableName())
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
        return inserts;
    }
}
