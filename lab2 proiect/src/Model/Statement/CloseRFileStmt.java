package Model.Statement;

import Model.ADTs.MyIDictionary;
import Model.ADTs.MyIHeap;
import Model.ADTs.MyIStack;
import Model.ADTs.PrgState;
import Model.Exceptions.myException;
import Model.Expression.Expression;
import Model.Type.StringType;
import Model.Type.Type;
import Model.Value.StringValue;
import Model.Value.Value;

import java.io.BufferedReader;
import java.io.IOException;

public class CloseRFileStmt implements IStmt{
    Expression exp;

    public CloseRFileStmt(Expression exp)
    {
        this.exp = exp;
    }

    @Override
    public String toString() {
        return "close(" + exp + ")";
    }

    @Override
    public PrgState execute(PrgState state) throws myException {
        MyIStack<IStmt> stk = state.getStk();
        MyIDictionary<String, Value> symbolTable = state.getSymTable();
        MyIHeap<Value> heap = state.getHeap();
        Value val = exp.evaluateExpression(symbolTable, heap);
        if(val.getType().equals(new StringType()))
        {
            StringValue value = (StringValue) val;
            MyIDictionary<StringValue, BufferedReader> fileTable = state.getFileTable();
            if(fileTable.isDefined(value)) {
                try {
                    BufferedReader bufferedReader = fileTable.lookup(value);
                    bufferedReader.close();
                    fileTable.remove(value, bufferedReader);
                }
                catch (IOException m)
                {
                    throw new myException(m.getMessage());
                }
            }
            else {
                throw new myException(value.toString() + " is not in the file table!\n");
            }
        }
        else {
            throw new myException("Expression is not of type string!\n");
        }
        return null;
    }

    @Override
    public MyIDictionary<String, Type> typeCheck(MyIDictionary<String, Type> typeEnv) throws myException {
        Type expType = exp.typeCheck(typeEnv);
        if (expType.equals(new StringType())) {
            return typeEnv;
        }
        else {
            throw new myException("The close file expression " + this.toString() + " is not a string");
        }
    }

    @Override
    public IStmt deepCopy() {
        return new CloseRFileStmt(exp.deepCopy());
    }
}
