package ru.agaev.webapp.storage;

import ru.agaev.webapp.exception.NotExistStorageException;
import ru.agaev.webapp.exception.StorageException;
import ru.agaev.webapp.model.Resume;
import ru.agaev.webapp.sql.ConnectionFactory;

import java.sql.*;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

interface sqlReader<T> {
    T execute(PreparedStatement ps) throws SQLException;
}

public class SqlStorage<T> implements Storage {
    protected Comparator<Resume> comparator = Comparator.comparing(Resume::getFullName).thenComparing(Resume::getUuid);
    private ConnectionFactory connectionFactory;
    private sqlHelper sqlHelper;

    public SqlStorage(String dbUrl, String dbUser, String dbPassword) {
        this.connectionFactory = () -> DriverManager.getConnection(dbUrl, dbUser, dbPassword);
        sqlHelper = new sqlHelper(connectionFactory);
    }

    @Override
    public void clear() {
//        try (Connection conn = connectionFactory.getConnection();
//             PreparedStatement ps = conn.prepareStatement("DELETE FROM resume");
//        ) {
//            ps.execute();
//        } catch (SQLException e) {
//            throw new StorageException(e);
//        }

        sqlHelper.execute("DELETE FROM resume", ps -> {
            ps.execute();
            return null;
        });
    }

    @Override
    public void update(Resume resume) {
//        try (Connection conn = connectionFactory.getConnection();
//             PreparedStatement ps = conn.prepareStatement("UPDATE resume SET full_name =? WHERE uuid =?")
//        ) {
//            ps.setString(1, resume.getFullName());
//            ps.setString(2, resume.getUuid());
//            int rows = ps.executeUpdate();
//            if (rows == 0) {
//                throw new StorageException("Resume not found");
//            }
//        } catch (SQLException e) {
//            throw new StorageException(e);
//        }

        sqlHelper.execute("UPDATE resume SET full_name =? WHERE uuid =?", ps -> {
            ps.setString(1, resume.getFullName());
            ps.setString(2, resume.getUuid());
            int rows = ps.executeUpdate();
            if (rows == 0) {
                throw new StorageException("Resume not found");
            }
            return null;
        });
    }

    @Override
    public void save(Resume resume) {
//        try (Connection conn = connectionFactory.getConnection();
//             PreparedStatement ps = conn.prepareStatement("INSERT INTO resume (uuid, full_name) VALUES (?, ?);")
//        ) {
//            ps.setString(1, resume.getUuid());
//            ps.setString(2, resume.getFullName());
//            ps.execute();
//        } catch (SQLException e) {
//            throw new StorageException(e);
//        }
        sqlHelper.execute("INSERT INTO resume (uuid, full_name) VALUES (?, ?);", ps -> {
            ps.setString(1, resume.getUuid());
            ps.setString(2, resume.getFullName());
            ps.execute();
            return null;
        });

    }

    @Override
    public Resume get(String uuid) {
//        try (Connection conn = connectionFactory.getConnection();
//             PreparedStatement ps = conn.prepareStatement("SELECT * FROM resume  WHERE uuid =?")
//        ) {
//            ps.setString(1, uuid);
//            ResultSet rs = ps.executeQuery();
//            if (!rs.next()) {
//                throw new NotExistStorageException(uuid);
//            }
//            Resume r = new Resume(uuid, rs.getString("full_name"));
//            return r;
//        } catch (SQLException e) {
//            throw new StorageException(e);
//        }

        return sqlHelper.execute("SELECT * FROM resume  WHERE uuid =?", ps -> {
            ps.setString(1, uuid);
            ResultSet rs = ps.executeQuery();
            if (!rs.next()) {
                throw new NotExistStorageException(uuid);
            }
            return new Resume(uuid, rs.getString("full_name"));
        });
    }

    @Override
    public void delete(String uuid) {
//        try (Connection conn = connectionFactory.getConnection();
//             PreparedStatement ps = conn.prepareStatement("DELETE FROM resume WHERE uuid =?")
//        ) {
//            ps.setString(1, uuid);
//            ps.execute();
//        } catch (SQLException e) {
//            throw new StorageException(e);
//        }

        sqlHelper.execute("DELETE FROM resume WHERE uuid =?", ps -> {
            ps.setString(1, uuid);
            ps.execute();
            return null;
        });

    }

    @Override
    public List<Resume> getAllSorted() {
//        List<Resume> result = new ArrayList<>();
//        try (Connection conn = connectionFactory.getConnection();
//             PreparedStatement ps = conn.prepareStatement("SELECT * FROM resume")) {
//            ResultSet rs = ps.executeQuery();
//            while (rs.next()) {
//                result.add(new Resume(rs.getString(1), rs.getString(2)));
//            }
//        } catch (SQLException e) {
//            throw new StorageException(e);
//        }
//        result.sort(comparator);
//        return result;

        return sqlHelper.execute("SELECT * FROM resume", ps -> {
            List<Resume> result = new ArrayList<>();
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                result.add(new Resume(rs.getString(1), rs.getString(2)));
            }
            result.sort(comparator);
            return result;
        });
    }

    @Override
    public int size() {
        //        try (Connection conn = connectionFactory.getConnection();
//             PreparedStatement ps = conn.prepareStatement("SELECT count(*) FROM resume")) {
//            ResultSet rs = ps.executeQuery();
//            if (rs.next()) {
//                return Integer.parseInt(rs.getString(1));
//            }
//        } catch (SQLException e) {
//            throw new StorageException(e);
//        }

        return sqlHelper.execute("SELECT count(*) FROM resume", ps -> {
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return Integer.parseInt(rs.getString(1));
            }
            return 0;
        });
    }
}

class sqlHelper {
    private final ConnectionFactory connectionFactory;

    public sqlHelper(ConnectionFactory connectionFactory) {
        this.connectionFactory = connectionFactory;
    }

    public <T> T execute(String sql, sqlReader sqlReader) {
        try (Connection conn = connectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            return (T) sqlReader.execute(ps);
        } catch (SQLException e) {
            throw new StorageException(e);
        }
    }
}