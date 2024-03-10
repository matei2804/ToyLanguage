package Model.ADTs;

import Model.Exceptions.myException;
import java.util.*;

public interface MyIHeap<V> {
    int allocate(V value);
    void update(int address, V value);
    V get(int address);
    void deallocate(int address);
    boolean contains(int address);
    Map<Integer, V> getContent();
    void setContent(Map<Integer, V> content);
    public void add(Integer id, V value) throws myException;
}
