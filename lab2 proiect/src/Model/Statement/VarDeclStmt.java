package Model.Statement;

import Model.ADTs.*;
import Model.Exceptions.myException;
import Model.Expression.Expression;
import Model.Type.BoolType;
import Model.Type.IntType;
import Model.Type.StringType;
import Model.Type.Type;
import Model.Value.BoolValue;
import Model.Value.IntValue;
import Model.Value.Value;

public class VarDeclStmt implements IStmt{
    String name;
    Type type;

    public VarDeclStmt(String n, Type t)
    {
        this.name = n;
        this.type = t;
    }

    @Override
    public PrgState execute(PrgState state) throws myException {

        MyIStack<IStmt> stack = state.getStk();
        MyIDictionary<String, Value> symbolTable = state.getSymTable();

        if(symbolTable.isDefined(name))
        {
            throw new myException("Variable already declared!");
        }
        else
        {
/*            if(type.equals(new BoolType()))
            {
                symbolTable.add(name, type.defaultValue());
            }
            else if(type.equals(new IntType()))
            {
                symbolTable.add(name, type.defaultValue());
            }
            else if (type.equals(new StringType()))
            {
                symbolTable.add(name, type.defaultValue());
            }
            else throw new myException("Type does not exist!");
            */
            symbolTable.add(name, type.defaultValue());
        }


        state.setSymbolTable(symbolTable);
        return null;
    }

    @Override
    public MyIDictionary<String, Type> typeCheck(MyIDictionary<String, Type> typeEnv) throws myException {
        typeEnv.add(name, type);
        return typeEnv;
    }

    @Override
    public IStmt deepCopy() {
        return new VarDeclStmt(name, type.deepCopy());
    }

    @Override
    public String toString() {
        return type + " " + name;
    }
}
