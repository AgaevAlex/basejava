package ru.agaev.webapp.storage;

import ru.agaev.webapp.model.Resume;

public class MapUuidStorage extends AbstractMapStorage {

    @Override
    protected Object findSearchKey(String uuid) {
        return storage.containsKey(uuid) ? uuid : null;
    }

    @Override
    protected Resume doGet(Object searchKey) {
        return storage.get((String) searchKey);
    }

    @Override
    protected void doRemove(Object searchKey) {
        storage.remove((String) searchKey);
    }
}
