package Model.Statement;

import Model.ADTs.*;
import Model.Exceptions.myException;
import Model.Expression.Expression;
import Model.Type.BoolType;
import Model.Type.Type;
import Model.Value.BoolValue;
import Model.Value.Value;

public class NopStmt implements IStmt{

    @Override
    public PrgState execute(PrgState state) throws myException {
        return null;
    }

    @Override
    public MyIDictionary<String, Type> typeCheck(MyIDictionary<String, Type> typeEnv) throws myException {
        return typeEnv;
    }

    @Override
    public IStmt deepCopy() {
        return new NopStmt();
    }

    public String toString()
    {
        return "NopStatement";
    }

}
