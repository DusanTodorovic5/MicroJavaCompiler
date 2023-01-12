// generated with ast extension for cup
// version 0.8
// 12/0/2023 15:8:12


package rs.ac.bg.etf.pp1.ast;

public interface Visitor { 

    public void visit(MethodDecl MethodDecl);
    public void visit(Mulop Mulop);
    public void visit(Relop Relop);
    public void visit(MethodType MethodType);
    public void visit(NonLastVarDecl NonLastVarDecl);
    public void visit(StatementList StatementList);
    public void visit(LastVarDecl LastVarDecl);
    public void visit(Addop Addop);
    public void visit(Factor Factor);
    public void visit(CondTerm CondTerm);
    public void visit(AssignOpExpression AssignOpExpression);
    public void visit(MethodDeclListClass MethodDeclListClass);
    public void visit(FormParsBrackets FormParsBrackets);
    public void visit(Designator Designator);
    public void visit(Term Term);
    public void visit(FormParsList FormParsList);
    public void visit(Condition Condition);
    public void visit(ActParsList ActParsList);
    public void visit(IfCondition IfCondition);
    public void visit(ListConstDecls ListConstDecls);
    public void visit(ConstructorDeclList ConstructorDeclList);
    public void visit(ClassExtends ClassExtends);
    public void visit(VarDeclList VarDeclList);
    public void visit(Expr Expr);
    public void visit(ClassFunctions ClassFunctions);
    public void visit(ActPars ActPars);
    public void visit(FormParsListWithComa FormParsListWithComa);
    public void visit(DesignatorList DesignatorList);
    public void visit(DesignatorStatement DesignatorStatement);
    public void visit(ClassFields ClassFields);
    public void visit(Statement Statement);
    public void visit(ListVarDecls ListVarDecls);
    public void visit(VarBrackets VarBrackets);
    public void visit(CondFact CondFact);
    public void visit(ConstDecls ConstDecls);
    public void visit(MethodDeclList MethodDeclList);
    public void visit(NonEmptyDesignatorList NonEmptyDesignatorList);
    public void visit(FormPars FormPars);
    public void visit(Percent Percent);
    public void visit(Div Div);
    public void visit(Mul Mul);
    public void visit(Minus Minus);
    public void visit(Plus Plus);
    public void visit(NotMore NotMore);
    public void visit(Less Less);
    public void visit(NotLess NotLess);
    public void visit(More More);
    public void visit(NotEquals NotEquals);
    public void visit(Equals Equals);
    public void visit(Assignop Assignop);
    public void visit(Label Label);
    public void visit(ArrayDesignator ArrayDesignator);
    public void visit(DotDesignator DotDesignator);
    public void visit(IdentDesignator IdentDesignator);
    public void visit(FactorExpr FactorExpr);
    public void visit(FactorNewArray FactorNewArray);
    public void visit(FactorNewObj FactorNewObj);
    public void visit(FactorBool FactorBool);
    public void visit(FactorChar FactorChar);
    public void visit(FactorNumber FactorNumber);
    public void visit(FactorDesignatorActPars FactorDesignatorActPars);
    public void visit(FactorDesignator FactorDesignator);
    public void visit(MulTerm MulTerm);
    public void visit(NormalTerm NormalTerm);
    public void visit(AddopExpr AddopExpr);
    public void visit(PositiveExpr PositiveExpr);
    public void visit(NegativeExpr NegativeExpr);
    public void visit(ConditionWithoutOp ConditionWithoutOp);
    public void visit(ConditionWithOp ConditionWithOp);
    public void visit(JustCondTerm JustCondTerm);
    public void visit(ConditionAnd ConditionAnd);
    public void visit(JustCondition JustCondition);
    public void visit(ConditionDerived1 ConditionDerived1);
    public void visit(ConditionOR ConditionOR);
    public void visit(SingleActParsList SingleActParsList);
    public void visit(ActParsListWithComa ActParsListWithComa);
    public void visit(EmptyActPars EmptyActPars);
    public void visit(ActParsWithList ActParsWithList);
    public void visit(SingleDesignatorList SingleDesignatorList);
    public void visit(NonEmptyDesignatorListWithComa NonEmptyDesignatorListWithComa);
    public void visit(EmptyDesignatorList EmptyDesignatorList);
    public void visit(DesignatorListNonEmpty DesignatorListNonEmpty);
    public void visit(DesignatorDecrement DesignatorDecrement);
    public void visit(DesignatorIncrement DesignatorIncrement);
    public void visit(DesignatorFunc DesignatorFunc);
    public void visit(DesignatorStatementAssignOp DesignatorStatementAssignOp);
    public void visit(DesignatorArray DesignatorArray);
    public void visit(ErrorDesignatorAssign ErrorDesignatorAssign);
    public void visit(DesignatorAssign DesignatorAssign);
    public void visit(ErrorIfConditionStatement ErrorIfConditionStatement);
    public void visit(IfConditionStatement IfConditionStatement);
    public void visit(ForEachStart ForEachStart);
    public void visit(WhileStart WhileStart);
    public void visit(BlockStatement BlockStatement);
    public void visit(ForeachStatement ForeachStatement);
    public void visit(PrintNumberStatement PrintNumberStatement);
    public void visit(PrintStatement PrintStatement);
    public void visit(ReadStatement ReadStatement);
    public void visit(ReturnStatement ReturnStatement);
    public void visit(VoidReturnStatement VoidReturnStatement);
    public void visit(ContinueStatement ContinueStatement);
    public void visit(BreakStatement BreakStatement);
    public void visit(WhileStatement WhileStatement);
    public void visit(IfElseStatement IfElseStatement);
    public void visit(IfStatement IfStatement);
    public void visit(DesignatorStatementState DesignatorStatementState);
    public void visit(EmptyStatementList EmptyStatementList);
    public void visit(StatementListWithStatement StatementListWithStatement);
    public void visit(FormParsDecl FormParsDecl);
    public void visit(FormParList FormParList);
    public void visit(FormParsListWithComaList FormParsListWithComaList);
    public void visit(FormParsListError FormParsListError);
    public void visit(FormParsListWithComaDone FormParsListWithComaDone);
    public void visit(FormParsError FormParsError);
    public void visit(FormParsEmptyList FormParsEmptyList);
    public void visit(FormParsWithList FormParsWithList);
    public void visit(FormParsBracketsExists FormParsBracketsExists);
    public void visit(MethodWithNoType MethodWithNoType);
    public void visit(MethodWithType MethodWithType);
    public void visit(MethodDeclaration MethodDeclaration);
    public void visit(NoMethodDeclList NoMethodDeclList);
    public void visit(HasMethodDeclList HasMethodDeclList);
    public void visit(NoMethodDeclListClass NoMethodDeclListClass);
    public void visit(HasMethodDeclListClass HasMethodDeclListClass);
    public void visit(ConstructorDecl ConstructorDecl);
    public void visit(SingleConstructor SingleConstructor);
    public void visit(MultipleConstructors MultipleConstructors);
    public void visit(ClassFunctionsWithoutAnything ClassFunctionsWithoutAnything);
    public void visit(ClassFunctionsWithOnlyPar ClassFunctionsWithOnlyPar);
    public void visit(ClassFunctionsWithConstructor ClassFunctionsWithConstructor);
    public void visit(ClassFunctionsWithMethods ClassFunctionsWithMethods);
    public void visit(ClassFunctionsWithConstructorAndMethods ClassFunctionsWithConstructorAndMethods);
    public void visit(NoClassFields NoClassFields);
    public void visit(HasClassFields HasClassFields);
    public void visit(ClassBody ClassBody);
    public void visit(ClassExtendsError ClassExtendsError);
    public void visit(BaseClass BaseClass);
    public void visit(ExtendedClass ExtendedClass);
    public void visit(ClassDecl ClassDecl);
    public void visit(LastListVarDeclError LastListVarDeclError);
    public void visit(LastVarDeclaration LastVarDeclaration);
    public void visit(ListVarDeclError ListVarDeclError);
    public void visit(NonLastVarDeclaration NonLastVarDeclaration);
    public void visit(ListNoVarDecls ListNoVarDecls);
    public void visit(ListVarDeclsWithComa ListVarDeclsWithComa);
    public void visit(VarDecl VarDecl);
    public void visit(NoVarBrackets NoVarBrackets);
    public void visit(VarBracketsOpenClosed VarBracketsOpenClosed);
    public void visit(VarName VarName);
    public void visit(ListNoConstDeclarations ListNoConstDeclarations);
    public void visit(ListConstDeclarations ListConstDeclarations);
    public void visit(ConstBoolDecl ConstBoolDecl);
    public void visit(ConstCharDecl ConstCharDecl);
    public void visit(ConstNumberDecl ConstNumberDecl);
    public void visit(ConstDecl ConstDecl);
    public void visit(Type Type);
    public void visit(NoDeclarations NoDeclarations);
    public void visit(ClassDeclarations ClassDeclarations);
    public void visit(VarDeclarations VarDeclarations);
    public void visit(ConstDeclarations ConstDeclarations);
    public void visit(ProgName ProgName);
    public void visit(Program Program);

}