package scanner;

import java.io.IOException;

import exception.LexicalException;
import token.Token;

public interface IScanner {
    /**
	 * Peeks and returns the next valid token from the input file without consuming
	 * characters.<br>
	 * 
	 * @return the next valid token
	 * @throws IOException      if an I/O error occurs while reading the file
	 * @throws LexicalException if a lexical error is encountered (invalid
	 *                          character)
	 */
    Token peekToken() throws LexicalException;

    /**
	 * Reads and returns the next valid token from the input file.<br>
	 * 
	 * This method skips all whitespace characters defined in `skpChars` and handles
	 * the following types of tokens:<br>
	 * - Identifiers and keywords (via `scanId()`);<br>
	 * - Operators (via `scanOperator()`);<br>
	 * - Delimiters like '=' and ';';<br>
	 * - Integer and floating-point numbers (via `scanNumber()`).
	 * 
	 * @return the next valid token
	 * @throws IOException      if an I/O error occurs while reading the file
	 * @throws LexicalException if a lexical error is encountered (invalid
	 *                          character)
	 */
	Token nextToken() throws LexicalException;
}
