package UIscenes;

import com.jfoenix.controls.JFXListView;
import com.jfoenix.controls.JFXProgressBar;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;

import java.io.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class ExtraControl implements Initializable {

    @FXML private JFXProgressBar _micBar;
    @FXML private Label _namesPracticed;
    @FXML private Label _namesInDB;
    @FXML private Label _prompt;
    @FXML private JFXListView<String> _listView;
    @FXML private Tab _mic;
    @FXML private Tab _prog;
    private List<String> _reports;

    private MicrophoneLevel _micLine;



    @Override
    public void initialize(URL location, ResourceBundle resources) {
        _micLine = new MicrophoneLevel(_micBar);
        getReports();
        getAttempts();
    }

    public void getReports() {
        _reports = new ArrayList<>();
        File rep = new File("reports.txt");
        try (BufferedReader br = new BufferedReader(new FileReader(rep))) {
            String line;
            while ((line = br.readLine()) != null) {
                _reports.add(line);
            }
        } catch (IOException e) {
            System.out.println("broke");
        }
        _listView.getItems().addAll(_reports);
    }

    public void getAttempts() {

    }

    public void back() {
        new sceneChange(Main.getPreviousScene(), Main.getPreviousScene());
        _micLine.closeLine();
    }

    public void remove() {
        if (!_listView.getSelectionModel().isEmpty()) {
            String toRemove = _listView.getSelectionModel().getSelectedItem();
            _reports.remove(toRemove);
            writeReports(_reports);
            setListView();
        }
    }

    public void setListView() {
        _listView.getItems().setAll(_reports);
    }

    public void writeReports(List<String> _reports) {
        File reports = new File("reports.txt");
        try {
            PrintWriter pw = new PrintWriter(reports);
            pw.close();
            Writer br = new BufferedWriter(new FileWriter(reports));
            for (String file : _reports) {
                br.append(file + "\n");
            }
            br.close();
        } catch (IOException e) {}
    }
}
