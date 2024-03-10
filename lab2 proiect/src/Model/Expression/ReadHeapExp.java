package Model.Expression;

import Model.ADTs.MyIDictionary;
import Model.ADTs.MyIHeap;
import Model.Exceptions.myException;
import Model.Type.IntType;
import Model.Type.ReferenceType;
import Model.Type.Type;
import Model.Value.*;

public class ReadHeapExp implements Expression{

     Expression exp;

    public ReadHeapExp(Expression exp) {
        this.exp = exp;
    }

    @Override
    public Value evaluateExpression(MyIDictionary<String, Value> symTable, MyIHeap<Value> heap) throws myException {
        Value val = exp.evaluateExpression(symTable, heap);
        if (val instanceof ReferenceValue) {
            ReferenceValue refVal = (ReferenceValue) val;
            if (heap.contains(refVal.getAddress())) {
                return heap.get(refVal.getAddress());
            } else {
                throw new myException("The address doesn't exist in the heap");
            }

        } else {
            throw new myException("The expression could not be evaluated to a RefValue");
        }
    }

    @Override
    public Type typeCheck(MyIDictionary<String, Type> typeEnv) throws myException {
        Type type = exp.typeCheck(typeEnv);
        if (type instanceof ReferenceType) {
            ReferenceType refType =(ReferenceType) type;
            return refType.getInner();
        } else
            throw new myException("the rH argument is not a Reference Type");
    }

    @Override
    public Expression deepCopy() {
        return new ReadHeapExp(exp.deepCopy());
    }

    @Override
    public String toString() {
        return "rH(" + exp + ")";
    }

}
