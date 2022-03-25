package ru.agaev.webapp.storage;

import ru.agaev.webapp.model.Resume;

import java.util.Arrays;

public abstract class AbstractArrayStorage implements Storage {
    protected static final int STORAGE_LIMIT = 100000;
    protected Resume[] storage = new Resume[STORAGE_LIMIT];
    protected int count = 0;

    public void save(Resume resume) {
        int index = findIndex(resume.getUuid());
        if (count == STORAGE_LIMIT) {
            System.out.println("Storage is full");
        } else if (index >= 0) {
            System.out.println("Resume " + resume.getUuid() + " already exist");
        } else {
            saveResume(resume, index);
            count++;
        }
    }

    public void clear() {
        Arrays.fill(storage, 0, count, null);
        count = 0;
    }

    public void update(Resume resume) {
        int index = findIndex(resume.getUuid());
        if (index != -1) {
            storage[index] = resume;
            System.out.println("Success. Resume  " + storage[index].getUuid() + " was updated");
        } else {
            System.out.println("Resume " + resume.getUuid() + " not found");
        }

    }

    public Resume get(String uuid) {
        int index = findIndex(uuid);
        if (index >= 0) {
            return storage[index];
        }
        System.out.println("Resume " + uuid + " not found");
        return null;

    }

    public Resume[] getAll() {
        return Arrays.copyOf(storage, count);
    }

    public int size() {
        return count;
    }

    public void delete(String uuid) {
        int index = findIndex(uuid);
        if (index >= 0) {
            if (count - 1 - index >= 0) System.arraycopy(storage, index + 1, storage, index, count - 1 - index);
            count--;
            storage[count] = null;
        } else {
            System.out.println("Resume " + uuid + " not found");
        }
    }

    protected abstract void saveResume(Resume resume, int index);

    protected abstract int findIndex(String uuid);

}
