package Model.Expression;

import Model.ADTs.MyIDictionary;
import Model.ADTs.MyIHeap;
import Model.Exceptions.myException;
import Model.Type.BoolType;
import Model.Type.IntType;
import Model.Type.Type;
import Model.Value.BoolValue;
import Model.Value.Value;

class LogicExp implements Expression {
    Expression e1;
    Expression e2;
    int op;

    public LogicExp(Expression e1, Expression e2, int op)
    {
        this.e1 = e1;
        this.e2 = e2;
        this.op = op;
    }
    @Override
    public Value evaluateExpression(MyIDictionary<String, Value> table, MyIHeap<Value> heap) throws myException {
        Value v1, v2;
        v1 = e1.evaluateExpression(table, heap);
        if(v1.getType().equals(new BoolType()))
        {
            v2 = e2.evaluateExpression(table, heap);
            if(v2.getType().equals(new BoolType()))
            {
                BoolValue bool1 = (BoolValue) v1;
                BoolValue bool2 = (BoolValue) v2;
                Boolean b1 = bool1.getVal();
                Boolean b2 = bool2.getVal();

                if(op == 1)
                {
                    return new BoolValue(b1 && b2);
                }
                else if (op == 2)
                {
                    return new BoolValue(b1 || b2);
                }

            }
            else throw new myException("Second operand is not a logic operator!");
        }
        else throw new myException("First operand is not a logic operator!");

        throw new myException("Invalid expression!");
    }

    @Override
    public Type typeCheck(MyIDictionary<String, Type> typeEnv) throws myException {
        Type typ1, typ2;
        typ1 = e1.typeCheck(typeEnv);
        typ2 = e2.typeCheck(typeEnv);
        if (typ1.equals(new BoolType()))
        {
            if (typ2.equals(new BoolType()))
            {
                return new BoolType();
            }
            else
                throw new myException("Second operand is not bool");
        }else
            throw new myException("First operand is not bool");
    }

    @Override
    public Expression deepCopy() {
        return new LogicExp(e1.deepCopy(), e2.deepCopy(), op);
    }

}
