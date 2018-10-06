package UIscenes;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;

public class AssessControl {

    public Button _go;
    public NameModel _CurrentName;
    @FXML public ComboBox<Integer> _numberOfCompares;

    public void Return() {
        new sceneChange("RECORD");
    }

    public void playAttempt(){
        new playWorker("DataBase-VoNZ-word/"+ _CurrentName._Name + "/"+ _CurrentName._Name + ".wav").execute();
    }

    public void playDataBase(){
        new playWorker("DataBase-VoNZ-word/" + _CurrentName._FileName).execute();
    }

    public void compare(){
        int numberOfCompares = _numberOfCompares.getValue();
        for(int i =0;i<numberOfCompares;i++){
            playDataBase();
            playAttempt();
        }
    }
}
