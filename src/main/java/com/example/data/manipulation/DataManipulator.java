package com.example.data.manipulation;

import tech.tablesaw.api.NumberColumn;
import tech.tablesaw.api.Table;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import org.json.JSONObject;

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
