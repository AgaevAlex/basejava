package ru.agaev.webapp.storage;

import org.junit.Ignore;
import org.junit.Test;

public class ListStorageTest extends AbstractStorageTest {
    public ListStorageTest() {
        super(new ListStorage());
    }

    @Test
    @Ignore
    public void storageOverflow() {
    }
}