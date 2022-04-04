package ru.agaev.webapp.storage;

import ru.agaev.webapp.exception.ExistStorageException;
import ru.agaev.webapp.exception.NotExistStorageException;
import ru.agaev.webapp.model.Resume;

public abstract class AbstractStorage implements Storage {
    protected int index;

    public void save(Resume resume) {
        findIndex(resume.getUuid());
        if (index >= 0) {
            throw new ExistStorageException(resume.getUuid());
        }
        doSave(resume);
    }

    public void update(Resume resume) {
        findIndex(resume.getUuid());
        if (index < 0) {
            throw new NotExistStorageException(resume.getUuid());
        }
        doUpdate(resume);
    }

    public Resume get(String uuid) {
        findIndex(uuid);
        if (index < 0) {
            throw new NotExistStorageException(uuid);
        }
        return doGet(uuid);
    }

    @Override
    public void delete(String uuid) {
        findIndex(uuid);
        if (index < 0) {
            throw new NotExistStorageException(uuid);
        }
        doRemove(uuid);
    }

    protected abstract void findIndex(String uuid);

    protected abstract void doSave(Resume resume);

    protected abstract void doUpdate(Resume resume);

    protected abstract Resume doGet(String uuid);

    protected abstract void doRemove(String uuid);

}
