package ru.agaev.webapp.storage;

import ru.agaev.webapp.exception.StorageException;
import ru.agaev.webapp.model.Resume;
import ru.agaev.webapp.storage.serializer.StreamSerializer;

import java.io.*;

public class ObjectStreamStorage implements StreamSerializer {


    @Override
    public void doWrite(Resume resume, OutputStream os) throws IOException {
        try (ObjectOutputStream oos = new ObjectOutputStream(os)) {
            oos.writeObject(resume);
        }
    }

    @Override
    public Resume doRead(InputStream is) throws IOException {
        try (ObjectInputStream ois = new ObjectInputStream(is)) {
            return (Resume) ois.readObject();
        } catch (ClassNotFoundException e) {
            throw new StorageException("Error read resume", null, e);
        }
    }

}

