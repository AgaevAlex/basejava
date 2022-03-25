package ru.agaev.webapp.storage;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import ru.agaev.webapp.exception.ExistStorageException;
import ru.agaev.webapp.exception.NotExistStorageException;
import ru.agaev.webapp.exception.StorageException;
import ru.agaev.webapp.model.Resume;


public class AbstractArrayStorageTest {
    private static final int STORAGE_LIMIT = 10000;
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
        Assert.assertEquals(5, storage.size());
        Assert.assertEquals(resume5, storage.get(resume5.getUuid()));
    }


    @Test(expected = StorageException.class)
    public void storageOverflow() {
        storage.clear();
        try {
            for (int i = 0; i < STORAGE_LIMIT; i++) {
                storage.save(new Resume("uuid" + i));
            }
        } catch (StorageException e) {
            Assert.fail("Stackoverflow ahead of time");
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
        Assert.assertEquals(0, storage.size());
    }

    @Test
    public void update() {
        storage.update(resume6);
        Assert.assertEquals(resume6, storage.get(resume6.getUuid()));
    }

    @Test(expected = NotExistStorageException.class)
    public void updateNotExistResume() {
        storage.update(resume5);
    }

    @Test
    public void get() {
        Assert.assertEquals(resume1, storage.get(resume1.getUuid()));
    }

    @Test(expected = NotExistStorageException.class)
    public void getNotExist() {
        storage.get("dummy");
    }

    @Test
    public void getAll() {
        Resume[] resumes = new Resume[4];
        resumes[0] = resume1;
        resumes[1] = resume2;
        resumes[2] = resume3;
        resumes[3] = resume4;
        Assert.assertArrayEquals(resumes, storage.getAll());
        Assert.assertEquals(4, storage.getAll().length);

    }

    @Test
    public void size() {
        Assert.assertEquals(4, storage.size());
    }

    @Test(expected = NotExistStorageException.class)
    public void deleteLast() {
        storage.delete(resume4.getUuid());
        Assert.assertEquals(3, storage.size());
        storage.get(resume4.getUuid());
    }

    @Test(expected = NotExistStorageException.class)
    public void deleteFirst() {
        storage.delete(resume1.getUuid());
        Resume[] resumes = storage.getAll();
        Assert.assertEquals(3, storage.size());
        Assert.assertEquals(resume2, resumes[0]);
        storage.get(resume1.getUuid());
    }

    @Test(expected = NotExistStorageException.class)
    public void deleteMiddle() {
        storage.delete(resume3.getUuid());
        Resume[] resumes = storage.getAll();
        Assert.assertEquals(3, storage.size());
        Assert.assertEquals(resume4, resumes[2]);
        storage.get(resume3.getUuid());
    }

}