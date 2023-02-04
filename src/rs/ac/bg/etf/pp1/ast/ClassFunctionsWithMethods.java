// generated with ast extension for cup
// version 0.8
// 4/1/2023 23:30:27


package rs.ac.bg.etf.pp1.ast;

public class ClassFunctionsWithMethods extends ClassFunctions {

    private MethodDeclListClass MethodDeclListClass;

    public ClassFunctionsWithMethods (MethodDeclListClass MethodDeclListClass) {
        this.MethodDeclListClass=MethodDeclListClass;
        if(MethodDeclListClass!=null) MethodDeclListClass.setParent(this);
    }

    public MethodDeclListClass getMethodDeclListClass() {
        return MethodDeclListClass;
    }

    public void setMethodDeclListClass(MethodDeclListClass MethodDeclListClass) {
        this.MethodDeclListClass=MethodDeclListClass;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(MethodDeclListClass!=null) MethodDeclListClass.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(MethodDeclListClass!=null) MethodDeclListClass.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(MethodDeclListClass!=null) MethodDeclListClass.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("ClassFunctionsWithMethods(\n");

        if(MethodDeclListClass!=null)
            buffer.append(MethodDeclListClass.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [ClassFunctionsWithMethods]");
        return buffer.toString();
    }
}
