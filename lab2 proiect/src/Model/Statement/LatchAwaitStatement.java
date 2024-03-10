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

public class LatchAwaitStatement implements IStmt{
    private String variableName;

    public LatchAwaitStatement(String variableName) {
        this.variableName = variableName;
    }

    @Override
    public PrgState execute(PrgState state) throws myException {
        try {
            Value variableValue = state.getSymTable().lookup(variableName);
            if (variableValue == null)
                throw new myException(variableName + "has not been declared!");
            if (!variableValue.getType().equals(new IntType()))
                throw new myException(variableName + "is not of type int!");


            int index = ((IntValue) variableValue).getVal();
            int latchValue = state.getLatchTable().get(index);

            PrgState.lock.lock();

            // if LatchTable[latchLocation] != 0 then
            if (latchValue != 0)
                // push back the await statement(that means the current program state must wait for the countdownlatch to reach zero)
                state.getStk().push(this);

            PrgState.lock.unlock();

            return null;
        } catch (myException e) {
            throw new myException(e.getMessage());
        }
    }

    @Override
    public MyIDictionary<String, Type> typeCheck(MyIDictionary<String, Type> typeTable) throws myException {
        try {
            Type variableType = typeTable.lookup(variableName);
            if (variableType == null)
                throw new myException(variableName + "has not been declared!");
            if (!variableType.equals(new IntType()))
                throw new myException(variableName + " is not of type int!");

        } catch (myException e) {
            throw new myException(e.getMessage());
        }

        return typeTable;
    }

    @Override
    public IStmt deepCopy() {
        return new LatchAwaitStatement(variableName);
    }

    @Override
    public String toString() {
        return "await(" + variableName + ")";
    }
}
