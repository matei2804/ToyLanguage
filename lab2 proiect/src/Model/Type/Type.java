package Model.Type;
import Model.Value.*;

public interface Type {
    Value defaultValue();
    Type deepCopy();
}
