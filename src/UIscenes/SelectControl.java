package UIscenes;


import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.animation.TranslateTransition;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.TextArea;
import javafx.scene.Node;
import javafx.scene.control.*;
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
    public TextArea _selectedNameArea;
    public List<String> _listOfChosenNames = new ArrayList<String>();
    @FXML private Label _nameText;
    public Button _listenButton;
    public Label _uploadList;
    public TextField _name;
    private Boolean List;
    private List<String> _listName = new ArrayList<String>();
    @FXML private ImageView _menu;
    @FXML private HBox slideInMenu;
    @FXML private Label _reports;
    @FXML private Label _add;
    private boolean _expanded;
    @FXML private Label _addArrow;

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
        setUp();
        //Normalise the audio, only when it's the first time it is booted up.
        normalise();
        //Tooltip for the Arrow image
        _add.setTooltip(new Tooltip("Add to the list of names"));
    }

    public void Listen() {
        if (!List && !_concatName.equals("")) {
            //parse(_name.getText());
            new NameModel(_concatName, "output.wav");
            new sceneChange("LISTEN");
        }else{
            //When the user tries to enter the listen menu without a valid input, show a warning dialog with steps
            //To fix the problem.
            errorPopUp("Empty Input Error","ERROR! The input is empty",
                    "Please either search a name through the \nsearch bar, or upload a list with names!");
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

                //This checks if all the names are valid, if not, an error dialogue pops up informing the user that
                //There is an error.
                String [] individualWords = line.split(" ");
                int completion = 0;
                for(int i =0; i<individualWords.length;i++) {
                    if (_listName.contains(individualWords[i])) {
                        completion++;
                    }else {
                        errorPopUp("Invalid Input Error", "ERROR! The input is invalid",
                                "Please double check that all the names are \ncorrect and are in the database!");
                        return;
                    }
                }
                if(completion==individualWords.length){
                    new NameModel(line, "output.wav");
                    completion = 0;
                }
            }
        }
        new sceneChange("LISTEN");
    }

    /**
     * This sets up the necessary variables needed within this class, such as a parsed list of names in the database and
     * setting up the autocomplete function in the search bar.
     */
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
        if((_listName.contains(_name.getText()))) {
            //Save the names, which is used to create the Name Model.
            _concatName = _concatName + _name.getText() + " ";
            //Updates the list view of all the chosen names.
            _nameText.setText(_concatName);
        }else{
            errorPopUp("Wrong Input Error","ERROR! This name does not exist",
                    "Please either search a name through the \nsearch bar, or upload a list with names!");
        }
        _name.clear();
    }

    public void clear() {
        _concatName = "";
        _nameText.setText(_concatName);
    }

    /**
     * Executes a bash file which helps normalise the audio files within the database, only if it hasn't been done before.
     */
    public void normalise(){
        new NormaliseWorker().execute();
    }

    /**
     * Creates an error pop up dialogue which alerts the user that there is an error, and depending on the arguments
     * it should help the user identify what went wrong and how to correct it.
     * @param title
     * @param headerText
     * @param contentText
     */
    public void errorPopUp(String title, String headerText, String contentText){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(headerText);
        alert.setContentText(contentText);
        alert.showAndWait();
    }
}
