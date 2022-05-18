package ru.agaev.webapp.storage;

import ru.agaev.webapp.Config;

public class SqlStorageTest extends AbstractStorageTest {
    private static String dbUrl = Config.get().getDbUrl();
    private static String dbUser = Config.get().getDbUser();
    private static String dbPassword = Config.get().getDbPassword();
    private final static Storage ARRAY_STORAGE = new SqlStorage(dbUrl, dbUser, dbPassword);

    public SqlStorageTest() {
        super(ARRAY_STORAGE);
    }
}