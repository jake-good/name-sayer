package UIscenes;

import com.jfoenix.controls.JFXListView;
import com.jfoenix.controls.JFXProgressBar;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class ExtraControl implements Initializable {

    @FXML private JFXProgressBar _micBar;
    @FXML private Label _namesPracticed;
    @FXML private Label _namesInDB;
    @FXML private Label _prompt;
    @FXML private JFXListView _listView;
    @FXML private Tab _mic;
    @FXML private Tab _prog;

    private MicrophoneLevel _micLine;



    @Override
    public void initialize(URL location, ResourceBundle resources) {
        _micLine = new MicrophoneLevel(_micBar);
        getReports();
        getAttempts();
    }

    public void getReports() {
        List<String> reports = new ArrayList<>();
        File rep = new File("ratings.txt");
        try (BufferedReader br = new BufferedReader(new FileReader(rep))) {
            String line;
            while ((line = br.readLine()) != null) {
                reports.add(line);
            }
        } catch (IOException e) {
            System.out.println("broke");
        }
        _listView.getItems().addAll(reports);
    }

    public void getAttempts() {

    }

    public void back() {
        new sceneChange(Main.getPreviousScene(), Main.getPreviousScene());
        _micLine.closeLine();
    }


}
