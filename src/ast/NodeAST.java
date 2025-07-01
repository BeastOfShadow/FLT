package ast;

import java.lang.invoke.TypeDescriptor;

import visitor.IVisitor;

public abstract class NodeAST {
    TypeDescriptor resType;
    String codiceDc;

    public abstract void accept(IVisitor visitor);
}
