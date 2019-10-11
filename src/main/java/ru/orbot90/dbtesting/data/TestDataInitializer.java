package ru.orbot90.dbtesting.data;

import ru.orbot90.dbtesting.DataFilesType;
import ru.orbot90.dbtesting.TestDataFilesLocationType;

import java.util.Collection;

/**
 * The initializer for the test data that takes the files from the given locations and loads them to TestData instances.
 *
 * @author Iurii Plevako orbot90@gmail.com
 */
public class TestDataInitializer {
    private final DataFilesType dataFilesType;
    private final TestDataFilesLocationType locationtype;

    public TestDataInitializer(DataFilesType dataFilesType, TestDataFilesLocationType locationType) {
        this.dataFilesType = dataFilesType;
        this.locationtype = locationType;
    }

    public TestData initTestData(Collection<String> testDataFilesLocations) {
        // TODO: implement
        return null;
    }
}
