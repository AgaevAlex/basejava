/**
 * Array based storage for Resumes
 */
public class ArrayStorage {
    Resume[] storage = new Resume[10000];
    int counter = 0;

    void clear() {
        for (int i = 0; i < counter; i++) {
            storage[i] = null;
        }
        counter = 0;
    }

    void save(Resume r) {
        storage[counter++] = r;
    }

    Resume get(String uuid) {
        for (int i = 0; i < counter; i++) {
            if (storage[i].uuid.equals(uuid)) {
                return storage[i];
            }
        }
        return null;
    }

    void delete(String uuid) {
        for (int i = 0; i < counter; i++) {
            if (storage[i].uuid.equals(uuid)) {
                for (int j = i; j < counter - 1; j++) {
                    storage[j] = storage[j + 1];
                }
                counter--;
                storage[counter] = null;
                break;
            }

        }
    }

    /**
     * @return array, contains only Resumes in storage (without null)
     */
    Resume[] getAll() {
        Resume[] storageTwo = new Resume[counter];

        for (int i = 0; i < counter; i++) {
            storageTwo[i] = storage[i];
        }
        return storageTwo;
    }

    int size() {
        return counter;
    }
}
