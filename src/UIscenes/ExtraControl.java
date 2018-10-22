package UIscenes;

import com.jfoenix.controls.JFXListView;
import com.jfoenix.controls.JFXProgressBar;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;

import java.net.URL;
import java.util.ResourceBundle;

public class ExtraControl implements Initializable {

    @FXML private JFXProgressBar _micBar;
    @FXML private Label _namesPracticed;
    @FXML private Label _namesInDB;
    @FXML private Label _prompt;
    @FXML private JFXListView _listView;


    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
}
