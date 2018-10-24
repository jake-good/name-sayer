package UIscenes;

import javax.swing.*;

public class CompareWorker extends SwingWorker<Void,Void> {

    private String _fileName;
    private int _numberOfCompares;
    public int _currentNumberOfCompares = 1;

    public CompareWorker(String fileName, int numberOfCompares){
        _fileName = fileName;
        _numberOfCompares = numberOfCompares;
    }

    /**
     * creates a process that compares the two files given in the compare scene by playing each
     * sequentially for the set amount of times.
     * @return
     */
    @Override
    public Void doInBackground(){
        //Play the audio file of the users attempt at saying the name.
        String cmd = "ffplay -nodisp " + _fileName + " -autoexit";
        System.out.println(cmd);
        ProcessBuilder builder = new ProcessBuilder("bash", "-c",
                cmd);
        try{
            Process process = builder.start();
            process.waitFor();

            //Play the databases recording of the names.
            String cmdDatabase = "ffplay -nodisp output.wav -autoexit";
            ProcessBuilder datebaseBuilder = new ProcessBuilder("bash","-c",cmdDatabase);
            Process databaseProcess = datebaseBuilder.start();
            databaseProcess.waitFor();


        }catch (Exception e){
            System.out.println("failed to play file");
        }
        return null;
    }

    /**
     * Recursively plays both audio files until it reaches the users desired amount of compares.
     */
    @Override
    public void done(){
        if(_currentNumberOfCompares<_numberOfCompares){
            _currentNumberOfCompares++;
            new CompareWorker(_fileName,_numberOfCompares-1).execute();
        }
    }

}
