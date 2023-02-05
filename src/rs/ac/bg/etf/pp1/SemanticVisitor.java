package rs.ac.bg.etf.pp1;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;
import java.util.Stack;
import java.util.TreeSet;

import org.apache.log4j.Logger;
import rs.ac.bg.etf.pp1.ast.*;
import rs.etf.pp1.symboltable.*;
import rs.etf.pp1.symboltable.concepts.*;

public class SemanticVisitor extends VisitorAdaptor {
	
	enum RelativeOperation { 
		EQUALS,
		NOTEQUALS,
		MORE,
		NOTLESS,
		LESS,
		NOTMORE
	}
	
	private class MethodStruct {
		public int argumentNumber = 0;
		public ArrayList<Struct> argumentTypes = new ArrayList<Struct>();
	}
	
	boolean errorDetected = false;
	boolean hasMainMethod = false;
	boolean hasReturn = false;
	
	Struct currentType = null;
	Obj currentMethod = null;
	int currentMethodVarCount = 0;
	int currentMethodParamCount = 0;
	String currentMethodStr = null;
	Struct currentMethodReturnType = null;
	boolean currentMethodHasReturn = false;
	boolean currentMethodFormalParameters = false;
	
	HashMap<String, MethodStruct> definedMethods = new HashMap<String, MethodStruct>();
	Set<String> constants = new TreeSet<String>();
	String currentFunctionCall = null;
	ArrayList<Struct> currentFunctionArgument = new ArrayList<Struct>();
	
	boolean isArray = false;
	int isInLoop = 0;
	int isInForEachLoop = 0;
	Stack<String> forEachLoopVars = new Stack<String>();
	RelativeOperation currentRelativeOperation = null;
	
	Logger log = Logger.getLogger(getClass());
	
	boolean isDesignatorListAssign = false;
	boolean inDesignatorList = false;
	Struct currentDesignatorListAssignType = null;
	
	SemanticVisitor() {
		Tab.currentScope.addToLocals(new Obj(Obj.Type, "bool", TabExtension.boolType));
		
		Tab.insert(Obj.Meth, "ord", Tab.charType);		
		Tab.insert(Obj.Meth,"len", Tab.intType);
		Tab.insert(Obj.Meth, "chr", Tab.intType);
		
		definedMethods.put("ord", new MethodStruct());
		definedMethods.put("len", new MethodStruct());
		definedMethods.put("chr", new MethodStruct());
		
		definedMethods.get("ord").argumentNumber++;
		definedMethods.get("len").argumentNumber++;
		definedMethods.get("chr").argumentNumber++;		

		definedMethods.get("ord").argumentTypes.add(Tab.charType);
		definedMethods.get("chr").argumentTypes.add(Tab.intType);
	}
	
	public void report_error(String message, SyntaxNode info) {
		errorDetected = true;
		StringBuilder msg = new StringBuilder(message);
		int line = (info == null) ? 0: info.getLine();
		if (line != 0)
			msg.append (" on line ").append(line);
		log.error(msg.toString());
	}
	
	public void report_semantic_error(String message, SyntaxNode info) {
		report_error("Semantic error: " + message, info);
	}
	
	public void report_semantic_error_on_line(String message, SyntaxNode info, int line) {
		report_error("Semantic error on line " + line + ": " + message, info);
	}

	public void report_info(String message, SyntaxNode info) {
		StringBuilder msg = new StringBuilder(message); 
		int line = (info == null) ? 0: info.getLine();
		if (line != 0)
			msg.append (" on line ").append(line);
		log.info("Info: " + msg.toString());
	}
	
	public boolean passed() {
		return !errorDetected;
	}
	
	
	public void visit(ProgName progName) {
		progName.obj =  Tab.insert(Obj.Prog, progName.getName(), Tab.noType);
		Tab.openScope();
	}
	
	public void visit(Program program) {
		Tab.chainLocalSymbols(program.getProgName().obj);
		Tab.closeScope();
	
		if (!hasMainMethod) {
			report_semantic_error("main method not found", null);
		}
	}
	
