package ru.agaev.webapp.storage;

import ru.agaev.webapp.storage.serializer.ObjectStreamSerializer;

public class ObjectStreamStorageTest extends AbstractStorageTest {

    public ObjectStreamStorageTest() {
        super(new FileStorage(STORAGE_DIR.toString(), new ObjectStreamSerializer()));
    }
}