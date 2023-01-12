// generated with ast extension for cup
// version 0.8
// 12/0/2023 17:7:2


package rs.ac.bg.etf.pp1.ast;

public class MethodDeclaration extends MethodDecl {

    private MethodType MethodType;
    private FormParsBrackets FormParsBrackets;
    private ClassFields ClassFields;
    private StatementList StatementList;

    public MethodDeclaration (MethodType MethodType, FormParsBrackets FormParsBrackets, ClassFields ClassFields, StatementList StatementList) {
        this.MethodType=MethodType;
        if(MethodType!=null) MethodType.setParent(this);
        this.FormParsBrackets=FormParsBrackets;
        if(FormParsBrackets!=null) FormParsBrackets.setParent(this);
        this.ClassFields=ClassFields;
        if(ClassFields!=null) ClassFields.setParent(this);
        this.StatementList=StatementList;
        if(StatementList!=null) StatementList.setParent(this);
    }

    public MethodType getMethodType() {
        return MethodType;
    }

    public void setMethodType(MethodType MethodType) {
        this.MethodType=MethodType;
    }

    public FormParsBrackets getFormParsBrackets() {
        return FormParsBrackets;
    }

    public void setFormParsBrackets(FormParsBrackets FormParsBrackets) {
        this.FormParsBrackets=FormParsBrackets;
    }

    public ClassFields getClassFields() {
        return ClassFields;
    }

    public void setClassFields(ClassFields ClassFields) {
        this.ClassFields=ClassFields;
    }

    public StatementList getStatementList() {
        return StatementList;
    }

    public void setStatementList(StatementList StatementList) {
        this.StatementList=StatementList;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(MethodType!=null) MethodType.accept(visitor);
        if(FormParsBrackets!=null) FormParsBrackets.accept(visitor);
        if(ClassFields!=null) ClassFields.accept(visitor);
        if(StatementList!=null) StatementList.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(MethodType!=null) MethodType.traverseTopDown(visitor);
        if(FormParsBrackets!=null) FormParsBrackets.traverseTopDown(visitor);
        if(ClassFields!=null) ClassFields.traverseTopDown(visitor);
        if(StatementList!=null) StatementList.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(MethodType!=null) MethodType.traverseBottomUp(visitor);
        if(FormParsBrackets!=null) FormParsBrackets.traverseBottomUp(visitor);
        if(ClassFields!=null) ClassFields.traverseBottomUp(visitor);
        if(StatementList!=null) StatementList.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("MethodDeclaration(\n");

        if(MethodType!=null)
            buffer.append(MethodType.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(FormParsBrackets!=null)
            buffer.append(FormParsBrackets.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(ClassFields!=null)
            buffer.append(ClassFields.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(StatementList!=null)
            buffer.append(StatementList.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [MethodDeclaration]");
        return buffer.toString();
    }
}
