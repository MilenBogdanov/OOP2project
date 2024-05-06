package project.OOP2.f22621615.basic_filefunctions;

import project.OOP2.f22621615.interfaces.Command;

public class PrintHelpCommand implements Command {
    @Override
    public void execute() {
        System.out.println("The following commands are supported:");
        System.out.println("open <file>\t\topens <file>");

        System.out.println("load <file>\t\tloads table from <file>"); //1
        System.out.println("showtables\t\tshows names of loaded tables"); //2
        System.out.println("describe\t\tdescribe the types of the columns in the table"); //3
        System.out.println("print\t\tdisplays all rows from a given table"); //4
        System.out.println("export \t\t<table> <file> exports a table to a file"); //5
        System.out.println("select \t\t<column-n> <value> <table name> searches for a selected value"); //6
        System.out.println("addcolumn \t\t<table name> <column name> <column type> add column with null value"); //7

        System.out.println("close\t\t\tcloses currently opened file");
        System.out.println("save\t\t\tsaves the currently open file");
        System.out.println("saveas <file>\t\tsaves the currently open file in <file>");
        System.out.println("help\t\t\tprints this information");
        System.out.println("exit\t\t\texits the program");
    }
}