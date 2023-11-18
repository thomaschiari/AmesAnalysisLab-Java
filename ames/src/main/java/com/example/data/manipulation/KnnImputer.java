package com.example.data.manipulation;

import weka.core.Instances;
import tech.tablesaw.api.Table;
import weka.filters.unsupervised.attribute.ReplaceMissingValues;

public class KnnImputer {

    private static Instances dataLoading(Table table) {

        Instances instances = null;

        try {
            DataLoader.convertToArff(table, "src/main/resources/data/raw/data.arff");
        } catch (Exception e) {
            System.err.println("Failed to convert data to arff: " + e.getMessage());
        }

        try {
            instances = DataLoader.loadArff("data.arff");
        } catch (Exception e) {
            System.err.println("Failed to load arff: " + e.getMessage());
        }
        return instances;
    }

    public static void impute(Table table) {

        Instances instances = dataLoading(table);

        try {
            ReplaceMissingValues replaceMissingValues = new ReplaceMissingValues();
            replaceMissingValues.setInputFormat(instances);
            instances = weka.filters.Filter.useFilter(instances, replaceMissingValues);
        } catch (Exception e) {
            System.err.println("Failed to impute missing values: " + e.getMessage());
        }

        try {
            DataLoader.convertToCsv(instances, "src/main/resources/data/raw/data.csv");
        } catch (Exception e) {
            System.err.println("Failed to convert data to csv: " + e.getMessage());
        }

    }

}
