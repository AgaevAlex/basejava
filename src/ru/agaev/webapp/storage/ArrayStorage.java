package ru.agaev.webapp.storage;

import ru.agaev.webapp.model.Resume;

/**
 * Array based storage for Resumes
 */
public class ArrayStorage extends AbstractArrayStorage {

    @Override
    protected void saveResume(Resume resume) {
        storage[count] = resume;
    }

    @Override
    protected int findIndex(String uuid) {
        for (int i = 0; i < count; i++) {
            if (storage[i].getUuid().equals(uuid)) {
                this.index = i;
                return index;
            }
        }
        return -1;
    }
}
