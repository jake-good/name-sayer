package UIscenes;


import javafx.scene.control.Button;

public class RecordControl {

    // The Record scene will presumably have a name and a name file associated with it. In this class we need individual methods for the following:
    // Playing the original name
    // Recording a new name
    // Playing/Pausing that recording
    // Viewing/deleting old recordings.

    public void Compare() {
        new sceneChange("ASSESS");
    }

    public void Select() {
        new sceneChange("SELECT");
    }

    public void Listen() {
        new sceneChange("LISTEN");
    }
}
