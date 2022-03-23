package ru.agaev.webapp.storage;

import ru.agaev.webapp.model.Resume;

import java.util.Arrays;

public abstract class AbstractArrayStorage implements Storage {
    protected static final int STORAGE_LIMIT = 100000;
    protected Resume[] storage = new Resume[STORAGE_LIMIT];
    protected int counter = 0;

    public void clear() {
        Arrays.fill(storage, 0, counter, null);
        counter = 0;
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
        return Arrays.copyOf(storage, counter);
    }

    public int size() {
        return counter;
    }


    public void delete(String uuid) {
        int index = findIndex(uuid);
        if (index >= 0) {
            if (counter - 1 - index >= 0) System.arraycopy(storage, index + 1, storage, index, counter - 1 - index);
            counter--;
            storage[counter] = null;
        } else {
            System.out.println("Resume " + uuid + " not found");
        }
    }

    protected abstract int findIndex(String uuid);

}
