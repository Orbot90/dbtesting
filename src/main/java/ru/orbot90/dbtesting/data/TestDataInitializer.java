package ru.orbot90.dbtesting.data;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import ru.orbot90.dbtesting.DataFilesType;
import ru.orbot90.dbtesting.TestDataFilesLocationType;

import java.io.IOException;
import java.io.InputStream;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * The initializer for the test data that takes the files from the given locations and loads them to TestData instances.
 *
 * @author Iurii Plevako orbot90@gmail.com
 */
public class TestDataInitializer {
    private final DataFilesType dataFilesType;
    private final TestDataFilesLocationType locationtype;
    private final ObjectMapper objectMapper = new ObjectMapper();

    public TestDataInitializer(DataFilesType dataFilesType, TestDataFilesLocationType locationType) {
        this.dataFilesType = dataFilesType;
        this.locationtype = locationType;
    }

    public TestData initTestData(Collection<String> testDataFilesLocations) {
        TestData resultTestData = new TestData();
        for (String dataFileLocation : testDataFilesLocations) {
            this.initializeTestDataFromFile(dataFileLocation, resultTestData);
        }
        return resultTestData;
    }

    private void initializeTestDataFromFile(String dataFileLocation, TestData testData) {
        String location = dataFileLocation;
        if (!location.startsWith("/")) {
            location = "/" + location;
        }
        try (InputStream testFileInputStream = TestDataInitializer.class.getResourceAsStream(location)) {
            Map<String, List<Map<String, String>>> testDataMap = objectMapper
                    .readValue(testFileInputStream, new TypeReference<Map<String, List<Map<String, String>>>>() {
            });

            for (Map.Entry<String, List<Map<String, String>>> testDataEntry : testDataMap.entrySet()) {
                String tableName = testDataEntry.getKey();

                List<Map<String, String>> testDataRecords = testDataEntry.getValue();
                List<String> columnNames = new LinkedList<>();
                List<String> values = new LinkedList<>();
                for (Map<String, String> record : testDataRecords) {
                    for (Map.Entry<String, String> columnValuePair : record.entrySet()) {
                        columnNames.add(columnValuePair.getKey());
                        values.add(columnValuePair.getValue());
                    }
                    testData.addRecord(tableName, columnNames, values);
                }
            }
        } catch (IOException e) {
            // TODO: Change to logging
            e.printStackTrace();
        }
    }
}
