package AST;

import TYPES.*;
import SYMBOL_TABLE.*;

public class AST_Stmt_Return extends AST_Stmt
{
	public AST_Exp exp;

	public AST_Stmt_Return(AST_Exp exp)
	{

		if (exp != null) PrintRule("stmt", "RETURN exp ;");
		if (exp == null) PrintRule("stmt", "RETURN ;");
		this.exp = exp;
	}

	public void PrintMe()
	{
		if (exp != null) exp.PrintMe();

		String ret = exp != null ? "RETURN exp" : "RETURN";
		AST_Graphviz.getInstance().logNode(
				SerialNumber,
				ret);

		if (exp != null) AST_Graphviz.getInstance().logEdge(SerialNumber, exp.SerialNumber);
	}

	public Type SemantMe() throws Exception{
		Type_Func typeFunc = SymbolTable.findFunc();

		if(typeFunc == null)
			throw new Exception("Return statement - not in a function");

		if(typeFunc.GetReturnType() == Type_Void.getInstance())
		{
			if(exp != null)
				throw new Exception("Return statement - mismatch return type");
			return typeFunc;
		}

		Type expType = exp.SemantMe();
		if(typeFunc.GetReturnType() == expType || ((typeFunc.GetReturnType() instanceof Type_Array ||
				typeFunc.GetReturnType() instanceof Type_Class) && expType == Type_Nil.getInstance()) )
			return typeFunc;

		throw new Exception("Return statement - mismatch return type");
	}
}
