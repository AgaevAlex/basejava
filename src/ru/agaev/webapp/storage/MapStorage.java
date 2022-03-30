package ru.agaev.webapp.storage;

import ru.agaev.webapp.model.Resume;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

public class MapStorage extends AbstractStorage {
    private Map<String, Resume> storage = new LinkedHashMap<>();
    private int counter = 0;

    @Override
    protected int findIndex(String uuid) {
        return storage.containsKey(uuid) ? 1 : -1;
    }

    @Override
    protected void doSave(Resume resume, int index) {
        storage.put(resume.getUuid(), resume);
    }

    @Override
    protected void doUpdate(Resume resume, int index) {
        storage.put(resume.getUuid(), resume);
        System.out.println("Success. Resume  " + storage.get(resume.getUuid()) + " was updated");

    }

    @Override
    protected Resume doGet(int index, String uuid) {
        return storage.get(uuid);
    }

    @Override
    protected void doRemove(int index, String uuid) {
        storage.remove(uuid);
    }

    @Override
    public void clear() {
        storage.clear();
    }

    @Override
    public Resume[] getAll() {
        return storage.values().toArray(new Resume[0]);
    }

    @Override
    public int size() {
        return storage.size();
    }
}
