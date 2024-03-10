package Model.Statement;

import Model.ADTs.MyIDictionary;
import Model.ADTs.MyIHeap;
import Model.ADTs.MyIStack;
import Model.ADTs.PrgState;
import Model.Exceptions.myException;
import Model.Expression.*;
import Model.Type.StringType;
import Model.Type.Type;
import Model.Value.StringValue;
import Model.Value.Value;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.Reader;

public class OpenRFileStmt implements IStmt{
    Expression exp;

    public OpenRFileStmt(Expression exp)
    {
        this.exp = exp;
    }

    @Override
    public String toString() {
        return "Open(" + exp + ")";
    }

    @Override
    public PrgState execute(PrgState state) throws myException {
        MyIStack<IStmt> stk = state.getStk();
        MyIHeap<Value> heap = state.getHeap();
        MyIDictionary<String, Value> symbolTable = state.getSymTable();
        Value val = exp.evaluateExpression(symbolTable, heap);
        if (val.getType().equals(new StringType())) {
            MyIDictionary<StringValue, BufferedReader> fileTable = state.getFileTable();
            StringValue value = (StringValue) val;
            if (!fileTable.isDefined(value)) {
                try {
                    Reader reader = new FileReader(value.getVal());
                    BufferedReader bufferedReader = new BufferedReader(reader);
                    fileTable.add(value, bufferedReader);
                    state.setFileTable(fileTable);

                } catch (FileNotFoundException e) {
                    throw new myException(e.getMessage());
                }
            } else {
                throw new myException("The file is used already!\n");
            }
        } else {
            throw new myException("Expression is not a StringValue!\n");
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
            throw new myException("The open file expression " + this.toString() + " is not a string");
        }
    }

    @Override
    public IStmt deepCopy() {
        return new OpenRFileStmt(exp.deepCopy());
    }
}
