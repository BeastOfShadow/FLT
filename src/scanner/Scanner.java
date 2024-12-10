package scanner;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PushbackReader;
import java.util.Map;
import java.util.Set;

import token.*;

public class Scanner {
	final char EOF = (char) -1;
	private int row;
	private PushbackReader buffer;

	/**
	 * Skip character set (includes EOF) and initialization
	 */
	private Set<Character> skpChars;

	/**
	 * Alphabet characters.
	 */
	private Set<Character> letters;

	/**
	 * Numbers.
	 */
	private Set<Character> digits;

	/**
	 * Mapping characters: '+', '-', '*', '/' and corresponding TokenType.
	 */
	private Map<Character, TokenType> operTkType;

	/**
	 * Mapping characters: '=', ';' and corresponding TokenType.
	 */
	private Map<Character, TokenType> delimTkType;

	// private Map<String, TokenType> opAssignTkType;

	/**
	 * Mapping strings: "print", "float", "int" and corresponding TokenType.
	 */
	private Map<String, TokenType> keyWordsTkType;

	private Token nextTk;

	/**
	 * Initializes token types and sets up file reading.
	 * 
	 * @param fileName name of file to read
	 * @throws FileNotFoundException if the file is not found
	 */
	public Scanner(String fileName) throws FileNotFoundException {
		this.buffer = new PushbackReader(new FileReader(fileName));
		row = 1;

		skpChars = Set.of(' ', '\n', '\t', '\r', EOF);
		letters = Set.of('a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's',
				't', 'u', 'v', 'w', 'x', 'y', 'z');
		digits = Set.of('0', '1', '2', '3', '4', '5', '6', '7', '8', '9');

		operTkType = Map.of('+', TokenType.PLUS, '-', TokenType.MINUS, '*', TokenType.TIMES, '/', TokenType.DIVIDE);
		delimTkType = Map.of('=', TokenType.ASSIGN, ';', TokenType.SEMI);
		keyWordsTkType = Map.of("print", TokenType.PRINT, "float", TokenType.TYFLOAT, "int", TokenType.TYINT);
	}

	public Token peekToken() throws IOException, LexicalException {
		if(nextTk == null) {
			nextTk = nextToken();	
		}

		return nextTk;
	}

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
	 * @return The next valid token
	 * @throws IOException      if an I/O error occurs while reading the file
	 * @throws LexicalException if a lexical error is encountered (invalid
	 *                          character)
	 */
	public Token nextToken() throws IOException, LexicalException {
		// nextChar contiene il prossimo carattere dell'input (non consumato).
		char nextChar; // Catturate l'eccezione IOException e
		// ritornate una LexicalException che la contiene

		if(nextTk != null) {
			Token aus = nextTk;

			nextTk = null;
			return aus;
		}

		// Avanza nel buffer leggendo i carattere in skipChars
		while (skpChars.contains(peekChar())) {

			// incrementando riga se leggi '\n'.
			if (peekChar() == '\n')
				row++;

			// Se raggiungi la fine del file ritorna il Token EOF
			if (peekChar() == EOF) {
				readChar();
				Token token = new Token(TokenType.EOF, row);
				return token;
			}

			readChar();
		}

		while (peekChar() != '\n') {
			/*
			 * Se nextChar e' in letters
			 * return scanId()
			 * che deve generare o un Token ID o parola chiave
			 */
			if (letters.contains(peekChar()))
				return scanId();

			/*
			 * Se nextChar e' o in operators oppure delimitatore
			 * ritorna il Token associato con l'operatore o il delimitatore
			 * Attenzione agli operatori di assegnamento!
			 */
			if (operTkType.containsKey(peekChar())) {
				return scanOperator();
			}

			// Se nextChar e' ; o =
			// ritorna il Token associato
			if (delimTkType.containsKey(peekChar())) {
				return new Token(delimTkType.get(readChar()), row);
			}

			// Se nextChar e' in numbers
			// return scanNumber()
			// che legge sia un intero che un float e ritorna il Token INUM o FNUM
			// i caratteri che leggete devono essere accumulati in una stringa
			// che verra' assegnata al campo valore del Token
			if (digits.contains(peekChar()))
				return scanNumber();

			char error = readChar();

			throw new LexicalException("ERROR: illegal character. Row: " + row + ", character: " + error);
		}

		// Altrimenti il carattere NON E' UN CARATTERE LEGALE sollevate una
		// eccezione lessicale dicendo la riga e il carattere che la hanno
		// provocata.
		throw new LexicalException("ERROR: illegal character. Row: " + row + ", character: " + peekChar());
	}

