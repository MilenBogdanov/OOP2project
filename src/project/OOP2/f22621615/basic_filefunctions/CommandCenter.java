package project.OOP2.f22621615.basic_filefunctions;

import project.OOP2.f22621615.enums.DataType;
import project.OOP2.f22621615.functionality.*;
import project.OOP2.f22621615.interfaces.Command;
import project.OOP2.f22621615.interfaces.FileCommand;
import project.OOP2.f22621615.database.Database;

import java.util.HashMap;
import java.util.Map;

public class CommandCenter {
    private StringBuilder fileContent;
    private Map<String, Command> commands;
    private OpenFileCommand openFileCommand;
    private Database database;
    private String lastLoadedFile; // Store the last loaded file

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
        commands.put("delete", new DeleteCommand(database, null, null, null)); // Add delete command
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

            if (commandName.equals("update")) {
                UpdateCommand updateCommand = (UpdateCommand) command;
                String[] params = parameter.split("\\s+");
                if (params.length == 5) {
                    updateCommand.setTableName(params[0]);
                    updateCommand.setSearchColumnName(params[1]);
                    // Here you can add logic to determine the DataType based on the input
                    updateCommand.setSearchColumnValue(params[2]);
                    updateCommand.setTargetColumnName(params[3]);
                    // Here you can add logic to determine the DataType based on the input
                    updateCommand.setTargetColumnValue(params[4]);
                } else {
                    System.out.println("Invalid parameters. Usage: update <tableName> <searchColumnName> <searchColumnValue> <targetColumnName> <targetColumnValue>");
                    return;
                }
            }

            if (commandName.equals("delete")) {
                DeleteCommand deleteCommand = (DeleteCommand) command;
                String[] params = parameter.split("\\s+");
                if (params.length == 3) {
                    deleteCommand.setTableName(params[0]);
                    deleteCommand.setSearchColumnName(params[1]);
                    deleteCommand.setSearchColumnValue(params[2]);
                } else {
                    System.out.println("Invalid parameters. Usage: delete <tableName> <searchColumnName> <searchColumnValue>");
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