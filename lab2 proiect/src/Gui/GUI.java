package Gui;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;


public class GUI extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader mainLoader = new FXMLLoader(getClass().getResource("MainScene.fxml"));
        Parent mainRoot = mainLoader.load();
        MainSceneController mainSceneController = mainLoader.getController();

        Stage secondaryStage = new Stage();
        secondaryStage.setTitle("Main Scene");
        secondaryStage.setScene(new Scene(mainRoot));
        secondaryStage.show();


        FXMLLoader programsLoader = new FXMLLoader(getClass().getResource("ProgramsScene.fxml"));
        programsLoader.setControllerFactory(c -> new ProgramsSceneController(mainSceneController));
        Parent programsRoot = programsLoader.load();

        primaryStage.setTitle("Programs Scene");
        primaryStage.setScene(new Scene(programsRoot));
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
