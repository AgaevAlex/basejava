package ru.agaev.webapp.sql;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public interface SqlReader<T> {
    T execute(PreparedStatement ps) throws SQLException;
}
