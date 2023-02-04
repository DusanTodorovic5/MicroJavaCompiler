// generated with ast extension for cup
// version 0.8
// 3/1/2023 21:40:48


package rs.ac.bg.etf.pp1.ast;

public class FactorDesignatorActPars extends Factor {

    private DesignatorFuncCall DesignatorFuncCall;
    private ActPars ActPars;

    public FactorDesignatorActPars (DesignatorFuncCall DesignatorFuncCall, ActPars ActPars) {
        this.DesignatorFuncCall=DesignatorFuncCall;
        if(DesignatorFuncCall!=null) DesignatorFuncCall.setParent(this);
        this.ActPars=ActPars;
        if(ActPars!=null) ActPars.setParent(this);
    }

    public DesignatorFuncCall getDesignatorFuncCall() {
        return DesignatorFuncCall;
    }

    public void setDesignatorFuncCall(DesignatorFuncCall DesignatorFuncCall) {
        this.DesignatorFuncCall=DesignatorFuncCall;
    }

    public ActPars getActPars() {
        return ActPars;
    }

    public void setActPars(ActPars ActPars) {
        this.ActPars=ActPars;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(DesignatorFuncCall!=null) DesignatorFuncCall.accept(visitor);
        if(ActPars!=null) ActPars.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(DesignatorFuncCall!=null) DesignatorFuncCall.traverseTopDown(visitor);
        if(ActPars!=null) ActPars.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(DesignatorFuncCall!=null) DesignatorFuncCall.traverseBottomUp(visitor);
        if(ActPars!=null) ActPars.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("FactorDesignatorActPars(\n");

        if(DesignatorFuncCall!=null)
            buffer.append(DesignatorFuncCall.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(ActPars!=null)
            buffer.append(ActPars.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [FactorDesignatorActPars]");
        return buffer.toString();
    }
}
