package UIscenes;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Tooltip;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;

import java.net.URL;
import java.util.ResourceBundle;

public class AssessControl implements Initializable {

    public Button _go;
    private NameModel _currentName;
    @FXML public ComboBox<Integer> _numberOfCompares;
    @FXML private ImageView _menu;
    @FXML private HBox slideInMenu;
    private boolean _expanded;
    public Label _nameLabel;
    @FXML private Label _yourAttempToolTip;
    @FXML private Label _dateBaseAttempToolTip;

    public void Return() {
        new sceneChange("RECORD", "ASSES");
    }

    public void Select() { new sceneChange("SELECT", "ASSES"); }

    public void Listen() {
        new sceneChange("LISTEN", "ASSES");
    }



    public void playAttempt(){
        new playWorker("'DataBase-VoNZ-word/"+ _currentName._Name + "/attempt.wav'").execute();
    }

    public void playDataBase(){
        new playWorker("output.wav").execute();
    }


    public void compareRecordings(){
        int numberOfCompares = _numberOfCompares.getValue();
        new compareWorker("'DataBase-VoNZ-word/"+ _currentName._Name + "/attempt.wav'",numberOfCompares).execute();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        _currentName = NameModel._Names.get(NameModel._currentName);
        _numberOfCompares.getItems().setAll(1, 2, 3, 4, 5, 6);
        _nameLabel.setText(_currentName.getName());
        _menu.setOnMouseClicked(event -> {
            _expanded = new SlideMenu(slideInMenu, _expanded).SlideMenuMake();
        });

        _yourAttempToolTip.setTooltip(new Tooltip("Play your attempt at saying the name"));
        _dateBaseAttempToolTip.setTooltip(new Tooltip("Play the databases attempt at saying the name"));
    }
}
