package project.OOP2.f22621615.basic_filefunctions;

import project.OOP2.f22621615.interfaces.Command;
import project.OOP2.f22621615.interfaces.FileCommand;

import java.util.HashMap;
import java.util.Map;

public class CommandCenter {
    private StringBuilder fileContent;
    private Map<String, Command> commands;
    private OpenFileCommand openFileCommand;

    public CommandCenter(StringBuilder fileContent) {
        this.fileContent = fileContent;
        this.openFileCommand = new OpenFileCommand(this.fileContent);
        initializeCommands();
    }

    private void initializeCommands() {
        commands = new HashMap<>();
        commands.put("open", openFileCommand);
        commands.put("close", new CloseFileCommand(this.fileContent));
        commands.put("save", new SaveFileCommand(openFileCommand, this.fileContent)); // Pass the OpenFileCommand instance
        commands.put("saveas", new SaveFileAsCommand(this.fileContent, openFileCommand)); // Pass the OpenFileCommand instance
        commands.put("help", new PrintHelpCommand());
        commands.put("exit", new ExitCommand());
    }

    public void executeCommand(String commandName, String parameter) {
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
                    saveAsCommand.setFileName(parameter); // Set the new file name
                    saveAsCommand.execute();
                } else {
                    if (!openFileCommand.isFileOpened()) {
                        System.out.println("No file is currently open. Use 'open' to open a file.");
                        return;
                    }
                    fileCommand.setFileName(parameter);
                }
            }
            command.execute();
        } else {
            System.out.println("Invalid command. Type 'help' to see the list of commands.");
        }
    }
}