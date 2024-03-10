package Model.ADTs;

import Model.Exceptions.myException;

public interface ILatchTable  extends MyIDictionary<Integer, Integer> {
    int put(Integer value) throws myException;
    int get(int position) throws myException;
    int getFirstFreeLocation();
}