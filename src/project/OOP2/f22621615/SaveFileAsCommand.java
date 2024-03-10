package project.OOP2.f22621615;

import java.io.*;

public class SaveFileAsCommand implements Command {
    private String newFileName;
    private StringBuilder fileContent;

    public SaveFileAsCommand(String newFileName, StringBuilder fileContent) {
        this.newFileName = newFileName;
        this.fileContent = fileContent;
    }

    public void setNewFileName(String newFileName) {
        this.newFileName = newFileName;
    }

    @Override
    public void execute() {
        saveToFile(newFileName, fileContent);
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