	public void visit(Type type) {
		Obj typeNode = Tab.find(type.getTypeName());
		if (typeNode == Tab.noObj) {
			report_semantic_error("type " + type.getTypeName() + " not found in symbol table", null);
			type.struct = Tab.noType;
		} else {
			if (Obj.Type == typeNode.getKind()) {
				type.struct = typeNode.getType();
			} else {
				report_semantic_error(type.getTypeName() + " is not a type", null);
				type.struct = Tab.noType;
			}
		}
		
		currentType = type.struct;
	}
	
	public void visit(VarDecl varDecl) {
		
	}
	
	public void visit(ConstNumberDecl constDecl) {
		Obj obj = Tab.find(constDecl.getConstName());
		
		if (obj != Tab.noObj) {
			report_semantic_error("variable with name " + constDecl.getConstName() + " already exists", null);
			return;
		}
		
		if (!Tab.intType.equals(currentType)) {
			report_semantic_error("variable " + constDecl.getConstName() + " is not of type int", null);
			return;
		}
		
		constants.add(constDecl.getConstName());
		obj = Tab.insert(Obj.Con, constDecl.getConstName(), Tab.intType);
		obj.setAdr(constDecl.getConstValue());
		currentMethodVarCount++;
	}
	
	public void visit(ConstCharDecl constDecl) {
		Obj obj = Tab.find(constDecl.getConstName());
		
		if (obj != Tab.noObj) {
			report_semantic_error("variable with name " + constDecl.getConstName() + " already exists", null);
			return;
		}
		
		if (!Tab.charType.equals(currentType)) {
			report_semantic_error("variable " + constDecl.getConstName() + " is not of type char", null);
			return;
		}
		
		constants.add(constDecl.getConstName());
		obj = Tab.insert(Obj.Con, constDecl.getConstName(), Tab.charType);
		obj.setAdr(constDecl.getConstValue());
		currentMethodVarCount++;
	}
	
	public void visit(ConstBoolDecl constDecl) {
		Obj obj = Tab.find(constDecl.getConstName());
		
		if (obj != Tab.noObj) {
			report_semantic_error("variable with name " + constDecl.getConstName() + " already exists", null);
			return;
		}
		
		if (!TabExtension.boolType.equals(currentType)) {
			report_semantic_error("variable " + constDecl.getConstName() + " is not of type int", null);
			return;
		}
		
		constants.add(constDecl.getConstName());
		obj = Tab.insert(Obj.Con, constDecl.getConstName(), TabExtension.boolType);
		obj.setAdr(constDecl.getConstValue() ? 1 : 0);
		currentMethodVarCount++;
	}
	
	public void visit(VarBracketsOpenClosed listVarDecls) {
		isArray = true;
	}
	
	public void visit(NoVarBrackets listVarDecls) {
		isArray = false;
	}
	
	public void visit(VarName varDecl) {
		Obj obj = Tab.currentScope.findSymbol(varDecl.getVarName());
		if (obj != null) {
			report_semantic_error("variable with name " + varDecl.getVarName() + " already defined", null);
			return;
		}
		
		if (isArray) {
			Tab.insert(Obj.Var, varDecl.getVarName(), new Struct(Struct.Array, currentType));
			isArray = false;
		} else {
			Tab.insert(Obj.Var, varDecl.getVarName(), currentType);
		}
		
		currentMethodVarCount++;
	}
	
