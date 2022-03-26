package ru.agaev.webapp.storage;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import ru.agaev.webapp.exception.ExistStorageException;
import ru.agaev.webapp.exception.NotExistStorageException;
import ru.agaev.webapp.exception.StorageException;
import ru.agaev.webapp.model.Resume;

import static ru.agaev.webapp.storage.AbstractArrayStorage.STORAGE_LIMIT;
import static org.junit.Assert.*;


public abstract class AbstractArrayStorageTest {
    private final Storage storage;
    private static final String UUID_1 = "uuid1";
    private static final String UUID_2 = "uuid2";
    private static final String UUID_3 = "uuid3";
    private static final String UUID_4 = "uuid4";
    private static final String UUID_5 = "uuid5";
    private static final String UUID_6 = "uuid4";
    private static final String UUID_7 = "testOverflow";
    Resume resume1 = new Resume(UUID_1);
    Resume resume2 = new Resume(UUID_2);
    Resume resume3 = new Resume(UUID_3);
    Resume resume4 = new Resume(UUID_4);
    Resume resume5 = new Resume(UUID_5);
    Resume resume6 = new Resume(UUID_6);
    Resume resume7 = new Resume(UUID_7);

    public AbstractArrayStorageTest(Storage storage) {
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
        storage.update(resume6);
        assertSame(resume6, storage.get(UUID_6));
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
        Resume[] resumes = {resume1, resume2, resume3, resume4};
        assertArrayEquals(resumes, storage.getAll());
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
        Resume[] resumes = storage.getAll();
        assertEquals(3, storage.size());
        assertEquals(resume2, resumes[0]);
        storage.get(UUID_1);
    }

    @Test(expected = NotExistStorageException.class)
    public void deleteMiddle() {
        storage.delete(UUID_3);
        Resume[] resumes = storage.getAll();
        assertEquals(3, storage.size());
        assertEquals(resume4, resumes[2]);
        storage.get(UUID_3);
    }
}