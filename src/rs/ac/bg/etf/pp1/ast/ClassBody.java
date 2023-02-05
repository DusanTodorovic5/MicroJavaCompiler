// generated with ast extension for cup
// version 0.8
// 5/1/2023 13:48:4


package rs.ac.bg.etf.pp1.ast;

public class ClassBody implements SyntaxNode {

    private SyntaxNode parent;
    private int line;
    private ClassFields ClassFields;
    private ClassFunctions ClassFunctions;

    public ClassBody (ClassFields ClassFields, ClassFunctions ClassFunctions) {
        this.ClassFields=ClassFields;
        if(ClassFields!=null) ClassFields.setParent(this);
        this.ClassFunctions=ClassFunctions;
        if(ClassFunctions!=null) ClassFunctions.setParent(this);
    }

    public ClassFields getClassFields() {
        return ClassFields;
    }

    public void setClassFields(ClassFields ClassFields) {
        this.ClassFields=ClassFields;
    }

    public ClassFunctions getClassFunctions() {
        return ClassFunctions;
    }

    public void setClassFunctions(ClassFunctions ClassFunctions) {
        this.ClassFunctions=ClassFunctions;
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
        if(ClassFields!=null) ClassFields.accept(visitor);
        if(ClassFunctions!=null) ClassFunctions.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(ClassFields!=null) ClassFields.traverseTopDown(visitor);
        if(ClassFunctions!=null) ClassFunctions.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(ClassFields!=null) ClassFields.traverseBottomUp(visitor);
        if(ClassFunctions!=null) ClassFunctions.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("ClassBody(\n");

        if(ClassFields!=null)
            buffer.append(ClassFields.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(ClassFunctions!=null)
            buffer.append(ClassFunctions.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [ClassBody]");
        return buffer.toString();
    }
}
