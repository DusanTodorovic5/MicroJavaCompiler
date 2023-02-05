package rs.ac.bg.etf.pp1;

import rs.etf.pp1.mj.runtime.*;
import rs.etf.pp1.symboltable.Tab;
import rs.etf.pp1.symboltable.concepts.Obj;

import java.util.ArrayList;
import java.util.Stack;

import rs.ac.bg.etf.pp1.CounterVisitor.FormParamCounter;
import rs.ac.bg.etf.pp1.CounterVisitor.VarCounter;
import rs.ac.bg.etf.pp1.SemanticVisitor.RelativeOperation;
import rs.ac.bg.etf.pp1.ast.*;

public class CodeGenerator extends VisitorAdaptor {
	private int mainPc;
	boolean voidMethod = false;
	
	ArrayList<Obj> lista = new ArrayList<Obj>();
	Stack<ArrayList<Integer>> addOr = new Stack<ArrayList<Integer>>();
	Stack<ArrayList<Integer>> addAnd = new Stack<ArrayList<Integer>>();
	Stack<ArrayList<Integer>> addThen = new Stack<ArrayList<Integer>>();
	
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
		
		if (DesignatorAssign.class != parent.getClass() && 
			DesignatorFuncCall.class != parent.getClass() &&
			FactorDesignator.class != parent.getClass() &&
			DesignatorIncrement.class != parent.getClass() &&
			DesignatorDecrement.class != parent.getClass() &&
			ReadStatement.class != parent.getClass() &&
			DesignatorArray.class != parent.getClass()) {
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
		Code.put(fact.struct.getElemType() == Tab.charType ? 0 : 1);
	}
	
	public void visit(ArrayDesignator designator) {
		// Code.load(designator.getDesignator().obj);
	}
	
	public void visit(FactorDesignator fact) {
		Code.load(fact.getDesignator().obj);
	}
	
	public void visit(DesignatorFuncCall funcCall) {
//		Obj funcObj = funcCall.getDesignator().obj;
//		int offset = funcObj.getAdr() - Code.pc;
//
//		Code.put(Code.call);
//		Code.put2(offset);
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
	
	
	public void visit(DesignatorArray designator) {
		for (int i=0;i<lista.size();i++) {
			if (lista.get(i) != Tab.noObj) {
				Code.load(designator.getDesignator().obj);
				Code.loadConst(i);
				Code.put(Code.aload);
				Code.store(lista.get(i));
			}
		}
		lista = new ArrayList<Obj>();
	}
	
	public void visit(DesignatorExists designator) {
		lista.add(designator.getDesignator().obj);
	}
	
	public void visit(DesignatorDoesntExist designator) {
		lista.add(Tab.noObj);
	}
	
	public void visit(DesignatorFunc func) {
		Obj methodNode = func.getDesignatorFuncCall().getDesignator().obj;
		
		if (methodNode.equals("ord") || methodNode.equals("chr")) {
			return;
		}
		if (methodNode.equals("len")) {
			Code.put(Code.arraylength);
			return;
		}
		
		int offset = methodNode.getAdr() - Code.pc;
		Code.put(Code.call);
		Code.put2(offset);
		
		if (methodNode.getType() != Tab.noType) {
			Code.put(Code.pop);
		}
	}
	
	public void visit(FactorDesignatorActPars func) {
		Obj methodNode = func.getDesignatorFuncCall().getDesignator().obj;
		
		if (methodNode.equals("ord") || methodNode.equals("chr")) {
			return;
		}
		if (methodNode.equals("len")) {
			Code.put(Code.arraylength);
			return;
		}
		
		int offset = methodNode.getAdr() - Code.pc;
		Code.put(Code.call);
		Code.put2(offset);
	}
	
	public void visit(IfStatement stmt) {
		addOr.pop();
		addAnd.pop();
		addThen.pop();
	}
	
	public void visit(IfStartStmt stmt) {
		addOr.push(new ArrayList<Integer>());
		addAnd.push(new ArrayList<Integer>());
		addThen.push(new ArrayList<Integer>());
	}	
	
	public void visit(IfEndStmt stmt) {
		ArrayList<Integer> list = addOr.peek();
		for (int add : list) {
			Code.fixup(add);
		}
		list.clear();
	}
	
	public void visit(OrEnd endOr) {
		Code.putJump(0);
		addOr.peek().add(Code.pc - 2);
		ArrayList<Integer> list = addAnd.peek();
		for (int add : list) {
			Code.fixup(add);
		}
		list.clear();
	}
	
	public void visit(EndOfIfStatement stmt) {
		if (stmt.getParent() instanceof IfElseStatement) {
			Code.putJump(0);
			addThen.peek().add(Code.pc - 2);
		}
		
		ArrayList<Integer> list = addAnd.peek();
		for (int add : list) {
			Code.fixup(add);
		}
		list.clear();
	}
	
	public void visit(IfElseStatement stmt) {
		ArrayList<Integer> list = addThen.peek();
		for (int add : list) {
			Code.fixup(add);
		}
		
		addOr.pop();
		addAnd.pop();
		addThen.pop();
	}
	
	
	
	public void visit(ConditionWithOp condition) {
		addAnd.peek().add(Code.pc - 2);
	}
	
	public void visit(ConditionWithoutOp condition) {
		Code.loadConst(1);
		Code.putFalseJump(Code.eq, 0);
		addAnd.peek().add(Code.pc - 2);
	}
	
	public void visit(Equals relop) {
		Code.putFalseJump(Code.eq, 0);
		addAnd.peek().add(Code.pc - 2);
	}
	
	public void visit(NotEquals relop) {
		Code.putFalseJump(Code.ne, 0);
		addAnd.peek().add(Code.pc - 2);
	}
	
	public void visit(More relop) {
		Code.putFalseJump(Code.gt, 0);
		addAnd.peek().add(Code.pc - 2);
	}
	
	public void visit(NotLess relop) {
		Code.putFalseJump(Code.ge, 0);
		addAnd.peek().add(Code.pc - 2);
	}
	
	public void visit(Less relop) {
		Code.putFalseJump(Code.lt, 0);
		addAnd.peek().add(Code.pc - 2);
	}
	
	public void visit(NotMore relop) {
		Code.putFalseJump(Code.le, 0);
		addAnd.peek().add(Code.pc - 2);
	}
} 
