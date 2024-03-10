package View;

import Model.ADTs.*;
import Model.Exceptions.myException;
import Model.Expression.*;
import Model.Statement.*;
import Model.Type.IntType;
import Model.Type.ReferenceType;
import Model.Type.StringType;
import Model.Type.Type;
import Model.Value.BoolValue;
import Model.Value.IntValue;
import Model.Value.StringValue;
import Repository.*;
import Controller.*;

import java.io.IOException;

class Interpreter {
    public static void main(String[] args) throws myException, IOException {
        /*IStmt example_1 = new CompStmt(
                new VarDeclStmt("x", new IntType()),
                new CompStmt(
                        new AssignStmt("x", new ValueExp(new IntValue(17))),
                        new PrintStmt(new VarExp("x"))
                )
        );

        try {
            example_1.typeCheck(new MyDictionary<String, Type>());
        }
        catch (myException e)
        {
            System.out.println(e.getMessage());
            System.out.println(example_1.toString() + " has a problem!");
            System.exit(0);
        }
        PrgState prg1 = new PrgState(new MyStack<>(), new MyDictionary<>(), new MyList<>(), new MyDictionary<>(),new MyHeap<>(), example_1);
        IRepository repo1 = new Repository(prg1, "log1.txt");
        Controller ctr1 = new Controller(repo1);


        IStmt example_2 = new CompStmt(
                new VarDeclStmt("x" , new IntType()),
                new CompStmt(new AssignStmt("x", new ArithmeticExp(
                        new ValueExp(new IntValue(3)),
                        new ArithmeticExp(
                                new ValueExp(new IntValue(5)), new ValueExp(new IntValue(7)), '*'
                        ),
                        '+'
                )
                ), new PrintStmt(new VarExp("x"))));

        try {
            example_2.typeCheck(new MyDictionary<String, Type>());
        }
        catch (myException e)
        {
            System.out.println(e.getMessage());
            System.out.println(example_2.toString() + " has a problem!");
            System.exit(0);
        }
        PrgState prg2 = new PrgState(new MyStack<>(), new MyDictionary<>(), new MyList<>(), new MyDictionary<>(),new MyHeap<>(), example_2);
        IRepository repo2 = new Repository(prg2,"log2.txt");
        Controller ctr2 = new Controller(repo2);


        IStmt example_3 = new CompStmt(
                new VarDeclStmt("s" , new IntType()),
                new CompStmt(new VarDeclStmt("x", new IntType()),
                        new CompStmt(
                                new AssignStmt("s", new ValueExp(new IntValue(5))),
                                new CompStmt(
                                        new IfStmt(
                                                new RelationalExp(new VarExp("s"), new ValueExp( new IntValue(1)),1),
                                                new AssignStmt("x", new ValueExp(new IntValue(20))),
                                                new AssignStmt("x", new ValueExp(new IntValue(2)))
                                        ),
                                        new PrintStmt(new VarExp("x"))
                                )
                        )
                )
        );

        try {
            example_3.typeCheck(new MyDictionary<String, Type>());
        }
        catch (myException e)
        {
            System.out.println(e.getMessage());
            System.out.println(example_3.toString() + " has a problem!");
            System.exit(0);
        }
        PrgState prg3 = new PrgState(new MyStack<>(), new MyDictionary<>(), new MyList<>(), new MyDictionary<>(),new MyHeap<>(), example_3);
        IRepository repo3 = new Repository(prg3,"log3.txt");
        Controller ctr3 = new Controller(repo3);

        IStmt example_4 = new CompStmt(
                new VarDeclStmt("fileName", new StringType()),
                new CompStmt(new AssignStmt("fileName", new ValueExp(new StringValue("D:\\Semestru 3\\Map\\lab2 proiect\\lab2 proiect\\test.txt"))),
                        new CompStmt(new OpenRFileStmt(new VarExp("fileName")),
                                new CompStmt(new VarDeclStmt("x", new IntType()),
                                        new CompStmt(new ReadFileStmt(new VarExp("fileName"), "x"),
                                                new CompStmt(new PrintStmt(new VarExp("x")),
                                                        new CompStmt(new ReadFileStmt(new VarExp("fileName"), "x"),
                                                                new CompStmt(new PrintStmt(new VarExp("x")),
                                                                        new CloseRFileStmt(new VarExp("fileName"))))))))));

        try {
            example_4.typeCheck(new MyDictionary<String, Type>());
        }
        catch (myException e)
        {
            System.out.println(e.getMessage());
            System.out.println(example_4.toString() + " has a problem!");
            System.exit(0);
        }
        PrgState prg4 = new PrgState(new MyStack<>(), new MyDictionary<>(), new MyList<>(), new MyDictionary<>(),new MyHeap<>(), example_4);
        IRepository repo4 = new Repository(prg4,"log4.txt");
        Controller ctr4 = new Controller(repo4);

        IStmt example_5 = new IfStmt(new RelationalExp(new ValueExp(new IntValue(5)), new ValueExp(new IntValue(10)), 2),
                new PrintStmt(new ValueExp(new IntValue(5))),new PrintStmt(new ValueExp(new IntValue(10))));

        try {
            example_5.typeCheck(new MyDictionary<String, Type>());
        }
        catch (myException e)
        {
            System.out.println(e.getMessage());
            System.out.println(example_5.toString() + " has a problem!");
            System.exit(0);
        }
        PrgState prg5 = new PrgState(new MyStack<>(), new MyDictionary<>(), new MyList<>(), new MyDictionary<>(),new MyHeap<>(), example_5);
        IRepository repo5 = new Repository(prg5,"log5.txt");
        Controller ctr5 = new Controller(repo5);

        IStmt example_6 = new CompStmt(new VarDeclStmt("v", new ReferenceType(new IntType())),
                new CompStmt(new NewStmt("v", new ValueExp(new IntValue(20))),
                        new CompStmt(new VarDeclStmt("a", new ReferenceType(new ReferenceType(new IntType()))),
                                new CompStmt(new NewStmt("a", new VarExp("v")),
                                        new PrintStmt(new VarExp("v"))))));

        try {
            example_6.typeCheck(new MyDictionary<String, Type>());
        }
        catch (myException e)
        {
            System.out.println(e.getMessage());
            System.out.println(example_6.toString() + " has a problem!");
            System.exit(0);
        }
        PrgState prg6 = new PrgState(new MyStack<>(), new MyDictionary<>(), new MyList<>(), new MyDictionary<>(),new MyHeap<>(), example_6);
        IRepository repo6 = new Repository(prg6,"log6.txt");
        Controller ctr6 = new Controller(repo6);

        IStmt example_7 = new CompStmt(new VarDeclStmt("v", new ReferenceType(new IntType())),
                new CompStmt(new NewStmt("v", new ValueExp(new IntValue(20))),
                        new CompStmt(new VarDeclStmt("a", new ReferenceType(new ReferenceType(new IntType()))),
                                new CompStmt(new NewStmt("a", new VarExp("v")),
                                        new CompStmt(new PrintStmt(new ReadHeapExp(new VarExp("v"))),
                                                new PrintStmt(new ArithmeticExp(new ReadHeapExp(new ReadHeapExp(new VarExp("a"))),
                                                        new ValueExp(new IntValue(5)),
                                                        '+')))))));

        try {
            example_7.typeCheck(new MyDictionary<String, Type>());
        }
        catch (myException e)
        {
            System.out.println(e.getMessage());
            System.out.println(example_7.toString() + " has a problem!");
            System.exit(0);
        }
        PrgState prg7 = new PrgState(new MyStack<>(), new MyDictionary<>(), new MyList<>(), new MyDictionary<>(),new MyHeap<>(), example_7);
        IRepository repo7 = new Repository(prg7,"log7.txt");
        Controller ctr7 = new Controller(repo7);

        IStmt example_8 = new CompStmt(new VarDeclStmt("x", new IntType()),
                new CompStmt(new AssignStmt("x", new ValueExp(new IntValue(10))),
                        new CompStmt(new WhileStmt(new RelationalExp(new VarExp("x"), new ValueExp(new IntValue(0)), 5), new CompStmt(new PrintStmt(new VarExp("x")), new AssignStmt("x", new ArithmeticExp(new VarExp("x"), new ValueExp(new IntValue(1)), '-')))),
                                new PrintStmt(new VarExp("x")))));

        try {
            example_8.typeCheck(new MyDictionary<String, Type>());
        }
        catch (myException e)
        {
            System.out.println(e.getMessage());
            System.out.println(example_8.toString() + " has a problem!");
            System.exit(0);
        }
        PrgState prg8 = new PrgState(new MyStack<>(), new MyDictionary<>(), new MyList<>(), new MyDictionary<>(),new MyHeap<>(), example_8);
        IRepository repo8 = new Repository(prg8,"log8.txt");
        Controller ctr8 = new Controller(repo8);

        IStmt example_9=new CompStmt(new VarDeclStmt("v",new ReferenceType(new IntType())),
                new CompStmt(new NewStmt("v",new ValueExp(new IntValue(20))),
                        new CompStmt(new VarDeclStmt("a",new ReferenceType(new ReferenceType(new IntType()))),
                                new CompStmt(new NewStmt("a",new VarExp("v")),
                                        new CompStmt(new NewStmt("v",new ValueExp(new IntValue(30))),
                                                new PrintStmt(new ReadHeapExp(new ReadHeapExp(new VarExp("a")))))))));

        try {
            example_9.typeCheck(new MyDictionary<String, Type>());
        }
        catch (myException e)
        {
            System.out.println(e.getMessage());
            System.out.println(example_9.toString() + " has a problem!");
            System.exit(0);
        }
        PrgState prg9 = new PrgState(new MyStack<>(), new MyDictionary<>(), new MyList<>(), new MyDictionary<>(),new MyHeap<>(), example_9);
        IRepository repo9 = new Repository(prg9,"log9.txt");
        Controller ctr9 = new Controller(repo9);

        IStmt example_10 =  new CompStmt(new VarDeclStmt("v", new IntType()),
                new CompStmt(new VarDeclStmt("a", new ReferenceType(new IntType())),
                        new CompStmt(new AssignStmt("v", new ValueExp(new IntValue(10))),
                                new CompStmt(new NewStmt("a", new ValueExp(new IntValue(22))),
                                        new CompStmt(new ForkStmt(new CompStmt(new WriteHeapStmt("a", new ValueExp(new IntValue(30))),
                                                new CompStmt(new AssignStmt("v", new ValueExp(new IntValue(32))),
                                                        new CompStmt(new PrintStmt(new VarExp("v")),
                                                                new PrintStmt(new ReadHeapExp(new VarExp("a"))))))),
                                                new CompStmt(new PrintStmt(new VarExp("v")),
                                                        new PrintStmt(new ReadHeapExp(new VarExp("a")))))))));

        try {
            example_10.typeCheck(new MyDictionary<String, Type>());
        }
        catch (myException e)
        {
            System.out.println(e.getMessage());
            System.out.println(example_10.toString() + " has a problem!");
            System.exit(0);
        }
        PrgState prg10 = new PrgState(new MyStack<>(), new MyDictionary<>(), new MyList<>(), new MyDictionary<>(),new MyHeap<>(), example_10);
        IRepository repo10 = new Repository(prg10,"log10.txt");
        Controller ctr10 = new Controller(repo10);

        TextMenu menu = new TextMenu();
        menu.addCommand(new ExitCommand("0", "exit"));
        menu.addCommand(new RunExample("1",example_3.toString(),ctr1));
        menu.addCommand(new RunExample("2",example_2.toString(),ctr2));
        menu.addCommand(new RunExample("3",example_3.toString(),ctr3));
        menu.addCommand(new RunExample("4",example_4.toString(),ctr4));
        menu.addCommand(new RunExample("5",example_5.toString(),ctr5));
        menu.addCommand(new RunExample("6",example_6.toString(),ctr6));
        menu.addCommand(new RunExample("7",example_7.toString(),ctr7));
        menu.addCommand(new RunExample("8",example_8.toString(),ctr8));
        menu.addCommand(new RunExample("9",example_9.toString(),ctr9));
        menu.addCommand(new RunExample("10",example_10.toString(),ctr10));
        */
        TextMenu menu = new TextMenu();
        IStmt ex1 = new CompStmt(
                new VarDeclStmt("a", new ReferenceType(new IntType())),
                new CompStmt(
                        new VarDeclStmt("b", new ReferenceType(new IntType())),
                        new CompStmt(
                                new VarDeclStmt("v", new IntType()),
                                new CompStmt(
                                        new NewStmt("a", new ValueExp(new IntValue(0))),
                                        new CompStmt(
                                                new NewStmt("b", new ValueExp(new IntValue(0))),
                                                new CompStmt(
                                                        new WriteHeapStmt("a", new ValueExp(new IntValue(1))),
                                                        new CompStmt(
                                                                new WriteHeapStmt("b", new ValueExp(new IntValue(2))),
                                                                new CompStmt(
                                                                        new ConditionalAssignmentStmt(
                                                                                "v",
                                                                                new RelationalExp(
                                                                                        new ReadHeapExp(new VarExp("a")),
                                                                                        new ReadHeapExp(new VarExp("b")),
                                                                                        1
                                                                                ),
                                                                                new ValueExp(new IntValue(100)),
                                                                                new ValueExp(new IntValue(200))
                                                                        ),
                                                                        new CompStmt(
                                                                                new PrintStmt(new VarExp("v")),
                                                                                new CompStmt(
                                                                                        new ConditionalAssignmentStmt(
                                                                                                "v",
                                                                                                new RelationalExp(
                                                                                                        new ArithmeticExp(
                                                                                                                new ReadHeapExp(new VarExp("b")),
                                                                                                                new ValueExp(new IntValue(2)),
                                                                                                                '-'
                                                                                                        ),
                                                                                                        new ReadHeapExp(new VarExp("a")),
                                                                                                        5
                                                                                                ),
                                                                                                new ValueExp(new IntValue(100)),
                                                                                                new ValueExp(new IntValue(200))
                                                                                        ),
                                                                                        new PrintStmt(new VarExp("v"))
                                                                                )
                                                                        )
                                                                )
                                                        )
                                                )
                                        )
                                )
                        )
                )
        );
        PrgState prg1 = new PrgState(new MyStack<>(), new MyDictionary<>(), new MyList<>(), new MyDictionary<>(),new MyHeap<>(), ex1, new MyLatchTable());
        IRepository repo1 = new Repository(prg1,"log1.txt");
        Controller ctr1 = new Controller(repo1);

        IStmt ex2 = new CompStmt(
                new VarDeclStmt("v1", new ReferenceType(new IntType())),
                new CompStmt(
                        new VarDeclStmt("v2", new ReferenceType(new IntType())),
                        new CompStmt(
                                new VarDeclStmt("v3", new ReferenceType(new IntType())),
                                new CompStmt(
                                        new VarDeclStmt("cnt", new IntType()),
                                        new CompStmt(
                                                new NewStmt("v1", new ValueExp(new IntValue(2))),
                                                new CompStmt(
                                                        new NewStmt("v2", new ValueExp(new IntValue(3))),
                                                        new CompStmt(
                                                                new NewStmt("v3", new ValueExp(new IntValue(4))),
                                                                new CompStmt(
                                                                        new NewLatchStatement("cnt", new ReadHeapExp(new VarExp("v2"))),
                                                                        new CompStmt(
                                                                                new ForkStmt(
                                                                                        new CompStmt(
                                                                                                new WriteHeapStmt("v1", new ArithmeticExp(new ReadHeapExp(new VarExp("v1")), new ValueExp(new IntValue(10)), '*')),
                                                                                                new CompStmt(
                                                                                                        new PrintStmt(new ReadHeapExp(new VarExp("v1"))),
                                                                                                        new CountDownStatement("cnt")
                                                                                                )
                                                                                        )
                                                                                ),
                                                                                new CompStmt(
                                                                                        new ForkStmt(
                                                                                                new CompStmt(
                                                                                                        new WriteHeapStmt("v2", new ArithmeticExp(new ReadHeapExp(new VarExp("v2")), new ValueExp(new IntValue(10)), '*')),
                                                                                                        new CompStmt(
                                                                                                                new PrintStmt(new ReadHeapExp(new VarExp("v2"))),
                                                                                                                new CountDownStatement("cnt")
                                                                                                        )
                                                                                                )
                                                                                        ),
                                                                                        new CompStmt(
                                                                                                new ForkStmt(
                                                                                                        new CompStmt(
                                                                                                                new WriteHeapStmt("v3", new ArithmeticExp(new ReadHeapExp(new VarExp("v3")), new ValueExp(new IntValue(10)), '*')),
                                                                                                                new CompStmt(
                                                                                                                        new PrintStmt(new ReadHeapExp(new VarExp("v3"))),
                                                                                                                        new CountDownStatement("cnt")
                                                                                                                )
                                                                                                        )
                                                                                                ),
                                                                                                new CompStmt(
                                                                                                        new LatchAwaitStatement("cnt"),
                                                                                                        new CompStmt(
                                                                                                                new PrintStmt(new ValueExp(new IntValue(100))),
                                                                                                                new CompStmt(
                                                                                                                        new CountDownStatement("cnt"),
                                                                                                                        new PrintStmt(new ValueExp(new IntValue(100)))
                                                                                                                )
                                                                                                        )
                                                                                                )
                                                                                        )
                                                                                )
                                                                        )
                                                                )
                                                        )
                                                )
                                        )
                                )
                        )
                )
        );

        PrgState prg2 = new PrgState(new MyStack<>(), new MyDictionary<>(), new MyList<>(), new MyDictionary<>(),new MyHeap<>(), ex2, new MyLatchTable());
        IRepository repo2 = new Repository(prg2,"log2.txt");
        Controller ctr2 = new Controller(repo2);


        menu.addCommand(new ExitCommand("0", "exit"));
        menu.addCommand(new RunExample("1",ex1.toString(),ctr1));
        menu.addCommand(new RunExample("2",ex2.toString(),ctr2));
        menu.show();
    }
}
