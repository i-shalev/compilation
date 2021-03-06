package AST;

import TYPES.*;

public class AST_Exp_String extends AST_Exp
{
	public String str;

	public AST_Exp_String(String str)
	{
		PrintRule("exp", String.format("STRING(%s)", str));

		this.str = str;
	}

	public void PrintMe()
	{
		AST_Graphviz.getInstance().logNode(
				SerialNumber,
				String.format("STRING(%s)", str));
	}

	public Type SemantMe()
	{
		return Type_String.getInstance();
	}
}
