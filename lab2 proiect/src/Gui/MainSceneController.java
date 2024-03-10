package Gui;

import Controller.Controller;
import Model.ADTs.*;
import Model.Exceptions.myException;
import Model.Statement.IStmt;
import Model.Value.StringValue;
import Model.Value.Value;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.util.Pair;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

public class MainSceneController {

    private Controller controller;
    public Button runButton;
    @FXML
    private TableColumn<Map.Entry<Integer, Value>, Integer> heapAdress;
    @FXML
    private TableColumn<Map.Entry<Integer, Value>, Value> heapValue;
    @FXML
    private TableColumn<Map.Entry<String, Value>, String> symbolTableVarName;
    @FXML
    private TableColumn<Map.Entry<String, Value>, Value> symbolTableValue;
    @FXML
    private TextField nrProgramsTextField;
    @FXML
    private TableView<Map.Entry<Integer, Value>> heapTableView;
    @FXML
    private ListView<Value> outListView;
    @FXML
    private ListView<Map.Entry<StringValue, BufferedReader>> fileTableListView;
    @FXML
    private ListView<String> programStateIDListView;
    @FXML
    private TableView<Map.Entry<String, Value>> symbolTableView;
    @FXML
    private ListView<IStmt> stackListView;
    @FXML
    private TableView<Pair<Integer, Integer>> latchTableView;
    @FXML
    private TableColumn<Pair<Integer, Integer>, String> latchLocationColumn;
    @FXML
    private TableColumn<Pair<Integer, Integer>, String> latchValueColumn;


    public void setController(Controller ctrl)
    {
        this.controller = ctrl;
        populateIds();
    }

    void populateIds()
    {
        List<PrgState> programs = controller.getProgramList();
        programStateIDListView.setItems(FXCollections.observableList(programs.stream().map(p-> String.valueOf(p.getId())).toList()));
        nrProgramsTextField.setText("" + programs.size());
    }

    void populateHeap(PrgState program)
    {
        MyIHeap<Value> heap = program.getHeap();
        Map<Integer, Value> heapAddr = heap.getContent();
        List<Map.Entry<Integer, Value>> heapTableList = new ArrayList<>(heapAddr.entrySet());

        this.heapTableView.setItems(FXCollections.observableList(heapTableList));
        this.heapTableView.refresh();
    }

    void populateOutList(PrgState program)
    {
        MyIList<Value> out = program.getOutList();
        List<Value> outList = new LinkedList<>();
        for(int i = 0; i < out.getSize(); i++)
        {
            outList.add(out.getElement(i));
        }

        this.outListView.setItems(FXCollections.observableList(outList));
        this.outListView.refresh();
    }

    void populateFileTable(PrgState program)
    {
        MyIDictionary<StringValue, BufferedReader> fileTable = program.getFileTable();
        Map<StringValue, BufferedReader> fileTableMap = new HashMap<>(fileTable.getContent());
        List<Map.Entry<StringValue, BufferedReader>> fileTableList = new ArrayList<>(fileTableMap.entrySet());
        this.fileTableListView.setItems(FXCollections.observableList(fileTableList));
        this.fileTableListView.refresh();
    }

   void populateSymbolTable(PrgState program)
   {
       MyIDictionary<String, Value> symbolTable = program.getSymTable();
       Map<String, Value> symbolTableMap = new HashMap<>(symbolTable.getContent());
       List<Map.Entry<String, Value>> symbolTableList = new ArrayList<>(symbolTableMap.entrySet());
       this.symbolTableView.setItems(FXCollections.observableList(symbolTableList));
       this.symbolTableView.refresh();
   }

   void populateStack(PrgState program)
   {
       MyIStack<IStmt> stack = program.getStk();
       List<IStmt> stackList = new ArrayList<>(stack.getContent());
       this.stackListView.setItems(FXCollections.observableList(stackList));
       this.stackListView.refresh();
   }

    private void populateLatchTableView() {
        PrgState programState = getCurrentProgram();
        ILatchTable latchTable = Objects.requireNonNull(programState).getLatchTable();
        ArrayList<Pair<Integer, Integer>> latchTableEntries = new ArrayList<>();
        for(Map.Entry<Integer, Integer> entry: latchTable.getContent().entrySet()) {
            latchTableEntries.add(new Pair<>(entry.getKey(), entry.getValue()));
        }
        latchTableView.setItems(FXCollections.observableArrayList(latchTableEntries));
    }

    @FXML
    private void initialize() {
        this.heapAdress.setCellValueFactory(p -> new SimpleIntegerProperty(p.getValue().getKey()).asObject());
        this.heapValue.setCellValueFactory(p -> new ReadOnlyObjectWrapper<>(p.getValue().getValue()));

        this.symbolTableVarName.setCellValueFactory(p-> new ReadOnlyObjectWrapper<>(p.getValue().getKey()));
        this.symbolTableValue.setCellValueFactory(p-> new ReadOnlyObjectWrapper<>(p.getValue().getValue()));

        this.programStateIDListView.setOnMouseClicked(mouseEvent -> {
            changeProgramState(getCurrentProgram());
        });

        this.runButton.setOnAction(actionEvent -> {
            setRunButton();
        });
        latchLocationColumn.setCellValueFactory(p -> new ReadOnlyObjectWrapper<>(p.getValue().getKey().toString()));
        latchValueColumn.setCellValueFactory(p -> new ReadOnlyObjectWrapper<>(p.getValue().getValue().toString()));
    }

    private void changeProgramState(PrgState currentProgramState) {
        if(currentProgramState == null)
            return;

        populateStack(currentProgramState);
        populateSymbolTable(currentProgramState);
        populateOutList(currentProgramState);
        populateFileTable(currentProgramState);
        populateHeap(currentProgramState);
        populateLatchTableView();
    }

    private PrgState getCurrentProgram()
    {
        if(this.programStateIDListView.getSelectionModel().getSelectedIndex() == -1)
            return null;

        int id = Integer.parseInt(this.programStateIDListView.getSelectionModel().getSelectedItem());
        List<PrgState> programs = controller.getProgramList();
        for(PrgState program : programs)
        {
            if(program.getId() == id)
                return program;
        }
        return null;
    }

    void setRunButton(){
        if(controller == null){
            Alert alert = new Alert(Alert.AlertType.ERROR, "The program was not selected", ButtonType.OK);
            alert.showAndWait();
            return;
        }

        if(getCurrentProgram() == null)
        {
            Alert alert = new Alert(Alert.AlertType.ERROR, "You have to select and ID!", ButtonType.OK);
            alert.showAndWait();
            return;
        }

        boolean programStateLeft = getCurrentProgram().getStk().isEmpty();
        if(programStateLeft){
            this.controller.setProgramListController(this.controller.removeCompletedPrograms(this.controller.getProgramList()));
            populateIds();
            Alert alert = new Alert(Alert.AlertType.ERROR, "Nothing left to execute", ButtonType.OK);
            alert.showAndWait();
            return;
        }

        try {
            controller.oneStepGUI(this.controller.getProgramList());
        }
        catch ( RuntimeException  e)
        {
            Alert alert = new Alert(Alert.AlertType.ERROR, e.getMessage(), ButtonType.OK);
            alert.showAndWait();
            return;
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        changeProgramState(getCurrentProgram());
        populateIds();
    }

}
