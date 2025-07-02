package visitor;

import ast.LangOper;
import ast.NodeAST;
import ast.NodeAssign;
import ast.NodeBinOp;
import ast.NodeCost;
import ast.NodeDecSt;
import ast.NodeDecl;
import ast.NodeDeref;
import ast.NodeId;
import ast.NodePrint;
import ast.NodeProgram;
import ast.TypeDescriptor;
import ast.TypeTd;
import symbolTable.Attributes;
import symbolTable.SymbolTable;

public class TypeCheckingVisitor implements IVisitor {
    private TypeDescriptor resType;
    private int row;
    private String errorMessage;

    // Error message to be returned in case of semantic errors
    public TypeCheckingVisitor() {
        SymbolTable.init();
        row = 0;
    }

    /**
     * Returns the result type of the last visited node.
     * 
     * @return the TypeDescriptor of the last visited node
     */
    public String getErrorMessage() {
        return errorMessage;
    }

    /**
     * Returns the result type of the last visited node.
     * 
     * @return the TypeDescriptor of the last visited node
     */
    @Override
    public void visit(NodeProgram node) {
        for (NodeDecSt decSt : node.getDecSts()) {
            decSt.accept(this);
            row++;
        }
    }

    /**
     * Visits a NodeId and checks if it is declared in the symbol table.
     * If it is declared, it sets the result type based on the variable's type.
     * If it is not declared, it sets the result type to ERROR with an appropriate
     * message.
     *
     * @param node the NodeId to visit
     */
    @Override
    public void visit(NodeId node) {
        if (SymbolTable.lookup(node.getName()) == null) {
            StringBuilder sb = new StringBuilder();
            sb.append("Semantic error: variable '");
            sb.append(node.getName());
            sb.append("' not declared.");

            System.out.println(resType.getMessage());
            resType = new TypeDescriptor(TypeTd.ERROR, sb.toString(), row);
            errorMessage = sb.toString();
        } else {
            switch (SymbolTable.lookup(node.getName()).getType()) {
                case INT:
                    System.out.println("Visit node id: " + node.getName() + " of type INT");
                    resType = new TypeDescriptor(TypeTd.INT);
                    break;
                case FLOAT:
                    System.out.println("Visit node id: " + node.getName() + " of type FLOAT");
                    resType = new TypeDescriptor(TypeTd.FLOAT);
                    break;
            }
        }
    }

    /**
     * Visits a NodeDecl and checks if the variable is already declared.
     * If it is not declared, it adds the variable to the symbol table.
     * It also checks the type of the initialization expression if present.
     *
     * @param node the NodeDecl to visit
     */
    @Override
    public void visit(NodeDecl node) {
        TypeDescriptor idTd;
        StringBuilder sb = new StringBuilder();

        if (SymbolTable.enter(node.getId().getName(), new Attributes(node.getType(), node.getId().getName()))) {
            switch (node.getType()) {
                case INT:
                    System.out.println("Visit node decl: " + node.getId().getName() + " of type INT");
                    idTd = new TypeDescriptor(TypeTd.INT);
                    break;
                case FLOAT:
                    System.out.println("Visit node decl: " + node.getId().getName() + " of type FLOAT");
                    idTd = new TypeDescriptor(TypeTd.FLOAT);
                    break;
                default:
                    System.out.println("Visit node decl: " + node.getId().getName() + " of unknown type");
                    sb.append("Semantic error: variable '");
                    sb.append(node.getId().getName());
                    sb.append("' has an unknown type.");
                    idTd = new TypeDescriptor(TypeTd.ERROR, sb.toString(), row);
                    errorMessage = sb.toString();
            }
        } else {
            sb.append("Semantic error: variable '");
            sb.append(node.getId().getName());
            sb.append("' already declared.");

            System.out.println(sb.toString());
            resType = new TypeDescriptor(TypeTd.ERROR, sb.toString(), row);
            errorMessage = sb.toString();
            return;
        }

        if (node.getInit() == null) {
            System.out.println("Visit node decl: " + node.getId().getName() + " with no initialization");
            resType = new TypeDescriptor(TypeTd.OK);
            return;
        }

        node.getInit().accept(this);
        TypeDescriptor initTd = resType;

        if (idTd.getType() == TypeTd.INT && initTd.getType() == TypeTd.FLOAT) {

            sb.append("Semantic error: cannot assign expression of type ");
            sb.append(initTd.getType());
            sb.append(" to variable '");
            sb.append(node.getId().getName());
            sb.append("' of type ");
            sb.append(idTd.getType());
            sb.append(".");

            resType = new TypeDescriptor(TypeTd.ERROR, sb.toString(), row);
            errorMessage = sb.toString();
        } else {
            System.out.println(
                    "Visit node decl: " + node.getId().getName() + " initialized with " + node.getInit().toString());

            resType = new TypeDescriptor(TypeTd.OK);
        }
    }

