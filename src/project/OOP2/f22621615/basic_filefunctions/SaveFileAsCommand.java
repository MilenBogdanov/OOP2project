package project.OOP2.f22621615.basic_filefunctions;

import project.OOP2.f22621615.interfaces.Command;
import project.OOP2.f22621615.interfaces.FileCommand;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class SaveFileAsCommand implements Command, FileCommand {
    private String newFileName;
    private StringBuilder fileContent;
    private OpenFileCommand openFileCommand;

    public SaveFileAsCommand(StringBuilder fileContent, OpenFileCommand openFileCommand) {
        this.fileContent = fileContent;
        this.openFileCommand = openFileCommand;
    }

    @Override
    public void setFileName(String fileName) {
        this.newFileName = fileName;
    }

    @Override
    public String getFileName() {
        return this.newFileName;
    }

    @Override
    public void execute() {
        if (newFileName != null && !newFileName.isEmpty()) {
            saveToFile(newFileName, fileContent);
        } else {
            System.out.println("Please specify a file name to save.");
        }
    }

    private void saveToFile(String fileName, StringBuilder fileContent) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
            writer.write(fileContent.toString());
            System.out.println("Successfully saved as " + new File(fileName).getName());
        } catch (IOException e) {
            System.out.println("Error saving the file: " + e.getMessage());
        }
    }
}