package token;

/**
 * Specificate all token type.
 */
public enum TokenType {
	
	/**
	 * Integer type value.
	 */
	INT, 
	
	/**
	 * Float type value (max 5 value after floating point).
	 */
	FLOAT,
	
	/**
	 * Representing an identifier (variable name).<br>
	 * For example: [a-z][a-z0-9]*<br>
	 * This means that the identifier must start with a letter (a-z).<br>
	 * After the initial letter, it can include any combination of letters (a-z) or digits (0-9).<br>
	 * The asterisk (*) indicates zero or more occurrences of the preceding character set.
	 */
	ID,
	
	/**
	 * Representing {@code int} keyword.
	 */
	TYINT,
	
	/**
	 * Representing {@code float} keyword.
	 */
	TYFLOAT,
	
	/**
	 * Representing {@code print} keyword.
	 */
	PRINT,
	
	/**
	 * Assign token to match "+= | -= | *= | /=".
	 */
	OP_ASSIGN,
	
	/**
	 * Assign token to match "=".
	 */
	ASSIGN,
	
	/**
	 * Assign token to match "+".
	 */
	PLUS,
	
	/**
	 * Assign token to match "-".
	 */
	MINUS,
	
	/**
	 * Assign token to match "*".
	 */
	TIMES,
	
	/**
	 * Assign token to match "/".
	 */
	DIVIDE,
	
	/**
	 * Assign token to match ";".
	 */
	SEMI,
	
	/**
	 * Assign token to match end of file.
	 */
	EOF;
}