    /**
     * Visits a NodeBinOp and checks the types of the left and right operands.
     * It changes the operation to DIV_FLOAT if either operand is of type FLOAT.
     * It sets the result type based on the types of the operands.
     *
     * @param node the NodeBinOp to visit
     */
    @Override
    public void visit(NodeBinOp node) {
        node.getLeft().accept(this);
        TypeDescriptor leftTd = resType;
        node.getRight().accept(this);
        TypeDescriptor rightTd = resType;

        if (leftTd.getType() == TypeTd.ERROR) {
            System.out.println("Error propagation visiting left operand: " + leftTd.getMessage());
            resType = leftTd;
        } else if (rightTd.getType() == TypeTd.ERROR) {
            System.out.println("Error propagation visiting right operand: " + rightTd.getMessage());
            resType = rightTd;
        }

        if (node.getOp() == LangOper.DIV && leftTd.getType() == TypeTd.FLOAT || rightTd.getType() == TypeTd.FLOAT) {
            System.out.println("Changing operation to DIV_FLOAT due to FLOAT type");
            node.setOp(LangOper.DIV_FLOAT);
        }

        if (leftTd.getType() == TypeTd.FLOAT || rightTd.getType() == TypeTd.FLOAT) {
            System.out.println("Visit node binop: " + node.getLeft().toString() + " " + node.getOp() + " "
                    + node.getRight().toString() +
                    " with result type FLOAT");

            resType = new TypeDescriptor(TypeTd.FLOAT);
        } else {
            System.out.println("Visit node binop: " + node.getLeft().toString() + " " + node.getOp() + " "
                    + node.getRight().toString() +
                    " with result type INT");

            resType = new TypeDescriptor(TypeTd.INT);
        }
    }

    /**
     * Visits a NodeDeref and checks the ID.
     * It sets the result type based on the ID's type.
     *
     * @param node the NodeDeref to visit
     */
    @Override
    public void visit(NodeDeref node) {
        node.getId().accept(this);

        System.out.println("Visit node deref: " + node.getId().toString());
    }

    /**
     * Visits a NodeCost and sets the result type based on the value's type.
     * It prints a message indicating the visit and the type of the cost.
     *
     * @param node the NodeCost to visit
     */
    @Override
    public void visit(NodeCost node) {
        switch (node.getType()) {
            case FLOAT:
                System.out.println("Visit node cost: " + node.getValue() + " of type FLOAT");
                resType = new TypeDescriptor(TypeTd.FLOAT);
                break;
            case INT:
                System.out.println("Visit node cost: " + node.getValue() + " of type INT");
                resType = new TypeDescriptor(TypeTd.INT);
                break;
        }
    }

    /**
     * Visits a NodeAssign and checks the ID and expression types.
     * It ensures that the expression type is compatible with the ID type.
     * If there is a semantic error, it sets the result type to ERROR with an
     * appropriate message.
     *
     * @param node the NodeAssign to visit
     */
    @Override
    public void visit(NodeAssign node) {
        StringBuilder sb = new StringBuilder();

        node.getId().accept(this);

        TypeDescriptor idTd = resType;

        node.getExpr().accept(this);

        TypeDescriptor exprTd = resType;

        if (idTd.getType() == TypeTd.ERROR) {
            System.out.println("Error propagation visiting id: " + idTd.getMessage());
            resType = idTd;
        }

        else if (exprTd.getType() == TypeTd.ERROR) {
            System.out.println("Error propagation visiting expression: " + exprTd.getMessage());
            resType = exprTd;
        }

        else if (!exprTd.compatibility(idTd)) {
            sb.append("Semantic error: cannot assign expression of type ");
            sb.append(exprTd.getType());
            sb.append(" to variable '");
            sb.append(node.getId().getName());
            sb.append("' of type ");
            sb.append(idTd.getType());
            sb.append(".");

            resType = new TypeDescriptor(TypeTd.ERROR, sb.toString(), row);
            errorMessage = sb.toString();
        }

        System.out.println("Visit node assign: " + node.getId().toString() + " = " + node.getExpr().toString());
        resType = new TypeDescriptor(TypeTd.OK);
    }

    /**
     * Visits a print node.
     * It checks if the ID is declared and has a valid type.
     * If the ID is valid, it prints a message indicating the visit.
     * If there is an error, it sets the result type to ERROR with an appropriate
     * message.
     *
     * @param node the NodePrint to visit
     */
    @Override
    public void visit(NodePrint node) {
        node.getId().accept(this);

        if (resType != null && resType.getType() != TypeTd.ERROR) {
            System.out.println("Visit node print: " + node.getId().toString());

            resType = new TypeDescriptor(TypeTd.OK);
        } else {
            System.err.println("Error propagation visiting node print: " + resType.getMessage());
        }
    }
}
