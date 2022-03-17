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
        int i = storageContainsResume(resume.getUuid());
        if (i == -1 && counter < storage.length) {
            storage[counter++] = resume;
        } else if (i != -1) {
            System.out.println("Resume " + resume.getUuid() + " already exist");
        } else if (counter == storage.length) {
            System.out.println("Storage is full");
        }
    }

    public Resume get(String uuid) {
        int i = storageContainsResume(uuid);
        if (i != -1) {
            return storage[i];
        } else {
            System.out.println("Resume " + uuid + " not found");
            return null;
        }
    }

    public void delete(String uuid) {
        int i = storageContainsResume(uuid);
        if (i != -1) {
            for (int j = i; j < counter - 1; j++) {
                storage[j] = storage[j + 1];
            }
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

        for (int i = 0; i < counter; i++) {
            storageTwo[i] = storage[i];
        }
        return storageTwo;
    }

    public int size() {
        return counter;
    }

    public void update(Resume resume) {
        int i = storageContainsResume(resume.getUuid());
        if (i != -1) {
            storage[i] = resume;
            System.out.println("Success");
        } else {
            System.out.println("Resume not found");
        }
    }


    public int storageContainsResume(String uuid) {
        for (int i = -0; i < counter; i++) {
            if (storage[i].getUuid().equals(uuid)) {
                return i;
            }
        }
        return -1;
    }
}
