package ru.agaev.webapp.storage;

import org.junit.Before;
import org.junit.Test;
import ru.agaev.webapp.exception.ExistStorageException;
import ru.agaev.webapp.exception.NotExistStorageException;
import ru.agaev.webapp.model.Resume;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;

public abstract class AbstractStorageTest {
    protected final Storage storage;
    private static final String UUID_1 = "uuid1";
    private static final String UUID_2 = "uuid2";
    private static final String UUID_3 = "uuid3";
    private static final String UUID_4 = "uuid4";
    private static final String UUID_5 = "uuid5";
    private static final String UUID_6 = "uuid4";
    private static final String UUID_7 = "testOverflow";
    private static final Resume resume1 = new Resume(UUID_1, "123");
    private static final Resume resume2 = new Resume(UUID_2, "123");
    private static final Resume resume3 = new Resume(UUID_3, "123");
    private static final Resume resume4 = new Resume(UUID_4, "");
    private static final Resume resume5 = new Resume(UUID_5, "123");
    private static final Resume resume6 = resume4;
    protected static final Resume resume7 = new Resume(UUID_7, "123");

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
        assertEquals(resume5, storage.get(UUID_5));
    }


    @Test(expected = ExistStorageException.class)
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

    @Test(expected = NotExistStorageException.class)
    public void deleteLast() {
        storage.delete(UUID_4);
        assertEquals(3, storage.size());
        storage.get(UUID_4);
    }

    @Test(expected = NotExistStorageException.class)
    public void deleteFirst() {
        storage.delete(UUID_1);
        List<Resume> expected = storage.getAllSorted();
        assertEquals(3, storage.size());
        assertEquals(resume2, expected.get(0));
        storage.get(UUID_1);
    }

    @Test(expected = NotExistStorageException.class)
    public void deleteMiddle() {
        storage.delete(UUID_3);
        List<Resume> expected = storage.getAllSorted();
        assertEquals(3, storage.size());
        assertEquals(resume4, expected.get(2));
        storage.get(UUID_3);
    }
}