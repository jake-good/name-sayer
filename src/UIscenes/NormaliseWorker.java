package UIscenes;

import javax.swing.*;

public class NormaliseWorker extends SwingWorker<Void,Void> {

    @Override
    public Void doInBackground(){
        //Runs bash script which normalises the audio in the database, only if it has not been done before.
        String cmd = "chmod +x normalise.sh ; ./normalise.sh";
        ProcessBuilder normaliseBuilder = new ProcessBuilder("/bin/sh", "-c", cmd);
        try {
            Process normaliseProcess = normaliseBuilder.start();
            normaliseProcess.waitFor();
        }catch(Exception e){
            e.printStackTrace();
        }
        return null;
    }
}
