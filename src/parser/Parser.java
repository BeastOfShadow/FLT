package parser;

import java.io.IOException;
import java.util.ArrayList;

import org.junit.platform.engine.support.hierarchical.Node;

import ast.LangOper;
import ast.LangType;
import ast.NodeAssign;
import ast.NodeBinOp;
import ast.NodeCost;
import ast.NodeDecSt;
import ast.NodeDecl;
import ast.NodeDeref;
import ast.NodeExpr;
import ast.NodeId;
import ast.NodePrint;
import ast.NodeProgram;
import ast.NodeStm;
import exception.LexicalException;
import exception.SyntacticException;
import scanner.Scanner;
import token.Token;
import token.TokenType;

public class Parser {
    private Scanner scanner;
    private String errorCatched = "Error detected";

    /**
     * Constructor for the Parser class.
     * Initializes the parser with a scanner to read tokens.
     * 
     * @param scanner the scanner to read tokens from
     */
    public Parser(Scanner scanner) {
        this.scanner = scanner;
    }

    /**
     * Matches the next token with the expected type.
     * If the token matches, it consumes it and returns it.
     * If it does not match, it throws a SyntacticException.
     * 
     * @param type the expected token type
     * @return the matched token
     * @throws SyntacticException if the token does not match the expected type
     */
    private Token match(TokenType type) throws SyntacticException {
        Token token;

        try {
            token = scanner.peekToken();

            System.out.println("Mathcing " + token.getType() + " with " + type);
        } catch (LexicalException e) {
            throw new SyntacticException(errorCatched, e);
        }

        if (type == token.getType())
            try {
                return scanner.nextToken();
            } catch (LexicalException e) {
                throw new SyntacticException(errorCatched, e);
            }

        throw new SyntacticException(
                "ERROR: expected token: " + type + " but was: " + token.getType() + " at line: " + token.getRow());
    }

    /**
     * Parses the input and creates a parse tree.
     * The parse tree is represented by a NodeProgram object.
     * This method starts the parsing process and returns the root of the parse
     * tree.
     * 
     * @return the root of the parse tree (NodeProgram)
     * @throws SyntacticException if there is a syntax error in the input
     */
    public NodeProgram parse() throws SyntacticException {
        System.out.println("Program started. Creating parse tree:");

        return parsePrg();
    }

    /**
     * Parses the program starting from the root production rule.
     * The root production rule is Prg -> DSs $.
     * 
     * @return the root of the parse tree (NodeProgram)
     * @throws SyntacticException if there is a syntax error in the input
     */
    private NodeProgram parsePrg() throws SyntacticException {
        Token token;

        try {
            token = scanner.peekToken();
        } catch (LexicalException e) {
            throw new SyntacticException(errorCatched, e);
        }

        // Prg -> DSs $
        switch (token.getType()) {
            case TYFLOAT, TYINT, ID, PRINT, EOF:
                System.out.println("Parsing program...");
                ArrayList<NodeDecSt> decSt = parseDSs();
                match(TokenType.EOF);
                return new NodeProgram(decSt);
            default:
                throw new SyntacticException("ERROR: token " + token.getType() + " at row " + token.getRow()
                        + " is not a start of program.");
        }
    }

    /**
     * Parses the declarations and statements in the program.
     * The production rule is DSs -> Dcl DSs | Stm DSs | ε.
     * 
     * @return a list of NodeDecSt objects representing declarations and statements
     * @throws SyntacticException if there is a syntax error in the input
     */
    private ArrayList<NodeDecSt> parseDSs() throws SyntacticException {
        Token token;

        try {
            token = scanner.peekToken();
        } catch (LexicalException e) {
            throw new SyntacticException(errorCatched, e);
        }

        // DSs -> Dcl DSs | Stm DSs | ε
        switch (token.getType()) {
            // DSs -> Dcl DSs
            case TYFLOAT, TYINT:
                System.out.println("Parsing declarations...");
                NodeDecl decl = parseDcl();
                ArrayList<NodeDecSt> list = parseDSs();
                list.add(0, decl);
                return list;
            // DSs -> Stm DSs
            case ID, PRINT:
                System.out.println("Parsing statements...");
                NodeStm stm = parseStm();
                ArrayList<NodeDecSt> list1 = parseDSs();
                list1.add(0, stm);
                return list1;
            // DSs -> ε
            case EOF:
                System.out.println("End of program reached.");
                return new ArrayList<>();
            default:
                throw new SyntacticException("ERROR: token " + token.getType() + " at row " + token.getRow()
                        + " is not a start of program.");
        }
    }

