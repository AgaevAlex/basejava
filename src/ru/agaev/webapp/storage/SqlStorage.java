package ru.agaev.webapp.storage;

import ru.agaev.webapp.exception.NotExistStorageException;
import ru.agaev.webapp.exception.StorageException;
import ru.agaev.webapp.model.Resume;
import ru.agaev.webapp.sql.SqlHelper;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class SqlStorage<T> implements Storage {
    private SqlHelper sqlHelper;

    public SqlStorage(String dbUrl, String dbUser, String dbPassword) {
        sqlHelper = new SqlHelper(dbUrl, dbUser, dbPassword);
    }

    @Override
    public void clear() {
        sqlHelper.execute("DELETE FROM resume", ps -> {
            ps.execute();
            return null;
        });
    }

    @Override
    public void update(Resume resume) {
        sqlHelper.execute("UPDATE resume SET full_name =? WHERE uuid =?", ps -> {
            ps.setString(1, resume.getFullName());
            ps.setString(2, resume.getUuid());
            if (ps.executeUpdate() == 0) {
                throw new StorageException("Resume not found");
            }
            return null;
        });
    }

    @Override
    public void save(Resume resume) {
        sqlHelper.execute("SELECT * FROM resume WHERE uuid =?", ps -> {
            ps.setString(1, resume.getUuid());
            ResultSet rs = ps.executeQuery();
            if (!rs.next()) {
                sqlHelper.execute("INSERT INTO resume (uuid, full_name) VALUES (?, ?);", ps1 -> {
                    ps1.setString(1, resume.getUuid());
                    ps1.setString(2, resume.getFullName());
                    ps1.execute();
                    return null;
                });
            } else {
                throw new StorageException("Resume" + resume.getUuid() + " already exist");
            }
            return null;
        });
    }

    @Override
    public Resume get(String uuid) {
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
        sqlHelper.execute("DELETE FROM resume WHERE uuid =?", ps -> {
            ps.setString(1, uuid);
            ps.execute();
            return null;
        });

    }

    @Override
    public List<Resume> getAllSorted() {
        return sqlHelper.execute("SELECT * FROM resume ORDER BY full_name,uuid asc", ps -> {
            List<Resume> result = new ArrayList<>();
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                result.add(new Resume(rs.getString(1), rs.getString(2)));
            }

            return result;
        });
    }


    @Override
    public int size() {
        return sqlHelper.execute("SELECT count(*) FROM resume", ps -> {
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt(1);
            }
            return 0;
        });
    }
}

