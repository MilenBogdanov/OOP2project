package project.OOP2.f22621615;

public class ExitCommand implements Command {
    @Override
    public void execute() {
        System.out.println("Exiting the program...");
        System.exit(0);
    }
}