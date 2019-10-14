package ru.orbot90.dbtesting.data;

import java.util.List;

/**
 * @author Iurii Plevako orbot90@gmail.com
 **/
public class TestDataUnit {
    private final String tableName;
    private final List<String> headers;
    private final List<String> values;

    public TestDataUnit(String tableName, List<String> headers, List<String> values) {
        this.tableName = tableName;
        this.headers = headers;
        this.values = values;
    }

    public String getTableName() {
        return tableName;
    }

    public List<String> getHeaders() {
        return headers;
    }

    public List<String> getValues() {
        return values;
    }
}
