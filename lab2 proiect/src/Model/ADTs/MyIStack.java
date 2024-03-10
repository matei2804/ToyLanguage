package Model.ADTs;
import Model.Exceptions.myException;

import java.util.Stack;

public interface MyIStack<T>{
    T pop() throws myException;
    void push(T v);
    boolean isEmpty();
    int size();
    String toString();

    Stack<T> getContent();
}
