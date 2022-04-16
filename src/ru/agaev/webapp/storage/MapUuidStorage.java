package ru.agaev.webapp.storage;

import ru.agaev.webapp.model.Resume;

public class MapUuidStorage extends AbstractMapStorage<String> {

    @Override
    protected String getSearchKey(String uuid) {
        return storage.containsKey(uuid) ? uuid : null;
    }

    @Override
    protected Resume doGet(String searchKey) {
        return storage.get(searchKey);
    }

    @Override
    protected void doRemove(String searchKey) {
        storage.remove(searchKey);
    }
}
