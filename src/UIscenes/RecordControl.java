package UIscenes;


import javafx.fxml.Initializable;
import javafx.scene.control.Button;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

public class RecordControl implements Initializable {

    private NameModel _currentName;
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

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        _currentName = NameModel._Names.get(NameModel._currentName);
    }

    public void Record() {
        new File("names/" + _currentName.getName()).mkdir();
        //Use a swingworker to prevent the GUI from freezing when recording the attempt.
        new recordingWorker(_currentName.getName()).execute();
    }

    public void Save() {}

    public void Discard() {

    }

    public void Play() {}
}
