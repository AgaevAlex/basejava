package ru.agaev.webapp.storage;

import ru.agaev.webapp.model.Resume;

import java.util.Arrays;

public class SortedArrayStorage extends AbstractArrayStorage {

    @Override
    public void save(Resume resume) {
        int index = findIndex(resume.getUuid());
        if (count == STORAGE_LIMIT) {
            System.out.println("Storage is full");
        } else if (index >= 0) {
            System.out.println("Resume " + resume.getUuid() + " already exist");
        } else {
            if (count == 0) {
                storage[count] = resume;
            } else {
                if (count + 1 - Math.abs(index) >= 0)
                    System.arraycopy(storage, Math.abs(index) - 1, storage, Math.abs(index), count + 1 - Math.abs(index));
                storage[Math.abs(index) - 1] = resume;
            }
            count++;
        }
    }

    @Override
    protected int findIndex(String uuid) {
        Resume searchKey = new Resume();
        searchKey.setUuid(uuid);
        return Arrays.binarySearch(storage, 0, count, searchKey);
    }
}
