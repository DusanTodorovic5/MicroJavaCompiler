package rs.ac.bg.etf.pp1;

import rs.ac.bg.etf.pp1.ast.VisitorAdaptor;
import rs.etf.pp1.mj.runtime.*;
import rs.etf.pp1.symboltable.Tab;
import rs.etf.pp1.symboltable.concepts.Obj;
import rs.ac.bg.etf.pp1.CounterVisitor.FormParamCounter;
import rs.ac.bg.etf.pp1.CounterVisitor.VarCounter;
import rs.ac.bg.etf.pp1.ast.*;

public class CodeGenerator extends VisitorAdaptor {
	private int mainPc;
	boolean voidMethod = false;
	
	public int getPc() {
		return mainPc;
	}
	
	public void visit(MethodWithType method) {
		voidMethod = false;
		if (method.getMethodName().equalsIgnoreCase("main")) {
			mainPc = Code.pc;
		}
		
		method.obj.setAdr(Code.pc);
		SyntaxNode methodNode = method.getParent();
		
		VarCounter varCnt = new VarCounter();
		methodNode.traverseTopDown(varCnt);
		
		FormParamCounter fpCnt = new FormParamCounter();
		methodNode.traverseTopDown(fpCnt);
		
		Code.put(Code.enter);
		Code.put(fpCnt.getCount());
		Code.put(fpCnt.getCount() + varCnt.getCount());
		
		
	}
	
	public void visit(MethodWithNoType method) {
		voidMethod = true;
		if (method.getMethodName().equalsIgnoreCase("main")) {
			mainPc = Code.pc;
		}
		
		method.obj.setAdr(Code.pc);
		SyntaxNode methodNode = method.getParent();
		
		VarCounter varCnt = new VarCounter();
		methodNode.traverseTopDown(varCnt);
		
		FormParamCounter fpCnt = new FormParamCounter();
		methodNode.traverseTopDown(fpCnt);
		
		Code.put(Code.enter);
		Code.put(fpCnt.getCount());
		Code.put(fpCnt.getCount() + varCnt.getCount());
	}
	
	public void visit(MethodDeclaration method) {
		Code.put(Code.exit);
		Code.put(Code.return_);
	}
	
	public void visit(DesignatorAssign designator) {
		Code.store(designator.getDesignator().obj);
	}
	
	public void visit(IdentDesignator designator) {
		SyntaxNode parent = designator.getParent();
		
		if (DesignatorAssign.class != parent.getClass() && DesignatorFuncCall.class != parent.getClass()) {
			Code.load(designator.obj);
		}
	}
	
	public void visit(PrintStatement printStmt) {
		if (printStmt.getExpr().struct == Tab.intType || printStmt.getExpr().struct == TabExtension.boolType) {
			Code.loadConst(5);
			Code.put(Code.print);
		} else if (printStmt.getExpr().struct == Tab.charType) {
			Code.loadConst(1);
			Code.put(Code.bprint);
		}
	}
	
	public void visit(PrintNumberStatement printStmt) {
		Code.loadConst(printStmt.getN1());
		
		if (printStmt.getExpr().struct == Tab.intType || printStmt.getExpr().struct == TabExtension.boolType) {
			Code.loadConst(5);
			Code.put(Code.print);
		} else if (printStmt.getExpr().struct == Tab.charType) {
			Code.loadConst(1);
			Code.put(Code.bprint);
		}
	}
	
	public void visit(ReadStatement readStmt) {
		Code.put(readStmt.getDesignator().obj.getType() == Tab.charType ?
			Code.bread : Code.read);
		Code.store(readStmt.getDesignator().obj);
	}
	
	public void visit(FactorNumber fact) {
		Obj con = Tab.insert(Obj.Con, "$", fact.struct);
		
		con.setLevel(0);
		con.setAdr(fact.getValNum());
		Code.load(con);
	}
	
	public void visit(FactorChar fact) {
		Obj con = Tab.insert(Obj.Con, "$", fact.struct);
		
		con.setLevel(0);
		con.setAdr(fact.getValChar());
		Code.load(con);
	}
	
	public void visit(FactorBool fact) {
		Obj con = Tab.insert(Obj.Con, "$", fact.struct);
		
		con.setLevel(0);
		con.setAdr(fact.getValBool() ? 1 : 0);
		Code.load(con);
	}
	
	public void visit(FactorNewArray fact) {
		Code.put(Code.newarray);
	}
	
	public void visit(FactorDesignator fact) {
		
	}
	
	public void visit(DesignatorFuncCall funcCall) {
		Obj funcObj = funcCall.getDesignator().obj;
		int offset = funcObj.getAdr() - Code.pc;

		Code.put(Code.call);
		Code.put2(offset);
		
	}
	
	public void visit(DesignatorIncrement designator) {
		Obj obj = designator.getDesignator().obj;
		
		if (obj.getKind() == Obj.Elem) {
			Code.put(Code.dup2);
		}
		
		Code.load(obj);
		
		Code.loadConst(1);
		Code.put(Code.add);
		
		Code.store(obj);
	}
	
	public void visit(DesignatorDecrement designator) {
		Obj obj = designator.getDesignator().obj;
		
		if (obj.getKind() == Obj.Elem) {
			Code.put(Code.dup2);
		}
		
		Code.load(obj);
		
		Code.loadConst(1);
		Code.put(Code.sub);
		
		Code.store(obj);
	}
	
	public void visit(NegativeExpr expr) {
		Code.put(Code.neg);
	}
	
	public void visit(AddopExpr expr) {
		Code.put((expr.getAddop() instanceof Plus) ? Code.add : Code.sub);
	}
	
	public void visit(MulTerm term) {
		if (term.getMulop() instanceof Mul) {
			Code.put(Code.mul);
		} else if (term.getMulop() instanceof Div) {
			Code.put(Code.div);
		} else if (term.getMulop() instanceof Percent){
			Code.put(Code.rem);
		}
	}
} 