	public void visit(MethodWithType methodType) {
		methodType.struct = methodType.getType().struct;
		currentMethodReturnType = methodType.getType().struct;
		Obj obj = Tab.currentScope().findSymbol(methodType.getMethodName());
		if (obj != null) {
			report_semantic_error("method with name " + methodType.getMethodName() + " already declared", methodType);
			return;
		}
		
		currentMethod = Tab.insert(Obj.Meth, methodType.getMethodName(), currentType);
		currentMethodStr = methodType.getMethodName();
		currentMethodVarCount = 0;
		currentMethodParamCount = 0;
		Tab.openScope();
		
		definedMethods.put(currentMethodStr, new MethodStruct());
		
		if (methodType.getMethodName().equals("main")) {
			report_semantic_error("main method must be void", null);
			hasMainMethod = true;
		}
	}

	
	public void visit(MethodWithNoType methodType) {
		methodType.struct = Tab.noType;
		currentMethodReturnType = Tab.noType;
		
		Obj obj = Tab.currentScope().findSymbol(methodType.getMethodName());
		if (obj != null) {
			report_semantic_error("method with name " + methodType.getMethodName() + " already declared", methodType);
			return;
		}
		
		currentMethod = Tab.insert(Obj.Meth, methodType.getMethodName(), Tab.noType);
		currentMethodStr = methodType.getMethodName();
		currentMethodVarCount = 0;
		currentMethodParamCount = 0;
		
		definedMethods.put(currentMethodStr, new MethodStruct());
		
		if (methodType.getMethodName().equals("main")) {
			hasMainMethod = true;
		}
		
		Tab.openScope();
	}
	
	public void visit(MethodDeclaration methodDecl) {
		Tab.chainLocalSymbols(currentMethod);
		Tab.closeScope();

		if (currentMethodStr.equals("main") && currentMethodParamCount > 0) {
			report_semantic_error("main method cannot have formal parameters", null);
		}
		
		if (currentMethodHasReturn == false && currentMethodReturnType != Tab.noType) {
			report_semantic_error("non-void function " + currentMethodStr + " must return value", null);
		}
		
		report_info("method: " + currentMethodStr + " defined with " + currentMethodVarCount + " variables and " + currentMethodParamCount + " parameters", null);
		
		currentMethod = null;
		currentMethodStr = null;
		currentMethodVarCount = 0;
		currentMethodParamCount = 0;
		currentMethodReturnType = null;
		currentMethodHasReturn = false;
	}
	
	public void visit(FormParsDecl formalParam) {
		currentMethodParamCount++;
		definedMethods.get(currentMethodStr).argumentNumber++;
		if (Tab.currentScope.findSymbol(formalParam.getFormParIdent()) != null) {
			report_semantic_error("symbol " + formalParam.getFormParIdent() + " is already defined",null);
			return;
		}
		
		if (isArray) {
			Obj obj = Tab.insert(Obj.Var, formalParam.getFormParIdent(), new Struct(Struct.Array, currentType));
		} else {
			Obj obj = Tab.insert(Obj.Var, formalParam.getFormParIdent(), currentType);
		}
		
		if (currentMethod != null) {
			if (isArray) {
				definedMethods.get(currentMethodStr).argumentTypes.add(new Struct(Struct.Array, currentType));
				isArray = false;
			} else {
				definedMethods.get(currentMethodStr).argumentTypes.add(currentType);
			}
		}
	}
	
	public void visit(WhileStart whileStart) {
		isInLoop++;
	}
	
	public void visit(WhileStatement whileStatement) {
		isInLoop--;
	}
	
	public void visit(ForEachStart forStart) {
//		isInLoop++;
		isInForEachLoop++;
		forEachLoopVars.push(forStart.getForeachName());
	}
	
	public void visit(ForeachStatement forEach) {
//		isInLoop--;
		isInForEachLoop--;
		String forEachName = forEachLoopVars.pop();
		
		if (forEach.getDesignator().obj.getType().getKind() != Struct.Array) {
			report_semantic_error("Foreach designator has to be of type array", forEach);
			return;
		}
		
		Obj ident = Tab.find(forEachName);
		if (ident == null || ident.getKind() != Obj.Var) {
			report_semantic_error("Ident " + forEachName + " is not defined", forEach);
			return;
		}
		
		if (ident.getType() != forEach.getDesignator().obj.getType().getElemType()) {
			report_semantic_error("Ident must be of same type as elements of array", forEach);
			return;
		}
	}
	
