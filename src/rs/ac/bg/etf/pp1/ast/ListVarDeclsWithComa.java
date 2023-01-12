// generated with ast extension for cup
// version 0.8
// 12/0/2023 15:8:12


package rs.ac.bg.etf.pp1.ast;

public class ListVarDeclsWithComa extends ListVarDecls {

    private NonLastVarDecl NonLastVarDecl;
    private ListVarDecls ListVarDecls;

    public ListVarDeclsWithComa (NonLastVarDecl NonLastVarDecl, ListVarDecls ListVarDecls) {
        this.NonLastVarDecl=NonLastVarDecl;
        if(NonLastVarDecl!=null) NonLastVarDecl.setParent(this);
        this.ListVarDecls=ListVarDecls;
        if(ListVarDecls!=null) ListVarDecls.setParent(this);
    }

    public NonLastVarDecl getNonLastVarDecl() {
        return NonLastVarDecl;
    }

    public void setNonLastVarDecl(NonLastVarDecl NonLastVarDecl) {
        this.NonLastVarDecl=NonLastVarDecl;
    }

    public ListVarDecls getListVarDecls() {
        return ListVarDecls;
    }

    public void setListVarDecls(ListVarDecls ListVarDecls) {
        this.ListVarDecls=ListVarDecls;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(NonLastVarDecl!=null) NonLastVarDecl.accept(visitor);
        if(ListVarDecls!=null) ListVarDecls.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(NonLastVarDecl!=null) NonLastVarDecl.traverseTopDown(visitor);
        if(ListVarDecls!=null) ListVarDecls.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(NonLastVarDecl!=null) NonLastVarDecl.traverseBottomUp(visitor);
        if(ListVarDecls!=null) ListVarDecls.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("ListVarDeclsWithComa(\n");

        if(NonLastVarDecl!=null)
            buffer.append(NonLastVarDecl.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(ListVarDecls!=null)
            buffer.append(ListVarDecls.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [ListVarDeclsWithComa]");
        return buffer.toString();
    }
}