    /**
     * Parses a declaration.
     * The production rule is Dcl -> Ty ID DclP.
     * 
     * @return a NodeDecl object representing the declaration
     * @throws SyntacticException if there is a syntax error in the input
     */
    private NodeDecl parseDcl() throws SyntacticException {
        Token token;
        LangType ty;

        try {
            token = scanner.peekToken();
        } catch (LexicalException e) {
            throw new SyntacticException(errorCatched, e);
        }

        NodeExpr expr;

        // Dcl -> Ty ID DclP
        switch (token.getType()) {
            case TYFLOAT, TYINT:
                ty = parseTy();
                token = match(TokenType.ID);
                System.out.println("Parsing declaration: " + token.getValue() + " of type " + ty);
                expr = parseDclP();
                return new NodeDecl(new NodeId(token.getValue()), ty, expr);
            default:
                throw new SyntacticException(
                        String.format("Unexpected token \'%s\' at line %d", token.getValue(), token.getRow()));
        }
    }

    /**
     * Parses a type.
     * The production rule is Ty -> TYFLOAT | TYINT.
     * 
     * @return the LangType of the parsed type
     * @throws SyntacticException if there is a syntax error in the input
     */
    private LangType parseTy() throws SyntacticException {
        Token token;

        try {
            token = scanner.peekToken();
        } catch (LexicalException e) {
            throw new SyntacticException(errorCatched, e);
        }

        // Ty -> TYFLOAT | TYINT
        switch (token.getType()) {
            // Ty -> TYFLOAT
            case TYFLOAT:
                match(TokenType.TYFLOAT);
                System.out.println("Parsing type: FLOAT");
                return LangType.FLOAT;
            // Ty -> TYINT
            case TYINT:
                match(TokenType.TYINT);
                System.out.println("Parsing type: INT");
                return LangType.INT;
            default:
                throw new SyntacticException(
                        String.format("Unexpected token \'%s\' at line %d", token.getValue(), token.getRow()));
        }

    }

    /**
     * Parses the declaration part of a declaration.
     * The production rule is DclP -> SEMI | ASSIGN Exp SEMI.
     * 
     * @return a NodeExpr object representing the expression in the declaration, or
     *         null if there is no expression
     * @throws SyntacticException if there is a syntax error in the input
     */
    private NodeExpr parseDclP() throws SyntacticException {
        Token token;

        try {
            token = scanner.peekToken();
        } catch (LexicalException e) {
            throw new SyntacticException(errorCatched, e);
        }

        // DclP -> SEMI | ASSIGN Exp SEMI
        switch (token.getType()) {
            // DclP -> SEMI
            case SEMI:
                match(TokenType.SEMI);
                System.out.println("Declaration without initialization.");
                return null;
            // DclP -> ASSIGN Exp SEMI
            case ASSIGN:
                match(TokenType.ASSIGN);
                NodeExpr node = parseExp();
                match(TokenType.SEMI);
                System.out.println("Declaration with initialization: " + node);
                return node;
            default:
                throw new SyntacticException(
                        String.format("Unexpected token \'%s\' at line %d", token.getValue(), token.getRow()));
        }
    }

