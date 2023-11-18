package com.example.data.manipulation;

import tech.tablesaw.api.Table;

public class DataManipulator {

    private Table getTable(String path) throws DataReadingException {
        try {
            return Table.read().csv(path);
        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
            throw new DataReadingException("Error reading data from " + path);
        }
    }

    public void dataManipulation(String path) throws DataReadingException {
        Table data = getTable(path);
        Preprocessor preprocessor = new Preprocessor();
        Table preprocessedData = preprocessor.preprocess(data);
        System.out.println(preprocessedData.first(5));
        DataNullCheck.checkForNulls(preprocessedData);
    }

}
