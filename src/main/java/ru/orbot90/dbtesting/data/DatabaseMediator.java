package ru.orbot90.dbtesting.data;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

/**
 * Service for working with the database.
 * Stores the data to the database and performs queries to it.
 *
 * @author Iurii Plevako orbot90@gmail.com
 **/
public class DatabaseMediator {

    private final DataSource dataSource;

    public DatabaseMediator(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    /**
     * Perform sql inserts.
     *
     * @param sqlInserts - List of inserts
     */
    public void insertData(List<String> sqlInserts) {
        try (Connection connection = dataSource.getConnection()) {
            connection.setAutoCommit(false);
            try {
                for (String insert : sqlInserts) {
                    connection.createStatement().executeUpdate(insert);
                }
                connection.commit();
            } catch (RuntimeException e) {
                connection.rollback();
            }
        } catch (SQLException e) {
            // TODO: Change to logging and wrapping
            e.printStackTrace();
        }

    }
}
