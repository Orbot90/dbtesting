package ru.orbot90.dbtesting.data;

import org.junit.Assert;
import org.junit.Test;
import ru.orbot90.dbtesting.TestDataFilesLocationType;

import java.util.Arrays;
import java.util.List;

import static ru.orbot90.dbtesting.DataFilesType.JSON;

/**
 * @author Iurii Plevako orbot90@gmail.com
 **/
public class TestDataInitializerTest {

    private TestDataInitializer testDataInitializer = new TestDataInitializer(JSON, TestDataFilesLocationType.CLASSPATH);

    @Test
    public void shoudInitializeTestData() {
        TestData testData = testDataInitializer.initTestData(Arrays.asList("testdata/initializer/initfile.jsn"));
        List<TestDataUnit> tableOneData = testData.getTestDataUnits().get("TableOne");
        Assert.assertEquals("TableOne data units count is wrong", 2, tableOneData.size());
        TestDataUnit tableOneFirstUnit = tableOneData.get(0);
        Assert.assertEquals("Wrong headers list", Arrays.asList("columnone", "columntwo"),tableOneFirstUnit.getHeaders());
        Assert.assertEquals("Wrong values list", Arrays.asList("1", "2"),tableOneFirstUnit.getValues());
        Assert.assertNotNull("Test data is null", testData);
        Assert.assertEquals("Wrong number of data units entries", 2, testData.getTestDataUnits().size());
    }
}
