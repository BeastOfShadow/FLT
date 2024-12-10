package parser;

import java.io.IOException;

import exception.SyntacticException;
import scanner.LexicalException;
import scanner.Scanner;
import token.Token;
import token.TokenType;

public class Parser {
    private Scanner scanner;

    public Parser(Scanner scanner) {
        this.scanner = scanner;
    }

    private void parseNT() {

    }

    private Token match(TokenType type) throws LexicalException, SyntacticException, IOException {
        Token token = scanner.peekToken();

        if(type.equals(token.getType())) return scanner.nextToken();

        throw new SyntacticException("ERROR: expected token: " + type + " but was: " + token.getType() + " at line: " + token.getRow());
    }

    public void parse() {
        this.parsePrg();
    }

    private void parsePrg() throws IOException, LexicalException {
        Token token = scanner.peekToken();

        switch (token.getType()) {
            case TYFLOAT, TYINT, ID, PRINT, EOF:
                parseDSs();
                break;
        
            default:
                break;
        }
    }

    private void parseDSs() {

    }

    private void parseDcl() {

    }

    private void parseTy() {

    }

    private void parseDclP() {

    }

    private void parseStm() {

    }

    private void parseExp() {

    }
    
    private void parseExpP() {

    }

    private void parseTr() {

    }

    private void parseTrp() {

    }

    private void parseVal() {

    }
}
