package ru.agaev.webapp.storage;

import ru.agaev.webapp.model.Resume;

/**
 * Array based storage for Resumes
 */
public class ArrayStorage {
    private Resume[] storage = new Resume[10000];
    private int counter = 0;

    public void clear() {
        for (int i = 0; i < counter; i++) {
            storage[i] = null;
        }
        counter = 0;
    }

    public void save(Resume resume) {
        int index = findIndex(resume.getUuid());
        if (counter == storage.length) {
            System.out.println("Storage is full");
        } else if (index != -1) {
            System.out.println("Resume " + resume.getUuid() + " already exist");
        } else {
            storage[counter++] = resume;
        }
    }

    public Resume get(String uuid) {
        int index = findIndex(uuid);
        if (index != -1) {
            return storage[index];
        }
        System.out.println("Resume " + uuid + " not found");
        return null;

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
        Resume[] storageTwo = new Resume[counter];

        System.arraycopy(storage, 0, storageTwo, 0, counter);
        return storageTwo;
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
            System.out.println("Resume not found");
        }
    }


    private int findIndex(String uuid) {
        for (int i = -0; i < counter; i++) {
            if (storage[i].getUuid().equals(uuid)) {
                return i;
            }
        }
        return -1;
    }
}
