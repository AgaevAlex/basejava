package ru.agaev.webapp.storage;

import ru.agaev.webapp.storage.serializer.JsonStreamSerializer;
import ru.agaev.webapp.storage.serializer.XmlStreamSerializer;

public class JsonPathStorageTest extends AbstractStorageTest {

    public JsonPathStorageTest() {
        super(new PathStorage(STORAGE_DIR, new JsonStreamSerializer()));
    }
}