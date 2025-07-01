package test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.FileNotFoundException;

import org.junit.jupiter.api.Test;

import exception.LexicalException;
import scanner.Scanner;
import token.Token;
import token.TokenType;

class TestTypeChecking {
    @Test
	void testDicRipeture() throws LexicalException, NoSuchMethodException, SecurityException,
			IllegalAccessException, FileNotFoundException {
		Scanner scanner = new Scanner("src/test/data/testTypeChecking/1_dicRipetute.txt");
        Token token = scanner.nextToken();
        
	}

    @Test
	void testIdNonDec1() throws LexicalException, NoSuchMethodException, SecurityException,
			IllegalAccessException, FileNotFoundException {
		Scanner scanner = new Scanner("src/test/data/testTypeChecking/2_idNonDec.txt");
        Token token = scanner.nextToken();
        
	}

    
    @Test
	void testIdNonDec2() throws LexicalException, NoSuchMethodException, SecurityException,
			IllegalAccessException, FileNotFoundException {
		Scanner scanner = new Scanner("src/test/data/testTypeChecking/3_idNonDec.txt");
        Token token = scanner.nextToken();
        
	}

    @Test
	void testTipoNonCompatibile() throws LexicalException, NoSuchMethodException, SecurityException,
			IllegalAccessException, FileNotFoundException {
		Scanner scanner = new Scanner("src/test/data/testTypeChecking/4_tipoNonCompatibile.txt");
        Token token = scanner.nextToken();
        
	}


    @Test
	void testCorretto1() throws LexicalException, NoSuchMethodException, SecurityException,
			IllegalAccessException, FileNotFoundException {
		Scanner scanner = new Scanner("src/test/data/testTypeChecking/5_corretto.txt");
        Token token = scanner.nextToken();
        
	}

    @Test
	void testCorretto2() throws LexicalException, NoSuchMethodException, SecurityException,
			IllegalAccessException, FileNotFoundException {
		Scanner scanner = new Scanner("src/test/data/testTypeChecking/6_corretto.txt");
        Token token = scanner.nextToken();
        
	}

    @Test
	void testCorretto3() throws LexicalException, NoSuchMethodException, SecurityException,
			IllegalAccessException, FileNotFoundException {
		Scanner scanner = new Scanner("src/test/data/testTypeChecking/7_corretto.txt");
        Token token = scanner.nextToken();
        
	}
}
