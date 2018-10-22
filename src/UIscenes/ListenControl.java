package UIscenes;

import javafx.animation.TranslateTransition;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
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
    @FXML
    private ImageView _nextArrow;
    @FXML
    private ImageView _prevArrow;

    @FXML private Pane _listViewPane;
    @FXML private ListView<String> _namesList;

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

        if (NameModel._Names.size() > 1) {
            for (NameModel name : NameModel._Names) {
                _namesList.getItems().add(name._Name);
            }
            _prevArrow.setVisible(false);
        } else {
            _nextArrow.setVisible(false);
            _prevArrow.setVisible(false);
        }
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
        boolean hasNext = NameModel.next();
        setName();
        new concatWorker(_currentName._Name).execute();
        if (!hasNext) {
            _nextArrow.setVisible(false);
        } else {
            _nextArrow.setVisible(true);
        } _prevArrow.setVisible(true);
    }

    public void previous() {
        boolean hasPrev = NameModel.prev();
        setName();
        new concatWorker(_currentName._Name).execute();
        if (!hasPrev) {
            _prevArrow.setVisible(false);
        } else {
            _prevArrow.setVisible(true);
        } _nextArrow.setVisible(true);
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
        slideIn.setFromY(250);
        slideIn.setToY(0);

        if (!_nameListExpanded) {
            slideIn.setRate(1);
            slideIn.play();
            _nameListExpanded=true;
        } else {
            slideIn.setRate(-1);
            slideIn.play();
            _nameListExpanded=false;
        }
    }
}
