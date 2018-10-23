package UIscenes;


import com.jfoenix.controls.JFXDialog;
import com.jfoenix.controls.JFXDialogLayout;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.Window;
import javafx.util.Duration;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

public class RecordControl implements Initializable {

    private NameModel _currentName;
    public Label Name;
    public TextField _recordingName;
    public ImageView Mic;
    @FXML
    private ImageView _menu;
    @FXML private AnchorPane _slideInMenu;
    @FXML private Button _playButton;
    @FXML private Button _discardButton;
    @FXML private StackPane _parent;

    @FXML private ProgressBar _recordingProgress;
    private boolean _expanded;
    private recordingWorker _recWorker;
    private Timeline _recTime;



    public void Compare() {
        if (!_playButton.isVisible()) {
            warning();
        } else {
            new sceneChange("ASSESS", "RECORD");
        }
    }

    public void Select() { new sceneChange("SELECT", "RECORD"); }

    public void Listen() {
        new sceneChange("LISTEN", "RECORD");
    }

    public void Extra() {
        new sceneChange("EXTRA", "RECORD");
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        _currentName = NameModel._Names.get(NameModel._currentName);
        Name.setText(_currentName.getName());
        _menu.setOnMouseClicked(event -> {
            _expanded = new SlideMenu(_slideInMenu, _expanded).SlideMenuMake();
        });
    }

    public void Record() {
        File file = new File("src/images/icons/micOrange.png");
        javafx.scene.image.Image image = new Image(file.toURI().toString());
        Mic.setImage(image);

        new File("DataBase-VoNZ-word/" + _currentName.getName()).mkdir();
        //Use a swingworker to prevent the GUI from freezing when recording the attempt.
        _recWorker = new recordingWorker(_currentName.getName(), Mic);
        _recWorker.execute();

        // Create a 10sec progress bar indicating the duration of recording.
         _recTime = new Timeline(
                new KeyFrame(Duration.ZERO, new KeyValue(_recordingProgress.progressProperty(), 0)),
                new KeyFrame(Duration.seconds(10), new KeyValue(_recordingProgress.progressProperty(), 1))
        );
        _recTime.setCycleCount(1);
        _recTime.play();


        _playButton.setVisible(true);
        _discardButton.setVisible(true);
    }

    public void stopRecording() {
        if (_recWorker.recProcess.isAlive()) {
            _recWorker.recProcess.destroy();
        }
        _recTime.stop();
    }

    public void Discard() {
        new File("DataBase-VoNZ-word/"+ _currentName.getName() + "/attempt.wav").delete();
        _playButton.setVisible(false);
        _discardButton.setVisible(false);
    }

    public void Play() {
        new playWorker("'DataBase-VoNZ-word/"+ _currentName.getName() + "/attempt.wav'").execute();
    }

    public void warning() {
        JFXDialog dialog = new JFXDialog();
        dialog.setDialogContainer(_parent);
        JFXDialogLayout layout = new JFXDialogLayout();
        layout.setBody(new Label("You cannot compare your attempt if you have not yet recorded one!"));
        layout.setHeading(new Label("ERROR! No recording attempt"));
        dialog.setContent(layout);
        dialog.show();
    }

}
