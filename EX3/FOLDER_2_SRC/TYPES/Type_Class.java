package TYPES;

public class Type_Class extends Type {
    public Type_Class father;          //should be null if is type
    public Type_List data_members;     // contains both variables and methods

    public Type_Class(Type_Class father, String name, Type_List data_members) {
        this.name = name;
        this.father = father;
        this.data_members = data_members;
    }

    public boolean isInheritsFrom(Type_Class cls) {

        if (this.name.equals(cls.name)) {
            return true;
        }

        return this.father != null && this.father.isInheritsFrom(cls);
    }


    public Type getVarField(String varFieldName)
    {
        for (Type_List it = data_members; it != null; it = it.next)
        {
            if (it.head instanceof Type_Class_Var_Dec)
            {
                Type_Class_Var_Dec varFieldType = (Type_Class_Var_Dec)it.head;
                if (varFieldName.equals(varFieldType.name)) { return varFieldType.type; }
            }
        }
        if (father != null) { return father.getVarField(varFieldName); }
        return null;
    }

    public Type_Func getFuncField(String funcFieldName)
    {
        for (Type_List it = data_members; it != null; it = it.next)
        {
            if (it.head instanceof Type_Func)
            {
                Type_Func func = (Type_Func)it.head;
                if (funcFieldName.equals(func.name)) { return func; }
            }
        }
        if (father != null) { return father.getFuncField(funcFieldName); }
        return null;
    }



}