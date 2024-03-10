package Model.ADTs;
import Model.Exceptions.myException;

public interface MyIList<T>{

    void addElement(T element);
    void removeElement(T element) throws myException;
    int getSize();
    T getElement(int position);
    String toString();

}
