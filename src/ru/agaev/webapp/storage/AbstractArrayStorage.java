package ru.agaev.webapp.storage;

import ru.agaev.webapp.exception.StorageException;
import ru.agaev.webapp.model.Resume;

import java.util.Arrays;
import java.util.List;

public abstract class AbstractArrayStorage extends AbstractStorage<Integer> {
    protected static final int STORAGE_LIMIT = 10000;
    protected Resume[] storage = new Resume[STORAGE_LIMIT];
    protected int count = 0;

    @Override
    protected List<Resume> doCopyList() {
        return Arrays.asList(Arrays.copyOf(storage, count));
    }

    @Override
    protected boolean isExist(Integer searchKey) {
        return searchKey >= 0;
    }

    @Override
    protected void doSave(Resume resume, Integer searchKey) {
        if (count == STORAGE_LIMIT) {
            throw new StorageException("Storage overflow", resume.getUuid());
        }
        saveResume(resume, searchKey);
        count++;
    }

    public void clear() {
        Arrays.fill(storage, 0, count, null);
        count = 0;
    }

    @Override
    protected void doUpdate(Resume resume, Integer searchKey) {
        int key = searchKey;
        storage[key] = resume;
        System.out.println("Success. Resume  " + storage[key].getUuid() + " was updated");
    }

    @Override
    protected Resume doGet(Integer searchKey) {
        return storage[searchKey];
    }


    public int size() {
        return count;
    }

    @Override
    protected void doRemove(Integer searchKey) {
        int key = searchKey;
        if (count - 1 - key >= 0) System.arraycopy(storage, key + 1, storage, key, count - 1 - key);
        count--;
        storage[count] = null;
    }

    protected abstract void saveResume(Resume resume, int index);

    protected abstract Integer getSearchKey(String uuid);

}
