package ru.agaev.webapp.storage;

import org.junit.Test;
import ru.agaev.webapp.exception.StorageException;
import ru.agaev.webapp.model.Resume;

import static org.junit.Assert.fail;
import static ru.agaev.webapp.storage.AbstractArrayStorage.STORAGE_LIMIT;

public abstract class AbstractArrayStorageTest extends AbstractStorageTest {

    public AbstractArrayStorageTest(Storage storage) {
        super(storage);
    }

    @Test(expected = StorageException.class)
    public void storageOverflow() {
        storage.clear();
        try {
            for (int i = 0; i < STORAGE_LIMIT; i++) {
                storage.save(new Resume("uuid" + i));
            }
        } catch (StorageException e) {
            fail("Stackoverflow ahead of time");
        }
        storage.save(resume4);
    }
}