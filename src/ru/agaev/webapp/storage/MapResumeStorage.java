package ru.agaev.webapp.storage;

import ru.agaev.webapp.model.Resume;

public class MapResumeStorage extends AbstractMapStorage {

    @Override
    protected Object findSearchKey(String uuid) {
        return storage.getOrDefault(uuid, null);
    }

    @Override
    protected Resume doGet(Object searchKey) {
        return (Resume) searchKey;
    }

    @Override
    protected void doRemove(Object searchKey) {
        Resume result = (Resume) searchKey;
        storage.remove(result.getUuid());
    }
}