    /**
     * Parses a statement.
     * The production rule is Stm -> ID Op Exp SEMI | PRINT ID SEMI.
     * 
     * @return a NodeStm object representing the parsed statement
     * @throws SyntacticException if there is a syntax error in the input
     */
    private NodeStm parseStm() throws SyntacticException {
        Token token;

        try {
            token = scanner.peekToken();
        } catch (LexicalException e) {
            throw new SyntacticException("Error catched", e);
        }

        // Stm -> ID Op Exp SEMI | PRINT ID SEMI
        switch (token.getType()) {
            // Stm -> ID Op Exp SEMI
            case ID:
                NodeId id = new NodeId(match(TokenType.ID).getValue());
                System.out.println("Parsing statement: " + token.getValue());
                LangOper op = parseOp();
                NodeExpr expr = parseExp();
                match(TokenType.SEMI);
                System.out.println("Parsed assignment operation: " + token.getValue() + " with operator: " + op
                        + " and expression: " + expr);

                if (op != null) {
                    NodeExpr left = new NodeDeref(id);
                    NodeBinOp binOp = new NodeBinOp(op, left, expr);
                    return new NodeAssign(new NodeId(token.getValue()), binOp);
                }

                return new NodeAssign(id, expr);
            // Stm -> PRINT ID SEMI
            case PRINT:
                match(TokenType.PRINT);
                token = match(TokenType.ID);
                match(TokenType.SEMI);
                System.out.println("Parsing print statement: " + token.getValue());
                return new NodePrint(new NodeId(token.getValue()));
            default:
                throw new SyntacticException(
                        String.format("Unexpected token \'%s\' at line %d", token.getType(), token.getRow()));
        }
    }

    /**
     * Parses an expression.
     * The production rule is Exp -> Tr ExpP.
     * 
     * @return a NodeExpr object representing the parsed expression
     * @throws SyntacticException if there is a syntax error in the input
     */
    private NodeExpr parseExp() throws SyntacticException {
        Token token;

        try {
            token = scanner.peekToken();
        } catch (LexicalException e) {
            throw new SyntacticException("Error catched", e);
        }

        // Exp -> Tr ExpP
        switch (token.getType()) {
            case ID, FLOAT, INT:
                System.out.println("Parsing expression starting with: " + token.getType());
                NodeExpr valExpr = parseTr();
                System.out.println("Parsed value expression: " + valExpr);
                return parseExpP(valExpr);
            default:
                throw new SyntacticException(
                        String.format("Unexpected token \'%s\' at line %d", token.getType(), token.getRow()));
        }
    }

    /**
     * Parses the continuation of an expression.
     * The production rule is ExpP -> PLUS Tr ExpP | MINUS Tr ExpP | SEMI.
     * 
     * @param left the left operand of the expression
     * @return a NodeExpr object representing the continuation of the expression
     * @throws SyntacticException if there is a syntax error in the input
     */
    private NodeExpr parseExpP(NodeExpr left) throws SyntacticException {
        Token token;
        NodeExpr nodeLeft;

        try {
            token = scanner.peekToken();
        } catch (LexicalException e) {
            throw new SyntacticException("Error catched", e);
        }

        // ExpP -> PLUS Tr ExpP | MINUS Tr ExpP | SEMI
        switch (token.getType()) {
            // ExpP -> PLUS Tr ExpP
            case PLUS:
                match(TokenType.PLUS);
                nodeLeft = parseTr();
                System.out.println(
                        "Parsed subtraction operation with left operand: " + left + " and right operand: " + nodeLeft);
                return new NodeBinOp(LangOper.PLUS, left, parseExpP(nodeLeft));
            // ExpP -> MINUS Tr ExpP
            case MINUS:
                match(TokenType.MINUS);
                nodeLeft = parseTr();
                System.out.println(
                        "Parsed subtraction operation with left operand: " + left + " and right operand: " + nodeLeft);
                return new NodeBinOp(LangOper.MINUS, left, parseExpP(nodeLeft));
            // ExpP -> SEMI
            case SEMI:
                System.out.println("End of expression reached with left operand: " + left);
                return left;
            default:
                throw new SyntacticException(
                        String.format("Unexpected token \'%s\' at line %d", token.getType(), token.getRow()));
        }
    }

    /**
     * Parses a term.
     * The production rule is Tr -> Val Trp.
     * 
     * @return a NodeExpr object representing the parsed term
     * @throws SyntacticException if there is a syntax error in the input
     */
    private NodeExpr parseTr() throws SyntacticException {
        Token token;

        try {
            token = scanner.peekToken();
        } catch (LexicalException e) {
            throw new SyntacticException("Error catched", e);
        }

        // Tr -> Val Trp
        switch (token.getType()) {
            case ID, FLOAT, INT:
                NodeExpr valExpr = parseVal();
                System.out.println("Parsed value expression: " + valExpr);
                return parseTrp(valExpr);
            default:
                throw new SyntacticException(
                        String.format("Unexpected token \'%s\' at line %d", token.getType(), token.getRow()));
        }
    }

