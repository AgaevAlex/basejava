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
    protected List<Resume> getListResume() {
        return Arrays.asList(Arrays.copyOf(storage, count));
    }

    @Override
    protected boolean isExist(Integer index) {
        return index >= 0;
    }

    @Override
    protected void doSave(Resume resume, Integer index) {
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
    protected void doUpdate(Resume resume, Integer ind) {
        int index = ind;
        storage[index] = resume;
        System.out.println("Success. Resume  " + storage[index].getUuid() + " was updated");
    }

    @Override
    protected Resume doGet(Integer index) {
        return storage[index];
    }


    public int size() {
        return count;
    }

    @Override
    protected void doRemove(Integer ind) {
        int index = ind;
        if (count - 1 - index >= 0) System.arraycopy(storage, index + 1, storage, index, count - 1 - index);
        count--;
        storage[count] = null;
    }

    protected abstract void saveResume(Resume resume, int index);

    protected abstract Integer getSearchKey(String uuid);

}
