package com.example.data.manipulation;

import tech.tablesaw.api.Row;
import tech.tablesaw.api.Table;
import tech.tablesaw.columns.Column;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import weka.core.Instances;
import weka.core.converters.ConverterUtils.DataSource;

public class DataLoader {

    public static void convertToArff(Table table, String arffFilePath) throws IOException {
        BufferedWriter writer = new BufferedWriter(new FileWriter(arffFilePath));
        writer.write("@relation " + table.name() + "\n\n");
        for (Column<?> column : table.columns()) {
            writer.write("@attribute " + column.name() + " ");
            if (column.type().name().equals("STRING")) {
                writer.write("string\n");
            } else {
                writer.write("numeric\n");
            }
        }
        writer.write("\n@data\n");
        for (Row row : table) {
            for (Column<?> column : table.columns()) {
                if (row.isMissing(column.name())) {
                    writer.write("?,");
                } else {
                    writer.write(row.getObject(column.name()) + ",");
                }
            }
            writer.newLine();
        }
    }

    public static Instances loadArff(String arffFilePath) throws Exception {
        DataSource source = new DataSource(arffFilePath);
        Instances data = source.getDataSet();
        if (data.classIndex() == -1) {
            data.setClassIndex(data.numAttributes() - 1);
        }
        return data;
    }

    public static void convertToCsv(Instances instances, String csvFilePath) throws IOException {
        BufferedWriter writer = new BufferedWriter(new FileWriter(csvFilePath));
        writer.write(instances.toString());
        writer.flush();
        writer.close();
    }

}
