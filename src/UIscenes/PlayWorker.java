package UIscenes;

import javax.swing.*;

public class PlayWorker extends SwingWorker<Void,Void> {

    private String _fileName;

    public PlayWorker(String fileName){
        _fileName = fileName;
    }

    @Override
    public Void doInBackground(){
        //Plays the specified audio file.
        String cmd = "ffplay -nodisp " + _fileName + " -autoexit";
        System.out.println(cmd);
        ProcessBuilder builder = new ProcessBuilder("bash", "-c",
                cmd);
        try{
            Process process = builder.start();
            process.waitFor();

        }catch (Exception e){
            System.out.println("failed to play file");
        }
        return null;
    }


}
