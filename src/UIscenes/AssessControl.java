package UIscenes;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;

import java.net.URL;
import java.util.ResourceBundle;

public class AssessControl implements Initializable {

    public Button _go;
    private NameModel _currentName;
    @FXML public ComboBox<Integer> _numberOfCompares;

    public void Return() {
        new sceneChange("RECORD");
    }

    public void playAttempt(){
        new playWorker("'DataBase VoNZ word/"+ _currentName._Name + "/"+ _currentName._recName + ".wav'").execute();
    }

    public void playDataBase(){
        new playWorker("output.wav").execute();
    }

    public void compare(){
        int numberOfCompares = _numberOfCompares.getValue();
        for(int i =0;i<numberOfCompares;i++){
            playDataBase();
            playAttempt();
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        _currentName = NameModel._Names.get(NameModel._currentName);
        _numberOfCompares.getItems().setAll(1, 2, 3, 4, 5, 6);
    }
}
