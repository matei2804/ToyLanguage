package Model.Expression;
import Model.ADTs.MyIDictionary;
import Model.ADTs.MyIHeap;
import Model.Exceptions.myException;
import Model.Type.Type;
import Model.Value.*;


public interface Expression {
    Value evaluateExpression(MyIDictionary<String,Value> table, MyIHeap<Value> heap) throws myException;
    Type typeCheck(MyIDictionary<String,Type> typeEnv) throws myException;
    Expression deepCopy();
}
