package UIscenes;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;


import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;


public class ReportPane implements Initializable {

    @FXML private ListView<String> _reportsList;
    @FXML private ImageView _closeButton;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
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
        _reportsList.getItems().addAll(reports);

        _closeButton.setOnMouseClicked(event ->{
           Stage stage = (Stage)_closeButton.getScene().getWindow();
           stage.close();
        });
    }
}
