package ru.agaev.webapp.storage;

import ru.agaev.webapp.model.Resume;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ListStorage extends AbstractStorage {
    private final List<Resume> storage = new ArrayList<>();

    @Override
    public void clear() {
        storage.clear();
    }

    @Override
    protected void doUpdate(Resume resume, Object searchKey) {
        storage.set((int) searchKey, resume);
        System.out.println("Success. Resume  " + storage.get((int) searchKey) + " was updated");
    }

    @Override
    protected Resume doGet(Object searchKey) {
        return storage.get((int) searchKey);
    }

    @Override
    protected void doRemove(Object searchKey) {
        storage.remove((int) searchKey);
    }

    @Override
    public List<Resume> getAllSorted() {
        List<Resume> result = storage;
        result.sort(new StorageComparator());
        return result;
    }

    @Override
    public int size() {
        return storage.size();
    }

    @Override
    protected boolean resumeExistOrNot(Object searchKey) {
        return (int) searchKey >= 0;
    }

    @Override
    protected Object findSearchKey(String uuid) {
        return storage.indexOf(new Resume(uuid));
    }

    @Override
    protected void doSave(Resume resume, Object searchKey) {
        storage.add(resume);
    }


}
