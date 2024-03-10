package Model.ADTs;
import java.util.*;
import Model.Exceptions.myException;

public class MyList<T> implements MyIList<T>{

    final LinkedList<T> list;

    public MyList()
    {
        this.list = new LinkedList<>();
    }
    @Override
    public void addElement(T element) {
        list.add(element);
    }

    @Override
    public void removeElement(T element) throws myException {
        boolean removed = false;
        Iterator<T> iterator = list.iterator();
        while (iterator.hasNext()) {
            T item = iterator.next();
            if (item.equals(element)) {
                iterator.remove();
                removed = true;
            }
        }
        if (!removed) {
            throw new myException("Element is not in list!\n");
        }
    }

    @Override
    public int getSize() {
        return list.size();
    }

    @Override
    public T getElement(int position) {
        return list.get(position);
    }

    @Override
    public String toString()
    {
        StringBuilder output = new StringBuilder();
        for(T element : list)
        {
            if(element != null)
            {
                output.append(element.toString());
                output.append(" ");
            }
        }
        if(output.isEmpty())
            return output.toString();
        else
        {
            output.append("\n");
            return output.toString();
        }
    }
}
