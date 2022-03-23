package ru.agaev.webapp.storage;

import ru.agaev.webapp.model.Resume;

import java.util.Arrays;

/**
 * Array based storage for Resumes
 */
public class ArrayStorage extends AbstractArrayStorage {

    @Override
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

    @Override
    protected int findIndex(String uuid) {
        for (int i = 0; i < counter; i++) {
            if (storage[i].getUuid().equals(uuid)) {
                return i;
            }
        }
        return -1;
    }
}
