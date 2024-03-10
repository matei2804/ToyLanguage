package Model.Expression;

import Model.ADTs.MyIDictionary;
import Model.ADTs.MyIHeap;
import Model.Exceptions.myException;
import Model.Type.Type;
import Model.Value.Value;

public class VarExp implements Expression {
    String id;

    public VarExp(String id)
    {
        this.id = id;
    }
    @Override
    public Value evaluateExpression(MyIDictionary<String, Value> table, MyIHeap<Value> heap) throws myException {

        if(table.lookup(id) == null)
            throw new myException("The variable does not exist!");
        else return table.lookup(id);
    }

    @Override
    public Type typeCheck(MyIDictionary<String, Type> typeEnv) throws myException {
        return typeEnv.lookup(id);
    }

    @Override
    public Expression deepCopy() {
        return new VarExp(id);
    }

    @Override
    public String toString()
    {
        return id;
    }
 }
