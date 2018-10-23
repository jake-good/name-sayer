package UIscenes;

import com.jfoenix.controls.JFXSpinner;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.util.Duration;

import java.io.File;
import java.net.URL;
import java.util.List;
import java.util.Random;
import java.util.ResourceBundle;

public class RandomControl implements Initializable {

    @FXML private Label _nameLabel;
    @FXML private JFXSpinner _loadSpinner;
    private List<String> _names;
    private String[] _dataBase;
    private Timeline _playTime;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        _names = new DataBaseWorker().process();
        File dir = new File("DataBase-VoNZ-word");
        _dataBase = dir.list();
    }

    public void back() {
        new SceneChange(Main.getPreviousScene(), Main.getPreviousScene());
    }

    public void playRandom() {
        Random random = new Random();
        int choice = random.nextInt(_names.size());

        new ConcatWorker(_names.get(choice)).execute();
        loadProgress(choice);
    }


    public void loadProgress(int choice) {
        _playTime = new Timeline(
                new KeyFrame(Duration.ZERO, new KeyValue(_loadSpinner.progressProperty(), 0)),
                new KeyFrame(Duration.seconds(1), e -> {
                    _nameLabel.setText(_names.get(choice));
                    new PlayWorker("output.wav").execute();
                    },
                new KeyValue(_loadSpinner.progressProperty(), 1))
        );
        _playTime.setCycleCount(1);
        _playTime.play();
        _nameLabel.setText(_names.get(choice));


    }


}
