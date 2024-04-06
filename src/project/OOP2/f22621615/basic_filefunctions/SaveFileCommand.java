package project.OOP2.f22621615.basic_filefunctions;

import project.OOP2.f22621615.interfaces.Command;
import project.OOP2.f22621615.interfaces.FileCommand;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class SaveFileCommand implements Command, FileCommand {
    private OpenFileCommand openFileCommand; // Reference to the OpenFileCommand instance
    private StringBuilder fileContent;

    public SaveFileCommand(OpenFileCommand openFileCommand, StringBuilder fileContent) {
        this.openFileCommand = openFileCommand;
        this.fileContent = fileContent;
    }

    @Override
    public void setFileName(String fileName) {
        // No need to set file name here as it's retrieved from the OpenFileCommand instance
    }

    @Override
    public String getFileName() {
        return openFileCommand.getFileName();
    }

    @Override
    public void execute() {
        String fileName = getFileName();
        if (fileName != null && !fileName.isEmpty()) {
            saveToFile(fileName, fileContent);
        } else {
            System.out.println("No file is currently open. Use 'open' to open a file.");
        }
    }

    private void saveToFile(String fileName, StringBuilder fileContent) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
            writer.write(fileContent.toString());
            System.out.println("Successfully saved " + new File(fileName).getName());
        } catch (IOException e) {
            System.out.println("Error saving the file: " + e.getMessage());
        }
    }
}