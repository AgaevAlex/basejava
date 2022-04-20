package ru.agaev.webapp;

import java.io.File;
import java.util.Objects;

public class MainFile {

    public static void main(String[] args) {
//        File file = new File("/Users/alexeyagaev/IdeaProjects/basejava1/.gitignore");
        //     System.out.println(file.getCanonicalPath());
        refreshFileList("basejava");
    }

    public static void refreshFileList(String strPath) {
        File dir = new File(strPath);
        File[] files = dir.listFiles();
        for (File file : Objects.requireNonNull(files)) {
            if (file.isDirectory()) {
                refreshFileList(file.getAbsolutePath());
            } else {
                String strFileName = file.getName();
                System.out.println("---" + strFileName);
            }
        }
    }
}
