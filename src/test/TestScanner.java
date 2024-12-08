package test;

import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import org.junit.jupiter.api.Test;

import scanner.LexicalException;
import scanner.Scanner;
import token.Token;
import token.TokenType;

class TestScanner {

	// DA FINIRE
	@Test
	void testCaratteriNonCaratteri() throws IOException, LexicalException, NoSuchMethodException, SecurityException,
			IllegalAccessException, InvocationTargetException {
		Scanner scanner = new Scanner("src/test/data/testScanner/caratteriNonCaratteri.txt");
		Token t;

		assertThrows(LexicalException.class, () -> {
			scanner.nextToken();
		});

		assertThrows(LexicalException.class, () -> {
			scanner.nextToken();
		});

		t = new Token(TokenType.SEMI, 2);
		assertTrue(t.equals(scanner.nextToken()));

		assertThrows(LexicalException.class, () -> {
			scanner.nextToken();
		});

		/*
		 * t = new Token(TokenType.PLUS, 3);
		 * assertTrue(t.equals(scanner.nextToken()));
		 */
	}

	@Test
	void testCaratteriSkip() throws IOException, LexicalException, NoSuchMethodException, SecurityException,
			IllegalAccessException, InvocationTargetException {
		Scanner scanner = new Scanner("src/test/data/testScanner/caratteriSkip");
		Token t;

		t = new Token(TokenType.EOF, 5);
		assertTrue(t.equals(scanner.nextToken()));
	}

	// DA RIVEDERE
	@Test
	void testErroriNumbers() throws IOException, LexicalException, NoSuchMethodException, SecurityException,
			IllegalAccessException, InvocationTargetException {
		Scanner scanner = new Scanner("src/test/data/testScanner/erroriNumbers.txt");
		Token t;

		/*
		 * t = new Token(TokenType.INT, 1, "0");
		 * assertTrue(t.equals(scanner.nextToken()));
		 * 
		 * t = new Token(TokenType.INT, 1, "33");
		 * assertTrue(t.equals(scanner.nextToken()));
		 */
		assertThrows(LexicalException.class, () -> {
			scanner.nextToken();
		});

		assertThrows(LexicalException.class, () -> {
			scanner.nextToken();
		});

		assertThrows(LexicalException.class, () -> {
			scanner.nextToken();
		});
		/*
		 * t = new Token(TokenType.EOF, 5);
		 * assertTrue(t.equals(scanner.nextToken()));
		 */
	}

	@Test
	void testEOF() throws IOException, LexicalException, NoSuchMethodException, SecurityException,
			IllegalAccessException, InvocationTargetException {
		Scanner scanner = new Scanner("src/test/data/testScanner/testEOF.txt");
		Token t;

		t = new Token(TokenType.EOF, 4);
		assertTrue(t.equals(scanner.nextToken()));
	}

	@Test
	void testIdKeyWords() throws IOException, LexicalException, NoSuchMethodException, SecurityException,
			IllegalAccessException, InvocationTargetException {
		Scanner scanner = new Scanner("src/test/data/testScanner/testIdKeyWords.txt");
		Token t;

		t = new Token(TokenType.TYINT, 1, "int");
		assertTrue(t.equals(scanner.nextToken()));

		assertThrows(LexicalException.class, () -> {
			scanner.nextToken();
		});

		t = new Token(TokenType.TYFLOAT, 2, "float");
		assertTrue(t.equals(scanner.nextToken()));

		t = new Token(TokenType.PRINT, 3, "print");
		assertTrue(t.equals(scanner.nextToken()));

		assertThrows(LexicalException.class, () -> {
			scanner.nextToken();
		});
		assertThrows(LexicalException.class, () -> {
			scanner.nextToken();
		});

		t = new Token(TokenType.TYINT, 6, "int");
		assertTrue(t.equals(scanner.nextToken()));

		assertThrows(LexicalException.class, () -> {
			scanner.nextToken();
		});
	}
}
