package Model.Value;
import Model.Type.*;

public class IntValue implements Value{
    final int val;
    public IntValue(int v)
    {
        this.val = v;
    }
    public IntValue()
    {
        this.val = 0;
    }
    public int getVal() {
        return val;
    }

    public String toString(){
        return Integer.toString(val);
    }

    public Type getType()
    {
        return new IntType();
    }

    @Override
    public Value deepCopy() {
        return new IntValue(val);
    }

    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof IntValue that)) return false;
        return val == that.val;
    }

}
