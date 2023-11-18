package com.example.data.manipulation;

import tech.tablesaw.api.Table;

public class DataNullCheck {
    public static void checkForNulls(Table df) {
        for (var column : df.columns()) {
            long nullCount = column.countMissing();
            if (nullCount > 0) {
                System.out.println("Coluna: " + column.name() + " - Valores Nulos: " + nullCount);
            }
        }
    }
}
