package ru.agaev.webapp;

import ru.agaev.webapp.model.Resume;
import ru.agaev.webapp.storage.Storage;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

/**
 * Interactive WriteObject for ru.agaev.webapp.storage.ArrayStorage implementation
 * (just run, no need to understand)
 */
public class MainArray {

    private final static Storage ARRAY_STORAGE = Config.get().getStorage();

    public static void main(String[] args) throws IOException, RuntimeException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        Resume r;
        while (true) {
            System.out.print("Введите одну из команд - (list | size | save  fullName | delete uuid | get uuid | clear | update uuid and fullName | exit): ");
            String[] params = reader.readLine().trim().toLowerCase().split(" ");
            if (params.length < 1 || params.length > 4) {
                System.out.println("Неверная команда.");
                continue;
            }
            String uuid = null;
            String fullName = " ";
            if (params.length == 2) {
                fullName = params[1].intern();
            }
            if (params.length == 3) {
                uuid = params[1].intern();
                fullName = params[2].intern();
            }
            if (params.length == 4) {
                uuid = params[1].intern();
                fullName = params[2].intern() + " " + params[3].intern();
            }
            switch (params[0]) {
                case "list":
                    printAll();
                    break;
                case "size":
                    System.out.println(ARRAY_STORAGE.size());
                    break;
                case "save":
                    r = new Resume(fullName);
                    ARRAY_STORAGE.save(r);
                    printAll();
                    break;
                case "delete":
                    ARRAY_STORAGE.delete(fullName);
                    printAll();
                    break;
                case "get":
                    System.out.println(ARRAY_STORAGE.get(fullName));
                    break;
                case "clear":
                    ARRAY_STORAGE.clear();
                    printAll();
                    break;
                case "update":
                    r = new Resume(uuid, fullName);
                    ARRAY_STORAGE.update(r);
                    printAll();
                    break;
                case "exit":
                    return;
                default:
                    System.out.println("Неверная команда.");
                    break;
            }
        }
    }

    static void printAll() {
        List<Resume> all = ARRAY_STORAGE.getAllSorted();
        System.out.println("----------------------------");
        if (all.size() == 0) {
            System.out.println("Empty");
        } else {
            for (Resume r : all) {
                System.out.println(r);
            }
        }
        System.out.println("----------------------------");
    }
}
