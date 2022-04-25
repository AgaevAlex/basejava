package ru.agaev.webapp.storage;

import ru.agaev.webapp.serializer.XmlStreamSerializer;

public class XmlPathStorageTest extends AbstractStorageTest {

    public XmlPathStorageTest() {
        super(new PathStorage(STORAGE_DIR, new XmlStreamSerializer()));
    }
}