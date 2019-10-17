package ru.orbot90.dbtesting;

import ru.orbot90.dbtesting.error.ContextInitializationException;

import javax.sql.DataSource;
import java.util.Arrays;
import java.util.Collection;
import java.util.LinkedList;

/**
 * Builder for the DBTestingContext.
 * Before launching build() method the dataSource must be set and either initFilesLocations or validationFilesLocations.
 * By default the dataFilesType is assumed as JSON.
 *
 * @author Iurii Plevako orbot90@gmail.com
 */
public class DBTestingContextBuilder {
    private DataFilesType dataFilesType = DataFilesType.JSON;
    private TestDataFilesLocationType locationType = TestDataFilesLocationType.CLASSPATH;
    private Collection<String> initFilesLocations;
    private Collection<String> validationFilesLocations;
    private Collection<String> dataRemoveFilesLocations;
    private DataSource dataSource;

    public DBTestingContext build() {
        if (dataSource == null) {
            throw new ContextInitializationException("DataSource must not be null");
        }
        if (this.isEmptyOrNull(initFilesLocations) && this.isEmptyOrNull(validationFilesLocations)) {
            throw new ContextInitializationException("Either init files or validation files must be set");
        }

        try {
            return new DBTestingContext(this.dataFilesType, this.initFilesLocations,
                    this.validationFilesLocations, this.dataRemoveFilesLocations, this.dataSource, this.locationType);
        } catch (Exception e) {
            throw new ContextInitializationException("Error while initializing testing context", e);
        }
    }

    public DBTestingContextBuilder setDataFilesType(DataFilesType dataFilesType) {
        this.dataFilesType = dataFilesType;
        return this;
    }

    public DBTestingContextBuilder setInitFilesLocations(String...initFilesLocations) {
        this.initFilesLocations = new LinkedList<>();
        this.initFilesLocations.addAll(Arrays.asList(initFilesLocations));
        return this;
    }

    public DBTestingContextBuilder setValidationFilesLocations(String...validationFilesLocations) {
        this.validationFilesLocations = new LinkedList<>();
        this.validationFilesLocations.addAll(Arrays.asList(validationFilesLocations));
        return this;
    }

    public DBTestingContextBuilder setDataRemoveFilesLocations(String...dataRemoveFilesLocations) {
        this.dataRemoveFilesLocations = new LinkedList<>();
        this.dataRemoveFilesLocations.addAll(Arrays.asList(dataRemoveFilesLocations));
        return this;
    }

    public DBTestingContextBuilder setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
        return this;
    }

    private <T> boolean isEmptyOrNull(Collection<T> collection) {
        return collection == null || collection.isEmpty();
    }
}
