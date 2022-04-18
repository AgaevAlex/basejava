package ru.agaev.webapp.storage;

import ru.agaev.webapp.exception.StorageException;
import ru.agaev.webapp.model.Resume;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public abstract class AbstractFileStorage extends AbstractStorage<File> {
    private File directiory;

    protected AbstractFileStorage(File directory) {
        Objects.requireNonNull(directory, "directory must not be null");
        if (!directory.isDirectory()) {
            throw new IllegalArgumentException(directory.getAbsolutePath() + " is not directory");
        }
        if (!directory.canRead() || !directory.canWrite()) {
            throw new IllegalArgumentException(directory.getAbsolutePath() + " is not readable/writable");
        }
        this.directiory = directory;

    }

    @Override
    protected List<Resume> getListResume() {
        List<Resume> result = new ArrayList<>();
        if (directiory.list() != null) {
            for (File file : Objects.requireNonNull(directiory.listFiles())) {
                try {
                    result.add(doRead(file));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return result;
    }

    @Override
    protected boolean isExist(File file) {
        return file.exists();
    }

    @Override
    protected File findSearchKey(String uuid) {
        return new File(directiory, uuid);
    }

    @Override
    protected void doSave(Resume resume, File file) {
        try {
            file.createNewFile();
            doWrite(resume, file);
        } catch (IOException e) {
            throw new StorageException("IO error", file.getName(), e);
        }
    }

    protected abstract void doWrite(Resume resume, File file) throws IOException;

    protected abstract Resume doRead(File file) throws IOException;

    @Override
    protected void doUpdate(Resume resume, File file) {
        try {
            doWrite(resume, file);
        } catch (IOException e) {
            throw new StorageException("IO error", file.getName(), e);
        }
    }

    @Override
    protected Resume doGet(File file) {
        try {
            return doRead(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void doRemove(File file) {
        file.delete();
    }

    @Override
    public void clear() {
        if (directiory.list() != null) {
            for (File file : Objects.requireNonNull(directiory.listFiles())) {
                file.delete();
            }
        }
        directiory.delete();

    }

    @Override
    public int size() {
        return Objects.requireNonNull(directiory.list()).length;
    }
}
