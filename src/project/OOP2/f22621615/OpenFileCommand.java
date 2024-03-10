package project.OOP2.f22621615;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class OpenFileCommand implements Command {
    private String fileName;
    private StringBuilder fileContent;

    public OpenFileCommand(String fileName, StringBuilder fileContent) {
        this.fileName = fileName;
        this.fileContent = fileContent;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    @Override
    public void execute() {
        try {
            File file = new File(fileName);
            if (file.exists()) {
                try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
                    String line;
                    while ((line = reader.readLine()) != null) {
                        fileContent.append(line).append("\n");
                    }
                    System.out.println("Successfully opened " + file.getName());
                }
            } else {
                fileContent.setLength(0);
                System.out.println("File not found. Created a new empty file.");
            }
        } catch (IOException e) {
            System.out.println("Error opening the file: " + e.getMessage());
        }
    }
}