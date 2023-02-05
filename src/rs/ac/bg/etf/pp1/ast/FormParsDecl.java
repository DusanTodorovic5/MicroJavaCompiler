// generated with ast extension for cup
// version 0.8
// 5/1/2023 13:48:4


package rs.ac.bg.etf.pp1.ast;

public class FormParsDecl implements SyntaxNode {

    private SyntaxNode parent;
    private int line;
    public rs.etf.pp1.symboltable.concepts.Obj obj = null;

    private Type Type;
    private String formParIdent;
    private VarBrackets VarBrackets;

    public FormParsDecl (Type Type, String formParIdent, VarBrackets VarBrackets) {
        this.Type=Type;
        if(Type!=null) Type.setParent(this);
        this.formParIdent=formParIdent;
        this.VarBrackets=VarBrackets;
        if(VarBrackets!=null) VarBrackets.setParent(this);
    }

    public Type getType() {
        return Type;
    }

    public void setType(Type Type) {
        this.Type=Type;
    }

    public String getFormParIdent() {
        return formParIdent;
    }

    public void setFormParIdent(String formParIdent) {
        this.formParIdent=formParIdent;
    }

    public VarBrackets getVarBrackets() {
        return VarBrackets;
    }

    public void setVarBrackets(VarBrackets VarBrackets) {
        this.VarBrackets=VarBrackets;
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
        if(VarBrackets!=null) VarBrackets.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(Type!=null) Type.traverseTopDown(visitor);
        if(VarBrackets!=null) VarBrackets.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(Type!=null) Type.traverseBottomUp(visitor);
        if(VarBrackets!=null) VarBrackets.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("FormParsDecl(\n");

        if(Type!=null)
            buffer.append(Type.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(" "+tab+formParIdent);
        buffer.append("\n");

        if(VarBrackets!=null)
            buffer.append(VarBrackets.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [FormParsDecl]");
        return buffer.toString();
    }
}
