package ru.agaev.webapp.storage;

import ru.agaev.webapp.model.Resume;

import java.util.*;

public class ListStorage extends AbstractStorage {
    private List<Resume> storage = new LinkedList<>();

    @Override
    public void clear() {
        storage.clear();
    }

    @Override
    protected void doUpdate(Resume resume, int index) {
        storage.set(index, resume);
        System.out.println("Success. Resume  " + storage.get(index) + " was updated");
    }

    @Override
    protected Resume doGet(int index, String uuid) {
        return storage.get(index);
    }

    @Override
    protected void doRemove(int index, String uuid) {
        storage.remove(index);
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
        return storage.indexOf(expected);
//        for (Resume resume : storage) {
//            if (resume.getUuid().equals(uuid)) {
//                return storage.indexOf(resume);
//            }
//        }
//        return -1;
    }

    @Override
    protected void doSave(Resume resume, int index) {
        storage.add(resume);
    }


}
