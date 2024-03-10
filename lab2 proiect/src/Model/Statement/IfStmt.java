package Model.Statement;

import Model.ADTs.*;
import Model.Exceptions.myException;
import Model.Expression.Expression;
import Model.Type.BoolType;
import Model.Type.Type;
import Model.Value.BoolValue;
import Model.Value.Value;

public class IfStmt implements IStmt{
    Expression exp;
    IStmt thenS;
    IStmt elseS;

    public IfStmt(Expression e, IStmt t, IStmt el) {
        exp = e;
        thenS = t;
        elseS = el;
    }
    public String toString(){ return "(IF(" + exp.toString() + ") THEN(" + thenS.toString()
            + ")ELSE(" +elseS.toString() + "))";
    }

    @Override
    public PrgState execute(PrgState state) throws myException {
        MyIStack<IStmt> stack = state.getStk();
        MyIHeap<Value> heap = state.getHeap();
        MyIDictionary<String, Value> symbolTable = state.getSymTable();
        Value condition = exp.evaluateExpression(symbolTable, heap);
        if (!condition.getType().equals(new BoolType())) {
            throw new myException("Condition is not a boolean!");
        } else {
            BoolValue val = (BoolValue) condition;
            if(val.getVal())
            {
                stack.push(thenS);
            }
            else {
                stack.push(elseS);
            }
        }
        state.setExeStack(stack);
        return null;
    }

    @Override
    public MyIDictionary<String, Type> typeCheck(MyIDictionary<String, Type> typeEnv) throws myException {
        Type typexp = exp.typeCheck(typeEnv);
        if (typexp.equals(new BoolType())) {
            thenS.typeCheck(typeEnv.deepCopy());
            elseS.typeCheck(typeEnv.deepCopy());
            return typeEnv;
        }
        else
            throw new myException("The condition of IF has not the type bool");

    }

    @Override
    public IStmt deepCopy() {
        return new IfStmt(exp.deepCopy(), thenS.deepCopy(), elseS.deepCopy());
    }

}