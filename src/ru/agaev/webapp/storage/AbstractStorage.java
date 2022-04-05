package ru.agaev.webapp.storage;

import ru.agaev.webapp.exception.ExistStorageException;
import ru.agaev.webapp.exception.NotExistStorageException;
import ru.agaev.webapp.model.Resume;

public abstract class AbstractStorage implements Storage {

    public void save(Resume resume) {
        Object searchKey = resumeExists(resume.getUuid());
        doSave(resume, searchKey);
    }

    public void update(Resume resume) {
        Object searchKey = resumeNotExists(resume.getUuid());
        doUpdate(resume, searchKey);
    }

    public Resume get(String uuid) {
        Object searchKey = resumeNotExists(uuid);
        return doGet(searchKey);
    }

    public void delete(String uuid) {
        Object searchKey = resumeNotExists(uuid);
        doRemove(searchKey);
    }

    public Object resumeNotExists(String uuid) {
        int searchKey = (int) findIndex(uuid);
        if (searchKey < 0) {
            throw new NotExistStorageException(uuid);
        }
        return searchKey;
    }

    public Object resumeExists(String uuid) {
        int searchKey = (int) findIndex(uuid);
        if (searchKey >= 0) {
            throw new ExistStorageException(uuid);
        }
        return searchKey;
    }

    protected abstract Object findIndex(String uuid);

    protected abstract void doSave(Resume resume, Object searchKey);

    protected abstract void doUpdate(Resume resume, Object searchKey);

    protected abstract Resume doGet(Object searchKey);

    protected abstract void doRemove(Object searchKey);

}
