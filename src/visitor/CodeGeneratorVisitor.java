package visitor;

import java.text.AttributedCharacterIterator.Attribute;

import ast.NodeAssign;
import ast.NodeBinOp;
import ast.NodeCost;
import ast.NodeDecSt;
import ast.NodeDecl;
import ast.NodeDeref;
import ast.NodeId;
import ast.NodePrint;
import ast.NodeProgram;
import exception.CodeGeneratorException;
import symbolTable.Attributes;
import symbolTable.Registers;
import symbolTable.SymbolTable;

public class CodeGeneratorVisitor implements IVisitor {
    private String codeDc;
    private String log;
    private String generatedCode = "";

    public CodeGeneratorVisitor() {
        Registers.init();
        codeDc = "";
        log = "";
    }

    /**
     * Returns the generated code as a string.
     * The code is stripped of leading and trailing whitespace.
     * 
     * @return the generated code
     */
    public String getGeneratedCode() {
        return generatedCode.strip();
    }

    /**
     * Returns the log of the code generation process.
     * The log contains messages about the code generation steps.
     * 
     * @return the log as a string
     */
    public String getLog() {
        return log;
    }

    /**
     * Sets the log message.
     * This method is used to set the log message in case of an error.
     * 
     * @param log the log message to set
     */
    @Override
    public void visit(NodeProgram node) {
        for (NodeDecSt dec : node.getDecSts()) {
            if (log.isBlank() || log.isEmpty()) {
                System.out.println("Visiting declaration: " + dec);
                dec.accept(this);
                if (!codeDc.isBlank()) {
                    System.out.println("Generated code for declaration: " + codeDc);
                    generatedCode += codeDc + " ";
                }
                codeDc = "";
            } else {
                generatedCode = "";
            }
        }
    }

    /**
     * Visits a declaration statement node.
     * This method generates code for variable declarations and initializations.
     * 
     * @param node the NodeDecSt object representing the declaration statement
     */
    @Override
    public void visit(NodeId node) {
        System.out.println("Visiting identifier: " + node.getName());
        codeDc = String.valueOf(SymbolTable.lookup(node.getName()).getRegister());
    }

    /**
     * Visits a declaration node.
     * This method generates code for variable declarations and initializations.
     * If the declaration has an initialization expression, it generates code for
     * that as well.
     * 
     * @param node the NodeDecl object representing the declaration
     */
    @Override
    public void visit(NodeDecl node) {
        Attributes attribute = SymbolTable.lookup(node.getId().getName());
        char register = 0;

        try {
            register = Registers.newRegister();
        } catch (CodeGeneratorException e) {
            log = e.getMessage();
            return;
        }

        attribute.setRegister(register);

        if (node.getInit() != null) {
            node.getInit().accept(this);
            String init = codeDc;

            node.getId().accept(this);
            String id = codeDc;

            codeDc = init + " s" + id;
        }
    }

    /**
     * Visits a declaration statement node.
     * This method generates code for variable declarations and initializations.
     * 
     * @param node the NodeDecSt object representing the declaration statement
     */
    @Override
    public void visit(NodeBinOp node) {
        node.getLeft().accept(this);
        String leftCode = codeDc;

        node.getRight().accept(this);
        String rightCode = codeDc;

        switch (node.getOp()) {
            case PLUS:
                codeDc = leftCode + " " + rightCode + " +";
                break;
            case MINUS:
                codeDc = leftCode + " " + rightCode + " -";
                break;
            case DIV:
                codeDc = leftCode + " " + rightCode + " /";
                break;
            case TIMES:
                codeDc = leftCode + " " + rightCode + " *";
                break;
            case DIV_FLOAT:
                codeDc = leftCode + " " + rightCode + " 5 k / 0 k";
                break;
        }
    }

    /**
     * Visits a dereference node.
     * This method generates code for dereferencing an identifier.
     * It appends the identifier's name to the code.
     * 
     * @param node the NodeDeref object representing the dereference
     */
    @Override
    public void visit(NodeDeref node) {
        System.out.println("Visiting dereference of: " + node.getId().getName());
        node.getId().accept(this);
        codeDc = "l" + codeDc;
    }

    /**
     * Visits a constant node.
     * This method generates code for a constant value.
     * It simply sets the code to the value of the constant.
     * 
     * @param node the NodeCost object representing the constant
     */
    @Override
    public void visit(NodeCost node) {
        System.out.println("Visiting constant: " + node.getValue());
        codeDc = node.getValue();
    }

    /**
     * Visits an assignment node.
     * This method generates code for variable assignments.
     * It first visits the expression to be assigned, then the identifier,
     * and finally combines the two into a single assignment code.
     * 
     * @param node the NodeAssign object representing the assignment
     */
    @Override
    public void visit(NodeAssign node) {
        System.out.println("Visiting assignment to: " + node.getId().getName());
        node.getExpr().accept(this);
        String exprCode = codeDc;

        System.out.println("Visiting identifier for assignment: " + node.getId().getName());
        node.getId().accept(this);
        String idCode = codeDc;

        codeDc = exprCode + " s" + idCode;
    }

    /**
     * Visits a print statement node.
     * This method generates code for printing an identifier.
     * It first visits the identifier to get its code,
     * then appends the print operation to the code.
     * 
     * @param node the NodePrint object representing the print statement
     */
    @Override
    public void visit(NodePrint node) {
        StringBuilder sb = new StringBuilder();
        System.out.println("Visiting print statement for: " + node.getId().getName());
        node.getId().accept(this);

        sb.append("l");
        sb.append(codeDc);
        sb.append(" p P");

        codeDc = sb.toString();
    }
}
