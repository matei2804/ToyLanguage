package Model.Statement;

import Model.ADTs.MyIDictionary;
import Model.ADTs.MyIHeap;
import Model.ADTs.MyIStack;
import Model.ADTs.PrgState;
import Model.Exceptions.myException;
import Model.Type.IntType;
import Model.Type.Type;
import Model.Value.IntValue;
import Model.Value.Value;
import Model.Expression.*;

public class CountDownStatement implements IStmt{
    private String variableName;

    public CountDownStatement(String variableName) {
        this.variableName = variableName;
    }

    @Override
    public PrgState execute(PrgState state) throws myException {
        Value variableValue = state.getSymTable().lookup(variableName);
        if (variableValue == null)
            throw new myException(variableName + " has not been declared!");
        if (!variableValue.getType().equals(new IntType()))
            throw new myException(variableName + " is not of type int!");

        PrgState.lock.lock();

        int index = ((IntValue) variableValue).getVal();
        int latchValue = state.getLatchTable().get(index);

        if (latchValue > 0)
            // LatchTable[latchLocation]=LatchTable[latchLocation]-1;
            state.getLatchTable().update(index, latchValue-1);

        state.getOutList().addElement(new IntValue(state.getId()));

        PrgState.lock.unlock();
        return null;
    }

    @Override
    public MyIDictionary<String, Type> typeCheck(MyIDictionary<String, Type> typeTable) throws myException {
        try {
            Type variableType = typeTable.lookup(variableName);
            if (variableType == null)
                throw new myException(variableName + "has not been declared!");
            if (!variableType.equals(new IntType()))
                throw new myException(variableName + "is not of type int!");

        } catch (myException e) {
            throw new myException(e.getMessage());
        }

        return typeTable;
    }

    @Override
    public IStmt deepCopy() {
        return new CountDownStatement(variableName);
    }

    @Override
    public String toString() {
        return "countDown(" + variableName + ")";
    }
}
