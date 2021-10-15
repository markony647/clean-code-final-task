package com.epam.engx.cleancode.finaltask.task1;

import com.epam.engx.cleancode.finaltask.task1.thirdpartyjar.DataSet;

import java.util.List;
import java.util.stream.Collectors;

public class TableBuilderNewGen {

    private static final String TOP_LEFT_CORNER = "╔";
    private static final String TOP_MIDDLE_CORNER = "╦";
    private static final String TOP_RIGHT_CORNER = "╗\n";

    private static final String BOTTOM_LEFT_CORNER = "╚";
    private static final String BOTTOM_MIDDLE_CORNER = "╩";
    private static final String BOTTOM_RIGHT_CORNER = "╝\n";

    private static final String MIDDLE_LEFT_CORNER = "╠";
    private static final String MIDDLE_MIDDLE_CORNER = "╬";
    private static final String MIDDLE_RIGHT_CORNER = "╣\n";

    private static final String SIDE_BORDER = "║";
    private static final String LINE = "═";
    private static final String SPLITTER = " ";
    private static final String NEW_LINE = "\n";


    public String getTableString(List<DataSet> data, String tableName) {
        if (getColumnContentLength(data) == 0) {
            return getEmptyTable(tableName);
        } else {
            return getHeaderAndStringOfTable(data);
        }
    }

    private String getHeaderAndStringOfTable(List<DataSet> data) {
        return getHeaderOfTheTable(data) + getStringTableData(data);
    }

    private String getEmptyTable(String tableName) {
        int oneColumn = 1;
        String content = generateTextEmptyTable(tableName);

        String result = generateTopLine(getContentLength(content), oneColumn);
        result += SIDE_BORDER + content + SIDE_BORDER  + NEW_LINE;
        result += generateBottomLine(getContentLength(content), oneColumn);
        return result;
    }

    private int getContentLength(String text) {
        return text.length();
    }

    private String generateTextEmptyTable(String tableName) {
        return  " Table '" + tableName + "' is empty or does not exist ";
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

    private List<String> getColumnNames(List<DataSet> dataSets) {
        return getFirstDataSet(dataSets).getColumnNames();
    }

    private DataSet getFirstDataSet(List<DataSet> dataSets) {
        return dataSets.get(0);
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

    private String getStringTableData(List<DataSet> dataSets) {
        String result = "";
        result += generateAllValuesLines(dataSets, countColumns(dataSets));
        result += generateBottomLine(calculateMaxColumnSize(dataSets), countColumns(dataSets));
        return result;
    }

    private String generateAllValuesLines(List<DataSet> dataSets, int columnCount) {
        String result = "";
        for (int row = 0; row < dataSets.size(); row++) {
            result += generateWholeValuesAllLines(dataSets, row);
            if (row < dataSets.size() - 1) {
                result += generateMiddleLine(calculateMaxColumnSize(dataSets), columnCount);
            }
        }
        return result;
    }

    private int countColumns(List<DataSet> dataSets) {
        if (dataSets.isEmpty()) {
            return 0;
        }
        return getColumnNames(dataSets).size();
    }

    private String getHeaderOfTheTable(List<DataSet> dataSets) {
        String result = "";
        result += generateTopLine(calculateMaxColumnSize(dataSets), countColumns(dataSets));
        result += generateWholeNamesLine(dataSets);
        result += generateLastStringOfHeader(dataSets.size(), calculateMaxColumnSize(dataSets), countColumns(dataSets));
        return result;
    }

    private String generateWholeNamesLine(List<DataSet> dataSets) {
        String result= "";
        List<String> columnNames = dataSets.get(0).getColumnNames();

        for (int column = 0; column < countColumns(dataSets); column++) {
            String columnName = conversionToEvenLength(columnNames.get(column));
            result += generateColumnValueLine(columnName, calculateMaxColumnSize(dataSets));
        }
        result += SIDE_BORDER + "\n";
        return result;
    }

    private String generateWholeValuesAllLines(List<DataSet> dataSets, int row) {
        List<Object> values = dataSets.get(row).getValues();
        return generateWholeValuesLine(values, countColumns(dataSets), calculateMaxColumnSize(dataSets));
    }

    private String generateWholeValuesLine(List<Object> values , int columnCount, int maxColumnSize) {
        String result = "";
        for (int column = 0; column < columnCount; column++) {
            String value = conversionToEvenLength(String.valueOf(values.get(column)));
            result += generateColumnValueLine(value, maxColumnSize);
        }
        result += SIDE_BORDER + "\n";
        return result;
    }

    private String conversionToEvenLength(String value) {
        if (value.length() % 2 == 1) {
            value += SPLITTER;
        }
        return value;
    }

    private int getColumnSizeWithOutName(int maxColumnSize, String columnName) {
        return  maxColumnSize - getColumnNameLength(columnName);
    }

    private String generateColumnValueLine(String string, int maxColumnSize) {
        String result = SIDE_BORDER;
        result += generateSplitterLine(getColumnSizeWithOutName(maxColumnSize, string) / 2);
        result += string;
        result += generateSplitterLine(getColumnSizeWithOutName(maxColumnSize, string) / 2);
        return result;
    }

    private String generateSplitterLine( int halfColumnSizeWithOutName) {
        return generateMiddlePartOfLine(halfColumnSizeWithOutName, SPLITTER);
    }

    private int getColumnNameLength(String columnName) {
        return columnName.length();
    }

    private String generateLastStringOfHeader(int size, int maxColumnSize, int columnCount) {
        String result = "";
        if (size > 0) {
            result += generateMiddleLine(maxColumnSize, columnCount);
        } else {
            result += generateBottomLine(maxColumnSize, columnCount);
        }
        return result;
    }

    private int calculateMaxColumnSize(List<DataSet> dataSets) {
        int maxColumnSize = getColumnContentLength(dataSets);
        return maxColumnSize + countSpaceNeededForEdges(isEven(maxColumnSize));
    }

    private int countSpaceNeededForEdges(boolean isEven) {
        if (isEven) {
            return 2;
        }
        return 3;
    }

    private boolean isEven(int number) {
        return number % 2 == 0;
    }

    private String generateTopLine(int length, int columnCount) {
        String result = TOP_LEFT_CORNER;
        result += generateWholeMiddlePartOfLine(length, columnCount, TOP_MIDDLE_CORNER);
        result += TOP_RIGHT_CORNER;
        return result;
    }

    private String generateMiddleLine(int length, int columnCount) {
        String result = MIDDLE_LEFT_CORNER;
        result += generateWholeMiddlePartOfLine(length, columnCount, MIDDLE_MIDDLE_CORNER);
        result += MIDDLE_RIGHT_CORNER;
        return result;
    }

    private String generateBottomLine(int length, int columnCount) {
        String result = BOTTOM_LEFT_CORNER;
        result += generateWholeMiddlePartOfLine(length, columnCount, BOTTOM_MIDDLE_CORNER);
        result += BOTTOM_RIGHT_CORNER;
        return result;
    }

    private String generateMiddlePartOfLine(int length, String element) {
        String result = "";
        for (int i = 0; i < length; i++) {
            result += element;
        }
        return result;
    }

    private String generateWholeMiddlePartOfLine(int length, int columnCount, String element) {
        String result = "";
        for (int j = 1; j < columnCount; j++) {
            result += generateMiddlePartOfLine(length, LINE);
            result += element;
        }
        result += generateMiddlePartOfLine(length, LINE);
        return result;
    }


}