	public void visit(BreakStatement statement) {
		if (isInLoop == 0) {
			report_semantic_error("cannot use break outside of a loop", statement);
		}
	}
	
	public void visit(ContinueStatement statement) {
		if (isInLoop == 0) {
			report_semantic_error("cannot use continue outside of a loop", statement);
		}
	}
	
	public void visit(VoidReturnStatement statement) {
		if (currentMethodReturnType != Tab.noType) {
			report_semantic_error("cannot have void return in non-void function " + currentMethodStr, statement);
		}
	}
	
	public void visit(ReturnStatement statement) {
		if (currentMethodReturnType == Tab.noType) {
			report_semantic_error("non-void function " + currentMethodStr + " must return value", statement);
		}
		
		currentMethodHasReturn = true;
		if (currentMethodReturnType != statement.getExpr().struct) {
			report_semantic_error("method must return the correct type", statement);
			currentMethodReturnType = null;
		}
	}	
	
	public void visit(IdentDesignator designator) {
		Obj obj = Tab.find(designator.getName());
		
		if (obj == Tab.noObj) {
			report_semantic_error_on_line(designator.getName() + " is not declared", null, designator.getLine());
		}
		
		designator.obj = obj;
	}
	
	public void visit(DesignatorFunc designatorFunc) {
		Obj func = designatorFunc.getDesignatorFuncCall().getDesignator().obj;
		
		if (Obj.Meth == func.getKind()) {
			designatorFunc.struct = func.getType();
		} else {
			report_semantic_error_on_line(designatorFunc.getLine() + ": "  + " not a function", null, designatorFunc.getLine());
			return;
		}
		
		String name = designatorFunc.getDesignatorFuncCall().getDesignator().obj.getName();
		
		if (name.equals("len")) {
			if (currentFunctionArgument.size() > 1) {
				report_semantic_error("Too many arguments in function call " + name, designatorFunc);
				return;
			}
			
			if (currentFunctionArgument.size() < 1) {
				report_semantic_error("Too few arguments in function call " + name, designatorFunc);
				return;
			}
			
			if (currentFunctionArgument.get(0).getKind() != Struct.Array) {
				report_semantic_error("len function only accepts array as argument", designatorFunc);
				return;
			}
			return;
		}
		
		if (currentFunctionArgument.size() < definedMethods.get(name).argumentTypes.size()) {
			report_semantic_error("Too few arguments in function call " + name, designatorFunc);
			return;
		}
		
		if (currentFunctionArgument.size() > definedMethods.get(name).argumentTypes.size()) {
			report_semantic_error("Too many arguments in function call " + name, designatorFunc);
			return;
		}
		
		for (int i=0;i<currentFunctionArgument.size();i++) {
			if (currentFunctionArgument.get(i) != definedMethods.get(name).argumentTypes.get(i)) {
				report_semantic_error("Incorect type on argument on position " + (i+1) + " in function call "+ name, designatorFunc);
			}
		}
		
		currentFunctionCall = null;
		currentFunctionArgument = new ArrayList<Struct>();
	}
	
	public void visit(DesignatorFuncCall funcCall) {
		currentFunctionCall = funcCall.getDesignator().obj.getName();
		currentFunctionArgument = new ArrayList<Struct>();
	}
	
