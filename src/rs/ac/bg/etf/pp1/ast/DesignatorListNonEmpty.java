// generated with ast extension for cup
// version 0.8
// 3/1/2023 21:40:48


package rs.ac.bg.etf.pp1.ast;

public class DesignatorListNonEmpty extends DesignatorList {

    private NonEmptyDesignatorList NonEmptyDesignatorList;

    public DesignatorListNonEmpty (NonEmptyDesignatorList NonEmptyDesignatorList) {
        this.NonEmptyDesignatorList=NonEmptyDesignatorList;
        if(NonEmptyDesignatorList!=null) NonEmptyDesignatorList.setParent(this);
    }

    public NonEmptyDesignatorList getNonEmptyDesignatorList() {
        return NonEmptyDesignatorList;
    }

    public void setNonEmptyDesignatorList(NonEmptyDesignatorList NonEmptyDesignatorList) {
        this.NonEmptyDesignatorList=NonEmptyDesignatorList;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(NonEmptyDesignatorList!=null) NonEmptyDesignatorList.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(NonEmptyDesignatorList!=null) NonEmptyDesignatorList.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(NonEmptyDesignatorList!=null) NonEmptyDesignatorList.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("DesignatorListNonEmpty(\n");

        if(NonEmptyDesignatorList!=null)
            buffer.append(NonEmptyDesignatorList.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [DesignatorListNonEmpty]");
        return buffer.toString();
    }
}
