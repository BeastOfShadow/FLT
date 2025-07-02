package ast;

import org.junit.platform.engine.support.hierarchical.Node;

import visitor.IVisitor;

public class NodeBinOp extends NodeExpr {
    private LangOper op;
    private NodeExpr left;
    private NodeExpr right;
    
    public NodeBinOp(LangOper op, NodeExpr left, NodeExpr right) {
        this.op = op;
        this.left = left;
        this.right = right;
    }

    public LangOper getOp() {
        return op;
    }

    public NodeExpr getLeft() {
        return left;
    }

    public NodeExpr getRight() {
        return right;
    }

    public void setOp(LangOper op) {
        this.op = op;
    }

    @Override
    public String toString() {
        return "[BinOp:" + op + "," + left + "," + right + "]";
    }

    public void accept(IVisitor visitor) {
        visitor.visit(this);
    }
}
