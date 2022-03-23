package ru.agaev.webapp.storage;

import ru.agaev.webapp.model.Resume;

import java.util.Arrays;

public class SortedArrayStorage extends AbstractArrayStorage {


    @Override
    public void save(Resume resume) {
        int index = findIndex(resume.getUuid());
        if (counter == STORAGE_LIMIT) {
            System.out.println("Storage is full");
        } else if (index >= 0) {
            System.out.println("Resume " + resume.getUuid() + " already exist");
        } else {
            if (counter == 0) {
                storage[counter] = resume;
            } else {
                if (counter + 1 - Math.abs(index) >= 0)
                    System.arraycopy(storage, Math.abs(index) - 1, storage, Math.abs(index), counter + 1 - Math.abs(index));
                storage[Math.abs(index) - 1] = resume;
            }
            counter++;
        }
    }


    @Override
    protected int findIndex(String uuid) {
        Resume searchKey = new Resume();
        searchKey.setUuid(uuid);
        return Arrays.binarySearch(storage, 0, counter, searchKey);
    }
}
