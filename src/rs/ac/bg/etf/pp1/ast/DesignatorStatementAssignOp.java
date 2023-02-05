// generated with ast extension for cup
// version 0.8
// 5/1/2023 13:48:4


package rs.ac.bg.etf.pp1.ast;

public class DesignatorStatementAssignOp extends DesignatorStatement {

    private AssignOpExpression AssignOpExpression;

    public DesignatorStatementAssignOp (AssignOpExpression AssignOpExpression) {
        this.AssignOpExpression=AssignOpExpression;
        if(AssignOpExpression!=null) AssignOpExpression.setParent(this);
    }

    public AssignOpExpression getAssignOpExpression() {
        return AssignOpExpression;
    }

    public void setAssignOpExpression(AssignOpExpression AssignOpExpression) {
        this.AssignOpExpression=AssignOpExpression;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(AssignOpExpression!=null) AssignOpExpression.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(AssignOpExpression!=null) AssignOpExpression.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(AssignOpExpression!=null) AssignOpExpression.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("DesignatorStatementAssignOp(\n");

        if(AssignOpExpression!=null)
            buffer.append(AssignOpExpression.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [DesignatorStatementAssignOp]");
        return buffer.toString();
    }
}
