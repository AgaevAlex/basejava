package ru.agaev.webapp.storage;

import org.junit.Before;
import org.junit.Test;
import ru.agaev.webapp.exception.ExistStorageException;
import ru.agaev.webapp.exception.NotExistStorageException;
import ru.agaev.webapp.exception.StorageException;
import ru.agaev.webapp.model.Resume;

import static org.junit.Assert.*;
import static org.junit.Assert.assertEquals;
import static ru.agaev.webapp.storage.AbstractArrayStorage.STORAGE_LIMIT;

public class AbstractStorageTest {
    private final Storage storage;
    private static final String UUID_1 = "uuid1";
    private static final String UUID_2 = "uuid2";
    private static final String UUID_3 = "uuid3";
    private static final String UUID_4 = "uuid4";
    private static final String UUID_5 = "uuid5";
    private static final String UUID_6 = "uuid4";
    private static final String UUID_7 = "testOverflow";
    private static final Resume resume1 = new Resume(UUID_1);
    private static final Resume resume2 = new Resume(UUID_2);
    private static final Resume resume3 = new Resume(UUID_3);
    private static final Resume resume4 = new Resume(UUID_4);
    private static final Resume resume5 = new Resume(UUID_5);
    private static final Resume resume6 = new Resume(UUID_6);
    private static final Resume resume7 = new Resume(UUID_7);

    public AbstractStorageTest(Storage storage) {
        this.storage = storage;
    }

    @Before
    public void setUp() {
        storage.clear();
        storage.save(resume1);
        storage.save(resume2);
        storage.save(resume3);
        storage.save(resume4);
    }

    @Test
    public void save() {
        storage.save(resume5);
        assertEquals(5, storage.size());
        assertEquals(resume5, storage.get(UUID_5));
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
        storage.save(resume7);
    }

    @Test(expected = ExistStorageException.class)
    public void saveExistResume() {
        storage.save(resume6);
    }

    @Test
    public void clear() {
        storage.clear();
        assertEquals(0, storage.size());
    }

    @Test
    public void update() {
        storage.update(resume4);
        assertSame(resume4, storage.get(UUID_6));
    }

    @Test(expected = NotExistStorageException.class)
    public void updateNotExistResume() {
        storage.update(resume5);
    }

    @Test
    public void get() {
        assertEquals(resume1, storage.get(UUID_1));
    }

    @Test(expected = NotExistStorageException.class)
    public void getNotExist() {
        storage.get("dummy");
    }

    @Test
    public void getAll() {
        Resume[] expected = {resume1, resume2, resume3, resume4};
        assertArrayEquals(expected, storage.getAll());
        assertEquals(4, storage.getAll().length);
    }

    @Test
    public void size() {
        assertEquals(4, storage.size());
    }

    @Test(expected = NotExistStorageException.class)
    public void deleteLast() {
        storage.delete(UUID_4);
        assertEquals(3, storage.size());
        storage.get(UUID_4);
    }

    @Test(expected = NotExistStorageException.class)
    public void deleteFirst() {
        storage.delete(UUID_1);
        Resume[] expected = storage.getAll();
        assertEquals(3, storage.size());
        assertEquals(resume2, expected[0]);
        storage.get(UUID_1);
    }

    @Test(expected = NotExistStorageException.class)
    public void deleteMiddle() {
        storage.delete(UUID_3);
        Resume[] expected = storage.getAll();
        assertEquals(3, storage.size());
        assertEquals(resume4, expected[2]);
        storage.get(UUID_3);
    }
}