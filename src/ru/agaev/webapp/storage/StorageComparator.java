package ru.agaev.webapp.storage;

import ru.agaev.webapp.model.Resume;

import java.util.Comparator;

public class StorageComparator implements Comparator<Resume> {
    @Override
    public int compare(Resume o1, Resume o2) {
        if (o1.getFullName().length() > o2.getFullName().length()) {
            return 1;
        }
        if ((o1.getFullName().length() < o2.getFullName().length())) {
            return -1;
        }
        if (o1.getUuid().length() > o2.getUuid().length()) {
            return 1;
        }
        if ((o1.getUuid().length() < o2.getUuid().length())) {
            return -1;
        }
        return o1.getUuid().compareTo(o2.getUuid());
    }
}
