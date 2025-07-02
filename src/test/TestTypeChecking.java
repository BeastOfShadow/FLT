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
import visitor.TypeCheckingVisitor;

class TestTypeChecking {
    @Test
	void testDicRipeture() throws FileNotFoundException, SyntacticException {
		NodeProgram np = new Parser(new Scanner("src/test/data/testTypeChecking/1_dicRipetute.txt")).parse();
        var tcVisit = new TypeCheckingVisitor();
		np.accept(tcVisit);
        assertEquals(tcVisit.getErrorMessage(), "Semantic error: variable 'a' already declared.");
	}

    @Test
	void testIdNonDec1() throws FileNotFoundException, SyntacticException {
		NodeProgram np = new Parser(new Scanner("src/test/data/testTypeChecking/2_idNonDec.txt")).parse();
        
        var tcVisit = new TypeCheckingVisitor();
		np.accept(tcVisit);
		
		assertEquals(tcVisit.getErrorMessage(), "Semantic error: variable 'b' not declared.");
	}

    
    @Test
	void testIdNonDec2() throws FileNotFoundException, SyntacticException {
		NodeProgram np = new Parser(new Scanner("src/test/data/testTypeChecking/3_idNonDec.txt")).parse();

		var tcVisit = new TypeCheckingVisitor();
		np.accept(tcVisit);

		assertEquals(tcVisit.getErrorMessage(), "Semantic error: variable 'c' not declared.");
        
	}

    @Test
	void testTipoNonCompatibile() throws FileNotFoundException, SyntacticException {
		NodeProgram np = new Parser(new Scanner("src/test/data/testTypeChecking/4_tipoNonCompatibile.txt")).parse();
		
		var tcVisit = new TypeCheckingVisitor();
		np.accept(tcVisit);
		
		/* errore tipi non compatibili */
		assertEquals(tcVisit.getErrorMessage(), "Semantic error: cannot assign expression of type FLOAT to variable 'b' of type INT.");
	}


    @Test
	void testCorretto1() throws FileNotFoundException, SyntacticException {
		NodeProgram np = new Parser(new Scanner("src/test/data/testTypeChecking/5_corretto.txt")).parse();
        
		var tcVisit = new TypeCheckingVisitor();
		np.accept(tcVisit);
		assertEquals(tcVisit.getErrorMessage(), null);

	}

    @Test
	void testCorretto2() throws FileNotFoundException, SyntacticException {
		NodeProgram np = new Parser(new Scanner("src/test/data/testTypeChecking/6_corretto.txt")).parse();
        
		var tcVisit = new TypeCheckingVisitor();
		np.accept(tcVisit);
		
		assertEquals(tcVisit.getErrorMessage(), null);
	}

    @Test
	void testCorretto3() throws FileNotFoundException, SyntacticException {
		NodeProgram np = new Parser(new Scanner("src/test/data/testTypeChecking/7_corretto.txt")).parse();
        
		var tcVisit = new TypeCheckingVisitor();
		np.accept(tcVisit);
		
		assertEquals(tcVisit.getErrorMessage(), null);
	}
}
