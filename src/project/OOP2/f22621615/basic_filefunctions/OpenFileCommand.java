package project.OOP2.f22621615.basic_filefunctions;

import project.OOP2.f22621615.interfaces.Command;
import project.OOP2.f22621615.interfaces.FileCommand;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class OpenFileCommand implements Command, FileCommand {
    private String fileName;
    private StringBuilder fileContent;
    private boolean fileOpened;

    public OpenFileCommand(StringBuilder fileContent) {
        this.fileContent = fileContent;
        this.fileOpened = false;
    }

    @Override
    public void execute() {
        if (fileName == null || fileName.isEmpty()) {
            System.out.println("Please specify a file to open.");
            return;
        }

        try {
            File file = new File(fileName);
            if (!file.exists()) {
                file.createNewFile();
                System.out.println("File not found. Created a new empty file.");
            }

            try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    fileContent.append(line).append("\n");
                }
                if (!fileOpened) {
                    System.out.println("Successfully opened " + file.getName());
                    fileOpened = true;
                }
            }
        } catch (IOException e) {
            System.out.println("Error opening/creating the file: " + e.getMessage());
        }
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    @Override
    public String getFileName() {
        return fileName;
    }

    public boolean isFileOpened() {
        return fileOpened;
    }
}