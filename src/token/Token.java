package token;

/**
 * Represents a token in the lexical analysis process.
 */
public class Token {

	/**
	 * Specific row in the source code where the token is located.
	 */
	private int row;
	/**
	 * Type of the token. All token type are inside TokenType class.
	 */
	private TokenType type;
	/**
	 * Textual value of the token. Used for identifier and numbers.
	 */
	private String value;

	// This might be used for tokens that do not have a meaningful textual
	// representation.
	/**
	 * Constructs a token with a specified type and row, but no value.
	 * 
	 * @param type    type of the token
	 * @param row     specific row in the source code where the token is located
	 * @param textual value of the token
	 */
	public Token(TokenType type, int row, String value) {
		this.type = type;
		this.row = row;
		this.value = value;
	}

	// This might be used for tokens that do not have a meaningful textual
	// representation.
	/**
	 * Constructs a token with a specified type and row, but no value.
	 * 
	 * @param type type of the token
	 * @param row  specific row in the source code where the token is located
	 */
	public Token(TokenType type, int row) {
		this.type = type;
		this.row = row;
	}

	/**
	 * Returns the row where the token is located.
	 * 
	 * @return specific row in the source code where the token is located
	 */
	public int getRow() {
		return row;
	}

	/**
	 * Returns the type of the token.
	 * 
	 * @return type of the token
	 */
	public TokenType getType() {
		return type;
	}

	/**
	 * Returns the value of the token.
	 * 
	 * @return value of the token
	 */
	public String getValue() {
		return value;
	}

	@Override
	public boolean equals(Object obj) {
		if(!(obj instanceof Token)) return false;

		Token token = (Token) obj;

		return token.row == row && token.type == type && (token.value == null ?  true : token.value.equals(value));
	}

	/**
	 * Returns a string representation of the token.
	 * <p>
	 * The format of the string is: {@code <type,r:row,value>} if the token has a
	 * value, or {@code <type,r:row>} if the token does not have a value.
	 * </p>
	 */
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();

		builder.append("<").append(type).append(",").append("r:" + row);
		builder = value != null ? builder.append("," + value + ">") : builder.append(">");

		return builder.toString();
	}
}
