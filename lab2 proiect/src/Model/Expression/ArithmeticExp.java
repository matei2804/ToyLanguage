package Model.Expression;
import Model.ADTs.MyIDictionary;
import Model.ADTs.MyIHeap;
import Model.Exceptions.myException;
import Model.Type.IntType;
import Model.Type.Type;
import Model.Value.*;

public class ArithmeticExp implements Expression {
    Expression e1;
    Expression e2;
    int op; //1-plus, 2-minus, 3-star, 4-divide

    public ArithmeticExp(Expression e1, Expression e2, char op)
    {
        this.e1 = e1;
        this.e2 = e2;

        if(op == '+') this.op = 1;
        else if(op == '-') this.op = 2;
        else if(op == '*') this.op = 3;
        else if (op == '/') this.op = 4;
    }
    public Value evaluateExpression(MyIDictionary<String,Value> table, MyIHeap<Value> heap) throws myException {
        Value v1,v2;
        v1= e1.evaluateExpression(table, heap);
        if (v1.getType().equals(new IntType())) {
            v2 = e2.evaluateExpression(table, heap);
            if (v2.getType().equals(new IntType())) {
                IntValue i1 = (IntValue)v1;
                IntValue i2 = (IntValue)v2;
                int n1,n2;
                n1= i1.getVal();
                n2 = i2.getVal();
                if (op==1) return new IntValue(n1+n2);
                if (op ==2) return new IntValue(n1-n2);
                if(op==3) return new IntValue(n1*n2);
                if(op==4)
                    if(n2==0) throw new myException("Division by zero");
                    else return new IntValue(n1/n2);
            }else
                throw new myException("Second operand is not an integer!");
        }else
            throw new myException("First operand is not an integer!");

        throw new myException("Incorrect operation!");
    }

    public Type typeCheck(MyIDictionary<String,Type> typeEnv) throws myException{
        Type typ1, typ2;
        typ1 = e1.typeCheck(typeEnv);
        typ2 = e2.typeCheck(typeEnv);
        if (typ1.equals(new IntType()))
        {
            if (typ2.equals(new IntType()))
            {
                return new IntType();
            }
            else
                throw new myException("Second operand is not an integer");
        }else
            throw new myException("first operand is not an integer");
    }

    @Override
    public Expression deepCopy() {
        if(op == 1)
            return new ArithmeticExp(e1.deepCopy(), e2.deepCopy(), '+');
        else if (op == 2)
            return new ArithmeticExp(e1.deepCopy(), e2.deepCopy(), '-');
        else if(op == 3)
            return new ArithmeticExp(e1.deepCopy(), e2.deepCopy(), '*');
        else if(op == 4)
            return new ArithmeticExp(e1.deepCopy(), e2.deepCopy(), '/');
        else
            return new ArithmeticExp(e1.deepCopy(), e2.deepCopy(), '+');
    }

    @Override
    public String toString()
    {
        if(op == 1)
            return e1.toString() + " + " + e2.toString();
        if(op == 2)
            return e1.toString() + " - " + e2.toString();
        if(op == 3)
            return e1.toString() + " * " + e2.toString();
        if(op == 4)
            return e1.toString() + " / " + e2.toString();

        return "";
    }
}
