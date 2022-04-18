package ru.agaev.webapp.storage;

import ru.agaev.webapp.model.Resume;

public class MapUuidStorage extends AbstractMapStorage<String> {

    @Override
    protected String findSearchKey(String uuid) {
        return storage.containsKey(uuid) ? uuid : null;
    }

    @Override
    protected Resume doGet(String key) {
        return storage.get(key);
    }

    @Override
    protected void doRemove(String key) {
        storage.remove(key);
    }

}
