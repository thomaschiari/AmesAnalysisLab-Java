package com.example.data.manipulation;

import tech.tablesaw.api.Table;
import tech.tablesaw.api.StringColumn;
import tech.tablesaw.columns.Column;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.List;
import java.util.ArrayList;

public class Preprocessor {

    private static String calculateMode(StringColumn column) {
        Map<String, Integer> frequencyMap = new HashMap<>();
        for (String value : column) {
            frequencyMap.put(value, frequencyMap.getOrDefault(value, 0) + 1);
        }
        Optional<Map.Entry<String, Integer>> maxEntry = frequencyMap.entrySet().stream().max(Map.Entry.comparingByValue());
        return maxEntry.get().getKey();
    }

    public List<String> checkForNulls(Table df) {
        List <String> nullColumns = new ArrayList<String>();
        for (var column : df.columns()) {
            long nullCount = column.countMissing();
            if (nullCount > 0) {
                System.out.println("Coluna: " + column.name() + " - Valores Nulos: " + nullCount);
                nullColumns.add(column.name());
            }
        }
        return nullColumns;
    }

    private Table fillNullValues(Table data) {

        String[] fillNoneColumns = {"Pool.QC", "Misc.Feature", "Alley", "Fence", "Fireplace.Qu", "Garage.Finish", "Garage.Qual", "Garage.Cond", "Garage.Type"};
        String[] fillNoBsmtColumns = {"Bsmt.Qual", "Bsmt.Cond", "Bsmt.Exposure", "BsmtFin.Type.1", "BsmtFin.Type.2"};
        String[] fillModeColumns = {"Electrical", "Functional", "Kitchen.Qual", "Exterior.1st", "Exterior.2nd", "MS.Zoning", "Sale.Type", "Mas.Vnr.Type"};

        for (String column : fillNoneColumns) {
            StringColumn col = (StringColumn) data.column(column);
            col.set(col.isMissing(), "None");
        }

        for (String column : fillNoBsmtColumns) {
            StringColumn col = (StringColumn) data.column(column);
            col.set(col.isMissing(), "NoBsmt");
        }

        for (String column : fillModeColumns) {
            StringColumn col = (StringColumn) data.column(column);
            String mode = calculateMode(col);
            col.set(col.isMissing(), mode);
        }

        return data;

    }

    public Table preprocess(Table data) {
        Table filledData = fillNullValues(data);
        List<String> nullColumns = checkForNulls(filledData);
        List<String> catFeatures = new ArrayList<String>();
        List<String> numFeatures = new ArrayList<String>();

        for (String column : nullColumns) {
            Column<?> columns = filledData.column(column);
            if (columns.type().name().equals("STRING")) {
                catFeatures.add(column);
            } else {
                numFeatures.add(column);
            }
        }

        System.out.println("Features Categóricas: " + catFeatures);
        System.out.println("Features Numéricas: " + numFeatures);

        KnnImputer.impute(filledData);

        return filledData;
    }

}
