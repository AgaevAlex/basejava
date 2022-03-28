package ru.agaev.webapp.storage;

import ru.agaev.webapp.exception.StorageException;
import ru.agaev.webapp.model.Resume;

import java.util.Arrays;

public abstract class AbstractArrayStorage extends AbstractStorage {
    protected static final int STORAGE_LIMIT = 10000;
    protected Resume[] storage = new Resume[STORAGE_LIMIT];
    protected int count = 0;

    @Override
    protected void doSave(Resume resume, int index) {
        if (count == STORAGE_LIMIT) {
            throw new StorageException("Storage overflow", resume.getUuid());
        }
        saveResume(resume, index);
        count++;
    }

    public void clear() {
        Arrays.fill(storage, 0, count, null);
        count = 0;
    }

    @Override
    protected void doUpdate(Resume resume, int index) {
        storage[index] = resume;
        System.out.println("Success. Resume  " + storage[index].getUuid() + " was updated");
    }


    @Override
    protected Resume doGet(int index) {
        return storage[index];
    }

    public Resume[] getAll() {
        return Arrays.copyOf(storage, count);
    }

    public int size() {
        return count;
    }

    @Override
    protected void doRemove(int index) {
        if (count - 1 - index >= 0) System.arraycopy(storage, index + 1, storage, index, count - 1 - index);
        count--;
        storage[count] = null;
    }

    protected abstract void saveResume(Resume resume, int index);

    protected abstract int findIndex(String uuid);

}
