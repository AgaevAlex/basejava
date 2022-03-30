package ru.agaev.webapp.storage;

import org.junit.Test;

public class MapStorageTest extends AbstractStorageTest {

    public MapStorageTest() {
        super(new MapStorage());
    }

    @Test
    public void storageOverflow() {
    }
}