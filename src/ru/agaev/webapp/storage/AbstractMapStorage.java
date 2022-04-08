package ru.agaev.webapp.storage;

import ru.agaev.webapp.model.Resume;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public abstract class AbstractMapStorage extends AbstractStorage {
    protected final Map<String, Resume> storage = new LinkedHashMap<>();

    @Override
    protected boolean isExist(Object searchKey) {
        return searchKey != null;
    }

    @Override
    protected void doSave(Resume resume, Object searchKey) {
        storage.put(resume.getUuid(), resume);
    }

    @Override
    protected void doUpdate(Resume resume, Object searchKey) {
        storage.put(resume.getUuid(), resume);
        System.out.println("Success. Resume  " + storage.get(resume.getUuid()) + " was updated");
    }

    @Override
    public void clear() {
        storage.clear();
    }

    @Override
    protected List<Resume> doList() {
        return new ArrayList<>(storage.values());
    }

    @Override
    public int size() {
        return storage.size();
    }


    protected abstract Resume doGet(Object searchKey);

    protected abstract void doRemove(Object searchKey);

    protected abstract Object findSearchKey(String uuid);
}