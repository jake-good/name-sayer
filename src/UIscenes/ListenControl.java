package UIscenes;

import javafx.animation.TranslateTransition;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.util.Duration;


import java.io.*;
import java.net.URL;
import java.util.ResourceBundle;

public class ListenControl implements Initializable {

    private NameModel _currentName;
    @FXML private Label _expandList;
    public Label _nameLabel;
    @FXML
    private HBox slideInMenu;
    @FXML
    private ImageView menu;
    @FXML private Pane _listViewPane;
    private boolean _nameListExpanded;
    private boolean _expanded;

    public void Record() {
        new sceneChange("RECORD");
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setName();
        new concatWorker(_currentName._Name).execute();
        menu.setOnMouseClicked(event -> {
            _expanded = new SlideMenu(slideInMenu, _expanded).SlideMenuMake();
        });
        _expandList.setOnMouseClicked(event -> {
            listTransition();
        });
    }

    public void Select() {
        new sceneChange("SELECT");
        NameModel._Names.clear();
        NameModel._currentName = 0;
    }

    public void setName() {
        _currentName = NameModel._Names.get(NameModel._currentName);
        _nameLabel.setText(_currentName.getName());
    }

    public void next() {
        NameModel.next();
        setName();
        new concatWorker(_currentName._Name).execute();
    }

    public void previous() {
        NameModel.prev();
        setName();
        new concatWorker(_currentName._Name).execute();
    }

    public void play() {
        // Play the current name via the filename
        new playWorker(_currentName.getFileName()).execute();
    }

    public void Rate() {
        File reportFile = new File("ratings.txt");
        System.out.println("rated");
        try {
            Writer output = new BufferedWriter(new FileWriter(reportFile, true));
            output.append(_currentName._Name + "\n");
            output.close();
        } catch (IOException e) {
            System.out.println("Error loading complaint log");
        }
    }

    public void listTransition() {
        TranslateTransition slideIn = new TranslateTransition(Duration.millis(250), _listViewPane);
        slideIn.setFromY(-232);
        slideIn.setToY(0);

        if (!_nameListExpanded) {
            slideIn.setRate(1);
            slideIn.play();
            _nameListExpanded=true;
        } else {
            slideIn.setRate(-1);
            slideIn.play();
        }
    }
}
