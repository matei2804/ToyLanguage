package Model.Statement;

import Model.ADTs.MyIDictionary;
import Model.ADTs.MyIStack;
import Model.ADTs.PrgState;
import Model.Exceptions.myException;
import Model.Type.Type;

public class CompStmt implements IStmt {
    IStmt first;
    IStmt second;

    public CompStmt(IStmt f, IStmt s)
    {
        this.first = f;
        this.second = s;
    }

    public String toString() {
        return "(" + first.toString() + ";" + second.toString() + ")";
    }

    @Override
    public PrgState execute(PrgState state) throws myException {
        MyIStack<IStmt> stk=state.getStk();
        stk.push(second);
        stk.push(first);
        state.setExeStack(stk);
        return null;
    }

    @Override
    public MyIDictionary<String, Type> typeCheck(MyIDictionary<String, Type> typeEnv) throws myException {
        return second.typeCheck(first.typeCheck(typeEnv));
    }

    @Override
    public IStmt deepCopy() {
        return new CompStmt(first.deepCopy(), second.deepCopy());
    }
}
