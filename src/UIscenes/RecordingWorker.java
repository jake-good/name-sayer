package UIscenes;

import com.jfoenix.controls.JFXButton;
import javafx.application.Platform;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import javax.swing.*;
import java.io.File;

public class RecordingWorker extends SwingWorker<Void,Void> {

    private String _currentName;
    private ImageView _Mic;
    private JFXButton _button;
    Process recProcess;

    public RecordingWorker(String currentName, ImageView Mic, JFXButton button){
        _currentName = currentName;
        _Mic = Mic;
        _button = button;
    }

    @Override
    public Void doInBackground(){
        new File("DataBase-VoNZ-word/"+ _currentName + "/attempt.wav").delete();
        //Records the attempt at saying the name.
        String cmd = "ffmpeg -f alsa -i default  -t 10 'DataBase-VoNZ-word/" + _currentName +
                "/attempt.wav'";
        ProcessBuilder builder = new ProcessBuilder("bash", "-c",
                cmd);
        try{
            recProcess = builder.start();
            recProcess.waitFor();

        }catch (Exception e){

        }
        return null;
    }

    @Override
    public void done(){
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                //_view.getAttempts();
                //_view.setDone();
                File file = new File("src/images/black-mic.png");
                javafx.scene.image.Image image = new Image(file.toURI().toString());
                _Mic.setImage(image);
                _button.setDisable(false);
            }
        });
    }
}
