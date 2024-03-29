package ru.orbot90.dbtesting;

import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mockito;
import ru.orbot90.dbtesting.error.ContextInitializationException;

import javax.sql.DataSource;

public class DBTestingContextBuilderTest {

    @Test(expected = ContextInitializationException.class)
    public void initContextWithNullDatasourceShouldThrowError() {
        DBTestingContext.builder()
                .setDataFilesType(DataFilesType.JSON)
                .setInitFilesLocations("location")
                .setValidationFilesLocations("location")
                .build();
    }

    @Test(expected = ContextInitializationException.class)
    public void initContextWithNullIniOrValidationFilesShouldThrowError() {
        DBTestingContext.builder()
                .setDataFilesType(DataFilesType.JSON)
                .setDataSource(Mockito.mock(DataSource.class))
                .build();
    }

    @Test
    public void shouldSuccessfullyInitContext() {
        DBTestingContext context = DBTestingContext.builder()
                .setDataSource(Mockito.mock(DataSource.class))
                .setInitFilesLocations("testdata/initializer/initfile.jsn")
                .setValidationFilesLocations("testdata/initializer/initfile.jsn")
                .build();

        Assert.assertNotNull("Returned context is null", context);
    }

    @Test
    public void shouldSuccessfullyInitContextWithOnlyInitData() {
        DBTestingContext context = DBTestingContext.builder()
                .setDataSource(Mockito.mock(DataSource.class))
                .setInitFilesLocations("testdata/initializer/initfile.jsn")
                .build();

        Assert.assertNotNull("Returned context is null", context);
    }

    @Test
    public void shouldSuccessfullyInitContextWithOnlyValidationData() {
        DBTestingContext context = DBTestingContext.builder()
                .setDataSource(Mockito.mock(DataSource.class))
                .setValidationFilesLocations("testdata/initializer/initfile.jsn")
                .build();

        Assert.assertNotNull("Returned context is null", context);
    }

}
