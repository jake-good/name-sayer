package UIscenes;

import javafx.application.Platform;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import javax.swing.*;
import java.io.File;

public class recordingWorker extends SwingWorker<Void,Void> {

    private String _currentName;
    private String _recName;
    private ImageView _Mic;

    public recordingWorker(String currentName, String recName, ImageView Mic){
        _currentName = currentName;
        _recName = recName;
        _Mic = Mic;
    }

    @Override
    public Void doInBackground(){

        //Records the attempt at saying the name.
        String cmd = "ffmpeg -f alsa -i default  -t 3 'DataBase-VoNZ-word/" + _currentName +
                "/" + _recName + ".wav'";
        ProcessBuilder builder = new ProcessBuilder("bash", "-c",
                cmd);
        try{
            Process process = builder.start();
            process.waitFor();

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
            }
        });
    }
}
