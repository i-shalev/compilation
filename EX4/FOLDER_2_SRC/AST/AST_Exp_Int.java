package AST;

import IR.*;
import TYPES.*;

public class AST_Exp_Int extends AST_Exp
{
	public int value;

	public AST_Exp_Int(int value)
	{
		PrintRule("exp", String.format("INT(%d)", value));

		this.value = value;
	}

	public void PrintMe()
	{
		AST_Graphviz.getInstance().logNode(
				SerialNumber,
				String.format("INT(%d)", value));
	}

	public Type SemantMe() throws Exception
	{
		if(value==32768)
			throw new SemanticException("too big integer");
		return Type_Int.getInstance();
	}
 
  	public boolean isZero(){
    return value==0;
  }

	public IRReg IRMe()
	{
		IRReg reg = new IRReg.TempReg();
		IR.add(new IRcommand_Li(reg, value));
		return reg;
	}

}