	public void visit(DesignatorStatementAssignOp designatorStm) {
		
	}
	
	
	public void visit(DesignatorIncrement designatorStm) {
		if (isInForEachLoop > 0 && forEachLoopVars.contains(designatorStm.getDesignator().obj.getName())) {
			report_semantic_error("Cannot assign values inside foreach loop", designatorStm);
			return;
		}
		
		if (designatorStm.getDesignator().obj.getKind() == Obj.Meth) {
			report_semantic_error("Cannot assign value to method " + designatorStm.getDesignator().obj.getName(), designatorStm);
			return;
		}
		
		if (designatorStm.getDesignator().obj.getKind() == Obj.Con) {
			report_semantic_error("Cannot assign value to constant " + designatorStm.getDesignator().obj.getName(), designatorStm);
			return;
		}

		if (designatorStm.getDesignator().obj.getType() != Tab.intType) {
			report_semantic_error("cannot increment non integer type", designatorStm);
			return;
		}
		
		if (designatorStm.getDesignator().obj.getKind() == Obj.Prog) {
			report_semantic_error("Cannot assign value to " + designatorStm.getDesignator().obj.getName(), designatorStm);
			return;
		}
		
		designatorStm.struct = designatorStm.getDesignator().obj.getType();
	}

	public void visit(DesignatorDecrement designatorStm) {
		if (isInForEachLoop > 0 && forEachLoopVars.contains(designatorStm.getDesignator().obj.getName())) {
			report_semantic_error("Cannot assign values inside foreach loop", designatorStm);
			return;
		}
		
		if (designatorStm.getDesignator().obj.getKind() == Obj.Meth) {
			report_semantic_error("Cannot assign value to method " + designatorStm.getDesignator().obj.getName(), designatorStm);
			return;
		}
		
		if (designatorStm.getDesignator().obj.getKind() == Obj.Prog) {
			report_semantic_error("Cannot assign value to " + designatorStm.getDesignator().obj.getName(), designatorStm);
			return;
		}
		
		if (designatorStm.getDesignator().obj.getKind() == Obj.Con) {
			report_semantic_error("Cannot assign value to constant " + designatorStm.getDesignator().obj.getName(), designatorStm);
			return;
		}
		
		if (designatorStm.getDesignator().obj.getType() != Tab.intType) {
			report_semantic_error("cannot decrement non integer type", designatorStm);
		}
		
		designatorStm.struct = designatorStm.getDesignator().obj.getType();
	}
	
	public void visit(DesignatorAssign assignOp) {
		if (isInForEachLoop > 0 && forEachLoopVars.contains(assignOp.getDesignator().obj.getName())) {
			report_semantic_error("Cannot assign values inside foreach loop", assignOp);
			return;
		}
		
		if (assignOp.getDesignator().obj.getKind() == Obj.Meth) {
			report_semantic_error("Cannot assign value to method " + assignOp.getDesignator().obj.getName(), assignOp);
			return;
		}
		
		if (assignOp.getDesignator().obj.getKind() == Obj.Prog) {
			report_semantic_error("Cannot assign value to " + assignOp.getDesignator().obj.getName(), assignOp);
			return;
		}
		
		if (assignOp.getDesignator().obj == null) {
			report_semantic_error(assignOp.getDesignator().obj.getName() + " is not defined", assignOp);
			return;
		}
		
		if (assignOp.getDesignator().obj.getKind() == Obj.Con) {
			report_semantic_error("Cannot assign value to constant field " + assignOp.getDesignator().obj.getName(), assignOp);
			return;
		}
		
		if (assignOp.getDesignator().obj.getType().getKind() == Struct.Array) {
			if (assignOp.getExpr().struct.getKind() != Struct.Array) {
				report_semantic_error("right side operator must be array", assignOp);
				return;
			}
			
			if (assignOp.getDesignator().obj.getType().getElemType() != assignOp.getExpr().struct.getElemType()) {
				report_semantic_error("left type of assign operator different from right", assignOp);
			}
		} else {
			if (assignOp.getDesignator().obj.getType() != assignOp.getExpr().struct) {
				report_semantic_error("left type of assign operator different from right", assignOp);
			}
		}
	}
	
