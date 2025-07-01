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

    public Parser(Scanner scanner) {
        this.scanner = scanner;
    }

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

    public NodeProgram parse() throws SyntacticException {
        System.out.println("Program started. Creating parse tree:");

        return parsePrg();
    }

    private NodeProgram parsePrg() throws SyntacticException {
        Token token;

        try {
            token = scanner.peekToken();
        } catch (LexicalException e) {
            throw new SyntacticException(errorCatched, e);
        }

        switch (token.getType()) {
            case TYFLOAT, TYINT, ID, PRINT, EOF:
                ArrayList<NodeDecSt> decSt = parseDSs();
                match(TokenType.EOF);
                return new NodeProgram(decSt);
            default:
                throw new SyntacticException("ERROR: token " + token.getType() + " at row " + token.getRow()
                        + " is not a start of program.");
        }
    }

    private ArrayList<NodeDecSt> parseDSs() throws SyntacticException {
        Token token;

        try {
            token = scanner.peekToken();
        } catch (LexicalException e) {
            throw new SyntacticException(errorCatched, e);
        }

        switch (token.getType()) {
            case TYFLOAT, TYINT:
                NodeDecl decl = parseDcl();
                ArrayList<NodeDecSt> list = parseDSs();
                list.add(0, decl);
                return list;
            case ID, PRINT:
                NodeStm stm = parseStm();
                ArrayList<NodeDecSt> list1 = parseDSs();
                list1.add(0, stm);
                return list1;
            case EOF:
                return new ArrayList<>();
            default:
                throw new SyntacticException("ERROR: token " + token.getType() + " at row " + token.getRow()
                        + " is not a start of program.");
        }
    }

    private NodeDecl parseDcl() throws SyntacticException {
        Token token;
        LangType ty;

        try {
            token = scanner.peekToken();
        } catch (LexicalException e) {
            throw new SyntacticException(errorCatched, e);
        }

        NodeExpr expr;

        switch (token.getType()) {
            case TYFLOAT, TYINT:
                ty = parseTy();
                token = match(TokenType.ID);
                expr = parseDclP();
                return new NodeDecl(new NodeId(token.getValue()), ty, expr);
            default:
                throw new SyntacticException(
                        String.format("Unexpected token \'%s\' at line %d", token.getValue(), token.getRow()));
        }
    }

    private LangType parseTy() throws SyntacticException {
        Token token;

        try {
            token = scanner.peekToken();
        } catch (LexicalException e) {
            throw new SyntacticException(errorCatched, e);
        }

        switch (token.getType()) {
            case TYFLOAT:
                match(TokenType.TYFLOAT);
                return LangType.FLOAT;
            case TYINT:
                match(TokenType.TYINT);
                return LangType.INT;
            default:
                throw new SyntacticException(
                        String.format("Unexpected token \'%s\' at line %d", token.getValue(), token.getRow()));
        }

    }

    private NodeExpr parseDclP() throws SyntacticException {
        Token token;

        try {
            token = scanner.peekToken();
        } catch (LexicalException e) {
            throw new SyntacticException(errorCatched, e);
        }

        switch (token.getType()) {
            case SEMI:
                match(TokenType.SEMI);
                return null;
            case ASSIGN:
                match(TokenType.ASSIGN);
                NodeExpr node = parseExp();
                match(TokenType.SEMI);
                return node;
            default:
                throw new SyntacticException(
                        String.format("Unexpected token \'%s\' at line %d", token.getValue(), token.getRow()));
        }
    }

    private NodeStm parseStm() throws SyntacticException {
        Token token;

        try {
            token = scanner.peekToken();
        } catch (LexicalException e) {
            throw new SyntacticException("Error catched", e);
        }

        switch (token.getType()) {
            case ID:
                match(TokenType.ID);
                parseOp();
                NodeExpr expr = parseExp();
                match(TokenType.SEMI);
                return new NodeAssign(new NodeId(token.getValue()), expr);
            case PRINT:
                match(TokenType.PRINT);
                token = match(TokenType.ID);
                match(TokenType.SEMI);
                return new NodePrint(new NodeId(token.getValue()));
            default:
                throw new SyntacticException(
                        String.format("Unexpected token \'%s\' at line %d", token.getType(), token.getRow()));
        }
    }

    private NodeExpr parseExp() throws SyntacticException {
        Token token;

        try {
            token = scanner.peekToken();
        } catch (LexicalException e) {
            throw new SyntacticException("Error catched", e);
        }

        switch (token.getType()) {
            case ID, FLOAT, INT:
                NodeExpr valExpr = parseTr();
                return parseExpP(valExpr);
            default:
                throw new SyntacticException(
                        String.format("Unexpected token \'%s\' at line %d", token.getType(), token.getRow()));
        }
    }

    private NodeExpr parseExpP(NodeExpr left) throws SyntacticException {
        Token token;
        NodeExpr nodeLeft;

        try {
            token = scanner.peekToken();
        } catch (LexicalException e) {
            throw new SyntacticException("Error catched", e);
        }

        switch (token.getType()) {
            case PLUS:
                match(TokenType.PLUS);
                nodeLeft = parseTr();
                return parseExpP(nodeLeft);
            case MINUS:
                match(TokenType.MINUS);
                nodeLeft = parseTr();
                return parseExpP(nodeLeft);
            case SEMI:
                return null;
            default:
                throw new SyntacticException(
                        String.format("Unexpected token \'%s\' at line %d", token.getType(), token.getRow()));
        }
    }

    private NodeExpr parseTr() throws SyntacticException {
        Token token;

        try {
            token = scanner.peekToken();
        } catch (LexicalException e) {
            throw new SyntacticException("Error catched", e);
        }

        switch (token.getType()) {
            case ID, FLOAT, INT:
                NodeExpr valExpr = parseVal();
                return parseTrp(valExpr);
            default:
                throw new SyntacticException(
                        String.format("Unexpected token \'%s\' at line %d", token.getType(), token.getRow()));
        }
    }

    private NodeExpr parseTrp(NodeExpr left) throws SyntacticException {
        Token token;

        try {
            token = scanner.peekToken();
        } catch (LexicalException e) {
            throw new SyntacticException("Error catched", e);
        }

        NodeExpr nodeLeft;

        switch (token.getType()) {
            case TIMES:
                match(TokenType.TIMES);
                nodeLeft = parseVal();
                return parseTrp(nodeLeft);
            case DIVIDE:
                match(TokenType.DIVIDE);
                nodeLeft = parseVal();
                return parseTrp(nodeLeft);
            case MINUS, PLUS, SEMI:
                return null;
            default:
                throw new SyntacticException(
                        String.format("Unexpected token \'%s\' at line %d", token.getType(), token.getRow()));
        }
    }

    private NodeExpr parseVal() throws SyntacticException {
        Token token;

        try {
            token = scanner.peekToken();
        } catch (LexicalException e) {
            throw new SyntacticException("Error catched", e);
        }

        switch (token.getType()) {
            case INT:
                token = match(token.getType());
                return new NodeCost(token.getValue(), LangType.INT);
            case FLOAT:
                token = match(token.getType());
                return new NodeCost(token.getValue(), LangType.FLOAT);
            case ID:
                token = match(token.getType());
                return new NodeDeref(new NodeId(token.getValue()));
            default:
                throw new SyntacticException(
                        String.format("Unexpected token \'%s\' at line %d", token.getType(), token.getRow()));
        }
    }

    private Token parseOp() throws SyntacticException {
        Token token;

        try {
            token = scanner.peekToken();
        } catch (LexicalException e) {
            throw new SyntacticException("Error catched", e);
        }

        switch (token.getType()) {
            case ASSIGN:
                return match(TokenType.ASSIGN);
            case OP_ASSIGN:
                return match(TokenType.OP_ASSIGN);
            default:
                throw new SyntacticException(
                        String.format("Unexpected token \'%s\' at line %d", token.getType(), token.getRow()));
        }
    }
}
