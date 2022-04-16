package ru.agaev.webapp.storage;

import ru.agaev.webapp.model.Resume;

public class MapResumeStorage extends AbstractMapStorage<Resume> {

    @Override
    protected Resume getSearchKey(String uuid) {
        return storage.getOrDefault(uuid, null);
    }

    @Override
    protected Resume doGet(Resume searchKey) {
        return searchKey;
    }

    @Override
    protected void doRemove(Resume searchKey) {
        Resume result = searchKey;
        storage.remove(result.getUuid());
    }
}
