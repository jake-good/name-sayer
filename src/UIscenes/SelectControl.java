package UIscenes;


import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.animation.TranslateTransition;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.Window;
import org.controlsfx.control.textfield.TextFields;
import javafx.util.Duration;
import sun.font.EAttribute;

import java.io.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class SelectControl implements Initializable {

    public String _concatName = "";
    public List<String> _listOfChosenNames = new ArrayList<String>();
    public ListView<String> _chosenNames;
    public Button _listenButton;
    public Label _uploadList;
    public TextField _name;
    private Boolean List;
    private List<String> _listName = new ArrayList<String>();
    @FXML private ImageView _menu;
    @FXML private HBox slideInMenu;
    @FXML private Label _reports;

    private boolean _expanded;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        List = false;
        _expanded = false;
        _menu.setOnMouseClicked(event -> {
            _expanded = new SlideMenu(slideInMenu, _expanded).SlideMenuMake();
        });
        _reports.setOnMouseClicked(event -> {
                new sceneChange("REPORT", 350, 300);
        });
    }

    public void Listen() {
        if (!List) {
            //parse(_name.getText());
            new NameModel(_concatName, "output.wav");
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

    public void setUp(){
        //Lists all the names of the files in the names database but removing the .wav extension
        ProcessBuilder listBuilder = new ProcessBuilder("/bin/bash", "-c", "ls DataBase-VoNZ-word -1 | sed 's/.*_//' | sed -n 's/\\.wav$//p'");
        try{
            Process listProcess = listBuilder.start();

            InputStream stdout = listProcess.getInputStream();

            BufferedReader stdoutBuffered = new BufferedReader(new InputStreamReader(stdout));
            String line = null;
            int i = 1;
            while((line = stdoutBuffered.readLine())!= null){
                if(_listName.contains(line)){
                    for(String name : _listName){
                        if(name == line){
                            i++;
                        }else if (name.contains("(")){
                            if(name.substring(0,name.length()-3).equals(line)){
                                i++;
                            }
                        }
                    }
                    //Adds an indicator that there are multiple names with the same name. eg Li, Li(1), Li(2)...
                    _listName.add(line + "(" + Integer.toString(i) + ")");
                    i=1;
                }else {
                    _listName.add(line);
                    i=1;
                }
            }
        }catch(Exception e){e.printStackTrace();}

        //Uses the 3rd party ControlsFX which is an open source project for JavaFX. This allows the textfield
        //To have an autocomplete function, more information at http://fxexperience.com/controlsfx/
        TextFields.bindAutoCompletion(_name,_listName);
    }

    public void add(){
        //Save the names, which is used to create the Name Model.
        _concatName = _concatName + _name.getText() + " ";
        _listOfChosenNames.add(_name.getText());
        ObservableList<String> data = FXCollections.observableArrayList(_listOfChosenNames);
        //Updates the list view of all the chosen names.
        _chosenNames.setItems(data);
    }

}
