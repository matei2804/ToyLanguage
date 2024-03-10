package Model.Statement;

import Model.ADTs.MyIDictionary;
import Model.ADTs.MyIHeap;
import Model.ADTs.MyIStack;
import Model.ADTs.PrgState;
import Model.Exceptions.myException;
import Model.Type.Type;
import Model.Value.Value;
import Model.Expression.*;

public class AssignStmt implements IStmt{
    String id;
    Expression exp;

    public AssignStmt(String id, Expression e)
    {
        this.id = id;
        this.exp = e;
    }

    public String toString(){ return id + "=" + exp.toString();}

    @Override
    public PrgState execute(PrgState state) throws myException
    {
        MyIStack<IStmt> stk = state.getStk();
        MyIDictionary<String,Value> symTbl= state.getSymTable();
        MyIHeap<Value> heap = state.getHeap();

        if (symTbl.isDefined(id))
        {
            Value val = exp.evaluateExpression(symTbl, heap);
            Type typId= (symTbl.lookup(id)).getType();
            if (val.getType().equals(typId))
            {
                symTbl.update(id, val);
            }
            else
            {
                throw new myException("declared type of variable" + id + " and type of the " +
                        "assigned expression do not match");
            }
        }
        else
        {
            throw new myException("the used variable" + id + " was not declared before");
        }
        state.setSymbolTable(symTbl);
        return null;
    }

    @Override
    public MyIDictionary<String, Type> typeCheck(MyIDictionary<String, Type> typeEnv) throws myException {
        Type typeVar = typeEnv.lookup(id);
        Type typeExp = exp.typeCheck(typeEnv);
        if (typeVar.equals(typeExp))
            return typeEnv;
        else
            throw new myException("Assignment: right hand side and left hand side have different types ");
    }

    @Override
    public IStmt deepCopy() {
        return new AssignStmt(id, exp.deepCopy());
    }
}
