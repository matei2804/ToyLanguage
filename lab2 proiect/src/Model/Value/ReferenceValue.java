package Model.Value;

import Model.Type.*;

public class ReferenceValue implements Value{
    int address;
    Type locationType;

    public ReferenceValue(int address, Type locationType)
    {
        this.address = address;
        this.locationType = locationType;
    }

    public Type getLocationType()
    {
        return locationType;
    }

    @Override
    public String toString() {
        return "(" + address + ", " + locationType + ")";
    }

    public int getAddress() {
        return address;
    }

    public Type getType() {
        return new ReferenceType(locationType);
    }

    @Override
    public Value deepCopy() {
        return new ReferenceValue(address, locationType);
    }

    public boolean equals(Object o)
    {
        if(this == o) return true;
        if(!(o instanceof ReferenceValue that)) return false;
        return address == that.address && locationType == that.locationType;
    }
}
