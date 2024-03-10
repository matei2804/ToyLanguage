package Model.Statement;

import Model.ADTs.*;
import Model.Exceptions.myException;
import Model.Expression.Expression;
import Model.Type.ReferenceType;
import Model.Type.Type;
import Model.Value.ReferenceValue;
import Model.Value.StringValue;
import Model.Value.Value;

import javax.sql.rowset.FilteredRowSet;
import java.io.BufferedReader;
import java.util.Map;

public class ForkStmt implements IStmt{

    IStmt stmt;
    public ForkStmt(IStmt s){
        this.stmt = s;
    }

    @Override
    public PrgState execute(PrgState state) throws myException {
        MyIStack<IStmt> stk = state.getStk();
        MyIDictionary<String, Value> symTbl= state.getSymTable();
        MyIHeap<Value> heap = state.getHeap();
        MyIList<Value> out = state.getOutList();
        MyDictionary<StringValue, BufferedReader> fileTable = (MyDictionary<StringValue, BufferedReader>) state.getFileTable();

        MyIStack<IStmt> newStack = new MyStack<>();
        MyIDictionary<String, Value> newSymTable = new MyDictionary<>();
        for(Map.Entry<String,Value>entry : symTbl.getContent().entrySet())
        {
            newSymTable.add(entry.getKey(), entry.getValue().deepCopy());
        }

        return new PrgState(newStack, newSymTable, out, fileTable, heap, stmt, state.getLatchTable());
    }

    @Override
    public MyIDictionary<String, Type> typeCheck(MyIDictionary<String, Type> typeEnv) throws myException {
        stmt.typeCheck(typeEnv.deepCopy());
        return typeEnv;
    }

    @Override
    public IStmt deepCopy() {
        return new ForkStmt(stmt.deepCopy());
    }

    @Override
    public String toString()
    {
        return "fork(" + stmt.toString() + ")";
    }
}
