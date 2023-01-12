// generated with ast extension for cup
// version 0.8
// 12/0/2023 17:7:2


package rs.ac.bg.etf.pp1.ast;

public class FactorBool extends Factor {

    private Boolean valBool;

    public FactorBool (Boolean valBool) {
        this.valBool=valBool;
    }

    public Boolean getValBool() {
        return valBool;
    }

    public void setValBool(Boolean valBool) {
        this.valBool=valBool;
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
        buffer.append("FactorBool(\n");

        buffer.append(" "+tab+valBool);
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [FactorBool]");
        return buffer.toString();
    }
}
