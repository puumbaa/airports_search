package com.renue.internship.common;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;

public abstract class Parser<T> {
    protected final String fileAbsolutePath;

    protected Parser(String filename) {
        this.fileAbsolutePath = new File(filename).getAbsolutePath();
    }

    public String parseLine(long charsBefore) {
        try (RandomAccessFile file = new RandomAccessFile(fileAbsolutePath, "r")) {
            file.seek(charsBefore);
            return new String(file.readLine().getBytes(StandardCharsets.ISO_8859_1), StandardCharsets.UTF_8);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public abstract void parseColumn(int columnIndex, T destination);

    public static String getCell(int i, String line, boolean toNormalize) {
        String[] split = line.split(",");
        int cnt = 0;
        for (int j = 0; j < split.length; j++) {
            if (split[j].endsWith("\"") && !split[j].startsWith("\"")) {
                split[j - 1] = split[j - 1].concat(",").concat(split[j]);
                System.arraycopy(split, j + 1, split, j, split.length - j - 1);
                cnt++;
            }
        }
        for (int j = split.length - cnt; j < split.length; j++) {
            split[j] = null;
        }
        return toNormalize ? split[i].replaceAll("\"", "").toLowerCase() : split[i];
    }
}
