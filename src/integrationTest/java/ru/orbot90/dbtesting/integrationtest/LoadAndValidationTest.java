package ru.orbot90.dbtesting.integrationtest;

import org.hsqldb.jdbc.JDBCDataSource;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import ru.orbot90.dbtesting.DBTestingContext;
import ru.orbot90.dbtesting.validation.ValidationResult;

import javax.sql.DataSource;
import java.sql.SQLException;

/**
 * @author Iurii Plevako orbot90@gmail.com
 **/
public class LoadAndValidationTest {

    private DataSource dataSource;

    @Before
    public void init() throws SQLException {
        JDBCDataSource dataSource = new JDBCDataSource();
        dataSource.setUrl("jdbc:hsqldb:file:db/testdb");
        this.dataSource = dataSource;

        dataSource.getConnection().createStatement().executeUpdate("DROP TABLE IF EXISTS tableone; DROP TABLE IF EXISTS tabletwo;");
        dataSource.getConnection().createStatement().executeUpdate("CREATE TABLE tableone (columnone varchar(100), columntwo varchar(100))");
        dataSource.getConnection().createStatement().executeUpdate("CREATE TABLE tabletwo (columnthree varchar(100))");
    }

    @Test
    public void shouldLoadAndValidateDataWithoutErrors() {
        DBTestingContext context = DBTestingContext.builder()
                .setDataSource(dataSource)
                .setInitFilesLocations("/testdata/initdata.jsn")
                .setValidationFilesLocations("/testdata/validationdata.jsn")
                .build();

        context.uploadInitData();
        ValidationResult validationResult = context.validateData();
        Assert.assertFalse("Validation result has errors", validationResult.hasErrors());
    }

    @Test
    public void shouldLoadAndValidateDataWithErrors() {
        DBTestingContext context = DBTestingContext.builder()
                .setDataSource(dataSource)
                .setInitFilesLocations("/testdata/initdata.jsn")
                .setValidationFilesLocations("/testdata/validationdata_forerrors.jsn")
                .build();

        context.uploadInitData();
        ValidationResult validationResult = context.validateData();
        Assert.assertTrue("Validation result has errors", validationResult.hasErrors());
        Assert.assertEquals("Wrong count error",
                "The count of records in the table \"TableOne\" is wrong. Actual number is: \"2\". Expected count: \"3\"",
                validationResult.getValidationErrors().get(0));
        Assert.assertEquals("Wrong exists error",
                "In the table \"TableOne\" was not found a record with query: SELECT * " +
                        "FROM TableOne WHERE columnone = 666 AND columntwo = 10",
                validationResult.getValidationErrors().get(1));
        for (String error : validationResult.getValidationErrors()) {
            System.out.println(error);
        }
    }
}
