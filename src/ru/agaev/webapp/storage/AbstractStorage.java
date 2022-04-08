package ru.agaev.webapp.storage;

import ru.agaev.webapp.exception.ExistStorageException;
import ru.agaev.webapp.exception.NotExistStorageException;
import ru.agaev.webapp.model.Resume;

import java.util.Comparator;
import java.util.List;

public abstract class AbstractStorage implements Storage {
    protected Comparator<Resume> comparator = Comparator.comparing(Resume::getFullName).thenComparing(Resume::getUuid);

    public List<Resume> getAllSorted() {
        List<Resume> result = doList();
        result.sort(comparator);
        return result;
    }

    public void save(Resume resume) {
        Object searchKey = receiveExistingSearchKey(resume.getUuid());
        doSave(resume, searchKey);
    }

    public void update(Resume resume) {
        Object searchKey = receiveNotExistingSearchKey(resume.getUuid());
        doUpdate(resume, searchKey);
    }

    public Resume get(String uuid) {
        Object searchKey = receiveNotExistingSearchKey(uuid);
        return doGet(searchKey);
    }

    public void delete(String uuid) {
        Object searchKey = receiveNotExistingSearchKey(uuid);
        doRemove(searchKey);
    }

    public Object receiveNotExistingSearchKey(String uuid) {
        Object searchKey = findSearchKey(uuid);
        if (!isExist(searchKey)) {
            throw new NotExistStorageException(uuid);
        }
        return searchKey;
    }

    public Object receiveExistingSearchKey(String uuid) {
        Object searchKey = findSearchKey(uuid);
        if (isExist(searchKey)) {
            throw new ExistStorageException(uuid);
        }
        return searchKey;
    }

    protected abstract List<Resume> doList();

    protected abstract boolean isExist(Object searchKey);

    protected abstract Object findSearchKey(String uuid);

    protected abstract void doSave(Resume resume, Object searchKey);

    protected abstract void doUpdate(Resume resume, Object searchKey);

    protected abstract Resume doGet(Object searchKey);

    protected abstract void doRemove(Object searchKey);

}
