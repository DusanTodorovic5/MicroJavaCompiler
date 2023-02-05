// generated with ast extension for cup
// version 0.8
// 5/1/2023 13:48:4


package rs.ac.bg.etf.pp1.ast;

public class ClassFunctionsWithConstructorAndMethods extends ClassFunctions {

    private ConstructorDeclList ConstructorDeclList;
    private MethodDeclListClass MethodDeclListClass;

    public ClassFunctionsWithConstructorAndMethods (ConstructorDeclList ConstructorDeclList, MethodDeclListClass MethodDeclListClass) {
        this.ConstructorDeclList=ConstructorDeclList;
        if(ConstructorDeclList!=null) ConstructorDeclList.setParent(this);
        this.MethodDeclListClass=MethodDeclListClass;
        if(MethodDeclListClass!=null) MethodDeclListClass.setParent(this);
    }

    public ConstructorDeclList getConstructorDeclList() {
        return ConstructorDeclList;
    }

    public void setConstructorDeclList(ConstructorDeclList ConstructorDeclList) {
        this.ConstructorDeclList=ConstructorDeclList;
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
        if(ConstructorDeclList!=null) ConstructorDeclList.accept(visitor);
        if(MethodDeclListClass!=null) MethodDeclListClass.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(ConstructorDeclList!=null) ConstructorDeclList.traverseTopDown(visitor);
        if(MethodDeclListClass!=null) MethodDeclListClass.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(ConstructorDeclList!=null) ConstructorDeclList.traverseBottomUp(visitor);
        if(MethodDeclListClass!=null) MethodDeclListClass.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("ClassFunctionsWithConstructorAndMethods(\n");

        if(ConstructorDeclList!=null)
            buffer.append(ConstructorDeclList.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(MethodDeclListClass!=null)
            buffer.append(MethodDeclListClass.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [ClassFunctionsWithConstructorAndMethods]");
        return buffer.toString();
    }
}
