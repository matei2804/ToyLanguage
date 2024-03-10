package Model.ADTs;
import Model.Exceptions.myException;
import java.util.*;

public class MyDictionary<T, E> implements MyIDictionary<T,E>{

    HashMap<T, E> dict;

    public MyDictionary() {
        this.dict = new HashMap<>();
    }

    @Override
    public E lookup(T id) {
        return dict.get(id);
    }

    @Override
    public boolean isDefined(T key) {
        return dict.containsKey(key);
    }

    @Override
    public MyIDictionary<T, E> deepCopy() {
        HashMap<T, E> newMap = new HashMap<T, E>(dict);
        MyDictionary<T, E> newDictionary = new MyDictionary<T, E>();
        newDictionary.setContent(newMap);
        return newDictionary;
    }

    public void setContent(HashMap<T, E> content) {
        dict = content;
    }
    @Override
    public Map<T, E> getContent() {
        return dict;
    }

    @Override
    public void add(T key, E element) throws myException {
        if (dict.containsKey(key)) {
            throw new myException("Key already exists!\n");
        }
        dict.put(key, element);
    }
    @Override
    public void update(T key, E element) throws myException {
        if(!dict.containsKey(key))
        {
            throw new myException("Element does not exist!\n");
        }
        else dict.replace(key, element);
    }

    @Override
    public void remove(T key, E element) throws myException {
        if (dict.containsKey(key)) {
            if (dict.get(key).equals(element)) {
                dict.remove(key);
            } else {
                throw new myException("Element does not exist for the provided key!\n");
            }
        } else {
            throw new myException("Key does not exist!\n");
        }
    }

    @Override
    public Map<T, E> getDictionary() {
        return dict;
    }

    @Override
    public String toString() {
        StringBuilder s = new StringBuilder();
        for(var elem: dict.keySet()) {
            if (elem != null)
                s.append(elem.toString()).append(" -> ").append(dict.get(elem).toString()).append('\n');
        }
        return s.toString();
    }
}
