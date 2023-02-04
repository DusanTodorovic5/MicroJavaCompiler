// generated with ast extension for cup
// version 0.8
// 3/1/2023 21:40:48


package rs.ac.bg.etf.pp1.ast;

public class FactorNumber extends Factor {

    private Integer valNum;

    public FactorNumber (Integer valNum) {
        this.valNum=valNum;
    }

    public Integer getValNum() {
        return valNum;
    }

    public void setValNum(Integer valNum) {
        this.valNum=valNum;
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
        buffer.append("FactorNumber(\n");

        buffer.append(" "+tab+valNum);
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [FactorNumber]");
        return buffer.toString();
    }
}
