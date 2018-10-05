package UIscenes;


import javafx.scene.control.Button;
import javafx.scene.control.Label;

public class SelectControl {

    public Button _listenButton;
    public Label _uploadList;


    public void Listen() {
        new sceneChange("LISTEN");
    }
}
