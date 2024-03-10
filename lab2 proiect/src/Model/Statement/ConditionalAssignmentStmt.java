package Model.Statement;

import Model.ADTs.MyIDictionary;
import Model.ADTs.MyIHeap;
import Model.ADTs.MyIStack;
import Model.ADTs.PrgState;
import Model.Exceptions.myException;
import Model.Type.BoolType;
import Model.Type.Type;
import Model.Value.Value;
import Model.Expression.*;

public class ConditionalAssignmentStmt implements IStmt
{
    String var;
    Expression exp1;
    Expression exp2;
    Expression exp3;

    public ConditionalAssignmentStmt(String var, Expression e1, Expression e2, Expression e3)
    {
        this.var = var;
        this.exp1 = e1;
        this.exp2 = e2;
        this.exp3 = e3;
    }

    @Override
    public PrgState execute(PrgState state) throws myException {
        MyIStack<IStmt> stk = state.getStk();
        MyIDictionary<String,Value> symTbl= state.getSymTable();

        if(symTbl.isDefined(var))
        {
            IStmt if_stmt = new AssignStmt(var, exp2);
            IStmt then_stmt = new AssignStmt(var, exp3);
            IStmt big_stmt = new IfStmt(exp1, if_stmt, then_stmt);
            //Create this statement (if (exp1) then v=exp2 else v=exp3)

            stk.push(big_stmt);
        }
        else
            throw new myException("The variable" + var + "is not defined in the symbol table!");

        state.setExeStack(stk);
        return null;
    }

    @Override
    public MyIDictionary<String, Type> typeCheck(MyIDictionary<String, Type> typeEnv) throws myException {
        Type type1 = exp1.typeCheck(typeEnv);
        Type type2 = exp2.typeCheck(typeEnv);
        Type type3 = exp3.typeCheck(typeEnv);

        if(type1.equals(new BoolType()))
        {
            if(type2.equals(type3) && type3.equals(typeEnv.lookup(var)))
            {
                return typeEnv;
            }
            else {
                throw new myException(var + "," + exp2.toString() + " and " + exp3.toString() + " do not have the same type!");
            }
        }
        else throw new myException("Expression " + exp1.toString() + " does not have a bool type!");
    }

    @Override
    public IStmt deepCopy() {
        return new ConditionalAssignmentStmt(var, exp1.deepCopy(), exp2.deepCopy(), exp3.deepCopy());
    }

    @Override
    public String toString() {
        return var + " = " + exp1.toString() + " ? " + exp2.toString() + " : " + exp3.toString();
    }
}
