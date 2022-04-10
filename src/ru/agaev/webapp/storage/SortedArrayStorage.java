package ru.agaev.webapp.storage;

import ru.agaev.webapp.model.Resume;

import java.util.Arrays;
import java.util.Comparator;

public class SortedArrayStorage extends AbstractArrayStorage {

    private static final Comparator<Resume> RESUME_COMPARATOR = Comparator.comparing(Resume::getUuid);

    @Override
    protected void saveResume(Resume resume, int index) {
        if (count == 0) {
            storage[count] = resume;
        } else {
            if (count + 1 - Math.abs(index) >= 0)
                System.arraycopy(storage, Math.abs(index) - 1, storage, Math.abs(index), count + 1 - Math.abs(index));
            storage[Math.abs(index) - 1] = resume;
        }
    }

    @Override
    protected Object getSearchKey(String uuid) {
        Resume searchKey = new Resume(uuid, " ");
        return Arrays.binarySearch(storage, 0, count, searchKey, RESUME_COMPARATOR);
    }
}
