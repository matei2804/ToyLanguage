package Model.ADTs;
import java.util.*;
import Model.Exceptions.myException;

public class MyStack<T> implements MyIStack<T>{
    final Stack<T> stack;

    public MyStack()
    {
        this.stack = new Stack<>();
    }
    @Override
    public T pop() throws myException {
        if(stack.isEmpty())
        {
            throw new myException("The stack is empty!\n");
        }
        return stack.pop();
    }

    @Override
    public void push(T v) {
        stack.push(v);
    }

    @Override
    public boolean isEmpty() {
        return stack.isEmpty();
    }

    @Override
    public int size() {
        return stack.size();
    }

    @Override
    public String toString()
    {
        StringBuilder output = new StringBuilder();

        for(T item : this.stack)
        {
            output.append(item).append(" | ");
        }
        if(!stack.isEmpty())
        {
            output.setLength(output.length() - 2);
        }

        if(output.isEmpty())
        {
            return output.toString();
        }
        else {
            output.append("\n");
            return output.toString();
        }
    }

    @Override
    public Stack<T> getContent()
    {
        return stack;
    }
}
