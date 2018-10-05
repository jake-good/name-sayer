package UIscenes;

import javafx.scene.control.Button;

public class ListenControl {

    public void Record() {
        new sceneChange("RECORD");
    }

    public void Select() {
        new sceneChange("SELECT");
    }
}
