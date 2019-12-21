/***********/
/* PACKAGE */
/***********/
package AST;

/*******************/
/* PROJECT IMPORTS */
/*******************/
import TYPES.*;
import SYMBOL_TABLE.*;

public class AST_TYPE_NAME extends AST_Node
{
	/****************/
	/* DATA MEMBERS */
	/****************/
	public String type;
	public String name;
	
	/******************/
	/* CONSTRUCTOR(S) */
	/******************/
	public AST_TYPE_NAME(String type,String name)
	{
		/******************************/
		/* SET A UNIQUE SERIAL NUMBER */
		/******************************/
		SerialNumber = AST_Node_Serial_Number.getFresh();
	
		this.type = type;
		this.name = name;
	}

	/*************************************************/
	/* The printing message for a type name AST node */
	/*************************************************/
	public void PrintMe()
	{
		/**************************************/
		/* AST NODE Type = AST Type NAME NODE */
		/**************************************/
		System.out.format("NAME(%s):Type(%s)\n",name,type);

		/***************************************/
		/* PRINT Node to AST GRAPHVIZ DOT file */
		/***************************************/
		AST_GRAPHVIZ.getInstance().logNode(
			SerialNumber,
			String.format("NAME:Type\n%s:%s",name,type));
	}

	/*****************/
	/* SEMANT ME ... */
	/*****************/
	public Type SemantMe()
	{
		Type t = SymbolTable.getInstance().find(type);
		if (t == null)
		{
			/**************************/
			/* ERROR: undeclared type */
			/**************************/
			System.exit(0);
			return null;
		}
		else
		{
			/*******************************************************/
			/* Enter var with name=name and type=t to symbol table */
			/*******************************************************/
			SymbolTable.getInstance().enter(name,t);
		}

		/****************************/
		/* return (existing) type t */
		/****************************/
		return t;
	}	
}
