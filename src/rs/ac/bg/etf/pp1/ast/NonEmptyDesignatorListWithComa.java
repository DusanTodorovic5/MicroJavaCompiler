// generated with ast extension for cup
// version 0.8
// 4/1/2023 23:30:27


package rs.ac.bg.etf.pp1.ast;

public class NonEmptyDesignatorListWithComa extends NonEmptyDesignatorList {

    private NonEmptyDesignatorList NonEmptyDesignatorList;
    private EpsilonDesignator EpsilonDesignator;

    public NonEmptyDesignatorListWithComa (NonEmptyDesignatorList NonEmptyDesignatorList, EpsilonDesignator EpsilonDesignator) {
        this.NonEmptyDesignatorList=NonEmptyDesignatorList;
        if(NonEmptyDesignatorList!=null) NonEmptyDesignatorList.setParent(this);
        this.EpsilonDesignator=EpsilonDesignator;
        if(EpsilonDesignator!=null) EpsilonDesignator.setParent(this);
    }

    public NonEmptyDesignatorList getNonEmptyDesignatorList() {
        return NonEmptyDesignatorList;
    }

    public void setNonEmptyDesignatorList(NonEmptyDesignatorList NonEmptyDesignatorList) {
        this.NonEmptyDesignatorList=NonEmptyDesignatorList;
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
        if(NonEmptyDesignatorList!=null) NonEmptyDesignatorList.accept(visitor);
        if(EpsilonDesignator!=null) EpsilonDesignator.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(NonEmptyDesignatorList!=null) NonEmptyDesignatorList.traverseTopDown(visitor);
        if(EpsilonDesignator!=null) EpsilonDesignator.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(NonEmptyDesignatorList!=null) NonEmptyDesignatorList.traverseBottomUp(visitor);
        if(EpsilonDesignator!=null) EpsilonDesignator.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("NonEmptyDesignatorListWithComa(\n");

        if(NonEmptyDesignatorList!=null)
            buffer.append(NonEmptyDesignatorList.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(EpsilonDesignator!=null)
            buffer.append(EpsilonDesignator.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [NonEmptyDesignatorListWithComa]");
        return buffer.toString();
    }
}
