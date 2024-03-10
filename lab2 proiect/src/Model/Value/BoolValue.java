package Model.Value;

import Model.Type.BoolType;
import Model.Type.Type;

public class BoolValue implements Value{

    final boolean val;
    public BoolValue(boolean v)
    {
        this.val = v;
    }

    public BoolValue()
    {
        this.val = false;
    }

    public boolean getVal()
    {
        return val;
    }

    @Override
    public Type getType() {
        return new BoolType();
    }

    @Override
    public Value deepCopy() {
        return new BoolValue(val);
    }

    public String toString()
    {
        return Boolean.toString(val);
    }

    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof BoolValue that)) return false;
        return val == that.val;
    }

}
