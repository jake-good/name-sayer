package UIscenes;

import com.jfoenix.controls.*;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.animation.TranslateTransition;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.util.Duration;


import java.io.*;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.Scanner;

public class ListenControl implements Initializable {

    private NameModel _currentName;
    @FXML private Label _expandList;
    public Label _nameLabel;
    @FXML
    private AnchorPane _slideInMenu;
    @FXML
    private ImageView menu;
    @FXML
    private ImageView _nextArrow;
    @FXML
    private ImageView _prevArrow;

    @FXML private Pane _listViewPane;
    @FXML private JFXListView<String> _namesList;
    @FXML private JFXProgressBar _audioProgress;
    @FXML private StackPane _parent;


    private boolean _nameListExpanded;
    private boolean _expanded;

    private Timeline _playTime;

    public void Record() {
        new sceneChange("RECORD", "LISTEN");
    }

    public void Extra() {
        new sceneChange("EXTRA", "LISTEN");
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setName();
        new concatWorker(_currentName._Name).execute();
        menu.setOnMouseClicked(event -> {
            _expanded = new SlideMenu(_slideInMenu, _expanded).SlideMenuMake();
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
        new sceneChange("SELECT", "LISTEN");
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
        new playWorker("output.wav").execute();
        playProgress(getWavLength());
        _currentName.addToListened();
    }

    public void report(String name) {
        File reportFile = new File("reports.txt");
        String line = "";
        try {
            Reader r = new BufferedReader(new FileReader(reportFile));
            Writer output = new BufferedWriter(new FileWriter(reportFile, true));
            while ((line = ((BufferedReader) r).readLine())!= null) {
                if (line.equals(name)) {
                    return;
                }
            }
            output.append(name + "\n");
            output.close();
        } catch (IOException e) {
            System.out.println("Error loading complaint log");
        }
    }

    public void listTransition() {
        TranslateTransition slideIn = new TranslateTransition(Duration.millis(250), _listViewPane);
        slideIn.setFromX(-200);
        slideIn.setToX(0);

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

    public double getWavLength() {
        String cmd = "ffprobe -v quiet -print_format compact=print_section=0:nokey=1:escape=csv -show_entries format=duration output.wav";
        ProcessBuilder builder = new ProcessBuilder("bash", "-c",
                cmd);
        double duration = 0;
        try{
            Process listProcess = builder.start();

            InputStream stdout = listProcess.getInputStream();

            BufferedReader stdoutBuffered = new BufferedReader(new InputStreamReader(stdout));
            String line = null;
            int i = 1;
            while((line = stdoutBuffered.readLine())!= null){
                System.out.println(line);
                duration = Double.parseDouble(line);
            }
        }catch (Exception e){
            System.out.println("failed to play file");
        }
        System.out.println(duration);
        return duration;
    }

    public void playProgress(double duration) {
        // Create a progress bar indicating the duration of recording.
        _playTime = new Timeline(
                new KeyFrame(Duration.ZERO, new KeyValue(_audioProgress.progressProperty(), 0)),
                new KeyFrame(Duration.seconds(duration), new KeyValue(_audioProgress.progressProperty(), 1))
        );
        _playTime.setCycleCount(1);
        _playTime.play();
    }

    public void chooseNamePrompt() {
        JFXDialog popup = new JFXDialog();
        JFXDialogLayout content = new JFXDialogLayout();
        JFXListView<String> names= new JFXListView<>();
        names.getItems().setAll(_currentName.get_nameIndividuals());

        JFXButton reportChoice = new JFXButton("Report");
        reportChoice.setOnAction(e ->   {
            if (!names.getSelectionModel().isEmpty()) {
                report(names.getSelectionModel().getSelectedItem());
                popup.close();
            }
        });
        HBox container = new HBox();
        container.getChildren().setAll(names, reportChoice);
        content.setHeading(new Label("Choose a name file to report"));
        content.setBody(container);
        popup.setContent(content);
        popup.setDialogContainer(_parent);
        popup.show();

    }

}
