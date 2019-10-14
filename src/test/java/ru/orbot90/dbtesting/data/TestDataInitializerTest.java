package ru.orbot90.dbtesting.data;

import org.junit.Assert;
import org.junit.Test;
import ru.orbot90.dbtesting.TestDataFilesLocationType;

import java.util.Arrays;

import static ru.orbot90.dbtesting.DataFilesType.JSON;

/**
 * @author Iurii Plevako orbot90@gmail.com
 **/
public class TestDataInitializerTest {

    private TestDataInitializer testDataInitializer = new TestDataInitializer(JSON, TestDataFilesLocationType.CLASSPATH);

    @Test
    public void shoudInitializeTestData() {
        TestData testData = testDataInitializer.initTestData(Arrays.asList("testdata/initializer/initfile.jsn"));
        Assert.assertNotNull("Test data is null", testData);
        Assert.assertEquals("Wrong number of data units", 3, testData.getTestDataUnits().size());
    }
}
