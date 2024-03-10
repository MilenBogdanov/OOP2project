package project.OOP2.f22621615;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class SaveFileCommand implements Command {
    private String fileName;
    private StringBuilder fileContent;

    public SaveFileCommand(String fileName, StringBuilder fileContent) {
        this.fileName = fileName;
        this.fileContent = fileContent;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    @Override
    public void execute() {
        if (fileName != null) {
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
