package rs.ac.bg.etf.pp1;

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
	
	boolean isArray = false;
	int isInLoop = 0;
	RelativeOperation currentRelativeOperation = null;
	
	Logger log = Logger.getLogger(getClass());
	
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
		log.info(msg.toString());
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
			report_semantic_error("type " + type.getTypeName() + " not found in STable", null);
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
		methodType.struct = currentType;
		currentMethodReturnType = currentType;
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
		
		report_info("method: " + currentMethodStr + " defined with " + currentMethodVarCount + " and " + currentMethodParamCount, null);
		
		currentMethod = null;
		currentMethodStr = null;
		currentMethodVarCount = 0;
		currentMethodParamCount = 0;
		currentMethodReturnType = null;
		currentMethodHasReturn = false;
	}
	
	public void visit(FormParsDecl formalParam) {
		currentMethodParamCount++;

		if (Tab.currentScope.findSymbol(formalParam.getFormParIdent()) != null) {
			report_semantic_error("symbol " + formalParam.getFormParIdent() + " is already defined",null);
			return;
		}
		
		if (isArray) {
			Obj obj = Tab.insert(Obj.Var, formalParam.getFormParIdent(), new Struct(Struct.Array, currentType));
		} else {
			Obj obj = Tab.insert(Obj.Var, formalParam.getFormParIdent(), currentType);
		}
	}
	
	public void visit(WhileStart whileStart) {
		isInLoop++;
	}
	
	public void visit(WhileStatement whileStatement) {
		isInLoop--;
	}
	
	public void visit(ForEachStart forStart) {
		isInLoop++;
	}
	
	public void visit(ForeachStatement forEach) {
		isInLoop--;
	}
	
	public void visit (BreakStatement statement) {
		if (isInLoop == 0) {
			report_semantic_error("cannot use break outside of a loop", statement);
		}
	}
	
	public void visit (ContinueStatement statement) {
		if (isInLoop == 0) {
			report_semantic_error("cannot use continue outside of a loop", statement);
		}
	}
	
	public void visit (VoidReturnStatement statement) {
		if (currentMethodReturnType != Tab.noType) {
			report_semantic_error("cannot have void return in non-void function " + currentMethodStr, statement);
		}
	}
	
	public void visit (ReturnStatement statement) {
		if (currentMethodReturnType == Tab.noType) {
			report_semantic_error("non-void function " + currentMethodStr + " must return value", statement);
		}
		currentMethodHasReturn = true;
	}	
	
	public void visit(IdentDesignator designator) {
		Obj obj = Tab.find(designator.getName());
		if (obj == Tab.noObj) {
			report_semantic_error_on_line(designator.getName() + " is not declared", null, designator.getLine());
		}
		
		designator.obj = obj;
	}
	
	public void visit(DesignatorFunc designatorFunc) {
		Obj func = designatorFunc.getDesignator().obj;
		
		if (Obj.Meth == func.getKind()) {
			designatorFunc.struct = func.getType();
		} else {
			report_semantic_error_on_line(designatorFunc.getLine() + ": "  + " not a function", null, designatorFunc.getLine());
		}
	}
	
	public void visit(ConditionWithoutOp condition) {
		if (condition.getExpr().struct != TabExtension.boolType) {
			report_semantic_error("condition must be of type bool", condition);
		}
	}
	
	public void visit(ConditionWithOp condition) {
		if (condition.getExpr().struct != condition.getExpr1().struct) {
			report_semantic_error("condition must be of type bool", condition);
			return;
		}
		
		if (currentRelativeOperation == RelativeOperation.EQUALS || currentRelativeOperation == RelativeOperation.NOTEQUALS) {
			currentRelativeOperation = null;
			return;
		}
		
		if (condition.getExpr().struct == TabExtension.boolType) {
			report_semantic_error("Relative operation cannot be used on variable of type bool", condition);
			return;
		}
		
		currentRelativeOperation = null;
	}
	
	public void visit(FactorDesignator factor) {
		factor.struct = factor.getDesignator().obj.getType();
	}
	
	public void visit(FactorDesignatorActPars factor) {
		if (factor.getDesignator().obj.getKind() != Obj.Meth) {
			report_semantic_error("identifier " + factor.getDesignator().obj.getName() + " is not a function", factor);
			factor.struct = Tab.noType;
			return;
		}
		
		factor.struct = factor.getDesignator().obj.getType();
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
}



















