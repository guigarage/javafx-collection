package com.guigarage.controls;

import java.io.File;
import java.nio.file.Files;
import java.text.DecimalFormat;
import java.text.NumberFormat;

/**
 * Created by hendrikebbers on 20.09.14.
 */
class Converter {

    private static int id;

    public static void main(String... args) throws Exception {
        Files.lines(new File("/Users/hendrikebbers/Desktop/emoji.txt").toPath()).map(r -> "E_" + nextId() + "(" + r.replace("\\x", ", (byte)0x").substring(2) + "),").forEach(s -> System.out.println(s));
    }

    private static String nextId() {
        return new DecimalFormat("0000").format(id++);
    }
}
