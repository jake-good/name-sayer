package UIscenes;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;

public class SceneChange {

    public SceneChange(String scene, String previous) {
        Parent root = null;
        try {
            root = FXMLLoader.load(getClass().getResource(scene + ".fxml"));
        } catch (IOException e1) {
            e1.printStackTrace();
        }
        Main.getStage().setScene(new Scene(root));
        Main.setPreviousScene(previous);

    }

    public SceneChange(String scene, int Width, int Height) {
        Parent root = null;
        Stage stage = new Stage();
        stage.initStyle(StageStyle.UNDECORATED);
        try {
            root = FXMLLoader.load(getClass().getResource(scene + ".fxml"));
        } catch (IOException e) {
            System.out.println("fail");
        }
        stage.setScene(new Scene(root, 350, 300));
        stage.showAndWait();
    }
}