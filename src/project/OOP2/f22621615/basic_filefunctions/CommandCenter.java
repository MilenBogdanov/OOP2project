package project.OOP2.f22621615.basic_filefunctions;

import project.OOP2.f22621615.functionality.DescribeTableCommand;
import project.OOP2.f22621615.functionality.LoadTableFromXMLFileCommand;
import project.OOP2.f22621615.functionality.PrintTableCommand;
import project.OOP2.f22621615.functionality.ShowTablesCommand;
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
        commands.put("load", new LoadTableFromXMLFileCommand(database, null));
        commands.put("close", new CloseFileCommand(this.fileContent));
        commands.put("save", new SaveFileCommand(openFileCommand, this.fileContent));
        commands.put("saveas", new SaveFileAsCommand(this.fileContent, openFileCommand));
        commands.put("help", new PrintHelpCommand());
        commands.put("exit", new ExitCommand());
        commands.put("showtables", new ShowTablesCommand(database));
        commands.put("describe", new DescribeTableCommand(database, null)); // Add describe command
        commands.put("print", new PrintTableCommand(database, null)); // Change null to parameter
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
                LoadTableFromXMLFileCommand loadCommand = (LoadTableFromXMLFileCommand) command;
                loadCommand.setFileName(parameter);
            }
            // Pass the parameter when executing the describe command
            if (commandName.equals("describe")) {
                DescribeTableCommand describeCommand = (DescribeTableCommand) command;
                describeCommand.describe(parameter);
            }
            // Set the tableName attribute before executing the print command
            if (commandName.equals("print")) {
                PrintTableCommand printCommand = (PrintTableCommand) command;
                printCommand.setTableName(parameter); // Set the tableName attribute
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