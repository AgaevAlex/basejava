package ru.agaev.webapp.storage;

import ru.agaev.webapp.model.Resume;

public class MapResumeStorage extends AbstractMapStorage<Resume> {

    @Override
    protected Resume findSearchKey(String uuid) {
        return storage.getOrDefault(uuid, null);
    }

    @Override
    protected Resume doGet(Resume searchKey) {
        return searchKey;
    }

    @Override
    protected void doRemove(Resume searchKey) {
        storage.remove(searchKey.getUuid());
    }
}
