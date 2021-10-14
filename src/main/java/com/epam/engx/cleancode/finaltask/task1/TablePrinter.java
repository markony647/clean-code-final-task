package com.epam.engx.cleancode.finaltask.task1;


import com.epam.engx.cleancode.finaltask.task1.thirdpartyjar.Command;
import com.epam.engx.cleancode.finaltask.task1.thirdpartyjar.DataSet;
import com.epam.engx.cleancode.finaltask.task1.thirdpartyjar.View;
import com.epam.engx.cleancode.finaltask.task1.thirdpartyjar.DatabaseManager;

import java.util.List;


public class TablePrinter implements Command {

    private final View view;
    private final DatabaseManager manager;
    private final TableBuilder tableBuilder = new TableBuilder();
    private String tableName;

    public TablePrinter(View view, DatabaseManager manager) {
        this.view = view;
        this.manager = manager;
    }

    public boolean canProcess(String command) {
        return command.startsWith("print ");
    }

    public void process(String input) {
        String[] command = input.split(" ");
        if (command.length != 2) {
            throw new IllegalArgumentException("incorrect number of parameters. Expected 1, but is " + (command.length - 1));
        }
        tableName = command[1];
        List<DataSet> data = manager.getTableData(tableName);
        view.write(tableBuilder.getTableString(data, tableName));
    }
}