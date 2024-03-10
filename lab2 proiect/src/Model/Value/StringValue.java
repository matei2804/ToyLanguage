package Model.Value;

import Model.Type.StringType;
import Model.Type.Type;

public class StringValue implements Value{

    final String val;

    public StringValue(String val) {
        this.val = val;
    }
    public StringValue() {
        this.val = "";
    }

    public String getVal()
    {
        return val;
    }

    public String toString()
    {
        return val;
    }

    @Override
    public Type getType() {
        return new StringType();
    }

    @Override
    public Value deepCopy() {
        return new StringValue(val);
    }

    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof StringValue that)) return false;
        return val.equals(that.val);
    }

}
