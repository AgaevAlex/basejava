package ru.agaev.webapp.storage;

import ru.agaev.webapp.model.Resume;

public interface Storage {
    void clear();
    void update(Resume r);
    void save(Resume r);
    Resume get (String uuid);
    void delete(String uuid);
    Resume[] getAll();
    int size();
}
