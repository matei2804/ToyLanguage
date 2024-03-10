package Model.Expression;
import Model.ADTs.MyIDictionary;
import Model.ADTs.MyIHeap;
import Model.Exceptions.myException;
import Model.Type.BoolType;
import Model.Type.IntType;
import Model.Type.Type;
import Model.Value.*;
import com.sun.jdi.BooleanType;

public class RelationalExp implements Expression{
    Expression exp1;
    Expression exp2;
    int op;

    public RelationalExp(Expression e1, Expression e2, int o)
    {
        exp1 = e1;
        exp2 = e2;
        op = o;
    }

    @Override
    public Value evaluateExpression(MyIDictionary<String, Value> symTable, MyIHeap<Value> heap) throws myException {
        Value val1, val2;
        val1 = exp1.evaluateExpression(symTable, heap);
        val2 = exp2.evaluateExpression(symTable, heap);
        if (val1.getType().equals(new IntType()) && val2.getType().equals(new IntType())) {
            IntValue intVal1, intVal2;
            intVal1 = (IntValue) val1;
            intVal2 = (IntValue) val2;
            int x = intVal1.getVal();
            int y = intVal2.getVal();
            switch (op) {
                case 1:
                    return new BoolValue(x < y);
                case 2:
                    return new BoolValue(x <= y);
                case 3:
                    return new BoolValue(x == y);
                case 4:
                    return new BoolValue(x != y);
                case 5:
                    return new BoolValue(x > y);
                case 6:
                    return new BoolValue(x >= y);
                default: throw new myException("Operator non-existent!");
            }
        }
        else {
            throw new myException("At least one operand is not an integer");
        }

    }

    @Override
    public Type typeCheck(MyIDictionary<String, Type> typeEnv) throws myException {
        Type type1 = exp1.typeCheck(typeEnv);
        Type type2 = exp2.typeCheck(typeEnv);

        if (type1.equals(new IntType())) {
            if (type2.equals(new IntType())) {
                return new BoolType();
            }
            else {
                throw new myException("The second operand is not an integer");
            }
        }
        else {
            throw new myException("The first operand is not an integer");
        }
    }

    @Override
    public Expression deepCopy() {
        return new RelationalExp(exp1.deepCopy(), exp2.deepCopy(), op);
    }

    @Override
    public String toString() {
        String s = "";
        switch (op) {
            case 1:
                s = "<";
                break;
            case 2:
                s = "<=";
                break;
            case 3:
                s = "==";
                break;
            case 4:
                s = "!=";
                break;
            case 5:
                s = ">";
                break;
            case 6:
                s = ">=";
        }
        return exp1 + s + exp2;
    }
}
