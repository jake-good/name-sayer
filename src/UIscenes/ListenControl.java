package UIscenes;

import javafx.fxml.Initializable;
import javafx.scene.control.Label;

import java.net.URL;
import java.util.ResourceBundle;

public class ListenControl implements Initializable {

    private NameModel _currentName;
    private int _nameIndex;

    public Label _nameLabel;

    public void Record() {
        new sceneChange("RECORD");
    }

    public void Select() {
        new sceneChange("SELECT");
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        _nameIndex = 0;
        setName();
    }

    public void setName() {
        _currentName = NameModel._Names.get(_nameIndex);
        _nameLabel.setText(_currentName.getName());
    }

    public void next() {
        if (_nameIndex < NameModel._Names.size() - 1) {
            _nameIndex++;
            setName();
        }
    }

    public void previous() {
        if (_nameIndex > 0) {
            _nameIndex--;
            setName();
        }
    }

    public void play() {
        // Play the current name via the filename
    }

}
