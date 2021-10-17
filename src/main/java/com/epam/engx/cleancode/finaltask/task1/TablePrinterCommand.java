package com.epam.engx.cleancode.finaltask.task1;


import com.epam.engx.cleancode.finaltask.task1.thirdpartyjar.Command;
import com.epam.engx.cleancode.finaltask.task1.thirdpartyjar.DataSet;
import com.epam.engx.cleancode.finaltask.task1.thirdpartyjar.View;
import com.epam.engx.cleancode.finaltask.task1.thirdpartyjar.DatabaseManager;

import java.util.List;


public class TablePrinterCommand implements Command {

    private static final int NUMBER_OF_PARAMETERS = 1;
    private static final int NUMBER_OF_COMMANDS = 1;
    private static final int POSITION_OF_TABLE_NAME = 1;
    private static final String COMMAND_NAME = "print";


    private final View view;
    private final DatabaseManager manager;
    private final TableBuilder tableBuilder = new TableBuilder();

    public static final String COMMAND_SPLITTER = " ";

    public TablePrinterCommand(View view, DatabaseManager manager) {
        this.view = view;
        this.manager = manager;
    }

    public boolean canProcess(String command) {
        return command.startsWith(COMMAND_NAME + COMMAND_SPLITTER);
    }

    public void process(String input) {
        String[] commandArguments = getCommand(input);
        validateCommand(commandArguments);
        String tableName = getTableName(commandArguments);
        List<DataSet> data = manager.getTableData(tableName);
        view.write(tableBuilder.getTableString(data, tableName));
    }

    private void validateCommand(String[] command) {
        if (command.length != NUMBER_OF_COMMANDS + NUMBER_OF_PARAMETERS) {
            throw new IllegalArgumentException("incorrect number of parameters. Expected 1, but is " + NUMBER_OF_PARAMETERS);
        }
    }

    private String[] getCommand(String input) {
        return input.split(COMMAND_SPLITTER);
    }

    private String getTableName(String[] commandArgumants) {
        return lastArgument(commandArgumants);
    }

    private String lastArgument(String[] command) {
        return command[POSITION_OF_TABLE_NAME];
    }
}