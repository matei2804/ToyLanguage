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

public class NewStmt implements IStmt{

    String var_name;
    Expression expression;

    public NewStmt(String var_name, Expression exp)
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
                Value value = expression.evaluateExpression(symTbl, heap);
                Value tableValue = symTbl.lookup(var_name);
                if(value.getType().equals(((ReferenceType) (tableValue.getType())).getInner()))
                {
                    int address = heap.allocate(value);
                    symTbl.update(var_name, new ReferenceValue(address, value.getType()));
                }
                else throw new myException("Value's type is not correct!");
           }
           else throw new myException("Type of variable is not ReferenceType!");
        }
        else
        {
            throw new myException("Variable name is not defined in the symbol table!");
        }
        state.setSymbolTable(symTbl);
        state.setHeap(heap);
        state.setExeStack(stk);
        return null;
    }

    @Override
    public MyIDictionary<String, Type> typeCheck(MyIDictionary<String, Type> typeEnv) throws myException {
        Type typevar = typeEnv.lookup(var_name);
        Type typexp = expression.typeCheck(typeEnv);
        if (typevar.equals(new ReferenceType(typexp)))
            return typeEnv;
        else
            throw new myException("NEW stmt: right hand side and left hand side have different types ");
    }

    @Override
    public IStmt deepCopy() {
        return new NewStmt(var_name, expression.deepCopy());
    }

    @Override
    public String toString(){
        return "new(" + var_name + ", " + expression.toString() + ")";
    }
}










