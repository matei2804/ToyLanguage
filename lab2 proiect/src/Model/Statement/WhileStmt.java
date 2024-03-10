package Model.Statement;

import Model.ADTs.MyIDictionary;
import Model.ADTs.MyIHeap;
import Model.ADTs.MyIStack;
import Model.ADTs.PrgState;
import Model.Exceptions.myException;
import Model.Expression.Expression;
import Model.Type.BoolType;
import Model.Type.Type;
import Model.Value.BoolValue;
import Model.Value.Value;

public class WhileStmt implements IStmt{

    Expression exp;
    IStmt statement;

    public WhileStmt(Expression e, IStmt s)
    {
        this.exp = e;
        this.statement = s;
    }

    @Override
    public PrgState execute(PrgState state) throws myException {
        MyIStack<IStmt> stk = state.getStk();
        MyIDictionary<String, Value> symTbl= state.getSymTable();
        MyIHeap<Value> heap = state.getHeap();
        Value condition = exp.evaluateExpression(symTbl, heap);
        if(!condition.getType().equals(new BoolType()))
        {
            throw new myException("Condition is not a boolean!\n");
        }
        BoolValue val = (BoolValue) condition;
        if(val.getVal())
        {
            stk.push(this.deepCopy());
            stk.push(statement);
            state.setExeStack(stk);
        }
        return null;
    }

    @Override
    public MyIDictionary<String, Type> typeCheck(MyIDictionary<String, Type> typeEnv) throws myException {
        Type typexp = exp.typeCheck(typeEnv);
        if (typexp.equals(new BoolType())) {
            statement.typeCheck(typeEnv.deepCopy());;
            return typeEnv;
        }
        else
            throw new myException("The condition of While it not of type bool");
    }

    @Override
    public IStmt deepCopy() {
        return new WhileStmt(exp.deepCopy(), statement.deepCopy());
    }

    @Override
    public String toString() {
        return "(while (" + exp + ") " + statement + ")";
    }

}
