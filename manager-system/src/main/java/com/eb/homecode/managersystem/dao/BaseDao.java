package com.eb.homecode.managersystem.dao;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Map;
import java.util.Scanner;

public class BaseDao {
    protected void parseFile(File file, Map map){
        try {
            Scanner sc = new Scanner(file);
            while (sc.hasNextLine()) {
                parseLine(sc.nextLine(), map);
            }

            sc.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    protected void parseLine(String line, Map map) {
        throw new RuntimeException("Method should be override by sub class.");
    }
}
