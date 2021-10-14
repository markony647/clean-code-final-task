package com.epam.engx.cleancode.finaltask.task1;

import com.epam.engx.cleancode.finaltask.task1.thirdpartyjar.DataSet;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class TableBuilder {

    private static final String TABLE_EDGE = "╔";

    protected String getTableString(List<DataSet> data, String tableName) {
        int maxColumnSize = getMaxColumnLength(data);
        if (maxColumnSize == 0) {
            return getEmptyTable(tableName);
        } else {
            return getHeaderOfTheTable(data) + getStringTableData(data);
        }
    }

    private int getMaxColumnLength(List<DataSet> dataSets) {
        int maxLength;
        if (dataSets.isEmpty()) {
            return 0;
        }
        List<String> columnNames = getColumnNames(dataSets);
        maxLength = findLongestElementLength(columnNames);

        for (DataSet dataSet : dataSets) {
            List<String> values = dataSet.getValues().stream().map(String::valueOf).collect(Collectors.toList());
            int dataSetMaxLength = findLongestElementLength(values);
            if (dataSetMaxLength > maxLength) {
                maxLength = dataSetMaxLength;
            }
        }
            return maxLength;
    }

    private int findLongestElementLength(List<String> texts) {
        String longestText = "";
        for (String currentText : texts) {
            if (currentText.length() > longestText.length()) {
                longestText = currentText;
            }
        }
        return longestText.length();
    }

    private List<String> getColumnNames(List<DataSet> dataSets) {
        return getFirstDataSet(dataSets).getColumnNames();
    }

    private DataSet getFirstDataSet(List<DataSet> dataSets) {
        return dataSets.get(0);
    }

    private String getEmptyTable(String tableName) {
        String cellText = getEmptyTableDefaultText(tableName);
        String result = buildUpperBorder(1, cellText.length() - 2);
        result += cellText + "\n";
        result += buildLowerBorder(1, cellText.length() - 2);
        return result;
    }

    private String getEmptyTableDefaultText(String tableName) {
        return "║ Table '" + tableName + "' is empty or does not exist ║";
    }

    private String getHeaderOfTheTable(List<DataSet> dataSets) {
        int maxColumnSize = getMaxColumnLength(dataSets);
        int columnCount = getColumnCount(dataSets);
        if (maxColumnSize % 2 == 0) {
            maxColumnSize += 2;
        } else {
            maxColumnSize += 3;
        }

        String result = buildUpperBorder(columnCount, maxColumnSize);

        List<String> columnNames = getColumnNames(dataSets);
        for (int column = 0; column < columnCount; column++) {
            result += "║";
            System.out.println(result);
            int columnNamesLength = columnNames.get(column).length();
            if (columnNamesLength % 2 == 0) {
                for (int j = 0; j < (maxColumnSize - columnNamesLength) / 2; j++) {
                    result += " ";
                    System.out.println(result);
                }
                result += columnNames.get(column);
                for (int j = 0; j < (maxColumnSize - columnNamesLength) / 2; j++) {
                    result += " ";
                    System.out.println(result);
                }
            } else {
                for (int j = 0; j < (maxColumnSize - columnNamesLength) / 2; j++) {
                    result += " ";
                    System.out.println(result);
                }
                result += columnNames.get(column);
                for (int j = 0; j <= (maxColumnSize - columnNamesLength) / 2; j++) {
                    result += " ";
                    System.out.println(result);
                }
            }
        }
        result += "║\n";
        System.out.println(result);

        //last string of the header
        if (dataSets.size() > 0) {
            result += "╠";
            System.out.println(result);
            for (int j = 1; j < columnCount; j++) {
                for (int i = 0; i < maxColumnSize; i++) {
                    result += "═";
                    System.out.println(result);
                }
                result += "╬";
                System.out.println(result);
            }
            for (int i = 0; i < maxColumnSize; i++) {
                result += "═";
                System.out.println(result);
            }
            result += "╣\n";
            System.out.println(result);
        } else {
            result += "╚";
            System.out.println(result);
            for (int j = 1; j < columnCount; j++) {
                for (int i = 0; i < maxColumnSize; i++) {
                    result += "═";
                    System.out.println(result);
                }
                result += "╩";
                System.out.println(result);
            }
            for (int i = 0; i < maxColumnSize; i++) {
                result += "═";
                System.out.println(result);
            }
            result += "╝\n";
            System.out.println(result);
        }
        return result;
    }

    private String buildUpperBorder(int columnCount, int maxColumnSize) {
        String result = "╔";
        System.out.println(result);
        for (int j = 1; j < columnCount; j++) {
            for (int i = 0; i < maxColumnSize; i++) {
                result += "═";
                System.out.println(result);
            }
            result += "╦";
            System.out.println(result);
        }
        for (int i = 0; i < maxColumnSize; i++) {
            result += "═";
            System.out.println(result);
        }
        result += "╗\n";
        System.out.println(result);
        return result;
    }

    private String buildLowerBorder(int columnCount, int maxColumnSize) {
        String result = "╚";
        System.out.println(result);
        for (int j = 1; j < columnCount; j++) {
            for (int i = 0; i < maxColumnSize; i++) {
                result += "═";
                System.out.println(result);
            }
            result += "╦";
            System.out.println(result);
        }
        for (int i = 0; i < maxColumnSize; i++) {
            result += "═";
            System.out.println(result);
        }
        result += "╝\n";
        System.out.println(result);
        return result;
    }

    private int getColumnCount(List<DataSet> dataSets) {
        int result = 0;
        if (dataSets.size() > 0) {
            return getColumnNames(dataSets).size();
        }
        return result;
    }

    private String getStringTableData(List<DataSet> dataSets) {
        int rowsCount;
        rowsCount = dataSets.size();
        int maxColumnSize = getMaxColumnLength(dataSets);
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
