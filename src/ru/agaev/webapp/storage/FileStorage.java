package ru.agaev.webapp.storage;

import ru.agaev.webapp.exception.StorageException;
import ru.agaev.webapp.model.Resume;
import ru.agaev.webapp.storage.serializer.StreamSerializer;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class FileStorage extends AbstractStorage<File> {
    private File directory;
    private StreamSerializer streamSerializer;


    protected FileStorage(String dir, StreamSerializer streamSerializer) {
        this.streamSerializer = streamSerializer;
        this.directory = new File(dir);
        Objects.requireNonNull(directory, "directory must not be null");
        if (!directory.isDirectory()) {
            throw new IllegalArgumentException(directory.getAbsolutePath() + " is not directory");
        }
        if (!directory.canRead() || !directory.canWrite()) {
            throw new IllegalArgumentException(directory.getAbsolutePath() + " is not readable/writable");
        }
    }

    @Override
    protected List<Resume> getListResume() {
        List<Resume> result = new ArrayList<>();
        nullChecker();
        for (File file : directory.listFiles()) {
            result.add(doGet(file));
        }
        return result;
    }

    @Override
    protected boolean isExist(File file) {
        return file.exists();
    }

    @Override
    protected File getSearchKey(String uuid) {
        return new File(directory, uuid);
    }

    @Override
    protected void doSave(Resume r, File file) {
        try {
            file.createNewFile();
        } catch (IOException e) {
            throw new StorageException("Couldn't create file " + file.getAbsolutePath(), file.getName(), e);
        }
        doUpdate(r, file);
    }


    @Override
    protected void doUpdate(Resume r, File file) {
        try {
            streamSerializer.doWrite(r, new BufferedOutputStream(new FileOutputStream(file)));
        } catch (IOException e) {
            throw new StorageException("File write error", r.getUuid(), e);
        }
    }

    @Override
    protected Resume doGet(File file) {
        try {
            return streamSerializer.doRead(new BufferedInputStream(new FileInputStream(file)));
        } catch (IOException e) {
            throw new StorageException("File write error", file.getName(), e);
        }
    }

    @Override
    protected void doRemove(File file) {
        try {
            file.delete();
        } catch (Exception e) {
            throw new StorageException("File delete error", file.getName(), e);
        }
    }

    @Override
    public void clear() {
        nullChecker();
        for (File file : directory.listFiles()) {
            file.delete();
        }
    }

    @Override
    public int size() {
        nullChecker();
        return directory.list().length;
    }

    private void nullChecker() {
        if (directory.listFiles() == null) {
            throw new StorageException("Directory read error");
        }
    }
}
