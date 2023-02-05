// generated with ast extension for cup
// version 0.8
// 5/1/2023 0:9:12


package rs.ac.bg.etf.pp1.ast;

public class FormParList extends FormParsList {

    private FormParsDecl FormParsDecl;

    public FormParList (FormParsDecl FormParsDecl) {
        this.FormParsDecl=FormParsDecl;
        if(FormParsDecl!=null) FormParsDecl.setParent(this);
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
        if(FormParsDecl!=null) FormParsDecl.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(FormParsDecl!=null) FormParsDecl.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(FormParsDecl!=null) FormParsDecl.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("FormParList(\n");

        if(FormParsDecl!=null)
            buffer.append(FormParsDecl.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [FormParList]");
        return buffer.toString();
    }
}
