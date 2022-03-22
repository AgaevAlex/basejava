package ru.agaev.webapp.storage;

import ru.agaev.webapp.model.Resume;

import java.util.Arrays;

public class SortedArrayStorage extends AbstractArrayStorage {
    private int counter = 0;

    @Override
    public void clear() {
        Arrays.fill(storage, 0, counter, null);
        counter = 0;
    }

    @Override
    public void save(Resume resume) {
        int index = findIndex(resume.getUuid());
        if (counter == STORAGE_LIMIT) {
            System.out.println("Storage is full");
        } else if (index >= 0) {
            System.out.println("Resume " + resume.getUuid() + " already exist");
        } else  {
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
    public int size() {
        return counter;
    }

    @Override
    public void update(Resume resume) {
        int index = findIndex(resume.getUuid());
        if (index >= 0) {
            storage[index] = resume;
            System.out.println("Success. Resume  " + storage[index].getUuid() + " was updated");
        } else {
            System.out.println("Resume " + resume.getUuid() + " not found");
        }
    }


    @Override
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

    @Override
    public Resume[] getAll() {
        return Arrays.copyOf(storage, counter);
    }

    @Override
    protected int findIndex(String uuid) {
        Resume searchKey = new Resume();
        searchKey.setUuid(uuid);
        return Arrays.binarySearch(storage, 0, counter, searchKey);
    }
}
