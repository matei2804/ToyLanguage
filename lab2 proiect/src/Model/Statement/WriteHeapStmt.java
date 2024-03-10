package Model.Statement;

import Model.ADTs.MyIDictionary;
import Model.ADTs.MyIHeap;
import Model.ADTs.MyIStack;
import Model.ADTs.PrgState;
import Model.Exceptions.myException;
import Model.Expression.Expression;
import Model.Type.ReferenceType;
import Model.Type.Type;
import Model.Value.ReferenceValue;
import Model.Value.Value;

import java.sql.Ref;

public class WriteHeapStmt implements IStmt{

    String var_name;
    Expression expression;

    public WriteHeapStmt(String var_name, Expression exp)
    {
        this.var_name = var_name;
        this.expression = exp;
    }

    @Override
    public PrgState execute(PrgState state) throws myException {
        MyIStack<IStmt> stk = state.getStk();
        MyIDictionary<String, Value> symTbl= state.getSymTable();
        MyIHeap<Value> heap = state.getHeap();

        if(symTbl.isDefined(var_name))
        {
            if(symTbl.lookup(var_name).getType() instanceof ReferenceType)
            {
                ReferenceValue value = (ReferenceValue) symTbl.lookup(var_name);
                if(heap.contains(value.getAddress()))
                {
                    Value expValue = expression.evaluateExpression(symTbl, heap);
                    if(symTbl.lookup(var_name).getType().equals(new ReferenceType(expValue.getType())))
                    {
                        int address = value.getAddress();
                        heap.update(address, expValue);
                    }
                }
                else throw new myException("The address to which " + var_name + " points is not in the heap");
            }
            else throw new myException(var_name + " is not a Reference!");
        }
        else throw new myException("Variable name is not defined!");

        return null;
    }

    @Override
    public MyIDictionary<String, Type> typeCheck(MyIDictionary<String, Type> typeEnv) throws myException {
        if (typeEnv.isDefined(var_name)) {
            Type variableType = typeEnv.lookup(var_name);
            Type expType = expression.typeCheck(typeEnv);
            if (!(variableType instanceof ReferenceType)) {
                throw new myException("The file name in " + this.toString() + " is not a string");
            }
            if (!variableType.equals(new ReferenceType(expType))) {
                throw new myException("The right side of " + this.toString() + " has other type than the referenced type of the left side");
            }
            return typeEnv;
        }
        else {
            throw new myException("The variable " + var_name + " is not defined");
        }
    }

    @Override
    public IStmt deepCopy() {
        return new WriteHeapStmt(var_name, expression.deepCopy());
    }

    @Override
    public String toString() {
        return "wH(" + var_name + "," + expression + ")";
    }
}
