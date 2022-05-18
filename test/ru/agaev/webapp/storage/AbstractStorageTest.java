package ru.agaev.webapp.storage;

import org.junit.Before;
import org.junit.Test;
import ru.agaev.webapp.Config;
import ru.agaev.webapp.ResumeTestData;
import ru.agaev.webapp.exception.NotExistStorageException;
import ru.agaev.webapp.exception.StorageException;
import ru.agaev.webapp.model.Resume;

import java.io.File;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public abstract class AbstractStorageTest {
    protected final static File STORAGE_DIR = Config.get().getStorageDir();

    private static final String UUID_1 = "uuid1";
    private static final String UUID_2 = "uuid2";
    private static final String UUID_3 = "uuid3";
    private static final String UUID_4 = "uuid4";
    private static final String UUID_5 = "uuid5";
    private static final String UUID_6 = "uuid4";
    private static final String UUID_7 = "testOverflow";
    protected static final Resume resume7 = ResumeTestData.createResume(UUID_7);
    private static final Resume resume1 = ResumeTestData.createResume(UUID_1);
    private static final Resume resume2 = ResumeTestData.createResume(UUID_2);
    private static final Resume resume3 = ResumeTestData.createResume(UUID_3);
    private static final Resume resume4 = ResumeTestData.createResume(UUID_4);
    private static final Resume resume6 = resume4;
    private static final Resume resume5 = ResumeTestData.createResume(UUID_5);
    protected final Storage storage;

    public AbstractStorageTest(Storage storage) {
        this.storage = storage;
    }

    @Before
    public void setUp() {
        storage.clear();
        storage.save(resume4);
        storage.save(resume3);
        storage.save(resume2);
        storage.save(resume1);
    }

    @Test
    public void save() {
        storage.save(resume5);
        assertEquals(5, storage.size());
        assertEquals(resume5, storage.get(resume5.getUuid()));
    }


    @Test(expected = StorageException.class)
    public void saveExistResume() {
        storage.save(resume4);
    }

    @Test
    public void clear() {
        storage.clear();
        assertEquals(0, storage.size());
    }

    @Test
    public void update() {
        storage.update(resume6);
        assertTrue(resume6.equals(storage.get(resume6.getUuid())));
    }

    @Test(expected = StorageException.class)
    public void updateNotExistResume() {
        storage.update(resume5);
    }

    @Test
    public void get() {
        assertEquals(resume1, storage.get(resume1.getUuid()));
    }

    @Test(expected = NotExistStorageException.class)
    public void getNotExist() {
        storage.get("dummy");
    }

    @Test
    public void getAll() {
        List<Resume> expected = Arrays.asList(resume1, resume2, resume3, resume4);
        assertEquals(expected, storage.getAllSorted());
        assertEquals(4, storage.getAllSorted().size());
    }

    @Test
    public void getAllSorted() {
        List<Resume> expected = Arrays.asList(resume1, resume2, resume3, resume4);
        assertEquals(expected, storage.getAllSorted());
        assertEquals(4, storage.getAllSorted().size());
    }

    @Test
    public void size() {
        assertEquals(4, storage.size());
    }

    @Test(expected = StorageException.class)
    public void deleteLast() {
        storage.delete(resume4.getUuid());
        assertEquals(3, storage.size());
        storage.get(resume4.getUuid());
    }

    @Test(expected = StorageException.class)
    public void deleteFirst() {
        storage.delete(resume1.getUuid());
        List<Resume> expected = storage.getAllSorted();
        assertEquals(3, storage.size());
        assertEquals(resume2, expected.get(0));
        storage.get(resume1.getUuid());
    }

    @Test(expected = StorageException.class)
    public void deleteMiddle() {
        storage.delete(resume3.getUuid());
        List<Resume> expected = storage.getAllSorted();
        assertEquals(3, storage.size());
        assertEquals(resume4, expected.get(2));
        storage.get(resume3.getUuid());
    }
}