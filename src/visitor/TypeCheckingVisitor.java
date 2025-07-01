package visitor;

import ast.NodeAST;
import ast.NodeAssign;
import ast.NodeBinOp;
import ast.NodeCost;
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
    private TypeDescriptor resType; // mantiene il risultato della visita

    @Override
    public void visit(NodeProgram node) {
        
    }

    @Override
    public void visit(NodeId node) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'visit'");
    }

    @Override
    public void visit(NodeDecl node) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'visit'");
    }

    @Override
    public void visit(NodeBinOp node) {
        node.getLeft().accept(this);
        TypeDescriptor leftTD = resType;
        node.getRight().accept(this);
        TypeDescriptor rightTD = resType;

        // I metodi visit per gli altri nodi concreti.......
    }

    @Override
    public void visit(NodeDeref node) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'visit'");
    }

    @Override
    public void visit(NodeCost node) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'visit'");
    }

    @Override
    public void visit(NodeAssign node) {
        Attributes attribute = SymbolTable.lookup(node.getId().toString());

        if(attribute != null) {
            SymbolTable.enter(node.getId().toString(), attribute);
        }
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'visit'");
    }

    @Override
    public void visit(NodePrint node) {
        Attributes attribute = SymbolTable.lookup(node.getId().toString());

        if(attribute != null) {
            node.accept(this);
            resType = new TypeDescriptor();
        }
    }
}
