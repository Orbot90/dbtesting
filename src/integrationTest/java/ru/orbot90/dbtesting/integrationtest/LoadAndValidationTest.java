package ru.orbot90.dbtesting.integrationtest;

import org.hsqldb.jdbc.JDBCDataSource;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import ru.orbot90.dbtesting.DBTestingContext;
import ru.orbot90.dbtesting.validation.ValidationResult;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

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
        dataSource.getConnection().createStatement().executeUpdate("CREATE TABLE tabletwo (columnthree int)");
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
    }

    @Test
    public void shouldDeleteData() throws SQLException {
        DBTestingContext context = DBTestingContext.builder()
                .setDataSource(dataSource)
                .setInitFilesLocations("/testdata/initdata.jsn")
                .setDataRemoveFilesLocations("/testdata/initdata.jsn")
                .build();

        context.uploadInitData();
        context.removeData();

        Statement statement = this.dataSource.getConnection().createStatement();
        ResultSet result = statement.executeQuery("SELECT * FROM TableOne");
        Assert.assertFalse("Table is not empty", result.next());
    }
}
