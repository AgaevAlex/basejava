package ru.agaev.webapp.sql;

import org.postgresql.util.PSQLException;
import ru.agaev.webapp.exception.ExistStorageException;
import ru.agaev.webapp.exception.StorageException;

import java.sql.SQLException;

public class ExceptionUtil {
    private ExceptionUtil() {
    }

    public static StorageException convertException(SQLException e) {
        if (e instanceof PSQLException && e.getSQLState().equals("23505")) {
            return new ExistStorageException(e.toString());
        }
        return new StorageException(e);
    }

}
