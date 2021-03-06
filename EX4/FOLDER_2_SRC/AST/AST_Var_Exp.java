package AST;

import IR.*;
import TYPES.Type;

public class AST_Var_Exp extends AST_Exp{
        private AST_Var var;

        public AST_Var_Exp(AST_Var var)
        {
            this.var = var;
        }

        public void PrintMe()
        {
            if (var != null) var.PrintMe();

            AST_Graphviz.getInstance().logNode(
                    SerialNumber,
                    String.format("Var\nExp"));

            if (var != null) AST_Graphviz.getInstance().logEdge(SerialNumber, var.SerialNumber);
        }

        @Override
        public Type SemantMe() throws Exception {
            return var.SemantMe();
        }

        @Override
        public IRReg IRMe()
        {
            IRReg reg = var.IRMe();
            IR.add(new IRcommand_Lw(reg, reg, 0));  // dereference
            return reg;
        }

    }