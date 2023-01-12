// generated with ast extension for cup
// version 0.8
// 12/0/2023 17:7:2


package rs.ac.bg.etf.pp1.ast;

public class NonEmptyDesignatorListWithComa extends NonEmptyDesignatorList {

    private NonEmptyDesignatorList NonEmptyDesignatorList;
    private Designator Designator;

    public NonEmptyDesignatorListWithComa (NonEmptyDesignatorList NonEmptyDesignatorList, Designator Designator) {
        this.NonEmptyDesignatorList=NonEmptyDesignatorList;
        if(NonEmptyDesignatorList!=null) NonEmptyDesignatorList.setParent(this);
        this.Designator=Designator;
        if(Designator!=null) Designator.setParent(this);
    }

    public NonEmptyDesignatorList getNonEmptyDesignatorList() {
        return NonEmptyDesignatorList;
    }

    public void setNonEmptyDesignatorList(NonEmptyDesignatorList NonEmptyDesignatorList) {
        this.NonEmptyDesignatorList=NonEmptyDesignatorList;
    }

    public Designator getDesignator() {
        return Designator;
    }

    public void setDesignator(Designator Designator) {
        this.Designator=Designator;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(NonEmptyDesignatorList!=null) NonEmptyDesignatorList.accept(visitor);
        if(Designator!=null) Designator.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(NonEmptyDesignatorList!=null) NonEmptyDesignatorList.traverseTopDown(visitor);
        if(Designator!=null) Designator.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(NonEmptyDesignatorList!=null) NonEmptyDesignatorList.traverseBottomUp(visitor);
        if(Designator!=null) Designator.traverseBottomUp(visitor);
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

        if(Designator!=null)
            buffer.append(Designator.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [NonEmptyDesignatorListWithComa]");
        return buffer.toString();
    }
}
