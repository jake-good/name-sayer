package UIscenes;


import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.io.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
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
            parse(_name.getText());

            new sceneChange("LISTEN");
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        List = false;
        _listenButton.setOnMouseEntered(e -> _listenButton.setStyle(HOVERED_BUTTON_STYLE));
        _listenButton.setOnMouseExited(e -> _listenButton.setStyle(IDLE_BUTTON_STYLE));

    }

    public void parse(String input) {
        String[] names  = input.split(" |-");
        List<String> files = new ArrayList<String>();
        for (String name : names) {
            String cmd = "ls DataBase VoNZ word/*" + name + ".wav";
            System.out.println(cmd);
            ProcessBuilder builder = new ProcessBuilder("/bin/bash", "-c", cmd);
            try {
                Process process = builder.start();
                InputStream stdout = process.getInputStream();
                BufferedReader stdoutBuffered = new BufferedReader(new InputStreamReader(stdout));
                String line = null;
                while ((line = stdoutBuffered.readLine()) != null) {
                    files.add(line);
                }
            } catch (Exception e) {
                System.out.println("error");
            }
            // Concatenate the file names to make the new wav
        }
        if (new File("output.wav").exists()) {
            new File("output.wav").delete();
        }
        new concatWorker(files).execute();
        new NameModel(_name.getText(), "output.wav");
    }

    public void getList() throws Exception {
        File namesList = new File("names.txt");
        try (BufferedReader br = new BufferedReader(new FileReader(namesList))) {
            String line;
            while ((line = br.readLine()) != null) {
                parse(line);
            }
        }
        new sceneChange("LISTEN");

    }
}
