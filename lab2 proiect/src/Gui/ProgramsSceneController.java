package Gui;

import Controller.Controller;
import Model.ADTs.*;
import Model.Exceptions.myException;
import Model.Expression.*;
import Model.Statement.*;
import Model.Type.*;
import Model.Value.BoolValue;
import Model.Value.IntValue;
import Model.Value.StringValue;
import Repository.IRepository;
import Repository.Repository;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ListView;
import Gui.MainSceneController;
import java.util.ArrayList;
import java.util.List;

public class ProgramsSceneController {

    private final MainSceneController mainSceneController;
    public Button runButton;
    @FXML
    private ListView<IStmt> programListView;

    public ProgramsSceneController(MainSceneController mainSceneController)
    {
        this.mainSceneController = mainSceneController;
    }

    private List<IStmt> buildPrograms()
    {
        IStmt example_1 = new CompStmt(
                new VarDeclStmt("x", new IntType()),
                new CompStmt(
                        new AssignStmt("x", new ValueExp(new IntValue(17))),
                        new PrintStmt(new VarExp("x"))
                )
        );

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

        IStmt example_5 = new IfStmt(new RelationalExp(new ValueExp(new IntValue(5)), new ValueExp(new IntValue(10)), 2),
                new PrintStmt(new ValueExp(new IntValue(5))),new PrintStmt(new ValueExp(new IntValue(10))));


        IStmt example_6 = new CompStmt(new VarDeclStmt("v", new ReferenceType(new IntType())),
                new CompStmt(new NewStmt("v", new ValueExp(new IntValue(20))),
                        new CompStmt(new VarDeclStmt("a", new ReferenceType(new ReferenceType(new IntType()))),
                                new CompStmt(new NewStmt("a", new VarExp("v")),
                                        new PrintStmt(new VarExp("v"))))));


        IStmt example_7 = new CompStmt(new VarDeclStmt("v", new ReferenceType(new IntType())),
                new CompStmt(new NewStmt("v", new ValueExp(new IntValue(20))),
                        new CompStmt(new VarDeclStmt("a", new ReferenceType(new ReferenceType(new IntType()))),
                                new CompStmt(new NewStmt("a", new VarExp("v")),
                                        new CompStmt(new PrintStmt(new ReadHeapExp(new VarExp("v"))),
                                                new PrintStmt(new ArithmeticExp(new ReadHeapExp(new ReadHeapExp(new VarExp("a"))),
                                                        new ValueExp(new IntValue(5)),
                                                        '+')))))));

        IStmt example_8 = new CompStmt(new VarDeclStmt("x", new IntType()),
                new CompStmt(new AssignStmt("x", new ValueExp(new IntValue(10))),
                        new CompStmt(new WhileStmt(new RelationalExp(new VarExp("x"), new ValueExp(new IntValue(0)), 5), new CompStmt(new PrintStmt(new VarExp("x")), new AssignStmt("x", new ArithmeticExp(new VarExp("x"), new ValueExp(new IntValue(1)), '-')))),
                                new PrintStmt(new VarExp("x")))));


        IStmt example_9=new CompStmt(new VarDeclStmt("v",new ReferenceType(new IntType())),
                new CompStmt(new NewStmt("v",new ValueExp(new IntValue(20))),
                        new CompStmt(new VarDeclStmt("a",new ReferenceType(new ReferenceType(new IntType()))),
                                new CompStmt(new NewStmt("a",new VarExp("v")),
                                        new CompStmt(new NewStmt("v",new ValueExp(new IntValue(30))),
                                                new PrintStmt(new ReadHeapExp(new ReadHeapExp(new VarExp("a")))))))));

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

        IStmt conditionalAssignment_example = new CompStmt(
                new VarDeclStmt("b", new BoolType()),
                new CompStmt(
                        new VarDeclStmt("c", new IntType()),
                        new CompStmt(
                                new AssignStmt("b", new ValueExp(new BoolValue(true))),
                                new CompStmt(
                                        new ConditionalAssignmentStmt("c", new VarExp("b"), new ValueExp(new IntValue(100)), new ValueExp(new IntValue(100))),
                                        new CompStmt(
                                                new PrintStmt(new VarExp("c")),
                                                new CompStmt(
                                                        new ConditionalAssignmentStmt("c", new ValueExp(new BoolValue(false)), new ValueExp(new IntValue(100)), new ValueExp(new IntValue(200))),
                                                        new PrintStmt(new VarExp("c"))
                                                )
                                        )
                                )
                        )
                )
        );

        try {
            conditionalAssignment_example.typeCheck(new MyDictionary<String, Type>());
        }
        catch (myException e)
        {
            Alert alert = new Alert(Alert.AlertType.ERROR, e.getMessage() + "\n" + conditionalAssignment_example.toString() + " has a problem!", ButtonType.OK);
            alert.showAndWait();
        }


        IStmt example_latch = new CompStmt(
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

        List<IStmt> programs = new ArrayList<>();
        programs.add(example_1);
        programs.add(example_2);
        programs.add(example_3);
        programs.add(example_4);
        programs.add(example_5);
        programs.add(example_6);
        programs.add(example_7);
        programs.add(example_8);
        programs.add(example_9);
        programs.add(example_10);
        programs.add(conditionalAssignment_example);
        programs.add(example_latch);

        return programs;
    }

    public void setRunButton()
    {
        this.runButton.setOnAction(actionEvent -> {

            int index = this.programListView.getSelectionModel().getSelectedIndex();
            if(index < 0)
                return;

            IStmt ex = this.programListView.getSelectionModel().getSelectedItem();
            PrgState program = new PrgState(new MyStack<>(), new MyDictionary<>(), new MyList<>(), new MyDictionary<>(),new MyHeap<>(), ex, new MyLatchTable());
            IRepository repository = new Repository(program, "log" + index + ".txt");
            Controller controller = new Controller(repository);
            this.mainSceneController.setController(controller);
        });
    }

    @FXML
    private void initialize() {

        List<IStmt> programs = buildPrograms();
        this.programListView.setItems(FXCollections.observableList(programs));
        setRunButton();
    }

}
