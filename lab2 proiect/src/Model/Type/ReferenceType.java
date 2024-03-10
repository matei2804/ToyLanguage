package Model.Type;

import Model.Value.*;

public class ReferenceType implements Type{
    Type inner;
    public ReferenceType(Type inner) {
        this.inner = inner;
    }

    public ReferenceType() {};
    public Type getInner() {
        return inner;
    }

    public boolean equals(Object another){
        if (another instanceof ReferenceType )
            return inner.equals(((ReferenceType) another).getInner());
        else
            return false;
    }
    public String toString() { return "Ref(" + inner.toString() + ")"; }
    public Value defaultValue() { return new ReferenceValue(0, inner);}

    @Override
    public Type deepCopy() {
        return new ReferenceType(inner.deepCopy());
    }
}
