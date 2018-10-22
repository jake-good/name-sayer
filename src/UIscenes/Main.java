package UIscenes;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    private static Stage _primaryStage;
    private static String _previousScene;
    @Override
    public void start(Stage primaryStage) throws Exception{
        _primaryStage = primaryStage;
        Parent root = FXMLLoader.load(getClass().getResource("SELECT.fxml"));
        primaryStage.setTitle("Name Sayer");
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
        _previousScene = "SELECT";
    }

    public static Stage getStage() {
        return _primaryStage;
    }

    public static String getPreviousScene() {
        return _previousScene;
    }

    public static void setPreviousScene(String scene) {
        _previousScene = scene;
    }



    public static void main(String[] args) {
        launch(args);
    }
}
