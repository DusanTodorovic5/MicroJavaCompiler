// generated with ast extension for cup
// version 0.8
// 12/0/2023 15:8:12


package rs.ac.bg.etf.pp1.ast;

public class FormParsListWithComaList extends FormParsList {

    private FormParsListWithComa FormParsListWithComa;
    private FormParsDecl FormParsDecl;

    public FormParsListWithComaList (FormParsListWithComa FormParsListWithComa, FormParsDecl FormParsDecl) {
        this.FormParsListWithComa=FormParsListWithComa;
        if(FormParsListWithComa!=null) FormParsListWithComa.setParent(this);
        this.FormParsDecl=FormParsDecl;
        if(FormParsDecl!=null) FormParsDecl.setParent(this);
    }

    public FormParsListWithComa getFormParsListWithComa() {
        return FormParsListWithComa;
    }

    public void setFormParsListWithComa(FormParsListWithComa FormParsListWithComa) {
        this.FormParsListWithComa=FormParsListWithComa;
    }

    public FormParsDecl getFormParsDecl() {
        return FormParsDecl;
    }

    public void setFormParsDecl(FormParsDecl FormParsDecl) {
        this.FormParsDecl=FormParsDecl;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(FormParsListWithComa!=null) FormParsListWithComa.accept(visitor);
        if(FormParsDecl!=null) FormParsDecl.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(FormParsListWithComa!=null) FormParsListWithComa.traverseTopDown(visitor);
        if(FormParsDecl!=null) FormParsDecl.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(FormParsListWithComa!=null) FormParsListWithComa.traverseBottomUp(visitor);
        if(FormParsDecl!=null) FormParsDecl.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("FormParsListWithComaList(\n");

        if(FormParsListWithComa!=null)
            buffer.append(FormParsListWithComa.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(FormParsDecl!=null)
            buffer.append(FormParsDecl.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [FormParsListWithComaList]");
        return buffer.toString();
    }
}
