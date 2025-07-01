package test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.IOException;

import org.junit.jupiter.api.Test;

import exception.LexicalException;
import exception.SyntacticException;
import parser.Parser;
import scanner.Scanner;

public class TestParser {

    @Test
    void testFirstTests() throws IOException, LexicalException, SyntacticException {
        Scanner scanner = new Scanner("src/test/data/testParser/firstTest.txt");
        Parser parser = new Parser(scanner);
        assertDoesNotThrow(parser::parse);
    }

    @Test
    void testParserCorretto1() throws IOException, LexicalException, SyntacticException {
        Scanner scanner = new Scanner("src/test/data/testParser/testParserCorretto1.txt");
        Parser parser = new Parser(scanner);
        assertDoesNotThrow(parser::parse);
    }

    @Test
    void testParserCorretto2() throws IOException, LexicalException, SyntacticException {
        Scanner scanner = new Scanner("src/test/data/testParser/testParserCorretto2.txt");
        Parser parser = new Parser(scanner);
        assertDoesNotThrow(parser::parse);
    }

    @Test
    void testParserEcc_0() throws IOException, LexicalException, SyntacticException {
        Scanner scanner = new Scanner("src/test/data/testParser/testParserEcc_0.txt");
        Parser parser = new Parser(scanner);
        assertThrows(SyntacticException.class, ()->parser.parse());
    }

    @Test
    void testParserEcc_1() throws IOException, LexicalException, SyntacticException {
        Scanner scanner = new Scanner("src/test/data/testParser/testParserEcc_1.txt");
        Parser parser = new Parser(scanner);
        assertThrows(SyntacticException.class, ()->parser.parse());
    }

    @Test
    void testParserEcc_2() throws IOException, LexicalException, SyntacticException {
        Scanner scanner = new Scanner("src/test/data/testParser/testParserEcc_2.txt");
        Parser parser = new Parser(scanner);
        assertThrows(SyntacticException.class, ()->parser.parse());
    }

    @Test
    void testParserEcc_3() throws IOException, LexicalException, SyntacticException {
        Scanner scanner = new Scanner("src/test/data/testParser/testParserEcc_3.txt");
        Parser parser = new Parser(scanner);
        assertThrows(SyntacticException.class, ()->parser.parse());
    }

    @Test
    void testParserEcc_4() throws IOException, LexicalException, SyntacticException {
        Scanner scanner = new Scanner("src/test/data/testParser/testParserEcc_4.txt");
        Parser parser = new Parser(scanner);
        assertThrows(SyntacticException.class, ()->parser.parse());
    }

    @Test
    void testParserEcc_5() throws IOException, LexicalException, SyntacticException {
        Scanner scanner = new Scanner("src/test/data/testParser/testParserEcc_5.txt");
        Parser parser = new Parser(scanner);
        assertThrows(SyntacticException.class, ()->parser.parse());
    }

    @Test
    void testParserEcc_6() throws IOException, LexicalException, SyntacticException {
        Scanner scanner = new Scanner("src/test/data/testParser/testParserEcc_6.txt");
        Parser parser = new Parser(scanner);
        assertThrows(SyntacticException.class, ()->parser.parse());
    }

    @Test
    void testParserEcc_7() throws IOException, LexicalException, SyntacticException {
        Scanner scanner = new Scanner("src/test/data/testParser/testParserEcc_7.txt");
        Parser parser = new Parser(scanner);
        assertThrows(SyntacticException.class, ()->parser.parse());
    }

    @Test
    void testSoloDich() throws IOException, LexicalException, SyntacticException {
        Scanner scanner = new Scanner("src/test/data/testParser/testSoloDich.txt");
        Parser parser = new Parser(scanner);
        assertDoesNotThrow(parser::parse);
    }

    @Test
    void testSoloDichPrint() throws IOException, LexicalException, SyntacticException {
        Scanner scanner = new Scanner("src/test/data/testParser/testSoloDichPrint.txt");
        Parser parser = new Parser(scanner);
        assertDoesNotThrow(parser::parse);
    }

}
