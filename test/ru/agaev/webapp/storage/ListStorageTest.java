package ru.agaev.webapp.storage;

import org.junit.Test;

public class ListStorageTest extends AbstractStorageTest {
    public ListStorageTest() {
        super(new ListStorage());
    }

    @Test
    public void storageOverflow() {
    }
}