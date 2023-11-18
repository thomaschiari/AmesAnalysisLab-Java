package com.example.data.manipulation;

import tech.tablesaw.api.Table;

public class DataManipulator {

    public Table getTable(String path) throws DataReadingException {
        try {
            return Table.read().csv(path);
        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
            throw new DataReadingException("Error reading data from " + path);
        }
    }
}
