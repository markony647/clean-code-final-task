package com.epam.engx.cleancode.finaltask.task1;

import com.epam.engx.cleancode.finaltask.task1.thirdpartyjar.DataSet;

import java.util.List;

public class TableBuilder {

    protected int getMaxColumnSize(List<DataSet> dataSets) {
        int maxLength = 0;
        if (dataSets.size() > 0) {
            List<String> columnNames = getColumnNames(dataSets);
            for (String columnName : columnNames) {
                if (columnName.length() > maxLength) {
                    maxLength = columnName.length();
                }
            }
            for (DataSet dataSet : dataSets) {
                List<Object> values = dataSet.getValues();
                for (Object value : values) {
//                    if (value instanceof String)
                    if (String.valueOf(value).length() > maxLength) {
                        maxLength = String.valueOf(value).length();
                    }
                }
            }
        }
        return maxLength;
    }

    private List<String> getColumnNames(List<DataSet> dataSets) {
        return getFirstDataSet(dataSets).getColumnNames();
    }

    private DataSet getFirstDataSet(List<DataSet> dataSets) {
        return dataSets.get(0);
    }

    protected String getTableString(List<DataSet> data, String tableName) {
        int maxColumnSize = getMaxColumnSize(data);
        if (maxColumnSize == 0) {
            return getEmptyTable(tableName);
        } else {
            return getHeaderOfTheTable(data) + getStringTableData(data);
        }
    }

    protected String getEmptyTable(String tableName) {
        String textEmptyTable = "║ Table '" + tableName + "' is empty or does not exist ║";
        String result = "╔";
        for (int i = 0; i < textEmptyTable.length() - 2; i++) {
            result += "═";
        }
        result += "╗\n";
        result += textEmptyTable + "\n";
        result += "╚";
        for (int i = 0; i < textEmptyTable.length() - 2; i++) {
            result += "═";
        }
        result += "╝\n";
        return result;
    }

    protected String getHeaderOfTheTable(List<DataSet> dataSets) {
        int maxColumnSize = getMaxColumnSize(dataSets);
        int columnCount = getColumnCount(dataSets);
        if (maxColumnSize % 2 == 0) {
            maxColumnSize += 2;
        } else {
            maxColumnSize += 3;
        }

        String result = printColumns(columnCount, maxColumnSize);

        List<String> columnNames = getColumnNames(dataSets);
        for (int column = 0; column < columnCount; column++) {
            result += "║";
            int columnNamesLength = columnNames.get(column).length();
            if (columnNamesLength % 2 == 0) {
                for (int j = 0; j < (maxColumnSize - columnNamesLength) / 2; j++) {
                    result += " ";
                }
                result += columnNames.get(column);
                for (int j = 0; j < (maxColumnSize - columnNamesLength) / 2; j++) {
                    result += " ";
                }
            } else {
                for (int j = 0; j < (maxColumnSize - columnNamesLength) / 2; j++) {
                    result += " ";
                }
                result += columnNames.get(column);
                for (int j = 0; j <= (maxColumnSize - columnNamesLength) / 2; j++) {
                    result += " ";
                }
            }
        }
        result += "║\n";

        //last string of the header
        if (dataSets.size() > 0) {
            result += "╠";
            for (int j = 1; j < columnCount; j++) {
                for (int i = 0; i < maxColumnSize; i++) {
                    result += "═";
                }
                result += "╬";
            }
            for (int i = 0; i < maxColumnSize; i++) {
                result += "═";
            }
            result += "╣\n";
        } else {
            result += "╚";
            for (int j = 1; j < columnCount; j++) {
                for (int i = 0; i < maxColumnSize; i++) {
                    result += "═";
                }
                result += "╩";
            }
            for (int i = 0; i < maxColumnSize; i++) {
                result += "═";
            }
            result += "╝\n";
        }
        return result;
    }

    private String printColumns(int columnCount, int maxColumnSize) {
        String result = "╔";
        for (int j = 1; j < columnCount; j++) {
            for (int i = 0; i < maxColumnSize; i++) {
                result += "═";
            }
            result += "╦";
        }
        for (int i = 0; i < maxColumnSize; i++) {
            result += "═";
        }
        result += "╗\n";
        return result;
    }

    protected int getColumnCount(List<DataSet> dataSets) {
        int result = 0;
        if (dataSets.size() > 0) {
            return getColumnNames(dataSets).size();
        }
        return result;
    }

    private String getStringTableData(List<DataSet> dataSets) {
        int rowsCount;
        rowsCount = dataSets.size();
        int maxColumnSize = getMaxColumnSize(dataSets);
        String result = "";
        if (maxColumnSize % 2 == 0) {
            maxColumnSize += 2;
        } else {
            maxColumnSize += 3;
        }
        int columnCount = getColumnCount(dataSets);
        for (int row = 0; row < rowsCount; row++) {
            List<Object> values = dataSets.get(row).getValues();
            result += "║";
            for (int column = 0; column < columnCount; column++) {
                int valuesLength = String.valueOf(values.get(column)).length();
                if (valuesLength % 2 == 0) {
                    for (int j = 0; j < (maxColumnSize - valuesLength) / 2; j++) {
                        result += " ";
                    }
                    result += String.valueOf(values.get(column));
                    for (int j = 0; j < (maxColumnSize - valuesLength) / 2; j++) {
                        result += " ";
                    }
                    result += "║";
                } else {
                    for (int j = 0; j < (maxColumnSize - valuesLength) / 2; j++) {
                        result += " ";
                    }
                    result += String.valueOf(values.get(column));
                    for (int j = 0; j <= (maxColumnSize - valuesLength) / 2; j++) {
                        result += " ";
                    }
                    result += "║";
                }
            }
            result += "\n";
            if (row < rowsCount - 1) {
                result += "╠";
                for (int j = 1; j < columnCount; j++) {
                    for (int i = 0; i < maxColumnSize; i++) {
                        result += "═";
                    }
                    result += "╬";
                }
                for (int i = 0; i < maxColumnSize; i++) {
                    result += "═";
                }
                result += "╣\n";
            }
        }
        result += "╚";
        for (int j = 1; j < columnCount; j++) {
            for (int i = 0; i < maxColumnSize; i++) {
                result += "═";
            }
            result += "╩";
        }
        for (int i = 0; i < maxColumnSize; i++) {
            result += "═";
        }
        result += "╝\n";
        return result;
    }
}
