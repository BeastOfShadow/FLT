package test;

import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import org.junit.jupiter.api.Test;

import exception.LexicalException;
import scanner.Scanner;
import token.Token;
import token.TokenType;

class TestScanner {

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

		t = new Token(TokenType.PLUS, 3);
		assertTrue(t.equals(scanner.nextToken()));
	}

	@Test
	void testCaratteriSkip() throws IOException, LexicalException, NoSuchMethodException, SecurityException,
			IllegalAccessException, InvocationTargetException {
		Scanner scanner = new Scanner("src/test/data/testScanner/caratteriSkip");
		Token t;

		t = new Token(TokenType.EOF, 5);
		assertTrue(t.equals(scanner.nextToken()));
	}

	@Test
	void testErroriNumbers() throws IOException, LexicalException, NoSuchMethodException, SecurityException,
			IllegalAccessException, InvocationTargetException {
		Scanner scanner = new Scanner("src/test/data/testScanner/erroriNumbers.txt");
		Token t;

		t = new Token(TokenType.INT, 1, "0");
		assertTrue(t.equals(scanner.nextToken()));

		t = new Token(TokenType.INT, 1, "33");
		assertTrue(t.equals(scanner.nextToken()));

		assertThrows(LexicalException.class, () -> {
			scanner.nextToken();
		});

		assertThrows(LexicalException.class, () -> {
			scanner.nextToken();
		});
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
	void testFloat() throws IOException, LexicalException, NoSuchMethodException, SecurityException,
			IllegalAccessException, InvocationTargetException {
		Scanner scanner = new Scanner("src/test/data/testScanner/testFloat.txt");
		Token t;

		t = new Token(TokenType.FLOAT, 1, "098.8095");
		assertTrue(t.equals(scanner.nextToken()));

		assertThrows(LexicalException.class, () -> {
			scanner.nextToken();
		});

		assertThrows(LexicalException.class, () -> {
			scanner.nextToken();
		});

		t = new Token(TokenType.FLOAT, 5, "89.99999");
		assertTrue(t.equals(scanner.nextToken()));
	}

	@Test
	void testGenerale() throws IOException, LexicalException, NoSuchMethodException, SecurityException,
			IllegalAccessException, InvocationTargetException {
		Scanner scanner = new Scanner("src/test/data/testScanner/testGenerale.txt");
		Token t;

		t = new Token(TokenType.TYINT, 1, "int");
		assertTrue(t.equals(scanner.nextToken()));

		t = new Token(TokenType.ID, 1, "temp");
		assertTrue(t.equals(scanner.nextToken()));

		t = new Token(TokenType.SEMI, 1);
		assertTrue(t.equals(scanner.nextToken()));

		t = new Token(TokenType.ID, 2, "temp");
		assertTrue(t.equals(scanner.nextToken()));

		t = new Token(TokenType.OP_ASSIGN, 2, "+=");
		assertTrue(t.equals(scanner.nextToken()));

		assertThrows(LexicalException.class, () -> {
			scanner.nextToken();
		});

		t = new Token(TokenType.SEMI, 2);
		assertTrue(t.equals(scanner.nextToken()));

		t = new Token(TokenType.TYFLOAT, 4);
		assertTrue(t.equals(scanner.nextToken()));

		t = new Token(TokenType.ID, 4, "b");
		assertTrue(t.equals(scanner.nextToken()));

		t = new Token(TokenType.SEMI, 4);
		assertTrue(t.equals(scanner.nextToken()));

		t = new Token(TokenType.ID, 5, "b");
		assertTrue(t.equals(scanner.nextToken()));

		t = new Token(TokenType.ASSIGN, 5);
		assertTrue(t.equals(scanner.nextToken()));

		t = new Token(TokenType.ID, 5, "temp");
		assertTrue(t.equals(scanner.nextToken()));

		t = new Token(TokenType.PLUS, 5);
		assertTrue(t.equals(scanner.nextToken()));

		t = new Token(TokenType.FLOAT, 5, "3.2");
		assertTrue(t.equals(scanner.nextToken()));

		t = new Token(TokenType.SEMI, 5);
		assertTrue(t.equals(scanner.nextToken()));

		t = new Token(TokenType.PRINT, 6);
		assertTrue(t.equals(scanner.nextToken()));

		t = new Token(TokenType.ID, 6, "b");
		assertTrue(t.equals(scanner.nextToken()));

		t = new Token(TokenType.SEMI, 6);
		assertTrue(t.equals(scanner.nextToken()));

		t = new Token(TokenType.EOF, 7);
		assertTrue(t.equals(scanner.nextToken()));
	}

	@Test
	void testId() throws IOException, LexicalException, NoSuchMethodException, SecurityException,
			IllegalAccessException, InvocationTargetException {
		Scanner scanner = new Scanner("src/test/data/testScanner/testId.txt");
		Token t;

		t = new Token(TokenType.ID, 1, "jskjdsfhkjdshkf");
		assertTrue(t.equals(scanner.nextToken()));

		t = new Token(TokenType.ID, 2, "printl");
		assertTrue(t.equals(scanner.nextToken()));

		t = new Token(TokenType.ID, 4, "ffloat");
		assertTrue(t.equals(scanner.nextToken()));

		t = new Token(TokenType.ID, 6, "hhhjj");
		assertTrue(t.equals(scanner.nextToken()));
	}

	@Test
	void testIdKeyWords() throws IOException, LexicalException, NoSuchMethodException, SecurityException,
			IllegalAccessException, InvocationTargetException {
		Scanner scanner = new Scanner("src/test/data/testScanner/testIdKeyWords.txt");
		Token t;

		t = new Token(TokenType.TYINT, 1);
		assertTrue(t.equals(scanner.nextToken()));

		t = new Token(TokenType.ID, 1, "inta");
		assertTrue(t.equals(scanner.nextToken()));

		t = new Token(TokenType.TYFLOAT, 2);
		assertTrue(t.equals(scanner.nextToken()));

		t = new Token(TokenType.PRINT, 3);
		assertTrue(t.equals(scanner.nextToken()));

		t = new Token(TokenType.ID, 4, "nome");
		assertTrue(t.equals(scanner.nextToken()));

		t = new Token(TokenType.ID, 5, "intnome");
		assertTrue(t.equals(scanner.nextToken()));

		t = new Token(TokenType.TYINT, 6);
		assertTrue(t.equals(scanner.nextToken()));

		t = new Token(TokenType.ID, 6, "nome");
		assertTrue(t.equals(scanner.nextToken()));
	}

	@Test
	void testInt() throws IOException, LexicalException, NoSuchMethodException, SecurityException,
			IllegalAccessException, InvocationTargetException {
		Scanner scanner = new Scanner("src/test/data/testScanner/testInt.txt");
		Token t;

		t = new Token(TokenType.INT, 1, "0050");
		assertTrue(t.equals(scanner.nextToken()));

		t = new Token(TokenType.INT, 2, "698");
		assertTrue(t.equals(scanner.nextToken()));

		t = new Token(TokenType.INT, 4, "560099");
		assertTrue(t.equals(scanner.nextToken()));

		t = new Token(TokenType.INT, 5, "1234");
		assertTrue(t.equals(scanner.nextToken()));
	}

	@Test
	void testKeyWords() throws IOException, LexicalException, NoSuchMethodException, SecurityException,
			IllegalAccessException, InvocationTargetException {
		Scanner scanner = new Scanner("src/test/data/testScanner/testKeywords.txt");
		Token t;

		t = new Token(TokenType.PRINT, 2);
		assertTrue(t.equals(scanner.nextToken()));

		t = new Token(TokenType.TYFLOAT, 2);
		assertTrue(t.equals(scanner.nextToken()));

		t = new Token(TokenType.TYINT, 5);
		assertTrue(t.equals(scanner.nextToken()));
	}

	@Test
	void testOpsDels() throws IOException, LexicalException, NoSuchMethodException, SecurityException,
			IllegalAccessException, InvocationTargetException {
		Scanner scanner = new Scanner("src/test/data/testScanner/testOpsDels.txt");
		Token t;

		t = new Token(TokenType.PLUS, 1);
		assertTrue(t.equals(scanner.nextToken()));

		t = new Token(TokenType.OP_ASSIGN, 1, "/=");
		assertTrue(t.equals(scanner.nextToken()));

		t = new Token(TokenType.MINUS, 2);
		assertTrue(t.equals(scanner.nextToken()));

		t = new Token(TokenType.TIMES, 2);
		assertTrue(t.equals(scanner.nextToken()));

		t = new Token(TokenType.DIVIDE, 3);
		assertTrue(t.equals(scanner.nextToken()));

		t = new Token(TokenType.OP_ASSIGN, 5, "+=");
		assertTrue(t.equals(scanner.nextToken()));

		t = new Token(TokenType.ASSIGN, 6);
		assertTrue(t.equals(scanner.nextToken()));

		t = new Token(TokenType.OP_ASSIGN, 6, "-=");
		assertTrue(t.equals(scanner.nextToken()));

		t = new Token(TokenType.MINUS, 8);
		assertTrue(t.equals(scanner.nextToken()));

		t = new Token(TokenType.ASSIGN, 8);
		assertTrue(t.equals(scanner.nextToken()));

		t = new Token(TokenType.OP_ASSIGN, 8, "*=");
		assertTrue(t.equals(scanner.nextToken()));

		t = new Token(TokenType.SEMI, 10);
		assertTrue(t.equals(scanner.nextToken()));
	}

	@Test
	void peekToken() throws IOException, LexicalException {
		Scanner scanner = new Scanner("src/test/data/testScanner/testGenerale.txt");

		assertEquals(scanner.peekToken().getType(), TokenType.TYINT);
		assertEquals(scanner.nextToken().getType(), TokenType.TYINT);
		assertEquals(scanner.peekToken().getType(), TokenType.ID);
		assertEquals(scanner.peekToken().getType(), TokenType.ID);
		Token t = scanner.nextToken();
		assertEquals(t.getType(), TokenType.ID);
		assertEquals(t.getRow(), 1);
		assertEquals(t.getValue(), "temp");
	}
}
