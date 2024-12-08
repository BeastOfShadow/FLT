package test;

import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import org.junit.jupiter.api.Test;

import scanner.LexicalException;
import scanner.Scanner;
import token.Token;

class TestScanner {

	@Test
	void testScanNumber_Integer() throws IOException, LexicalException, NoSuchMethodException, SecurityException, IllegalAccessException, InvocationTargetException {
		Scanner scanner = new Scanner("C:\\Users\\simon\\Documents\\1.UPO\\1.Programmazione\\FLTGianna\\ProgettoFlt\\src\\test\\data\\testScanner\\erroriNumbers.txt");
		Token token = scanner.nextToken();
		/*Method method = Scanner.class.getDeclaredMethod("scanNumber", Scanner.class);
		method.setAccessible(true);
		Token token = (Token) method.invoke(erroriNumbers);
		assertEquals("q", token);*/
		
	}
}
