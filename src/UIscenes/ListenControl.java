package UIscenes;

import javafx.fxml.Initializable;
import javafx.scene.control.Label;

import java.net.URL;
import java.util.ResourceBundle;

public class ListenControl implements Initializable {

    private NameModel _currentName;

    public Label _nameLabel;

    public void Record() {
        new sceneChange("RECORD");
    }

    public void Select() {
        new sceneChange("SELECT");
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setName();
    }

    public void setName() {
        _currentName = NameModel._Names.get(NameModel._currentName);
        _nameLabel.setText(_currentName.getName());
    }

    public void next() {
        NameModel.next();

    }

    public void previous() {
        NameModel.prev();

    }

    public void play() {
        // Play the current name via the filename
        new playWorker(_currentName.getFileName()).execute();
    }

    public void Rate() {
        // Placeholder, this method should get a number between 1 - 5 and set that as the rating for a particular name.
        _currentName.Rate();
    }

}
