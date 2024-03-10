package Model.ADTs;

import Model.Exceptions.myException;

public class MyLatchTable extends MyDictionary<Integer, Integer> implements ILatchTable{
    private int nextFreeLocation;

    public MyLatchTable() {
        super();
        this.nextFreeLocation = 1;
    }

    @Override
    public void add(Integer key, Integer value) throws myException {
        if (!key.equals(nextFreeLocation))
            throw new myException("Invalid lock table location!");
        super.add(key, value);
        synchronized (this) {
            nextFreeLocation++;
        }
    }

    @Override
    public int put(Integer value) throws myException {
        super.add(nextFreeLocation, value);
        synchronized (this) {
            nextFreeLocation++;
        }
        return nextFreeLocation-1;
    }

    @Override
    public int get(int position) throws myException {
        synchronized (this) {
            if (!this.dict.containsKey(position))
                throw new myException(position + " is not present in the table!");
            return this.dict.get(position);
        }
    }

    @Override
    public int getFirstFreeLocation(){
        int locationAddress = 1;
        while (this.dict.get(locationAddress) != null)
            locationAddress++;
        return locationAddress;
    }
}
