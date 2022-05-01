package ru.agaev.webapp.storage;

import ru.agaev.webapp.storage.serializer.DataStreamSerializer;

public class DataPathStorageTest extends AbstractStorageTest {

    public DataPathStorageTest() {
        super(new PathStorage(STORAGE_DIR, new DataStreamSerializer()));
    }
}