	/**
	 * Reads and returns an identifier (ID) or keyword token.
	 * 
	 * An identifier starts with a letter and may contain letters and digits.
	 * If the text matches a keyword defined in `keyWordsTkType`,
	 * it returns the corresponding keyword token.
	 * 
	 * @return a token of type `ID` or a keyword (`PRINT`, `TYFLOAT`, `TYINT`)
	 * @throws IOException      if an I/O error occurs while reading the file
	 * @throws LexicalException if the identifier is invalid (does not start with a
	 *                          letter)
	 */
	private Token scanId() throws IOException, LexicalException {
		StringBuilder builder = new StringBuilder();

		if (letters.contains(peekChar())) {
			builder.append(readChar());

			while ((letters.contains(peekChar()) || digits.contains(peekChar())) && !skpChars.contains(peekChar())) {
				builder.append(readChar());
			}

			if (keyWordsTkType.containsKey(builder.toString())) {
				return new Token(keyWordsTkType.get(builder.toString()), row);
			}

			return new Token(TokenType.ID, row, builder.toString());
		}

		throw new LexicalException("ERROR: invalid format for id. Must start with a character. Result=" + builder);
	}

	/**
	 * Reads and returns an operator token.
	 * 
	 * This method handles basic operators such as '+', '-', '*', '/' and assignment
	 * operators like '=' and '=='.
	 * 
	 * @return a token corresponding to the operator read
	 * @throws IOException if an I/O error occurs while reading the file
	 */
	private Token scanOperator() throws IOException {
		StringBuilder builder = new StringBuilder();

		builder.append(readChar());

		return peekChar() == '=' ? new Token(TokenType.OP_ASSIGN, row, builder.append(readChar()).toString())
				: new Token(operTkType.get(builder.toString().charAt(0)), row);
	}

	/**
	 * Reads and returns a numeric token (integer or float).
	 * 
	 * This method handles both integers and floating-point numbers, ensuring that:
	 * - Floating-point numbers do not have more than 5 decimal places.
	 * - Floating-point numbers have at least one digit after the decimal point.
	 * 
	 * If an invalid format is detected, a lexical exception is thrown.
	 * 
	 * @return a token of type `INT` for integers or `FLOAT` for floating-point
	 *         numbers
	 * @throws IOException      if an I/O error occurs while reading the file
	 * @throws LexicalException if the number format is invalid
	 */
	private Token scanNumber() throws IOException, LexicalException {
		StringBuilder builder = new StringBuilder();
		int countFloatingPoint = 0;
		boolean lexicalError = false;

		while (digits.contains(peekChar())) {
			builder.append(readChar());
		}

		if (peekChar() == '.') {
			builder.append(readChar());

			while (digits.contains(peekChar())) {
				builder.append(readChar());
				countFloatingPoint++;
			}

			if (countFloatingPoint == 0) {
				throw new LexicalException("ERROR: invalid float format. No digits after decimal point. Result="
						+ builder + ", row: " + row);
			}

			if (countFloatingPoint > 5) {
				throw new LexicalException(
						"ERROR: float have more than 5 decimals number. Result=" + builder + ", row: " + row);
			}
		}

		while (letters.contains(peekChar()) || peekChar() == '.' || digits.contains(peekChar())) {
			readChar();
			lexicalError = true;
		}

		if (lexicalError) {
			throw new LexicalException("ERROR: invalid format. Result=" + builder + ", row: " + row);
		}

		return builder.toString().contains(".") ? new Token(TokenType.FLOAT, row, builder.toString())
				: new Token(TokenType.INT, row, builder.toString());

		/*
		 * if(skpChars.contains(peekChar())) {
		 * return builder.toString().contains(".") ? new Token(TokenType.FLOAT, row,
		 * builder.toString()) : new Token(TokenType.INT, row, builder.toString());
		 * }
		 * 
		 * while (!skpChars.contains(peekChar())) {
		 * builder.append(readChar());
		 * }
		 * 
		 * throw new LexicalException("ERROR: token is NaN. Result=" + builder);
		 */
	}

	/**
	 * Reads char and it consumes it.
	 * 
	 * @return read char
	 * @throws IOException if I/O error exception while reading file
	 */
	private char readChar() throws IOException {
		return ((char) this.buffer.read());
	}

	/**
	 * Reads char without consuming it.
	 * 
	 * @return read char
	 * @throws IOException if I/O error exception while reading file
	 */
	private char peekChar() throws IOException {
		char c = (char) buffer.read();
		buffer.unread(c);
		return c;
	}
}
