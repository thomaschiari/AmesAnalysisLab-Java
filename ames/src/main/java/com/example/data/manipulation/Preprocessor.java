package com.example.data.manipulation;

import tech.tablesaw.api.Table;
import tech.tablesaw.api.StringColumn;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class Preprocessor {

    private static String calculateMode(StringColumn column) {
        Map<String, Integer> frequencyMap = new HashMap<>();
        for (String value : column) {
            frequencyMap.put(value, frequencyMap.getOrDefault(value, 0) + 1);
        }
        Optional<Map.Entry<String, Integer>> maxEntry = frequencyMap.entrySet().stream().max(Map.Entry.comparingByValue());
        return maxEntry.get().getKey();
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
        return filledData;
    }

}