    /**
     * Parses the continuation of a term.
     * The production rule is Trp -> TIMES Val Trp | DIVIDE Val Trp | MINUS | PLUS |
     * SEMI.
     * 
     * @param left the left operand of the term
     * @return a NodeExpr object representing the continuation of the term
     * @throws SyntacticException if there is a syntax error in the input
     */
    private NodeExpr parseTrp(NodeExpr left) throws SyntacticException {
        Token token;

        try {
            token = scanner.peekToken();
        } catch (LexicalException e) {
            throw new SyntacticException("Error catched", e);
        }

        NodeExpr nodeLeft;

        // Trp -> TIMES Val Trp | DIVIDE Val Trp | MINUS | PLUS | SEMI
        switch (token.getType()) {
            // Trp -> TIMES Val Trp
            case TIMES:
                match(TokenType.TIMES);
                nodeLeft = parseVal();
                System.out.println("Parsed multiplication operation with left operand: " + left + " and right operand: "
                        + nodeLeft);
                return new NodeBinOp(LangOper.TIMES, left, parseTrp(nodeLeft));
            // Trp -> DIVIDE Val Trp
            case DIVIDE:
                match(TokenType.DIVIDE);
                nodeLeft = parseVal();
                System.out.println(
                        "Parsed division operation with left operand: " + left + " and right operand: " + nodeLeft);
                return new NodeBinOp(LangOper.DIV, left, parseTrp(nodeLeft));
            // Trp -> MINUS | PLUS | SEMI
            case MINUS, PLUS, SEMI:
                System.out.println("End of term reached with left operand: " + left);
                return left;
            default:
                throw new SyntacticException(
                        String.format("Unexpected token \'%s\' at line %d", token.getType(), token.getRow()));
        }
    }

    /**
     * Parses a value.
     * The production rule is Val -> INT | FLOAT | ID.
     * 
     * @return a NodeExpr object representing the parsed value
     * @throws SyntacticException if there is a syntax error in the input
     */
    private NodeExpr parseVal() throws SyntacticException {
        Token token;

        try {
            token = scanner.peekToken();
        } catch (LexicalException e) {
            throw new SyntacticException("Error catched", e);
        }

        // Val -> INT | FLOAT | ID
        switch (token.getType()) {
            // Val -> INT
            case INT:
                token = match(token.getType());
                System.out.println("Parsing integer value: " + token.getValue());
                return new NodeCost(token.getValue(), LangType.INT);
            // Val -> FLOAT
            case FLOAT:
                token = match(token.getType());
                System.out.println("Parsing float value: " + token.getValue());
                return new NodeCost(token.getValue(), LangType.FLOAT);
            // Val -> ID
            case ID:
                token = match(token.getType());
                System.out.println("Parsing identifier: " + token.getValue());
                return new NodeDeref(new NodeId(token.getValue()));
            default:
                throw new SyntacticException(
                        String.format("Unexpected token \'%s\' at line %d", token.getType(), token.getRow()));
        }
    }

    /**
     * Parses an operation assignment.
     * The production rule is Op -> ASSIGN | OP_ASSIGN.
     * 
     * @return a LangOper object representing the operation, or null if there is no
     *         operation
     * @throws SyntacticException if there is a syntax error in the input
     */
    private LangOper parseOp() throws SyntacticException {
        Token token;

        try {
            token = scanner.peekToken();
        } catch (LexicalException e) {
            throw new SyntacticException("Error catched", e);
        }

        // Op -> ASSIGN | OP_ASSIGN
        switch (token.getType()) {
            // Op -> ASSIGN
            case ASSIGN:
                match(TokenType.ASSIGN);
                return null;
            // Op -> OP_ASSIGN
            case OP_ASSIGN:
                match(TokenType.OP_ASSIGN);
                System.out.println("Parsing operation assignment: " + token.getValue());
                switch (token.getValue()) {
                    case "+=":
                        return LangOper.PLUS;
                    case "-=":
                        return LangOper.MINUS;
                    case "*=":
                        return LangOper.TIMES;
                    case "/=":
                        return LangOper.DIV;
                    default:
                        throw new SyntacticException(
                                String.format("Unexpected token \'%s\' at line %d", token.getType(), token.getRow()));
                }
        }

        return null;
    }
}
