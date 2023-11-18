package com.example.data.manipulation;

import tech.tablesaw.api.Table;

public class Main {
    public static void main(String[] args) {

        if (args.length != 1) {
            System.err.println("Usage: java -jar data-manipulation.jar <path>");
            System.exit(1);
        }

        DataManipulator dataManipulator = new DataManipulator();
        try {
            Table table = dataManipulator.getTable(args[0]);
            System.out.println(table.print());
        } catch (DataReadingException e) {
            System.err.println("Failed to process data: " + e.getMessage());
        }
    }
}
