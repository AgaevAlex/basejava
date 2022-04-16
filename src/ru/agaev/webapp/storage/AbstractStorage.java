package ru.agaev.webapp.storage;

import ru.agaev.webapp.exception.ExistStorageException;
import ru.agaev.webapp.exception.NotExistStorageException;
import ru.agaev.webapp.model.Resume;

import java.util.Collections;
import java.util.List;
import java.util.logging.Logger;

public abstract class AbstractStorage<SK> implements Storage {
    private static final Logger log = Logger.getLogger(AbstractStorage.class.getName());

    protected abstract List<Resume> doCopyList();

    protected abstract boolean isExist(SK searchKey);

    protected abstract SK getSearchKey(String uuid);

    protected abstract void doSave(Resume resume, SK searchKey);

    protected abstract void doUpdate(Resume resume, SK searchKey);

    protected abstract Resume doGet(SK searchKey);

    protected abstract void doRemove(SK searchKey);

    public List<Resume> getAllSorted() {
        List<Resume> result = doCopyList();
        Collections.sort(result);
        return result;
    }

    public void save(Resume resume) {
        log.info("Save " + resume);
        SK searchKey = receiveNotExistingSearchKey(resume.getUuid());
        doSave(resume, searchKey);
    }

    public void update(Resume resume) {
        log.info("Update " + resume);
        SK searchKey = receiveExistingSearchKey(resume.getUuid());
        doUpdate(resume, searchKey);
    }

    public Resume get(String uuid) {
        log.info("Get " + uuid);
        SK searchKey = receiveExistingSearchKey(uuid);
        return doGet(searchKey);
    }

    public void delete(String uuid) {
        log.info("Delete " + uuid);
        SK searchKey = receiveExistingSearchKey(uuid);
        doRemove(searchKey);
    }

    public SK receiveExistingSearchKey(String uuid) {
        SK searchKey = getSearchKey(uuid);
        if (!isExist(searchKey)) {
            log.warning("Resume " + uuid + " not exist");
            throw new NotExistStorageException(uuid);
        }
        return searchKey;
    }

    public SK receiveNotExistingSearchKey(String uuid) {
        SK searchKey = getSearchKey(uuid);
        if (isExist(searchKey)) {
            log.warning("Resume " + uuid + " already exist");
            throw new ExistStorageException(uuid);
        }
        return searchKey;
    }
}
