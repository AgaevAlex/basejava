package ru.agaev.webapp.sql;

import org.postgresql.util.PSQLException;
import ru.agaev.webapp.exception.ExistStorageException;
import ru.agaev.webapp.exception.StorageException;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class SqlHelper {
    private final ConnectionFactory connectionFactory;

    public SqlHelper(String dbUrl, String dbUser, String dbPassword) {
        this.connectionFactory = () -> DriverManager.getConnection(dbUrl, dbUser, dbPassword);
    }

    public <T> T execute(String sql, SqlReader sqlReader) {
        try (Connection conn = connectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            return (T) sqlReader.execute(ps);
        } catch (PSQLException e) {
            throw new ExistStorageException(e.toString());
        } catch (SQLException e) {
            throw new StorageException(e);
        }
    }
}
