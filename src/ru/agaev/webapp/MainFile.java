package ru.agaev.webapp;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;

public class MainFile {

    public static void main(String[] args) {


//        File file = new File("/Users/alexeyagaev/IdeaProjects/basejava1/.gitignore");
        //     System.out.println(file.getCanonicalPath());
      refreshFileList("/Users/alexeyagaev/IdeaProjects/basejava1");
    }

    public static void refreshFileList(String strPath) {
        File dir = new File(strPath);
        File[] files = dir.listFiles();

        for (int i = 0; i < Objects.requireNonNull(files).length; i++) {
            if (files[i].isDirectory()) {
                refreshFileList(files[i].getAbsolutePath());
                System.out.println((files[i].getAbsolutePath()));
            } else {
                String strFileName = files[i].getName();
                System.out.println("---" + strFileName);
            }
        }
    }
}
