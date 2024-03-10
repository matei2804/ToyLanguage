package Model.ADTs;

import Model.Exceptions.myException;
import Model.Statement.IStmt;
import Model.Value.StringValue;
import Model.Value.Value;
import java.io.BufferedReader;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class PrgState{
    MyIStack<IStmt> exeStack;
    MyIDictionary<String, Value> symbolTable;
    MyIList<Value> out;
    MyIDictionary<StringValue, BufferedReader> fileTable;
    MyIHeap<Value> heap;
    private int id;
    private static int freeId = 0;
    static public Lock lock = new ReentrantLock();
    private ILatchTable latchTable;


    public PrgState(MyIStack<IStmt> stk, MyIDictionary<String,Value> symbol, MyIList<Value> ot, MyDictionary<StringValue, BufferedReader> fileTable, MyIHeap<Value> heap, IStmt prg, ILatchTable latchTable){
        exeStack = stk;
        symbolTable = symbol;
        out = ot;
        this.fileTable = fileTable;
        this.heap = heap;
        this.id = getNewId();
        stk.push(prg);
        this.latchTable = latchTable;
    }

    public int getId(){return id;}

    public PrgState oneStep() throws myException{
        if(exeStack.isEmpty()) throw new myException("Program State stack is empty");
        IStmt crtStmt = exeStack.pop();
        return crtStmt.execute(this);
    }

    public synchronized int getNewId()
    {
        freeId++;
        return freeId;
    }

    public boolean isNotCompleted(){
        return !this.exeStack.isEmpty();
    }

    public MyIStack<IStmt> getStk() {
        return exeStack;
    }
    public void setExeStack(MyIStack<IStmt> newStack)
    {
        this.exeStack = newStack;
    }

    public MyIDictionary<String, Value> getSymTable() {
        return symbolTable;
    }
    public void setSymbolTable(MyIDictionary<String, Value> newSymbolTable)
    {
        this.symbolTable = newSymbolTable;
    }
    public MyIList<Value> getOutList()
    {
        return out;
    }
    public void setOutList(MyIList<Value> newOutList)
    {
        this.out = newOutList;
    }
    public MyIDictionary<StringValue, BufferedReader> getFileTable()
    {
        return this.fileTable;
    }
    public void setFileTable(MyIDictionary<StringValue, BufferedReader> f)
    {
        this.fileTable = f;
    }

    public MyIHeap<Value> getHeap() {return this.heap;}

    public void setHeap(MyIHeap<Value> newHeap) {this.heap = newHeap; }

    public void setLatchTable(ILatchTable latchTable) {
        this.latchTable = latchTable;
    }

    public ILatchTable getLatchTable() {
        return latchTable;
    }

    public String latchTableToString() throws myException {
        StringBuilder latchTableStringBuilder = new StringBuilder();
        for (int key: latchTable.getDictionary().keySet()) {
            //latchTableStringBuilder.append(String.format("%d -> %d\n", key, latchTable.get(key)));
            latchTableStringBuilder.append(key).append(" -> ").append(latchTable.get(key));
        }
        return latchTableStringBuilder.toString();
    }

    public String toString(){
        try {
            return "PROGRAM STATE:\n" +
                    "ID: " + id + "\n" +
                    "Execution stack:\n" + exeStack.toString() +
                    "Symbol table:\n" + symbolTable.toString() +
                    "Heap: \n" + heap.toString() +
                    "Output list:\n" + out.toString() +
                    "File Table:\n" + fileTable.toString() +
                    "Latch table:\n" + latchTableToString() + "\n";
        } catch (myException e) {
            throw new RuntimeException(e);
        }
    }
}