	public void visit(DesignatorArray assignOp) {
		if (assignOp.getDesignator().obj.getType().getKind() != Struct.Array) {
			report_semantic_error("variable" + assignOp.getDesignator().obj.getName() + " must be array", assignOp);
			return;
		}
		
		if (assignOp.getDesignator().obj.getType().getElemType() != currentDesignatorListAssignType) {
			report_semantic_error("incompatible type of " + assignOp.getDesignator().obj.getName() + " and values trying to assign", assignOp);
			return;
		}
		
		if (isDesignatorListAssign) {			
			report_info("assigned values to array " + assignOp.getDesignator().obj.getName(), assignOp);
		}
		
		currentDesignatorListAssignType = null;
		isDesignatorListAssign = false;
		inDesignatorList = false;
	}
	
	public void visit(SingleDesignatorList desList) {
//		currentDesignatorListAssignType = desList.getDesignator().obj.getType();
		isDesignatorListAssign = true;
	}

	public void visit(DesignatorExists desList) {
		if (inDesignatorList == false) {
			currentDesignatorListAssignType = desList.getDesignator().obj.getType();
		}
		
		inDesignatorList = true;
		if (currentDesignatorListAssignType != desList.getDesignator().obj.getType()) {
			report_semantic_error("type of " + desList.getDesignator().obj.getName() + " inside list is incompatible", desList);
			isDesignatorListAssign = false;
		}
	}
	
	public void visit(ActParsListWithComa actpars) {
		currentFunctionArgument.add(actpars.getExpr().struct);
	}
	
	public void visit(SingleActParsList actpars) {
		currentFunctionArgument.add(actpars.getExpr().struct);
	}
	
	public void visit(ConditionWithoutOp condition) {
		if (condition.getExpr().struct != TabExtension.boolType) {
			report_semantic_error("condition must be of type bool", condition);
		}
	}
	
	public void visit(ConditionWithOp condition) {
		if (condition.getExpr().struct != condition.getExpr1().struct) {
			report_semantic_error("types in condition must be compatible types!", condition);
			return;
		}		
		
		if ((currentRelativeOperation == RelativeOperation.EQUALS || 
			currentRelativeOperation == RelativeOperation.NOTEQUALS) &&
			(condition.getExpr().struct.getKind() == Struct.Array || condition.getExpr().struct.getKind() == Struct.Class)) {
			currentRelativeOperation = null;
			report_semantic_error("such relative operation cannot be used on type array or class", condition);
			return;
		}
		
		// if (condition.getExpr().struct == TabExtension.boolType) {
		// 	report_semantic_error("relative operation cannot be used on variable of type bool", condition);
		// 	return;
		// }
		
		// currentRelativeOperation = null;
	}
	
	public void visit(FactorDesignator factor) {
		factor.struct = factor.getDesignator().obj.getType();
	}
	
	public void visit(FactorDesignatorActPars factor) {
		if (factor.getDesignatorFuncCall().getDesignator().obj.getKind() != Obj.Meth) {
			report_semantic_error("identifier " + factor.getDesignatorFuncCall().getDesignator().obj.getName() + " is not a function", factor);
			factor.struct = Tab.noType;
			return;
		}
		
		factor.struct = factor.getDesignatorFuncCall().getDesignator().obj.getType();
		
		
		Obj func = factor.getDesignatorFuncCall().getDesignator().obj;
		
		String name = factor.getDesignatorFuncCall().getDesignator().obj.getName();
		
		if (definedMethods.get(name) == null) {
			report_semantic_error("Method with name " + name + " not declared", factor);
			return;
		}
		
		if (currentFunctionArgument.size() < definedMethods.get(name).argumentTypes.size()) {
			report_semantic_error("Too few arguments in function call " + name, factor);
			return;
		}
		
		if (currentFunctionArgument.size() > definedMethods.get(name).argumentTypes.size()) {
			report_semantic_error("Too many arguments in function call " + name, factor);
			return;
		}
		
		for (int i=0;i<currentFunctionArgument.size();i++) {
			if (currentFunctionArgument.get(i) != definedMethods.get(name).argumentTypes.get(i)) {
				report_semantic_error("Incorect type of argument on position " + (i+1) + " in function call "+ name, factor);
			}
		}
		
		currentFunctionCall = null;
		currentFunctionArgument = new ArrayList<Struct>();
	}
	
