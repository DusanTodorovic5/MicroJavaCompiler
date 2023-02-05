// generated with ast extension for cup
// version 0.8
// 5/1/2023 13:48:4


package rs.ac.bg.etf.pp1.ast;

public class SingleDesignatorList extends NonEmptyDesignatorList {

    private EpsilonDesignator EpsilonDesignator;

    public SingleDesignatorList (EpsilonDesignator EpsilonDesignator) {
        this.EpsilonDesignator=EpsilonDesignator;
        if(EpsilonDesignator!=null) EpsilonDesignator.setParent(this);
    }

    public EpsilonDesignator getEpsilonDesignator() {
        return EpsilonDesignator;
    }

    public void setEpsilonDesignator(EpsilonDesignator EpsilonDesignator) {
        this.EpsilonDesignator=EpsilonDesignator;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(EpsilonDesignator!=null) EpsilonDesignator.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(EpsilonDesignator!=null) EpsilonDesignator.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(EpsilonDesignator!=null) EpsilonDesignator.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("SingleDesignatorList(\n");

        if(EpsilonDesignator!=null)
            buffer.append(EpsilonDesignator.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [SingleDesignatorList]");
        return buffer.toString();
    }
}
