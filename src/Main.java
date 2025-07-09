import java.io.IOException;
import java.io.PrintWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;

import ast.NodeProgram;
import exception.SyntacticException;
import parser.Parser;
import scanner.Scanner;
import symbolTable.SymbolTable;
import visitor.CodeGeneratorVisitor;
import visitor.TypeCheckingVisitor;

public class Main {
    static List<String> lines = new ArrayList<>();
    static java.util.Scanner scanner = new java.util.Scanner(System.in);

    /**
     * Saves the lines of code to a file at the specified path.
     * @param path The path to the file where the code will be saved.
     * If the file already exists, it will be overwritten.
     * Otherwise, it will be created.
     * @throws IOException If an I/O error occurs while writing to the file.
     * Prints a message indicating the file has been saved.
     * If an error occurs, it prints an error message and exits the program.
     */
    static void saveToFile(String path) {
        try (PrintWriter pw = new PrintWriter(path)) {
            lines.forEach(pw::println);
            System.out.println("\nFile saved to: " + path);
        } catch (IOException e) {
            System.err.println("Save error: " + e.getMessage());
            System.exit(1);
        }
    }

    /**
     * Compiles the file at the specified path.
     * It parses the file, performs type checking, and generates code.
     * @param path The path to the file to be compiled.
     * @throws FileNotFoundException If the file is not found.
     * @throws SyntacticException If there are syntax errors in the code.
     * Prints the generated code and the symbol table after compilation.
     */
    static void compileFile(String path) throws FileNotFoundException, SyntacticException {
        System.out.println("\nCompiling " + path + "...\n");
        NodeProgram nP = new Parser(new Scanner(path)).parse();
        var tcVisit = new TypeCheckingVisitor();
        nP.accept(tcVisit);
        var cgVisit = new CodeGeneratorVisitor();
        nP.accept(cgVisit);
        var generateCode = cgVisit.getGeneratedCode();
        System.out.println("\nCompilation complete! Current is now direct!\n");
        System.out.println("GENERATED DC CODE: \n" + generateCode);
        System.out.println("\nSYMBOL TABLE: \n" + SymbolTable.toStr());
    }

    public static void main(String[] args) throws FileNotFoundException, SyntacticException {
        StringBuilder sb = new StringBuilder();
        int lineNumber = 1;

        sb.append("┌─────────────────────────────────────────────────────────┐\n");
        sb.append(String.format("│ %-55s │\n", "From AC to DC — Smoothing out the waves of your code!"));
        sb.append(String.format("│ %-55s │\n", "Welcome to MiniLang Compiler!"));
        sb.append("├─────────────────────────────────────────────────────────┤\n");
        sb.append(String.format("│ %-55s │\n", "Start writing your code below:"));
        sb.append(String.format("│ %-55s │\n", "IMPORTANT: digit  the word \"save\" when finished!"));
        sb.append("└─────────────────────────────────────────────────────────┘\n");

        System.out.println(sb.toString());
        boolean terminate = false;
       
        while (!terminate) {
            System.out.printf("%d | ", lineNumber);
            
            String input = scanner.nextLine();
            
            if(input.equals("save")) terminate = true;
            else {
                lines.add(input);
                lineNumber++;
            }
        }
        
        System.out.print("\nInsert output file name (without extension): ");
        String fileName = "";
        
        fileName = scanner.nextLine().trim();
        if (fileName.isEmpty()) {
            fileName = "output";
        }
       
        File filePath = new File(fileName + ".txt");
       
        saveToFile(filePath.getAbsolutePath());
        compileFile(filePath.getAbsolutePath());
    }
}