	public void visit(ArrayDesignator designator) {
		if (designator.getExpr().struct != Tab.intType) {
			report_semantic_error("Expresion inside [] must be of type integer", designator);
			return;
		}
		
		if (designator.getDesignator().obj.getType().getKind() != Struct.Array) {
			report_semantic_error("Designator" + designator.getDesignator().obj.getName() + "must be array", designator);
			return;
		}
		
		designator.obj = new Obj(Obj.Elem, designator.getDesignator().obj.getName(), designator.getDesignator().obj.getType().getElemType());
	}
	
	public void visit(FactorNumber factor) {
		factor.struct = Tab.intType;
	}
	
	public void visit(FactorChar factor) {
		factor.struct = Tab.charType;
	}
	
	public void visit(FactorBool factor) {
		factor.struct = TabExtension.boolType;
	}
	
	public void visit(FactorNewObj factor) {
		
	}
	
	public void visit(FactorNewArray factor) {
		if (factor.getExpr().struct != Tab.intType) {
			report_semantic_error("Expression must be of type int", factor);
		}
		factor.struct = new Struct(Struct.Array, factor.getType().struct);
	}
	
	public void visit(FactorExpr factor) {
		factor.struct = factor.getExpr().struct;
	}
	
	public void visit(NormalTerm term) {
//		if (term.getFactor().struct != Tab.intType) {
//			report_semantic_error("Term must have value of int", term);
//		}
		term.struct = term.getFactor().struct;
	}
	
	public void visit(MulTerm term) {
		if (term.getTerm().struct != Tab.intType || term.getFactor().struct != Tab.intType) {
			report_semantic_error("Term must have value of int", term);
		}
		term.struct = Tab.intType;
	}
	
	public void visit(NegativeExpr expr) {
		if (expr.getTerm().struct != Tab.intType) {
			report_semantic_error("Term must have value of int", expr);
		}
		expr.struct = expr.getTerm().struct;
	}
	
	public void visit(PositiveExpr expr) {
		expr.struct = expr.getTerm().struct;
	}
	
	public void visit(AddopExpr expr) {
		if (expr.getTerm().struct != Tab.intType || expr.getExpr().struct != Tab.intType) {
			report_semantic_error("Term must have value of int", expr);
		}
		expr.struct = Tab.intType;
	}
	
	public void visit(Equals relop) {
		currentRelativeOperation = RelativeOperation.EQUALS;
	}
	
	public void visit(NotEquals relop) {
		currentRelativeOperation = RelativeOperation.NOTEQUALS;
	}
	
	public void visit(More relop) {
		currentRelativeOperation = RelativeOperation.MORE;
	}
	
	public void visit(NotLess relop) {
		currentRelativeOperation = RelativeOperation.NOTLESS;
	}
	
	public void visit(Less relop) {
		currentRelativeOperation = RelativeOperation.LESS;
	}
	
	public void visit(NotMore relop) {
		currentRelativeOperation = RelativeOperation.NOTMORE;
	}
	
	public void visit(ReadStatement statement) {
		if (statement.getDesignator().obj.getType() != Tab.intType && 
			statement.getDesignator().obj.getType() != TabExtension.boolType &&
			statement.getDesignator().obj.getType() != Tab.charType) {
			report_semantic_error("incorrect type in read statement", statement);
		}
	}
	
	public void visit(PrintStatement statement) {
		if (statement.getExpr().struct != Tab.intType &&
				statement.getExpr().struct != TabExtension.boolType &&
				statement.getExpr().struct != Tab.charType) {
			report_semantic_error("incorrect type in read statement", statement);
		}
	}
	
	public void visit(PrintNumberStatement statement) {
		if (statement.getExpr().struct != Tab.intType &&
				statement.getExpr().struct != TabExtension.boolType &&
				statement.getExpr().struct != Tab.charType) {
			report_semantic_error("incorrect type in read statement", statement);
		}
	}
}



















