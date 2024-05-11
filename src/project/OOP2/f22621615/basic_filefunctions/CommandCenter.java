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
    private String lastLoadedFile; // Store the last loaded file

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
        commands.put("load", new LoadTableFromTextFileCommand(database, null)); // Pass null as the file name
        commands.put("close", new CloseFileCommand(this.fileContent));
        commands.put("save", new SaveFileCommand(openFileCommand, this.fileContent));
        commands.put("saveas", new SaveFileAsCommand(this.fileContent, openFileCommand));
        commands.put("help", new PrintHelpCommand());
        commands.put("exit", new ExitCommand());
        commands.put("showtables", new ShowTablesCommand(database));
        commands.put("describe", new DescribeTableCommand(database, null)); // Add describe command
        commands.put("print", new PrintTableRowsCommand(database));
        commands.put("export", new ExportTableCommand(database, null, null)); // Add export command
        commands.put("select", new SelectCommand(database, null, null, null)); // Add select command
        commands.put("addcolumn", new AddColumnCommand(database, null, null, null, null)); // Add addcolumn command
        commands.put("update", new UpdateCommand(database, null, null, null, null, null)); // Add update command
        commands.put("delete", new DeleteCommand(database, null, null, null));
        commands.put("insert", new InsertCommand(database, null, null)); // Add insert command
        commands.put("rename", new RenameTableCommand(database, null, null)); // Add rename command
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
            // Pass the parameter when executing the load command
            if (commandName.equals("load")) {
                LoadTableFromTextFileCommand loadCommand = (LoadTableFromTextFileCommand) command;
                loadCommand.setFileName(parameter);
            }
            // Pass the parameter when executing the describe command
            if (commandName.equals("describe")) {
                DescribeTableCommand describeCommand = (DescribeTableCommand) command;
                describeCommand.describe(parameter);
            }
            // Pass the parameter when executing the print command
            if (commandName.equals("print")) {
                PrintTableRowsCommand printCommand = (PrintTableRowsCommand) command;
                printCommand.setTableName(parameter);
            }
            // Pass the parameters when executing the export command
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
                    // Here you can add logic to determine the DataType based on the input
                    addColumnCommand.setColumnType(DataType.valueOf(params[2]));
                    addColumnCommand.setFileName(lastLoadedFile); // Set the filename
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

                    // Get the data type of the search column
                    Column searchColumn = database.getTableByName(params[0]).getColumn(params[1]);
                    Object searchColumnValue = parseColumnValue(params[2], searchColumn.getType());
                    if (searchColumnValue != null) {
                        updateCommand.setSearchColumnValue(searchColumnValue);
                    } else {
                        System.out.println("Invalid search column value: " + params[2]);
                        return;
                    }

                    updateCommand.setTargetColumnName(params[3]);

                    // Get the data type of the target column
                    Column targetColumn = database.getTableByName(params[0]).getColumn(params[3]);
                    Object targetColumnValue = parseColumnValue(params[4], targetColumn.getType());
                    if (targetColumnValue != null) {
                        updateCommand.setTargetColumnValue(targetColumnValue);
                    } else {
                        System.out.println("Invalid target column value: " + params[4]);
                        return;
                    }

                    // Prompt the user to enter the filename
                    System.out.print("Enter the filename to save the updated table: ");
                    Scanner scanner = new Scanner(System.in);
                    String fileName = scanner.nextLine();
                    updateCommand.setFileName(fileName);

                    // Execute the command
                    //updateCommand.execute();
                } else {
                    System.out.println("Invalid parameters. Usage: update <tableName> <searchColumnName> <searchColumnValue> <targetColumnName> <targetColumnValue>");
                    return;
                }
            }

            if (commandName.equals("delete")) {
                // Retrieve the delete command from the commands map
                DeleteCommand deleteCommand = (DeleteCommand) commands.get("delete");

                // Parse the parameter to extract table name, search column name, and search column value
                String[] params = parameter.split("\\s+");
                if (params.length == 3) {
                    deleteCommand.setTableName(params[0]);
                    deleteCommand.setSearchColumnName(params[1]);

                    // Here you can add logic to determine the DataType based on the input
                    // For simplicity, let's assume the search column value is always a string
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

            command.execute();

            // Remember the last loaded file
            if (commandName.equals("load")) {
                lastLoadedFile = parameter;
            }
        } else {
            System.out.println("Invalid command. Type 'help' to see the list of commands.");
        }
    }
}