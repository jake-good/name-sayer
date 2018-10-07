package UIscenes;


import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.Window;
import sun.font.EAttribute;

import java.io.*;
import java.net.URL;
import java.util.ResourceBundle;

public class SelectControl implements Initializable {

    public Button _listenButton;
    public Label _uploadList;
    public TextField _name;
    private Boolean List;

    private static final String HOVERED_BUTTON_STYLE = "-fx-background-color: #8c6019;";
    private static final String IDLE_BUTTON_STYLE = "-fx-background-color: #a5721f;";


    public void Listen() {
        if (!List) {
            //parse(_name.getText());
            new NameModel(_name.getText(), "output.wav");
            new sceneChange("LISTEN");
        }
    }

    public void uploadFile() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open Resource File");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Audio Files", "*.txt", "*.mp3", "*.aac"));
        Window stage = new Stage();
        File selectedFile = fileChooser.showOpenDialog(stage);
        String fileName = "";
        if (selectedFile != null) {
            fileName = selectedFile.getName();
            try {
                getList(fileName);
            } catch (Exception e) {}
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        List = false;
        _listenButton.setOnMouseEntered(e -> _listenButton.setStyle(HOVERED_BUTTON_STYLE));
        _listenButton.setOnMouseExited(e -> _listenButton.setStyle(IDLE_BUTTON_STYLE));

    }



    public void getList(String name) throws Exception {
        File namesList = new File("names.txt");
        try (BufferedReader br = new BufferedReader(new FileReader(namesList))) {
            String line;
            while ((line = br.readLine()) != null) {
                //parse(line);
                new NameModel(line, "output.wav");
            }
        }
        new sceneChange("LISTEN");

    }
}
