package Model.Statement;

import Model.ADTs.*;
import Model.Exceptions.myException;
import Model.Expression.Expression;
import Model.Type.Type;
import Model.Value.Value;

public class PrintStmt implements IStmt{
    Expression exp;

    public PrintStmt(Expression e)
    {
        this.exp = e;
    }

    public String  toString(){ return "print(" + exp.toString() + ")"; }

    @Override
    public PrgState execute(PrgState state) throws myException {
        MyIHeap<Value> heap = state.getHeap();
        MyIList<Value> out = state.getOutList();
        Value val = exp.evaluateExpression(state.getSymTable(), heap);
        out.addElement(val);
        state.setOutList(out);
        return null;
    }

    @Override
    public MyIDictionary<String, Type> typeCheck(MyIDictionary<String, Type> typeEnv) throws myException {
        exp.typeCheck(typeEnv);
        return typeEnv;
    }

    @Override
    public IStmt deepCopy() {
        return new PrintStmt(exp.deepCopy());
    }
}

