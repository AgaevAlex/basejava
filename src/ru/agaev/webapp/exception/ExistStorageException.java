package ru.agaev.webapp.exception;

public class ExistStorageException extends StorageException {
    private static final long serialVersionUID = 6304183797853002398L;

    public ExistStorageException(String uuid) {
        super("Resume " + uuid + " already exist", uuid);
    }

    public ExistStorageException(String uuid, String e) {
        super("Resume " + uuid + " already exist", uuid);
    }
}
