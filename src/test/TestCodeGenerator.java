package test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.FileNotFoundException;

import org.junit.jupiter.api.Test;

import ast.NodeProgram;
import exception.LexicalException;
import exception.SyntacticException;
import parser.Parser;
import scanner.Scanner;
import token.Token;
import token.TokenType;
import visitor.CodeGeneratorVisitor;
import visitor.TypeCheckingVisitor;

public class TestCodeGenerator {
    @Test
    void testAssign() throws FileNotFoundException, SyntacticException {
        NodeProgram nP = new Parser(new Scanner("src/test/data/TestCodeGenerator/1_assign.txt")).parse();
        var tcVisit = new TypeCheckingVisitor();
        nP.accept(tcVisit);
        var cgVisit = new CodeGeneratorVisitor();
        nP.accept(cgVisit);

        assertEquals(cgVisit.getLog(), "");
        assertEquals("1 6 / sa la p P", cgVisit.getGeneratedCode());
    }

    @Test
    void testDivisioni() throws FileNotFoundException, SyntacticException {
        NodeProgram nP = new Parser(new Scanner("src/test/data/TestCodeGenerator/2_divsioni.txt")).parse();
        var tcVisit = new TypeCheckingVisitor();
        nP.accept(tcVisit);
        var cgVisit = new CodeGeneratorVisitor();
        nP.accept(cgVisit);

        assertEquals(cgVisit.getLog(), "");
        assertEquals(
                "0 sa la 1 + sa 6 sb 1.0 6 5 k / 0 k la lb / + sc la p P lb p P lc p P", cgVisit.getGeneratedCode());
    }

    @Test
    void testGenerale() throws FileNotFoundException, SyntacticException {
        NodeProgram nP = new Parser(new Scanner("src/test/data/TestCodeGenerator/3_generale.txt")).parse();
        var tcVisit = new TypeCheckingVisitor();
        nP.accept(tcVisit);
        var cgVisit = new CodeGeneratorVisitor();
        nP.accept(cgVisit);

        assertEquals(cgVisit.getLog(), "");
        assertEquals("5 3 + sa la 0.5 5 k / 0 k sb la p P lb 4 5 k / 0 k sb lb p P lb 1 - sc lc lb 5 k / 0 k sc lc p P",
        cgVisit.getGeneratedCode());
    }

    @Test
    void testRegistriFiniti() throws FileNotFoundException, SyntacticException {
        NodeProgram nP = new Parser(new Scanner("src/test/data/TestCodeGenerator/4_registriFiniti.txt")).parse();
        var tcVisit = new TypeCheckingVisitor();
        nP.accept(tcVisit);
        var cgVisit = new CodeGeneratorVisitor();
        nP.accept(cgVisit);

        assertEquals("No more registers available", cgVisit.getLog());
        assertEquals("", cgVisit.getGeneratedCode());
    }
}
