package com.epam.engx.cleancode.finaltask.task1;

import com.epam.engx.cleancode.finaltask.task1.thirdpartyjar.DataSet;

import java.util.List;
import java.util.stream.Collectors;

public class TableBuilder {

    private static final String TABLE_EDGE = "╔";
    private String result;

    protected String getTableString(List<DataSet> data, String tableName) {
        int contentLength = getColumnContentLength(data);
        if (contentLength == 0) {
            return getEmptyTable(tableName);
        } else {
            return getHeaderOfTheTable(data) + getStringTableData(data);
        }
    }

    private int getColumnContentLength(List<DataSet> dataSets) {
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
        result = buildUpperBorder(1, cellText.length() - 2);
        result += cellText + "\n";
        result += buildLowerBorder(1, cellText.length() - 2);
        result += "\n";
        return result;
    }

    private String getEmptyTableDefaultText(String tableName) {
        return "║ Table '" + tableName + "' is empty or does not exist ║";
    }

    private boolean isColumnDividesToEqualParts(int columnSize) {
        return columnSize % 2 == 0;
    }

    private String getHeaderOfTheTable(List<DataSet> dataSets) {
        int spaceForEdges = 2;
        int columnLength = getColumnContentLength(dataSets);
        int columnCount = getColumnCount(dataSets);
        if (isColumnDividesToEqualParts(columnLength)) {
            columnLength += spaceForEdges;
        } else {
            columnLength += spaceForEdges + 1;
        }

        String result = buildUpperBorder(columnCount, columnLength);

        List<String> columnNames = getColumnNames(dataSets);

        for (int column = 0; column < columnCount; column++) {
            result = addBorder(result);
            int spaceReservedForContent = columnNames.get(column).length();
            int spaceReservedForIndents = columnLength - spaceReservedForContent;
            int spaceReservedForForOneSideIndents = spaceReservedForIndents / 2;

            result = appendSpaces(result, spaceReservedForForOneSideIndents);
            result = appendText(result, columnNames.get(column));
            if (spaceReservedForContent % 2 == 0) {
                result = appendSpaces(result, spaceReservedForForOneSideIndents);
            } else {
                result = appendSpaces(result, spaceReservedForForOneSideIndents + 1);
            }
        }
        result = addBorder(result);
        result += "\n";

        //last string of the header
        if (dataSets.size() > 0) {
            result = addLeftEdge(result);
            for (int j = 1; j < columnCount; j++) {
                result = addHorizontalBorderSeparator(columnLength, result);
                result = addMiddleColumnSeparator(result);
            }
            result = addHorizontalBorderSeparator(columnLength, result);
            result = addRightEdge(result);
            result += "\n";
        } else {
            result += "╚";
            for (int j = 1; j < columnCount; j++) {
                result = addHorizontalBorderSeparator(columnLength, result);
                result += "╩";
            }
            result = addHorizontalBorderSeparator(columnLength, result);
            result += "╝\n";
        }
        return result;
    }

    private String addRightEdge(String result) {
        return result + "╣";
    }

    private String addLeftEdge(String result) {
        return result + "╠";
    }

    private String addHorizontalBorderSeparator(int columnLength, String result) {
        for (int i = 0; i < columnLength; i++) {
            result += "═";
        }
        return result;
    }

    private String addMiddleColumnSeparator(String result) {
        return result += "╬";
    }

    private String addBorder(String result) {
        return result + "║";
    }

    private String appendText(String result, String text) {
        return result + text;
    }

    private String appendSpaces(String result, int count) {
        for (int j = 0; j < count; j++) {
            result += " ";
        }
        return result;
    }

    private String buildUpperBorder(int columnCount, int maxColumnSize) {
        String result = "╔";
        for (int j = 1; j < columnCount; j++) {
            result = addHorizontalBorderSeparator(maxColumnSize, result);
            result += "╦";
        }
        result = addHorizontalBorderSeparator(maxColumnSize, result);
        result += "╗\n";
        return result;
    }

    private String buildLowerBorder(int columnCount, int maxColumnSize) {
        String result = "╚";
        for (int j = 1; j < columnCount; j++) {
            result = addHorizontalBorderSeparator(maxColumnSize, result);
            result += "╩";
        }
        result = addHorizontalBorderSeparator(maxColumnSize, result);
        result += "╝";
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
        int maxColumnSize = getColumnContentLength(dataSets);
        String result = "";
        if (maxColumnSize % 2 == 0) {
            maxColumnSize += 2;
        } else {
            maxColumnSize += 3;
        }
        int columnCount = getColumnCount(dataSets);
        for (int row = 0; row < rowsCount; row++) {
            List<Object> values = dataSets.get(row).getValues();
            result = addBorder(result);
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
                    result = addBorder(result);
                } else {
                    for (int j = 0; j < (maxColumnSize - valuesLength) / 2; j++) {
                        result += " ";
                    }
                    result += String.valueOf(values.get(column));
                    for (int j = 0; j <= (maxColumnSize - valuesLength) / 2; j++) {
                        result += " ";
                    }
                    result = addBorder(result);
                }
            }
            result += "\n";
            if (row < rowsCount - 1) {
                result = addLeftEdge(result);
                for (int j = 1; j < columnCount; j++) {
                    result = addHorizontalBorderSeparator(maxColumnSize, result);
                    result += "╬";
                }
                result = addHorizontalBorderSeparator(maxColumnSize, result);
                result = addRightEdge(result);
                result += "\n";
            }
        }
        result += "╚";
        for (int j = 1; j < columnCount; j++) {
            result = addHorizontalBorderSeparator(maxColumnSize, result);
            result += "╩";
        }
        result = addHorizontalBorderSeparator(maxColumnSize, result);
        result += "╝\n";
        return result;
    }
}
