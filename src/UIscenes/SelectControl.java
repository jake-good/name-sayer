package UIscenes;


import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

public class SelectControl implements Initializable {

    public Button _listenButton;
    public Label _uploadList;
    public TextField _firstName;
    public TextField _familyName;
    private Boolean List;

    public void Listen() {
        if (!List) {
            String fullName = _firstName.getText() + " " +  _familyName.getText();
            new NameModel(fullName, "placeholder");
            new sceneChange("LISTEN");
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        List = false;
    }
}
