package Model.ADTs;

import Model.Exceptions.myException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MyHeap<V> implements MyIHeap<V>{

    Map<Integer, V> map;
    Integer freeLocation;

    public MyHeap()
    {
        map = new HashMap<Integer, V>();
        freeLocation = 0;
    }

    @Override
    public int allocate(V value){
        int newLocation = freeLocation + 1;
        map.put(newLocation, value);
        freeLocation = newLocation;
        return newLocation;
    }

    @Override
    public void update(int address, V value) {
        map.replace(address, value);
    }

    @Override
    public V get(int address)
    {
        return map.get(address);
    }

    @Override
    public void deallocate(int address) {
        map.remove(address);
    }

    @Override
    public boolean contains(int address){
        return map.containsKey(address);
    }

    @Override
    public Map<Integer, V> getContent()
    {
        return map;
    }

    @Override
    public void add(Integer id, V value) throws myException {
        if(map.containsKey(id))
        {
            throw new myException("Address already exists!");
        }
        else {
            map.put(id, value);
        }
    }

    @Override
    public void setContent(Map<Integer, V> content) {
        map = content;
    }

    @Override
    public String toString() {
        StringBuilder content = new StringBuilder();

        for (Map.Entry<Integer, V> el : map.entrySet()) {
            content.append(el.getKey()).append("-").append(el.getValue()).append("\n");
        }
        return content.toString();
    }
}
