package ast;

import java.util.ArrayList;

import visitor.IVisitor;

public class NodeProgram extends NodeAST {
    private ArrayList<NodeDecSt> decSts;

    public NodeProgram(ArrayList<NodeDecSt> decSts) {
        this.decSts = decSts;
    }

    public ArrayList<NodeDecSt> getDecSts() {
        return decSts;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();

        for (NodeDecSt node : decSts) {
            builder.append(node);
        }

        return "[Program:" + builder.toString() + "]";
    }    

    public void accept(IVisitor visitor) {
        visitor.visit(this);
    }
}
