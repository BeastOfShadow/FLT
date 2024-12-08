package test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.Test;

import token.Token;
import token.TokenType;

class TestToken {

	@Test
	void testMethodsWithFirstConstructor() {
		Token token = new Token(TokenType.ID, 5, "variable");

		assertEquals(TokenType.ID, token.getType());
		assertTrue(TokenType.ASSIGN != token.getType());

		assertEquals(5, token.getRow());
		assertTrue(6 != token.getRow());

		assertEquals("variable", token.getValue());
		assertTrue("var" != token.getValue());

		assertEquals("<ID,r:5,variable>", token.toString());
		assertTrue("<ID,r:5,var>" != token.toString());
	}

	@Test
	void testMethodsWithoutSecondConstructor() {
		Token token = new Token(TokenType.PLUS, 3);

		assertEquals(TokenType.PLUS, token.getType());
		assertTrue(TokenType.ASSIGN != token.getType());
		
		assertEquals(3, token.getRow());
		assertTrue(6 != token.getRow());

		assertNull(token.getValue());

		assertEquals("<PLUS,r:3>", token.toString());
		assertTrue("<ID,r:5,var>" != token.toString());
	}
}