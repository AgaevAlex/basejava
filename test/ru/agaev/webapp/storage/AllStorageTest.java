package ru.agaev.webapp.storage;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import ru.agaev.webapp.storage.serializer.JsonStreamSerializer;

@RunWith(Suite.class)
@Suite.SuiteClasses({
        ArrayStorageTest.class,
        SortedArrayStorageTest.class,
        ListStorageTest.class,
        MapUuidStorageTest.class,
        MapResumeStorageTest.class,
        ObjectStreamStorageTest.class,
        ObjectStreamPathStorageTest.class,
        XmlPathStorageTest.class,
        JsonStreamSerializer.class
})
public class AllStorageTest {
}
