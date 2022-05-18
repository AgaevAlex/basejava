package ru.agaev.webapp.exception;

public class StorageException extends RuntimeException {
    private static final long serialVersionUID = -2921079729882014209L;
    private String uuid;

    public StorageException(String message) {
        super(message);
    }

    public StorageException(String message, String uuid) {
        super(message);
        this.uuid = uuid;
    }

    public StorageException(String message, Exception e) {
        super(message, e);
    }

    public StorageException(String message, String uuid, Exception e) {
        super(message, e);
        this.uuid = uuid;
    }

    public StorageException(Exception e) {
        this(e.getMessage());
    }

    public String getUuid() {
        return uuid;
    }

}
