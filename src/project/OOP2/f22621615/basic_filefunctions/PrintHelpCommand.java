package project.OOP2.f22621615.basic_filefunctions;

import project.OOP2.f22621615.interfaces.Command;

public class PrintHelpCommand implements Command {
    @Override
    public void execute() {
        System.out.println("The following commands are supported:");
        System.out.println("open <file>\t\topens <file>");

        System.out.println("load <file>\t\tloads table from <file>"); //1
        System.out.println("showtables\t\tshows names of loaded tables"); //2

        System.out.println("close\t\t\tcloses currently opened file");
        System.out.println("save\t\t\tsaves the currently open file");
        System.out.println("saveas <file>\t\tsaves the currently open file in <file>");
        System.out.println("help\t\t\tprints this information");
        System.out.println("exit\t\t\texits the program");
    }
}