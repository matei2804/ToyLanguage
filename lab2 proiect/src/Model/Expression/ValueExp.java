package Model.Expression;
import Model.ADTs.MyIDictionary;
import Model.ADTs.MyIHeap;
import Model.Exceptions.myException;
import Model.Type.Type;
import Model.Value.Value;

public class ValueExp implements Expression {
    Value e;

    public ValueExp(Value e)
    {
        this.e = e;
    }

    @Override
    public Value evaluateExpression(MyIDictionary<String, Value> tbl, MyIHeap<Value> heap) throws myException {
        return e;
    }

    @Override
    public Type typeCheck(MyIDictionary<String, Type> typeEnv) throws myException {
        return e.getType();
    }

    @Override
    public Expression deepCopy() {
        return new ValueExp(e.deepCopy());
    }

    @Override
    public String toString()
    {
        return e.toString();
    }
}
