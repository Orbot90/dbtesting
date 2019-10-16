package ru.orbot90.dbtesting.data;

import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

public class SqlPreparerTest {

    private SqlPreparer sqlPreparer = new SqlPreparer();

    @Test
    public void shouldPrepareSqlForInsert() {
        TestData testData = new TestData();
        testData.addRecord("TableOne", Arrays.asList("columnone", "columntwo"), Arrays.asList("1", "2"));
        testData.addRecord("TableOne", Arrays.asList("columnone", "columntwo"), Arrays.asList("3", "4"));
        testData.addRecord("TableTwo", Arrays.asList("columnthree", "columnfour"), Arrays.asList("5", "6"));
        List<String> insertSqls = sqlPreparer.prepareInsertQueries(testData);
        Assert.assertNotNull("Insert sqls list is null", insertSqls);
        Assert.assertEquals("Size of the sqls list is wrong", 3, insertSqls.size());
        Assert.assertEquals("Wrong SQL insert was generated", "INSERT INTO TableOne " +
                "(columnone,columntwo) VALUES ('1','2')", insertSqls.get(0));
    }

    @Test
    public void shouldPrepareSqlForInsertWithDifferentTypes() {
        TestData testData = new TestData();
        testData.addRecord("TableOne", Arrays.asList("columnone", "columntwo"), Arrays.asList("abc", "2"));
        testData.addRecord("TableOne", Arrays.asList("columnone", "columntwo"), Arrays.asList(null, "1"));
        List<String> insertSqls = sqlPreparer.prepareInsertQueries(testData);

        Assert.assertEquals("Wrong sql", "INSERT INTO TableOne (columnone,columntwo) VALUES ('abc','2')",
                insertSqls.get(0));
        Assert.assertEquals("Wrong sql", "INSERT INTO TableOne (columntwo) VALUES ('1')",
                insertSqls.get(1));
        for (String sql : insertSqls) {
            System.out.println(sql);
        }
    }

    @Test
    public void shouldPrepareCountQuery() {
        String countQuery = sqlPreparer.prepareCountQuery("TableName");
        Assert.assertEquals("Wrong query was generated", "SELECT COUNT(*) FROM TableName", countQuery);
    }

}
