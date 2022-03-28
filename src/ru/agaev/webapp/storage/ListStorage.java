package ru.agaev.webapp.storage;

import ru.agaev.webapp.exception.ExistStorageException;
import ru.agaev.webapp.exception.NotExistStorageException;
import ru.agaev.webapp.model.Resume;

import java.util.*;

public class ListStorage extends AbstractStorage {
    protected LinkedList<Resume> storage = new LinkedList<>();

    @Override

    public void clear() {
        storage.clear();
    }

    @Override
    public void update(Resume resume) {
        int index = storage.indexOf(resume);
        if (index < 0) {
            throw new NotExistStorageException(resume.getUuid());
        }
        storage.set(index, resume);
        System.out.println("Success. Resume  " + storage.get(index) + " was updated");
    }

    @Override
    public void save(Resume resume) {
        int index = storage.indexOf(resume);
        if (index != -1) {
            throw new ExistStorageException(resume.getUuid());
        }
        storage.add(resume);
    }

    @Override
    public Resume get(String uuid) {
        for (Resume result : storage) {
            if (result.getUuid().equals(uuid)) {
                return result;
            }
        }
        throw new NotExistStorageException(uuid);
    }

    @Override
    public void delete(String uuid) {
        Iterator<Resume> iterator = storage.iterator();
        boolean deleteCheck = false;

        while (iterator.hasNext()) {
            Resume r = iterator.next();
            if (Objects.equals(r.getUuid(), uuid)) {
                iterator.remove();
                deleteCheck = true;
            }
        }
        if (!deleteCheck) {
            throw new NotExistStorageException(uuid);
        }
    }

    @Override
    public Resume[] getAll() {
        return storage.toArray(new Resume[0]);
    }

    @Override
    public int size() {
        return storage.size();
    }


}
