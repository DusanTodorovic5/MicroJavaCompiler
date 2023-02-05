// generated with ast extension for cup
// version 0.8
// 5/1/2023 0:9:12


package rs.ac.bg.etf.pp1.ast;

public class ListConstDeclarations extends ListConstDecls {

    private ListConstDecls ListConstDecls;
    private ConstDecls ConstDecls;

    public ListConstDeclarations (ListConstDecls ListConstDecls, ConstDecls ConstDecls) {
        this.ListConstDecls=ListConstDecls;
        if(ListConstDecls!=null) ListConstDecls.setParent(this);
        this.ConstDecls=ConstDecls;
        if(ConstDecls!=null) ConstDecls.setParent(this);
    }

    public ListConstDecls getListConstDecls() {
        return ListConstDecls;
    }

    public void setListConstDecls(ListConstDecls ListConstDecls) {
        this.ListConstDecls=ListConstDecls;
    }

    public ConstDecls getConstDecls() {
        return ConstDecls;
    }

    public void setConstDecls(ConstDecls ConstDecls) {
        this.ConstDecls=ConstDecls;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(ListConstDecls!=null) ListConstDecls.accept(visitor);
        if(ConstDecls!=null) ConstDecls.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(ListConstDecls!=null) ListConstDecls.traverseTopDown(visitor);
        if(ConstDecls!=null) ConstDecls.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(ListConstDecls!=null) ListConstDecls.traverseBottomUp(visitor);
        if(ConstDecls!=null) ConstDecls.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("ListConstDeclarations(\n");

        if(ListConstDecls!=null)
            buffer.append(ListConstDecls.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(ConstDecls!=null)
            buffer.append(ConstDecls.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [ListConstDeclarations]");
        return buffer.toString();
    }
}
