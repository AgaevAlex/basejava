package ru.agaev.webapp.storage;

import ru.agaev.webapp.model.Resume;

import java.util.HashMap;
import java.util.Map;

public class MapStorage extends AbstractStorage {
    protected Map<Integer, Resume> storage = new HashMap<>();

    @Override
    protected int findIndex(String uuid) {
        return 0;
    }

    @Override
    protected void doSave(Resume resume, int index) {

    }

    @Override
    protected void doUpdate(Resume resume, int index) {

    }

    @Override
    protected Resume doGet(int index) {
        return null;
    }

    @Override
    protected void doRemove(int index) {

    }

    @Override
    public void clear() {

    }

    @Override
    public Resume[] getAll() {
        return new Resume[0];
    }

    @Override
    public int size() {
        return 0;
    }
}
