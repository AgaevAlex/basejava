package ru.agaev.webapp.storage;

import ru.agaev.webapp.model.Resume;

import java.util.Arrays;

/**
 * Array based storage for Resumes
 */
public class ArrayStorage extends AbstractArrayStorage {
    private int counter = 0;

    public void clear() {
        Arrays.fill(storage, 0, counter, null);
        counter = 0;
    }

    public void save(Resume resume) {
        int index = findIndex(resume.getUuid());
        if (counter == STORAGE_LIMIT) {
            System.out.println("Storage is full");
        } else if (index != -1) {
            System.out.println("Resume " + resume.getUuid() + " already exist");
        } else {
            storage[counter++] = resume;
        }
    }

    public void delete(String uuid) {
        int index = findIndex(uuid);
        if (index != -1) {
            if (counter - 1 - index >= 0) System.arraycopy(storage, index + 1, storage, index, counter - 1 - index);
            counter--;
            storage[counter] = null;
        } else {
            System.out.println("Resume " + uuid + " not found");
        }
    }

    /**
     * @return array, contains only Resumes in storage (without null)
     */
    public Resume[] getAll() {
        return Arrays.copyOf(storage, counter);
    }

    public int size() {
        return counter;
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


    protected int findIndex(String uuid) {
        for (int i = 0; i < counter; i++) {
            if (storage[i].getUuid().equals(uuid)) {
                return i;
            }
        }
        return -1;
    }
}
