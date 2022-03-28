package ru.agaev.webapp.storage;

import junit.framework.TestCase;
import org.junit.Test;
import ru.agaev.webapp.exception.StorageException;
import ru.agaev.webapp.model.Resume;

import static org.junit.Assert.fail;
import static ru.agaev.webapp.storage.AbstractArrayStorage.STORAGE_LIMIT;

public class ListStorageTest extends AbstractArrayStorageTest {
    public ListStorageTest() {
        super(new ListStorage());
    }

    @Test
    public void storageOverflow() {
    }
}