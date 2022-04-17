package ru.agaev.webapp.storage;

import ru.agaev.webapp.model.Resume;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public abstract class AbstractMapStorage<SK> extends AbstractStorage<SK> {
    protected final Map<String, Resume> storage = new LinkedHashMap<>();

    @Override
    protected boolean isExist(SK index) {
        return index != null;
    }

    @Override
    protected void doSave(Resume resume, SK index) {
        storage.put(resume.getUuid(), resume);
    }

    @Override
    protected void doUpdate(Resume resume, SK index) {
        storage.put(resume.getUuid(), resume);
        System.out.println("Success. Resume  " + storage.get(resume.getUuid()) + " was updated");
    }

    @Override
    public void clear() {
        storage.clear();
    }

    @Override
    protected List<Resume> getListResume() {
        return new ArrayList<>(storage.values());
    }

    @Override
    public int size() {
        return storage.size();
    }


    protected abstract Resume doGet(SK index);

    protected abstract void doRemove(SK index);

    protected abstract SK findSearchKey(String uuid);
}