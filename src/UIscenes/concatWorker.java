package UIscenes;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

public class concatWorker extends SwingWorker<Void,Void> {

    private List<String> _files = new ArrayList<String>();

    public concatWorker(List<String> files){
        _files = files;
    }

    @Override
    public Void doInBackground(){

        int numberOfNames = 0;

        String concatNames = "";
        for(String currentName : _files){
            concatNames = concatNames + " -i " + currentName;
            numberOfNames++;
        }
        String command = "ffmpeg " + concatNames + " -filter_complex '[0:0]concat=n=" + numberOfNames + ":v=0:a=1[out]' -map '[out]' output.wav";
        ProcessBuilder concatBuilder = new ProcessBuilder("bash", "-c", command);
        try {
            Process concatProcess = concatBuilder.start();
            concatProcess.waitFor();
        }catch(Exception e){

        }
        return null;
    }

}
