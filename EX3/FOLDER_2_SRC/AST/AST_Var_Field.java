package AST;

import TYPES.*;

public class AST_Var_Field extends AST_Var
{
    public AST_Var var;
    public String fieldName;

    public AST_Var_Field(AST_Var var, String fieldName)
    {
        PrintRule("var", String.format("var . ID(%s)", fieldName));

        this.var = var;
        this.fieldName = fieldName;
    }

    public void PrintMe()
    {
        if (var != null) var.PrintMe();

        AST_Graphviz.getInstance().logNode(
                SerialNumber,
                String.format("var.field(%s)",fieldName));

        if (var != null) AST_Graphviz.getInstance().logEdge(SerialNumber, var.SerialNumber);
    }

    public Type SemantMe() throws Exception
    {
        Type varType = var.SemantMe();
        if (!(varType instanceof Type_Class))
            throw new SemanticException("Var field statement - var is not object of class");
        Type t1 = ((Type_Class) varType).getVarField(fieldName);
        if (t1 == null){
            throw new SemanticException("Var field statement - class doesn't have that field ");
        }
        return t1;
    }
}