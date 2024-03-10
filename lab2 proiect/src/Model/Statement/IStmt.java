package Model.Statement;

import Model.ADTs.*;
import Model.Exceptions.myException;
import Model.Type.Type;

public interface IStmt{
    PrgState execute(PrgState state) throws myException;
    MyIDictionary<String, Type> typeCheck(MyIDictionary<String,Type> typeEnv) throws myException;
    IStmt deepCopy();
}