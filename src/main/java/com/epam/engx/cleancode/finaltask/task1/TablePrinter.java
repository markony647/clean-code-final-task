package com.epam.engx.cleancode.finaltask.task1;


import com.epam.engx.cleancode.finaltask.task1.thirdpartyjar.Command;
import com.epam.engx.cleancode.finaltask.task1.thirdpartyjar.DataSet;
import com.epam.engx.cleancode.finaltask.task1.thirdpartyjar.View;
import com.epam.engx.cleancode.finaltask.task1.thirdpartyjar.DatabaseManager;

import java.util.List;


public class TablePrinter implements Command {


    public static final int EXPECTED_NUMBER_OF_PARAMETERS = 2;
    public static final int POSITION_OF_TABLE_NAME = 1;

    private final View view;
    private final DatabaseManager manager;
    private final TableBuilderNewGen tableBuilderNewGen = new TableBuilderNewGen();
    private String tableName;

    private String[] command;

    public static final String COMMAND_SPLITTER = " ";

    public TablePrinter(View view, DatabaseManager manager) {
        this.view = view;
        this.manager = manager;
    }

    public boolean canProcess(String command) {
        return command.startsWith("print ");
    }

    public void process(String input) {
        command = getCommand(input);
        validateCommand(command);
        tableName = getTableName(command);
        List<DataSet> data = manager.getTableData(tableName);
        view.write(tableBuilderNewGen.getTableString(data, tableName));
    }

    private void validateCommand(String[] command) {
        if (command.length != EXPECTED_NUMBER_OF_PARAMETERS) {
            throw new IllegalArgumentException("incorrect number of parameters. Expected 1, but is " + (command.length - 1));
        }
    }

    private String[] getCommand(String input) {
        return input.split(COMMAND_SPLITTER);
    }

    private String getTableName(String[] command) {
        return lastArgument(command);
    }

    private String lastArgument(String[] command) {
        return command[POSITION_OF_TABLE_NAME];
    }
}