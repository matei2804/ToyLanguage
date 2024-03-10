package Model.Expression;

import Model.ADTs.MyIDictionary;
import Model.ADTs.MyIHeap;
import Model.Exceptions.myException;
import Model.Type.BoolType;
import Model.Type.IntType;
import Model.Type.Type;
import Model.Value.BoolValue;
import Model.Value.Value;


public class NotExp implements Expression{

    Expression e;

    public NotExp(Expression e)
    {
        this.e = e;
    }

    @Override
    public Value evaluateExpression(MyIDictionary<String, Value> table, MyIHeap<Value> heap) throws myException {
        Value v = e.evaluateExpression(table, heap);
        if(v.getType().equals(new BoolType()))
        {
            BoolValue bool = (BoolValue) v;
            boolean return_bool = bool.getVal();
            return new BoolValue(!return_bool);
        }
        else throw new myException(e.toString() + " is not a boolean!");

    }

    @Override
    public Type typeCheck(MyIDictionary<String, Type> typeEnv) throws myException {
        Type type1;
        type1 = e.typeCheck(typeEnv);
        if (type1.equals(new BoolType())) {
            return new BoolType();
        }else
            throw new myException("First operand is not an boolean");
    }

    @Override
    public String toString() {
        return "!" + e.toString();
    }

    @Override
    public Expression deepCopy() {
        return new NotExp(e.deepCopy());
    }
}
