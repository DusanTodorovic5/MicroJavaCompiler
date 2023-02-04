// generated with ast extension for cup
// version 0.8
// 4/1/2023 23:30:27


package rs.ac.bg.etf.pp1.ast;

public class VarName implements SyntaxNode {

    private SyntaxNode parent;
    private int line;
    public rs.etf.pp1.symboltable.concepts.Obj obj = null;

    private String varName;
    private VarBrackets VarBrackets;

    public VarName (String varName, VarBrackets VarBrackets) {
        this.varName=varName;
        this.VarBrackets=VarBrackets;
        if(VarBrackets!=null) VarBrackets.setParent(this);
    }

    public String getVarName() {
        return varName;
    }

    public void setVarName(String varName) {
        this.varName=varName;
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
        if(VarBrackets!=null) VarBrackets.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(VarBrackets!=null) VarBrackets.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(VarBrackets!=null) VarBrackets.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("VarName(\n");

        buffer.append(" "+tab+varName);
        buffer.append("\n");

        if(VarBrackets!=null)
            buffer.append(VarBrackets.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [VarName]");
        return buffer.toString();
    }
}
