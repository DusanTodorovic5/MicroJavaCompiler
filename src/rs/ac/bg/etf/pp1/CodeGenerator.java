package rs.ac.bg.etf.pp1;

import rs.etf.pp1.mj.runtime.*;
import rs.etf.pp1.symboltable.Tab;
import rs.etf.pp1.symboltable.concepts.Obj;

import java.util.ArrayList;
import java.util.HashMap;
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
	Stack<ArrayList<Integer>> addBreak = new Stack<ArrayList<Integer>>();
	Stack<ArrayList<Integer>> addContinue = new Stack<ArrayList<Integer>>();
	Stack<Integer> addWhile = new Stack<Integer>();
	
	
	HashMap<String, Obj> internalMap = new HashMap<String, Obj>();
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
		internalMap.put(designator.getName(), designator.obj);
		
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
			Code.put(Code.print);
		} else if (printStmt.getExpr().struct == Tab.charType) {
			Code.put(Code.bprint);
		}
	}
	
	public void visit(ReadStatement readStmt) {
		Code.put(readStmt.getDesignator().obj.getType() == Tab.charType ?
			Code.bread : Code.read);
		Code.store(readStmt.getDesignator().obj);
	}
	
	public void visit(FactorNumber fact) {
		Obj con = Tab.insert(Obj.Con, "$", Tab.intType);
		
		con.setLevel(0);
		con.setAdr(fact.getValNum());
		Code.load(con);
	}
	
	public void visit(FactorChar fact) {
		Obj con = Tab.insert(Obj.Con, "$", Tab.charType);
		
		con.setLevel(0);
		con.setAdr(fact.getValChar());
		Code.load(con);
	}
	
	public void visit(FactorBool fact) {
		Obj con = Tab.insert(Obj.Con, "$", TabExtension.boolType);
		
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
	
	boolean hasArr = false;
	
	public void visit(DesignatorArray designator) {
		if (hasArr) {
			for (int i=lista.size() - 1; i>= 0;i--) {
				if (lista.get(i) != Tab.noObj) {
					Code.load(designator.getDesignator().obj);
					Code.loadConst(i);
					Code.put(Code.aload);
					Code.store(lista.get(i));
				}
			}
		} else {
			for (int i=0;i<lista.size();i++) {
				if (lista.get(i) != Tab.noObj) {
					Code.load(designator.getDesignator().obj);
					Code.loadConst(i);
					Code.put(Code.aload);
					Code.store(lista.get(i));
				}
			}
		}
		
		lista = new ArrayList<Obj>();
	}
	
	public void visit(DesignatorExists designator) {
		lista.add(designator.getDesignator().obj);
		if (designator.getDesignator() instanceof ArrayDesignator) {
			hasArr = true;
		}
	}
	
	public void visit(DesignatorDoesntExist designator) {
		lista.add(Tab.noObj);
	}
	
	public void visit(DesignatorFunc func) {
		Obj methodNode = func.getDesignatorFuncCall().getDesignator().obj;
		
		if (methodNode.getName().equals("ord") || methodNode.getName().equals("chr")) {
			return;
		}
		if (methodNode.getName().equals("len")) {
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
		
		if (methodNode.getName().equals("ord") || methodNode.getName().equals("chr")) {
			return;
		}
		
		if (methodNode.getName().equals("len")) {
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
		addAnd.peek().clear();
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
		// list.clear();
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
	
	public void visit(EndOfIfCondition condition) {
		ArrayList<Integer> list = addOr.peek();
		for (int add : list) {
			Code.fixup(add);
		}
		addOr.peek().clear();
	}
	
	public void visit(ConditionWithOp condition) {
		
		if (condition.getRelop() instanceof Equals) {
			Code.putFalseJump(Code.eq, 0);
		} else if (condition.getRelop() instanceof NotEquals) {
			Code.putFalseJump(Code.ne, 0);
		} else if (condition.getRelop() instanceof More) {
			Code.putFalseJump(Code.gt, 0);
		} else if (condition.getRelop() instanceof NotLess) {
			Code.putFalseJump(Code.ge, 0);
		} else if (condition.getRelop() instanceof Less) {
			Code.putFalseJump(Code.lt, 0);
		} else if (condition.getRelop() instanceof NotMore) {
			Code.putFalseJump(Code.le, 0);
		}
		addAnd.peek().add(Code.pc - 2);
	}
	
	public void visit(ConditionWithoutOp condition) {
		Code.loadConst(1);
		Code.putFalseJump(Code.eq, 0);
		addAnd.peek().add(Code.pc - 2);
	}
	
	public void visit(WhileStart ws) {
		addOr.push(new ArrayList<Integer>());
		addAnd.push(new ArrayList<Integer>());
		addBreak.push(new ArrayList<Integer>());
		addContinue.push(new ArrayList<Integer>());
		
		addWhile.push(Code.pc);
	}
	
	public void visit (WhileEnd we) {
		ArrayList<Integer> list = addOr.peek();
		for (int add : list) {
			Code.fixup(add);
		}
		addOr.peek().clear();
				
		Code.putJump(addWhile.peek());
		
		list = addAnd.peek();
		for (int add : list) {
			Code.fixup(add);
		}
		addAnd.peek().clear();
		
		list = addBreak.peek();
		for (int add : list) {
			Code.fixup(add);
		}

		addBreak.peek().clear();
	}
	
	public void visit(WhileStatement statement) {
		addOr.pop();
		addAnd.pop();
		addBreak.pop();
		addContinue.pop();
		addWhile.pop();
	}
	
	public void visit(BreakStatement statement) {
		Code.putJump(0);
		addBreak.peek().add(Code.pc - 2);
	}
	
	public void visit(ContinueStatement statement) {
		Code.putJump(addWhile.peek());
		addContinue.peek().add(Code.pc - 2);
	}
	
	Stack<Obj> currentForEachDes = new Stack<Obj>();
	Stack<Integer> foreachStack = new Stack<Integer>();
	
	public void visit(ForEachStartDes foreach) {
		currentForEachDes.push(foreach.getDesignator().obj);
		Code.load(foreach.getDesignator().obj);
		Code.put(Code.arraylength);
		Code.loadConst(0);
	}
	
	public void visit(ForEachEnd foreach) {
		Code.loadConst(1);
		Code.put(Code.add);
		Code.put(Code.dup2);
		Code.putFalseJump(Code.eq, foreachStack.peek());
		Code.put(Code.pop);
		Code.put(Code.pop);
	}
	
	public void visit(ForEachStart foreach) {
		foreachStack.push(Code.pc);
		Code.put(Code.dup);
		Code.load(currentForEachDes.peek());
		Code.put(Code.dup_x1);
		Code.put(Code.pop);
		Code.put(Code.aload);
		Code.store(foreach.obj);
	}
	
	public void visit(ForeachStatement foreach) {
		foreachStack.pop();
		currentForEachDes.pop();
	}
} 
