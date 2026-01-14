package ru.job4j.io;

import java.io.*;
import java.util.function.Predicate;

public class ParseFile {
    private final File file;

    public ParseFile(final File file) {
        this.file = file;
    }

    public String getContent() {
        return content(c -> true);
    }

    public String getContentWithoutUnicode() {
        return content(c -> c < 128);
    }

    private String content(Predicate<Character> filter) {
        StringBuilder builder = new StringBuilder();
        try (InputStream input = new FileInputStream(file)) {
            int data;
            while ((data = input.read()) != -1) {
                char c = (char) data;
                if (filter.test(c)) {
                    builder.append(c);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return builder.toString();
    }
}