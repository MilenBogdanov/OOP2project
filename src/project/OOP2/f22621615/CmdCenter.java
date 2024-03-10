package project.OOP2.f22621615;

import java.util.HashMap;
import java.util.Map;

public class CmdCenter {
    private Map<String, Command> commandMap = new HashMap<>();
    private StringBuilder fileContent = new StringBuilder();
    private String currentFileName = "";

    public CmdCenter() {
        commandMap.put("close", new CloseFileCommand(fileContent));
        commandMap.put("exit", new ExitCommand());
        commandMap.put("help", new PrintHelpCommand());
        commandMap.put("open", new OpenFileCommand("", fileContent));
        commandMap.put("save", new SaveFileCommand("", fileContent));
        commandMap.put("saveas", new SaveFileAsCommand("", fileContent));
    }

    public void executeCommand(String command) {
        String[] parts = command.split("\\s", 2);

        String commandName = parts[0].toLowerCase();
        String argument = parts.length > 1 ? parts[1] : "";

        Command cmd = commandMap.get(commandName);

        if (cmd != null) {
            if (cmd instanceof OpenFileCommand) {
                ((OpenFileCommand) cmd).setFileName(argument);
            } else if (cmd instanceof SaveFileCommand) {
                ((SaveFileCommand) cmd).setFileName(argument);
            } else if (cmd instanceof SaveFileAsCommand) {
                ((SaveFileAsCommand) cmd).setNewFileName(argument);
            }

            cmd.execute();
        } else {
            System.out.println("Unknown command: " + commandName);
        }
    }
}