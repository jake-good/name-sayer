package UIscenes;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Tooltip;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

import java.net.URL;
import java.util.ResourceBundle;

public class AssessControl implements Initializable {

    public Button _go;
    private NameModel _currentName;
    @FXML public ComboBox<Integer> _numberOfCompares;
    @FXML private ImageView _menu;
    @FXML private AnchorPane _slideInMenu;
    private boolean _expanded;
    public Label _nameLabel;
    @FXML private Label _yourAttempToolTip;
    @FXML private Label _dateBaseAttempToolTip;

    public void Return() {
        new SceneChange("RECORD", "ASSESS");
    }

    public void Select() { new SceneChange("SELECT", "ASSESS");
        NameModel._Names.clear();
        NameModel._currentName = 0;
    }

    public void Listen() {
        new SceneChange("LISTEN", "ASSESS");
    }

    public void Extra() {
        new SceneChange("EXTRA", "ASSESS");
    }

    public void Random() {
        new SceneChange("RANDOM", "ASSESS");
    }


    /**
     * Plays the users' audio recording of saying the name.
     */
    public void playAttempt(){
        new PlayWorker("'DataBase-VoNZ-word/"+ _currentName._Name + "/attempt.wav'").execute();
    }

    /**
     * Plays the databases audio recording saying the name.
     */
    public void playDataBase(){
        new PlayWorker("output.wav").execute();
    }


    /**
     * Plays the users attempt and then the databases recording sequentially. This gets repeated for how many time
     * The user has specified for.
     */
    public void compareRecordings(){
        int numberOfCompares = _numberOfCompares.getValue();
        new CompareWorker("'DataBase-VoNZ-word/"+ _currentName._Name + "/attempt.wav'",numberOfCompares).execute();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        _currentName = NameModel._Names.get(NameModel._currentName);
        _numberOfCompares.getItems().setAll(1, 2, 3, 4, 5, 6);
        _nameLabel.setText(_currentName.getName());
        _menu.setOnMouseClicked(event -> {
            _expanded = new SlideMenu(_slideInMenu, _expanded).SlideMenuMake();
        });

        _yourAttempToolTip.setTooltip(new Tooltip("Play your attempt at saying the name"));
        _dateBaseAttempToolTip.setTooltip(new Tooltip("Play the databases attempt at saying the name"));
    }
}
