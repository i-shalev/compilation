package AST;

import SYMBOL_TABLE.SymbolTable;
import TYPES.*;

public class AST_Func_Dec extends AST_Class_Field
{
    public String retType;
    public String funcName;
    public AST_Params_List params;
    public AST_Stmt_List statements;

    public AST_Func_Dec(String retType, String funcName, AST_Params_List params, AST_Stmt_List statements)
    {
        if (params != null) PrintRule("funcDec", "ID ID ( idList ) { stmtList }");
        if (params == null) PrintRule("funcDec", "ID ID ( ) { stmtList }");

        this.retType = retType;
        this.funcName = funcName;
        this.params = params;
        this.statements = statements;
    }

    public void PrintMe()
    {
        if (params != null) params.PrintMe();
        if (statements != null) statements.PrintMe();

        AST_Graphviz.getInstance().logNode(
                SerialNumber,
                String.format("Function\nDEC\n(%s %s)", retType, funcName));

        if (params != null) AST_Graphviz.getInstance().logEdge(SerialNumber, params.SerialNumber);
        if (statements != null) AST_Graphviz.getInstance().logEdge(SerialNumber, statements.SerialNumber);
    }

    public Type_Func SemantDeclaration() throws Exception {
        Type ret = SymbolTable.find(retType);
        
        if (!(ret instanceof Type_Object || ret instanceof Type_Void))
            throw new SemanticException("Return type is invalid");

        if (!(SymbolTable.isGlobalScope() || SymbolTable.isInScope(Type_Scope.CLASS)))
            throw new SemanticException("Declaration is invalid");

        // Get function parameters type
        Type_List paramsTypes = params != null ? params.SemantDeclaration() : null;

        Type_Func func = new Type_Func(ret, funcName, paramsTypes);

        if (SymbolTable.findInScope(funcName) != null)
            throw new SemanticException("Function already declared in scope");

        if (SymbolTable.isInScope(Type_Scope.CLASS)) { // function is a class method
            Type_Class currClass = SymbolTable.findClass(); // get current class

            Type_Class father = currClass.father;
            if (father != null) {
                Type_Func fatherFunc = father.getFuncField(funcName);
                if (fatherFunc != null && !func.isOverride(fatherFunc))
                    throw new SemanticException("Function overloading is not allowed");
            }
        }

        return func;
    }

    public Type SemantMe() throws Exception {
        Type_Func funcType = SemantDeclaration();
        SymbolTable.enter(funcName, funcType);
        if (SymbolTable.isDirectlyInScope(Type_Scope.CLASS)){
          Type_Class c1 = SymbolTable.findClass();
          c1.data_members = Type_List.add(funcType,c1.data_members);
        }
        SymbolTable.beginScope(Type_Scope.FUNC);
        if (params != null) params.SemantMe();
        if (statements != null) { statements.SemantMe(); }
        SymbolTable.endScope();

        return funcType;
    }
}
