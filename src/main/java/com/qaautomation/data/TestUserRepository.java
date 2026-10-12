package com.qaautomation.data;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Reads test users from an in-memory H2 database using plain JDBC and SQL.
 * Keeping credentials in a table (instead of hard-coding them in tests) means
 * new users can be added in one place: testdata/test-users.sql.
 */
public class TestUserRepository {

    // INIT runs the SQL script when the connection opens. The script is safe to run more than once.
    private static final String JDBC_URL =
            "jdbc:h2:mem:testdata;DB_CLOSE_DELAY=-1;INIT=RUNSCRIPT FROM 'classpath:testdata/test-users.sql'";

    public TestUser findByType(String userType) {
        String sql = "SELECT username, password FROM test_users WHERE user_type = ?";
        try (Connection connection = DriverManager.getConnection(JDBC_URL);
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, userType);
            try (ResultSet result = statement.executeQuery()) {
                if (!result.next()) {
                    throw new IllegalArgumentException("No test user with type: " + userType);
                }
                return new TestUser(result.getString("username"), result.getString("password"));
            }
        } catch (SQLException e) {
            throw new IllegalStateException("Could not read test users from the database", e);
        }
    }

    public List<String> findAllUserTypes() {
        String sql = "SELECT user_type FROM test_users ORDER BY user_type";
        List<String> userTypes = new ArrayList<>();
        try (Connection connection = DriverManager.getConnection(JDBC_URL);
             PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet result = statement.executeQuery()) {
            while (result.next()) {
                userTypes.add(result.getString("user_type"));
            }
        } catch (SQLException e) {
            throw new IllegalStateException("Could not read test users from the database", e);
        }
        return userTypes;
    }
}
