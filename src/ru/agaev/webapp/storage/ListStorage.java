package ru.agaev.webapp.storage;

import ru.agaev.webapp.model.Resume;

import java.util.LinkedList;
import java.util.List;

public class ListStorage extends AbstractStorage {
    private final List<Resume> storage = new LinkedList<>();

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
    public Resume[] getAll() {
        return storage.toArray(new Resume[0]);
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
