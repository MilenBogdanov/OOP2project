package project.OOP2.f22621615;

public class CloseFileCommand implements Command {
    private StringBuilder fileContent;

    public CloseFileCommand(StringBuilder fileContent) {
        this.fileContent = fileContent;
    }

    @Override
    public void execute() {
        fileContent.setLength(0);
        System.out.println("Successfully closed the file.");
    }
}