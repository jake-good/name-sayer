package UIscenes;


import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.Window;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

public class RecordControl implements Initializable {

    private NameModel _currentName;
    public Label Name;
    public TextField _recordingName;
    private String _activeRecording;
    public Label recLabel;
    public ImageView Mic;
    @FXML
    private ImageView _menu;
    @FXML private HBox slideInMenu;
    private boolean _expanded;


    public Button cr;
    public Button p;
    public Button d;
    public Button c;

    // The Record scene will presumably have a name and a name file associated with it. In this class we need individual methods for the following:
    // Playing the original name
    // Recording a new name
    // Playing/Pausing that recording
    // Viewing/deleting old recordings.


    public void Compare() { new sceneChange("ASSESS"); }

    public void Select() { new sceneChange("SELECT"); }

    public void Listen() {
        new sceneChange("LISTEN");
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        _currentName = NameModel._Names.get(NameModel._currentName);
        Name.setText(_currentName.getName());
        _menu.setOnMouseClicked(event -> {
            _expanded = new SlideMenu(slideInMenu, _expanded).SlideMenuMake();
        });
    }

    public void Record() {
        File file = new File("src/images/orange-mic.png");
        javafx.scene.image.Image image = new Image(file.toURI().toString());
        Mic.setImage(image);

        new File("DataBase-VoNZ-word/" + _currentName.getName()).mkdir();
        //Use a swingworker to prevent the GUI from freezing when recording the attempt.
        String recName = _recordingName.getText();
        new recordingWorker(_currentName.getName(), recName, Mic).execute();
        setActiveRecording(recName);
    }

    public void Discard() {
        new File("DataBase-VoNZ-word/"+ _currentName.getName() + "/"+ _activeRecording + ".wav").delete();
        setActiveRecording("No Recording selected");
    }

    public void changeActiveRecording() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open Resource File");
        fileChooser.setInitialDirectory(new File("DataBase-VoNZ-word/"+ _currentName.getName()));
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Audio Files", "*.wav", "*.mp3", "*.aac"));
        Window stage = new Stage();
        File selectedFile = fileChooser.showOpenDialog(stage);
        if (selectedFile != null) {
            String fileName = selectedFile.getName();
            setActiveRecording(fileName.substring(0, fileName.lastIndexOf('.')));
        }
    }

    public void Play() {
        new playWorker("'DataBase-VoNZ-word/"+ _currentName.getName() + "/"+ _activeRecording + ".wav'").execute();
    }

    public void setActiveRecording(String name) {
        _activeRecording = name;
        recLabel.setText(name);
        _currentName.set_recName(name);
    }
}
