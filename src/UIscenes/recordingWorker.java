package UIscenes;

import javafx.application.Platform;

import javax.swing.*;

public class recordingWorker extends SwingWorker<Void,Void> {

    private String _currentName;
    private String _recName;

    public recordingWorker(String currentName, String recName){
        _currentName = currentName;
        _recName = recName;
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
            }
        });
    }
}
