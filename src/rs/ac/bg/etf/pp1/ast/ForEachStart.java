// generated with ast extension for cup
// version 0.8
// 5/1/2023 0:9:12


package rs.ac.bg.etf.pp1.ast;

public class ForEachStart implements SyntaxNode {

    private SyntaxNode parent;
    private int line;
    public rs.etf.pp1.symboltable.concepts.Obj obj = null;

    private String foreachName;

    public ForEachStart (String foreachName) {
        this.foreachName=foreachName;
    }

    public String getForeachName() {
        return foreachName;
    }

    public void setForeachName(String foreachName) {
        this.foreachName=foreachName;
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
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("ForEachStart(\n");

        buffer.append(" "+tab+foreachName);
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [ForEachStart]");
        return buffer.toString();
    }
}
