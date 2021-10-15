package com.epam.engx.cleancode.finaltask.task1;

import com.epam.engx.cleancode.finaltask.task1.enums.OtherSeparators;
import com.epam.engx.cleancode.finaltask.task1.thirdpartyjar.DataSet;

import java.util.List;
import java.util.stream.Collectors;

import static com.epam.engx.cleancode.finaltask.task1.enums.LowerSeparators.*;
import static com.epam.engx.cleancode.finaltask.task1.enums.MiddleSeparators.*;
import static com.epam.engx.cleancode.finaltask.task1.enums.OtherSeparators.*;
import static com.epam.engx.cleancode.finaltask.task1.enums.UpperSeparators.*;


public class TableBuilder {

//    private static final String SIDE_BORDER = "║";
//    private static final String LINE = "═";
//    private static final String INDENT = " ";
//    private static final String NEW_LINE = "\n";

    public String getTableString(List<DataSet> data, String tableName) {
        if (data.isEmpty()) {
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
        result += SIDE_BORDER.getValue() + content + SIDE_BORDER.getValue()  + NEW_LINE.getValue();
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
        StringBuilder result = new StringBuilder();
        for (int row = 0; row < dataSets.size(); row++) {
            result.append(generateWholeLine(dataSets, row));
            if (isNotLastRow(dataSets, row)) {
                result.append(generateMiddleLine(calculateMaxColumnSize(dataSets), columnCount));
            }
        }
        return result.toString();
    }

    private boolean isNotLastRow(List<DataSet> dataSets, int row) {
        return row < dataSets.size() - 1;
    }

    private int countColumns(List<DataSet> dataSets) {
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
        StringBuilder result = new StringBuilder();
        List<String> columnNames = dataSets.get(0).getColumnNames();

        for (int column = 0; column < countColumns(dataSets); column++) {
            String columnName = conversionToEvenLength(columnNames.get(column));
            result.append(generateColumnValueLine(columnName, calculateMaxColumnSize(dataSets)));
        }
        result.append(SIDE_BORDER.getValue()).append(NEW_LINE.getValue());
        return result.toString();
    }

    private String generateWholeLine(List<DataSet> dataSets, int row) {
        List<Object> values = dataSets.get(row).getValues();
        return generateWholeValuesLine(values, countColumns(dataSets), calculateMaxColumnSize(dataSets));
    }

    private String generateWholeValuesLine(List<Object> values , int columnCount, int maxColumnSize) {
        StringBuilder result = new StringBuilder();
        for (int column = 0; column < columnCount; column++) {
            String value = conversionToEvenLength(String.valueOf(values.get(column)));
            result.append(generateColumnValueLine(value, maxColumnSize));
        }
        result.append(SIDE_BORDER.getValue() + NEW_LINE.getValue());
        return result.toString();
    }

    private String conversionToEvenLength(String value) {
        if (! isEven(value.length())) {
            value += OtherSeparators.INDENT.getValue();
        }
        return value;
    }

    private int getSizeReservedForIndents(int maxColumnSize, String columnName) {
        return maxColumnSize - getColumnNameLength(columnName);
    }

    private String generateColumnValueLine(String columnText, int maxColumnSize) {
        String result = SIDE_BORDER.getValue();
        int spaceReservedForIndents = getSizeReservedForIndents(maxColumnSize, columnText);
        int spaceReservedForIndentsFormOneSide = spaceReservedForIndents / 2;

        result += generateIndent(spaceReservedForIndentsFormOneSide);
        result += columnText;
        result += generateIndent(spaceReservedForIndentsFormOneSide);
        return result;
    }

    private String generateIndent(int halfColumnSizeWithOutName) {
        return generateMiddleLinePart(halfColumnSizeWithOutName, OtherSeparators.INDENT.getValue());
    }

    private int getColumnNameLength(String columnName) {
        return columnName.length();
    }

    private String generateLastStringOfHeader(int size, int maxColumnSize, int columnCount) {
        if (size > 0) {
            return generateMiddleLine(maxColumnSize, columnCount);
        }
        return generateBottomLine(maxColumnSize, columnCount);
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
        String result = UPPER_LEFT_CORNER.getValue();
        result += generateMiddleLine(length, columnCount, UPPER_MIDDLE_CORNER.getValue());
        result += UPPER_RIGHT_CORNER.getValue() + NEW_LINE.getValue();
        return result;
    }

    private String generateMiddleLine(int length, int columnCount) {
        String result = MIDDLE_LEFT_CORNER.getValue();
        result += generateMiddleLine(length, columnCount, MIDDLE_MIDDLE_CORNER.getValue());
        result += MIDDLE_RIGHT_CORNER.getValue() + NEW_LINE.getValue();
        return result;
    }

    private String generateBottomLine(int length, int columnCount) {
        String result = LOWER_LEFT_CORNER.getValue();
        result += generateMiddleLine(length, columnCount, LOWER_MIDDLE_CORNER.getValue());
        result += LOWER_RIGHT_CORNER.getValue() + NEW_LINE.getValue();
        return result;
    }

    private String generateMiddleLinePart(int length, String element) {
        String result = "";
        for (int i = 0; i < length; i++) {
            result += element;
        }
        return result;
    }

    private String generateMiddleLine(int length, int columnCount, String element) {
        String result = "";
        for (int j = 1; j < columnCount; j++) {
            result += generateMiddleLinePart(length, LINE.getValue());
            result += element;
        }
        result += generateMiddleLinePart(length, LINE.getValue());
        return result;
    }
}
