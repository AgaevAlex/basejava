package ru.agaev.webapp.storage;

import ru.agaev.webapp.exception.StorageException;
import ru.agaev.webapp.model.Resume;

import java.util.Arrays;

public abstract class AbstractArrayStorage extends AbstractStorage {
    protected static final int STORAGE_LIMIT = 10000;
    protected Resume[] storage = new Resume[STORAGE_LIMIT];
    protected int count = 0;

    @Override
    protected void doSave(Resume resume, Object searchKey) {
        if (count == STORAGE_LIMIT) {
            throw new StorageException("Storage overflow", resume.getUuid());
        }
        saveResume(resume, (int) searchKey);
        count++;
    }

    public void clear() {
        Arrays.fill(storage, 0, count, null);
        count = 0;
    }

    @Override
    protected void doUpdate(Resume resume, Object searchKey) {
        int key = (int) searchKey;
        storage[key] = resume;
        System.out.println("Success. Resume  " + storage[key].getUuid() + " was updated");
    }

    @Override
    protected Resume doGet(Object searchKey) {
        return storage[(int) searchKey];
    }

    public Resume[] getAll() {
        return Arrays.copyOf(storage, count);
    }

    public int size() {
        return count;
    }

    @Override
    protected void doRemove(Object searchKey) {
        int key = (int) searchKey;
        if (count - 1 - key >= 0) System.arraycopy(storage, key + 1, storage, key, count - 1 - key);
        count--;
        storage[count] = null;
    }

    protected abstract void saveResume(Resume resume, int index);

    protected abstract Object findSearchKey(String uuid);

}
