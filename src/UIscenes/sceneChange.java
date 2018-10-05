package UIscenes;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class sceneChange {

    public sceneChange(String scene) {
        Parent root = null;
        try {
            root = FXMLLoader.load(getClass().getResource(scene + ".fxml"));
        } catch (IOException e1) {
            e1.printStackTrace();
        }
        Main.getStage().setScene(new Scene(root));

    }

    public sceneChange(String scene, Stage stage, int Width, int Height) {
        Parent root;
        try {
            root = FXMLLoader.load(getClass().getResource(scene + ".fxml"));
            stage.setScene(new Scene(root, Width, Height));
            stage.show();
        } catch (IOException e) {}
    }
}