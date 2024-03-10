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

public class NewLatchStatement implements IStmt {
    private String variableName;
    private Expression expression;

    public NewLatchStatement(String variableName, Expression expression){
        this.variableName = variableName;
        this.expression = expression;
    }

    @Override
    public PrgState execute(PrgState state) throws myException {
        PrgState.lock.lock();

        Value expressionValue = expression.evaluateExpression(state.getSymTable(), state.getHeap());

        if (!expressionValue.getType().equals(new IntType())) {
            PrgState.lock.unlock();
            throw new myException("Expression " + expression.toString() + "is not an integer!");
        }

        int latch = ((IntValue) expressionValue).getVal();
        int freeLocation = state.getLatchTable().put(latch);
        Value variableValue = state.getSymTable().lookup(variableName);

        if (!variableValue.getType().equals(new IntType())) {
            PrgState.lock.unlock();
            throw new myException("Variable" + variableName + "is not an int type!");
        }

        // SymTable2 = update(SymTable1,var, newFreeLocation)
        state.getSymTable().update(variableName, new IntValue(freeLocation));
        PrgState.lock.unlock();
        return null;
    }

    @Override
    public MyIDictionary<String, Type> typeCheck(MyIDictionary<String, Type> typeEnv) throws myException {
        Type variableType = typeEnv.lookup(variableName);
        if (variableName == null)
            throw new myException(variableName + "has not been declared!");
        if (!variableType.equals(new IntType()))
            throw new myException(variableName + " is not of type int!");

        Type expressionType = expression.typeCheck(typeEnv);
        if (!expressionType.equals(new IntType()))
            throw new myException(expressionType.toString() + "is not an int!");

        return typeEnv;
    }

    @Override
    public IStmt deepCopy() {
        return new NewLatchStatement(variableName, expression);
    }

    @Override
    public String toString() {
        return "newLatch(" + variableName + ", " + expression.toString() + ")";
    }
}