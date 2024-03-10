package Model.Statement;

import Model.ADTs.MyIDictionary;
import Model.ADTs.MyIHeap;
import Model.ADTs.MyIStack;
import Model.ADTs.PrgState;
import Model.Exceptions.myException;
import Model.Expression.Expression;
import Model.Type.IntType;
import Model.Type.StringType;
import Model.Type.Type;
import Model.Value.IntValue;
import Model.Value.StringValue;
import Model.Value.Value;

import java.io.BufferedReader;
import java.io.IOException;

public class ReadFileStmt implements IStmt{
    Expression exp;
    String var_name;

    public ReadFileStmt(Expression e, String s)
    {
        this.exp = e;
        this.var_name = s;
    }

    public String toString() {
        return "Read From " + exp + " into " + var_name;
    }

    @Override
    public PrgState execute(PrgState state) throws myException {
        MyIStack<IStmt> stk = state.getStk();
        MyIHeap<Value> heap = state.getHeap();
        MyIDictionary<String, Value> symbolTable = state.getSymTable();
        Value val = exp.evaluateExpression(symbolTable, heap);
        if(symbolTable.isDefined(var_name))
        {
            if(symbolTable.lookup(var_name).getType().equals(new IntType()))
            {
                if(val.getType().equals(new StringType()))
                {
                    StringValue value = (StringValue) val;
                    MyIDictionary<StringValue, BufferedReader> fileTable = state.getFileTable();
                    if(fileTable.isDefined(value))
                    {
                        BufferedReader bufferedReader = fileTable.lookup(value);
                        try{
                            String line = bufferedReader.readLine();
                            Value intVal;
                            IntType type = new IntType();
                            if (line == null) {
                                intVal = type.defaultValue();
                            } else if ( ! line.chars().allMatch(Character::isDigit) )
                            {
                                throw new myException("Given type is not supported by file operation!");
                            }
                            else {
                                intVal = new IntValue(Integer.parseInt(line));
                            }
                            symbolTable.update(var_name, intVal);

                        } catch (IOException e) {
                            throw new myException(e.getMessage());
                        }
                    }
                    else {
                        throw new myException("The file " + value.getVal() + " is not in the File Table!");
                    }
                }
                else {
                    throw new myException("Expression is not of type String!\n");
                }
            }
            else {
                throw new myException(var_name + " is not int type!\n");
            }
        }
        else {
            throw new myException(var_name + " is not defined in symbol table!\n");
        }
        return null;
    }

    @Override
    public MyIDictionary<String, Type> typeCheck(MyIDictionary<String, Type> typeEnv) throws myException {
        if(typeEnv.isDefined(var_name))
        {
            Type varType = typeEnv.lookup(var_name);
            Type expType = exp.typeCheck(typeEnv);
            if (!varType.equals(new IntType())) {
                throw new myException("The variable in " + this.toString() + " is not an integer");
            }
            if (!expType.equals(new StringType())) {
                throw new myException("The file name in " + this.toString() + " is not a string");
            }
            return typeEnv;
        }
        else throw new myException(var_name + "is not defined!");
    }

    @Override
    public IStmt deepCopy() {
        return new ReadFileStmt(exp.deepCopy(), var_name);
    }
}
