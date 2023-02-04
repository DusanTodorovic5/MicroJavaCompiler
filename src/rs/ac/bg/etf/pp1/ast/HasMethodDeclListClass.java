// generated with ast extension for cup
// version 0.8
// 3/1/2023 21:40:48


package rs.ac.bg.etf.pp1.ast;

public class HasMethodDeclListClass extends MethodDeclListClass {

    private MethodDeclListClass MethodDeclListClass;
    private MethodDecl MethodDecl;

    public HasMethodDeclListClass (MethodDeclListClass MethodDeclListClass, MethodDecl MethodDecl) {
        this.MethodDeclListClass=MethodDeclListClass;
        if(MethodDeclListClass!=null) MethodDeclListClass.setParent(this);
        this.MethodDecl=MethodDecl;
        if(MethodDecl!=null) MethodDecl.setParent(this);
    }

    public MethodDeclListClass getMethodDeclListClass() {
        return MethodDeclListClass;
    }

    public void setMethodDeclListClass(MethodDeclListClass MethodDeclListClass) {
        this.MethodDeclListClass=MethodDeclListClass;
    }

    public MethodDecl getMethodDecl() {
        return MethodDecl;
    }

    public void setMethodDecl(MethodDecl MethodDecl) {
        this.MethodDecl=MethodDecl;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(MethodDeclListClass!=null) MethodDeclListClass.accept(visitor);
        if(MethodDecl!=null) MethodDecl.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(MethodDeclListClass!=null) MethodDeclListClass.traverseTopDown(visitor);
        if(MethodDecl!=null) MethodDecl.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(MethodDeclListClass!=null) MethodDeclListClass.traverseBottomUp(visitor);
        if(MethodDecl!=null) MethodDecl.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("HasMethodDeclListClass(\n");

        if(MethodDeclListClass!=null)
            buffer.append(MethodDeclListClass.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(MethodDecl!=null)
            buffer.append(MethodDecl.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [HasMethodDeclListClass]");
        return buffer.toString();
    }
}
