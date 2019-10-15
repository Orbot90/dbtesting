package ru.orbot90.dbtesting.data;

import java.util.List;

/**
 * @author Iurii Plevako orbot90@gmail.com
 **/
public class TestDataUnit {
    private final List<String> headers;
    private final List<String> values;

    public TestDataUnit(List<String> headers, List<String> values) {
        this.headers = headers;
        this.values = values;
    }

    public List<String> getHeaders() {
        return headers;
    }

    public List<String> getValues() {
        return values;
    }
}
