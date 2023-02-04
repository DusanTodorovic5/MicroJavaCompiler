// generated with ast extension for cup
// version 0.8
// 3/1/2023 21:40:48


package rs.ac.bg.etf.pp1.ast;

public class ConstDecl implements SyntaxNode {

    private SyntaxNode parent;
    private int line;
    private Type Type;
    private ConstDecls ConstDecls;
    private ListConstDecls ListConstDecls;

    public ConstDecl (Type Type, ConstDecls ConstDecls, ListConstDecls ListConstDecls) {
        this.Type=Type;
        if(Type!=null) Type.setParent(this);
        this.ConstDecls=ConstDecls;
        if(ConstDecls!=null) ConstDecls.setParent(this);
        this.ListConstDecls=ListConstDecls;
        if(ListConstDecls!=null) ListConstDecls.setParent(this);
    }

    public Type getType() {
        return Type;
    }

    public void setType(Type Type) {
        this.Type=Type;
    }

    public ConstDecls getConstDecls() {
        return ConstDecls;
    }

    public void setConstDecls(ConstDecls ConstDecls) {
        this.ConstDecls=ConstDecls;
    }

    public ListConstDecls getListConstDecls() {
        return ListConstDecls;
    }

    public void setListConstDecls(ListConstDecls ListConstDecls) {
        this.ListConstDecls=ListConstDecls;
    }

    public SyntaxNode getParent() {
        return parent;
    }

    public void setParent(SyntaxNode parent) {
        this.parent=parent;
    }

    public int getLine() {
        return line;
    }

    public void setLine(int line) {
        this.line=line;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(Type!=null) Type.accept(visitor);
        if(ConstDecls!=null) ConstDecls.accept(visitor);
        if(ListConstDecls!=null) ListConstDecls.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(Type!=null) Type.traverseTopDown(visitor);
        if(ConstDecls!=null) ConstDecls.traverseTopDown(visitor);
        if(ListConstDecls!=null) ListConstDecls.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(Type!=null) Type.traverseBottomUp(visitor);
        if(ConstDecls!=null) ConstDecls.traverseBottomUp(visitor);
        if(ListConstDecls!=null) ListConstDecls.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("ConstDecl(\n");

        if(Type!=null)
            buffer.append(Type.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(ConstDecls!=null)
            buffer.append(ConstDecls.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(ListConstDecls!=null)
            buffer.append(ListConstDecls.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [ConstDecl]");
        return buffer.toString();
    }
}
