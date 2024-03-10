package Model.ADTs;
import Model.Exceptions.myException;
import java.util.*;

public interface MyIDictionary<T, E> {
    E lookup(T key);
    void add(T key, E element) throws myException;
    void update(T key, E element) throws myException;
    void remove(T key, E element) throws myException;
    Map<T, E> getDictionary();
    String toString();
    boolean isDefined(T id);
    MyIDictionary<T, E> deepCopy();
    Map<T, E> getContent();
}
