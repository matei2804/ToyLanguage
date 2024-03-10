package Model.Value;
import Model.Type.*;

public interface Value {
    Type getType();
    Value deepCopy();
}
