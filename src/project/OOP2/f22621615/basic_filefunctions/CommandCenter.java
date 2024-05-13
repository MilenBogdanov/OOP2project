package project.OOP2.f22621615.basic_filefunctions;

import project.OOP2.f22621615.database.Column;
import project.OOP2.f22621615.database.Table;
import project.OOP2.f22621615.enums.DataType;
import project.OOP2.f22621615.functionality.*;
import project.OOP2.f22621615.interfaces.Command;
import project.OOP2.f22621615.interfaces.FileCommand;
import project.OOP2.f22621615.database.Database;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class CommandCenter {
    private StringBuilder fileContent;
    private Map<String, Command> commands;
    private OpenFileCommand openFileCommand;
    private Database database;
    private String lastLoadedFile;

    private Object parseColumnValue(String value, DataType dataType) {
        switch (dataType) {
            case INTEGER:
                return Integer.parseInt(value);
            case FLOAT:
                return Float.parseFloat(value);
            case STRING:
            default:
                return value;
        }
    }

    public CommandCenter(StringBuilder fileContent, Database database) {
        this.fileContent = fileContent;
        this.database = database;
        this.openFileCommand = new OpenFileCommand(this.fileContent);
        initializeCommands();
    }

    private void initializeCommands() {
        commands = new HashMap<>();
        commands.put("open", openFileCommand);
        commands.put("load", new LoadTableFromTextFileCommand(database, null));
        commands.put("close", new CloseFileCommand(this.fileContent));
        commands.put("save", new SaveFileCommand(openFileCommand, this.fileContent));
        commands.put("saveas", new SaveFileAsCommand(this.fileContent, openFileCommand));
        commands.put("help", new PrintHelpCommand());
        commands.put("exit", new ExitCommand());
        commands.put("showtables", new ShowTablesCommand(database));
        commands.put("describe", new DescribeTableCommand(database, null));
        commands.put("print", new PrintTableRowsCommand(database));
        commands.put("export", new ExportTableCommand(database, null, null));
        commands.put("select", new SelectCommand(database, null, null, null));
        commands.put("addcolumn", new AddColumnCommand(database, null, null, null, null));
        commands.put("update", new UpdateCommand(database, null, null, null, null, null));
        commands.put("delete", new DeleteCommand(database, null, null, null));
        commands.put("insert", new InsertCommand(database, null, null));
        commands.put("rename", new RenameTableCommand(database, null, null));
        commands.put("count", new CountRowsCommand(database, null, null, null));
        commands.put("aggregate", new AggregateCommand(database));
    }

    public void executeCommand(String commandName, String parameter) {
        if (commandName.equals("load") && parameter.equals(lastLoadedFile)) {
            System.out.println("File '" + parameter + "' is already loaded.");
            return;
        }

        Command command = commands.get(commandName);
        if (command != null) {
            if (command instanceof FileCommand) {
                FileCommand fileCommand = (FileCommand) command;
                if (fileCommand instanceof OpenFileCommand) {
                    OpenFileCommand openCommand = (OpenFileCommand) fileCommand;
                    openCommand.setFileName(parameter);
                    openCommand.execute();
                } else if (fileCommand instanceof SaveFileAsCommand) {
                    SaveFileAsCommand saveAsCommand = (SaveFileAsCommand) fileCommand;
                    saveAsCommand.setFileName(parameter);
                    saveAsCommand.execute();
                } else {
                    if (!openFileCommand.isFileOpened()) {
                        System.out.println("No file is currently open. Use 'open' to open a file.");
                        return;
                    }
                    fileCommand.setFileName(parameter);
                }
            }

            if (commandName.equals("load")) {
                LoadTableFromTextFileCommand loadCommand = (LoadTableFromTextFileCommand) command;
                loadCommand.setFileName(parameter);
            }

            if (commandName.equals("describe")) {
                DescribeTableCommand describeCommand = (DescribeTableCommand) command;
                describeCommand.describe(parameter);
            }

            if (commandName.equals("print")) {
                PrintTableRowsCommand printCommand = (PrintTableRowsCommand) command;
                printCommand.setTableName(parameter);
            }

            if (commandName.equals("export")) {
                ExportTableCommand exportCommand = (ExportTableCommand) command;
                String[] params = parameter.split("\\s+");
                if (params.length == 2) {
                    exportCommand.setTableName(params[0]);
                    exportCommand.setFileName(params[1]);
                } else {
                    System.out.println("Invalid parameters. Usage: export <tableName> <fileName>");
                    return;
                }
            }
            if (commandName.equals("addcolumn")) {
                AddColumnCommand addColumnCommand = (AddColumnCommand) command;
                String[] params = parameter.split("\\s+");
                if (params.length == 3) {
                    addColumnCommand.setTableName(params[0]);
                    addColumnCommand.setColumnName(params[1]);
                    addColumnCommand.setColumnType(DataType.valueOf(params[2]));
                    addColumnCommand.setFileName(lastLoadedFile);
                } else {
                    System.out.println("Invalid parameters. Usage: addcolumn <tableName> <columnName> <columnType>");
                    return;
                }
            }

            if (commandName.equals("select")) {
                SelectCommand selectCommand = (SelectCommand) command;
                String[] params = parameter.split("\\s+");
                if (params.length == 3) {
                    selectCommand.setColumnName(params[0]);
                    selectCommand.setValue(params[1]);
                    selectCommand.setTableName(params[2]);
                } else {
                    System.out.println("Invalid parameters. Usage: select <column_name> <value> <table_name>");
                    return;
                }
            }

            if (commandName.equals("update")) {
                UpdateCommand updateCommand = (UpdateCommand) command;
                String[] params = parameter.split("\\s+");
                if (params.length == 5) {
                    updateCommand.setTableName(params[0]);
                    updateCommand.setSearchColumnName(params[1]);

                    Column searchColumn = database.getTableByName(params[0]).getColumn(params[1]);
                    Object searchColumnValue = parseColumnValue(params[2], searchColumn.getType());
                    if (searchColumnValue != null) {
                        updateCommand.setSearchColumnValue(searchColumnValue);
                    } else {
                        System.out.println("Invalid search column value: " + params[2]);
                        return;
                    }

                    updateCommand.setTargetColumnName(params[3]);

                    Column targetColumn = database.getTableByName(params[0]).getColumn(params[3]);
                    Object targetColumnValue = parseColumnValue(params[4], targetColumn.getType());
                    if (targetColumnValue != null) {
                        updateCommand.setTargetColumnValue(targetColumnValue);
                    } else {
                        System.out.println("Invalid target column value: " + params[4]);
                        return;
                    }

                    System.out.print("Enter the filename to save the updated table: ");
                    Scanner scanner = new Scanner(System.in);
                    String fileName = scanner.nextLine();
                    updateCommand.setFileName(fileName);

                } else {
                    System.out.println("Invalid parameters. Usage: update <tableName> <searchColumnName> <searchColumnValue> <targetColumnName> <targetColumnValue>");
                    return;
                }
            }

            if (commandName.equals("delete")) {
                DeleteCommand deleteCommand = (DeleteCommand) commands.get("delete");

                String[] params = parameter.split("\\s+");
                if (params.length == 3) {
                    deleteCommand.setTableName(params[0]);
                    deleteCommand.setSearchColumnName(params[1]);

                    deleteCommand.setSearchColumnValue(params[2]);

                    // Execute the delete command
                    deleteCommand.execute();
                } else {
                    System.out.println("Invalid parameters. Usage: delete <tableName> <searchColumnName> <searchColumnValue>");
                }
                return;
            }
            if (commandName.equals("insert")) {
                InsertCommand insertCommand = (InsertCommand) command;
                String[] params = parameter.split("\\s+");
                if (params.length >= 2) {
                    String tableName = params[0];
                    Table table = database.getTableByName(tableName);
                    if (table != null) {
                        String[] values = Arrays.copyOfRange(params, 1, params.length);
                        insertCommand.setTableName(tableName);
                        insertCommand.setValues(values);
                        insertCommand.execute();
                    } else {
                        System.out.println("Table '" + tableName + "' not found.");
                    }
                } else {
                    System.out.println("Invalid parameters. Usage: insert <tableName> <value1> <value2> ... <valueN>");
                }
                return;
            }

            if (commandName.equals("rename")) {
                RenameTableCommand renameCommand = (RenameTableCommand) command;
                String[] params = parameter.split("\\s+");
                if (params.length == 2) {
                    String oldTableName = params[0];
                    String newTableName = params[1];
                    if (database.tableExists(oldTableName)) {
                        renameCommand.setOldName(oldTableName);
                        renameCommand.setNewName(newTableName);
                        renameCommand.execute();
                    } else {
                        System.out.println("Table '" + oldTableName + "' not found.");
                    }
                } else {
                    System.out.println("Invalid parameters. Usage: rename <oldTableName> <newTableName>");
                    return;
                }
            }

            // Handling count command
            if (commandName.equals("count")) {
                CountRowsCommand countRowsCommand = (CountRowsCommand) command;
                String[] params = parameter.split("\\s+");
                if (params.length == 3) {
                    countRowsCommand.setTableName(params[0]);
                    countRowsCommand.setSearchColumnName(params[1]);
                    countRowsCommand.setSearchValue(params[2]);
                    countRowsCommand.execute();
                } else {
                    System.out.println("Invalid parameters. Usage: count <tableName> <searchColumnName> <searchValue>");
                }
                return;
            }

            if (commandName.equals("aggregate")) {
                AggregateCommand aggregateCommand = (AggregateCommand) command;
                String[] params = parameter.split("\\s+");
                if (params.length == 5) {
                    aggregateCommand.setTableName(params[0]);
                    aggregateCommand.setSearchColumnName(params[1]);
                    aggregateCommand.setSearchValue(params[2]);
                    aggregateCommand.setTargetColumnName(params[3]);
                    aggregateCommand.setOperation(params[4]);
                    aggregateCommand.execute();
                } else {
                    System.out.println("Invalid parameters. Usage: aggregate <tableName> <searchColumnName> <searchValue> <targetColumnName> <operation>");
                }
                return;
            }


            command.execute();

            if (commandName.equals("load")) {
                lastLoadedFile = parameter;
            }
        } else {
            System.out.println("Invalid command. Type 'help' to see the list of commands.");
        }
    }
}