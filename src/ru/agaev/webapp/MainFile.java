package ru.agaev.webapp;

import java.io.File;
import java.util.Objects;

public class MainFile {
    private static StringBuilder counter = new StringBuilder();

    public static void main(String[] args) {
        refreshFileList("basejava/src");
    }

    public static void refreshFileList(String strPath) {
        File dir = new File(strPath);
        File[] files = dir.listFiles();
        for (File file : Objects.requireNonNull(files)) {
            counter.append("-");
            if (file.isDirectory()) {
                System.out.println(counter + file.getPath());
                refreshFileList(file.getAbsolutePath());
            } else {
                String strFileName = file.getName();
                System.out.println(counter + strFileName);
                counter.setLength(counter.length() - 1);
            }
        }
        if (counter.length() != 0) counter.setLength(counter.length() - 1);

    }
}
