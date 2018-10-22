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
    public ImageView Mic;
    @FXML
    private ImageView _menu;
    @FXML private HBox slideInMenu;
    private boolean _expanded;



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
        new recordingWorker(_currentName.getName(), Mic).execute();
    }

    public void Discard() {
        new File("DataBase-VoNZ-word/"+ _currentName.getName() + "/attempt.wav").delete();
    }

    public void Play() {
        new playWorker("'DataBase-VoNZ-word/"+ _currentName.getName() + "/attempt.wav'").execute();
    }

}
