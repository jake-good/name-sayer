package UIscenes;


import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDialog;
import com.jfoenix.controls.JFXDialogLayout;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.Window;
import org.controlsfx.control.textfield.TextFields;
import java.io.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;

public class SelectControl implements Initializable {

    // FXML Components references in the scene.
    @FXML private Label _nameText;
    @FXML private TextField _inputField;
    @FXML private ImageView _menuIcon;
    @FXML private AnchorPane _slideInMenu;
    @FXML private Label _arrowIcon;
    @FXML private StackPane _parent;

    // Functionality fields
    private boolean _isList;
    private boolean _menuExpanded;
    private List<String> _dataBaseNames = new ArrayList<String>();
    private List<String> _nameIndividuals;
    private List<String> _reportedNames;
    private String _concatName = "";


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        _isList = false;
        _menuExpanded = false;
        _nameIndividuals = new ArrayList<>();


        _menuIcon.setOnMouseClicked(event -> {
            _menuExpanded = new SlideMenu(_slideInMenu, _menuExpanded).SlideMenuMake();
        });


        setUp(); // Build the list of names in database for the input field auto-fill and error checking.
        normalise(); // Normalise the audio, only when it's the first time it is booted up.
        getReportedNames(); // Build list of names that have been reported.
    }

    /**
     * Changes the screen to the LISTEN screen.
     */
    public void Listen() {
        if (!_isList && !_concatName.equals("")) {
            //parse(_name.getText());
            new NameModel(_concatName, _nameIndividuals);
            new sceneChange("LISTEN", "SELECT");
        }else{
            //When the user tries to enter the listen menu without a valid input, show a warning dialog with steps
            //To fix the problem.
            errorPopUp("Empty Input Error","ERROR! The input is empty",
                    "Please either search a name through the \nsearch bar, or upload a list with names!");
        }
    }

    /**
     * Changes the screen to the EXTRA screen.
     */
    public void extra() {
        new sceneChange("EXTRA", "SELECT");
    }

    /**
     * Changes the screen to the RANDOM screen.
     */
    public void Random() {
        new sceneChange("RANDOM", "SELECT");
    }


    /**
     * Loads a text file that the user has chosen to upload.
     */
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

    /**
     * Uses the uploaded text file to load all the names into the application.
     * @param fileName
     * @throws Exception
     */
    public void getList(String fileName) throws Exception {
        File namesList = new File(fileName);
        try (BufferedReader br = new BufferedReader(new FileReader(namesList))) {
            String line;
            while ((line = br.readLine()) != null) {
                //This checks if all the names are valid, if not, an error dialogue pops up informing the user that
                //There is an error.
                String [] individualWords = line.split(" ");
                int completion = 0;
                for(int i =0; i<individualWords.length;i++) {
                    if (_dataBaseNames.contains(individualWords[i])) {
                        completion++;
                    }else {
                        errorPopUp("Invalid Input Error", "ERROR! The input is invalid",
                                "Please double check that all the names are \ncorrect and are in the database!");
                        return;
                    }
                }
                if(completion==individualWords.length){
                    new NameModel(line, Arrays.asList(individualWords));
                    completion = 0;
                }
            }
        }
        new sceneChange("LISTEN", "SELECT");
    }

    /**
     * This sets up the necessary variables needed within this class, such as a parsed list of names in the database and
     * setting up the autocomplete function in the search bar.
     */
    public void setUp(){
        //Lists all the names of the files in the names database but removing the .wav extension
        _dataBaseNames = new DataBaseWorker().process();

        //Uses the 3rd party ControlsFX which is an open source project for JavaFX. This allows the textfield
        //To have an autocomplete function, more information at http://fxexperience.com/controlsfx/
        TextFields.bindAutoCompletion(_inputField, _dataBaseNames);
        NameModel._totalNames = _dataBaseNames.size();
    }

    /**
     * Adds the chosen name to the list of names to be used and also updates the display to reflect this addition
     */
    public void add(){
        if (_reportedNames.contains(_inputField.getText())) {
            errorPopUpButton("ERROR! The name you have chosen has been reported for poor quality", new JFXButton("Proceed regardless"), _inputField.getText());
        } else if((_dataBaseNames.contains(_inputField.getText()))) {
            //Save the names, which is used to create the Name Model.
            _concatName = _concatName + _inputField.getText() + " ";
            _nameIndividuals.add(_inputField.getText());
            //Updates the list view of all the chosen names.
            _nameText.setText(_concatName);
        } else {
            errorPopUp("Wrong Input Error","ERROR! This name does not exist",
                    "Please either search a name through the \nsearch bar, or upload a list with names!");
        }
        _inputField.clear();
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
        JFXDialog dialog = new JFXDialog();
        dialog.setDialogContainer(_parent);
        JFXDialogLayout layout = new JFXDialogLayout();
        layout.setBody(new Label(contentText));
        layout.setHeading(new Label(headerText));
        dialog.setContent(layout);
        dialog.show();
    }

    public void errorPopUpButton(String headerText, JFXButton button, String name){
        JFXDialog dialog = new JFXDialog();
        dialog.setDialogContainer(_parent);
        JFXDialogLayout layout = new JFXDialogLayout();
        button.setOnAction(e-> {
            _concatName = _concatName + name + " ";
            _nameIndividuals.add(name);
            //Updates the list view of all the chosen names.
            _nameText.setText(_concatName);
            dialog.close();
        });
        button.setStyle("-fx-background-color: orange");
        layout.setBody(button);
        layout.setHeading(new Label(headerText));
        dialog.setContent(layout);
        dialog.show();
    }

    public void getReportedNames() {
        _reportedNames = new ArrayList<>();
        File reportFile = new File("reports.txt");
        String line = "";
        try {
            Reader r = new BufferedReader(new FileReader(reportFile));
            while ((line = ((BufferedReader) r).readLine())!= null) {
                _reportedNames.add(line);
            }
        } catch (IOException e) {
            System.out.println("Error loading complaint log");
        }
    }

    public void handleEnter(KeyEvent ke) {
        if (ke.getCode().equals(KeyCode.ENTER)) {
            add();
        }
    }


}
