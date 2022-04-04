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
    protected void doUpdate(Resume resume) {
        storage.set(this.index, resume);
        System.out.println("Success. Resume  " + storage.get(this.index) + " was updated");
    }

    @Override
    protected Resume doGet(String uuid) {
        return storage.get(this.index);
    }

    @Override
    protected void doRemove(String uuid) {
        storage.remove(this.index);
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
    protected int findIndex(String uuid) {
        Resume expected = new Resume(uuid);
        this.index = storage.indexOf(expected);
        return storage.indexOf(expected);
    }

    @Override
    protected void doSave(Resume resume) {
        storage.add(resume);
    }


}
