package ru.orbot90.dbtesting.data;

import ru.orbot90.dbtesting.error.DatabaseError;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
            // TODO: Change to logging
            e.printStackTrace();
            throw new DatabaseError(e);
        }

    }

    public int performCountQuery(String countSql) {
        try (Connection connection = dataSource.getConnection()) {
            ResultSet resultSet = connection.createStatement().executeQuery(countSql);
            resultSet.next();
            return resultSet.getInt(0);
        } catch (SQLException e) {
            // TODO: Change to logging
            e.printStackTrace();
            throw new DatabaseError(e);
        }
    }

    public Map<String, Boolean> runSelectQueriesAndCheckExistence(List<String> selectQueries) {
        Map<String, Boolean> result = new HashMap<>();
        for (String selectQuery : selectQueries) {
            try (Connection connection = dataSource.getConnection()) {
                ResultSet resultSet = connection.createStatement().executeQuery(selectQuery);
                if (resultSet.next()) {
                    result.put(selectQuery, true);
                } else {
                    result.put(selectQuery, false);
                }
            } catch (SQLException e) {
                // TODO: Change to logging
                e.printStackTrace();
                throw new DatabaseError(e);
            }
        }
        return result;
    }
}
