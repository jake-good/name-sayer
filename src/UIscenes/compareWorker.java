package UIscenes;

import javax.swing.*;

public class compareWorker extends SwingWorker<Void,Void> {

    private String _fileName;
    private int _numberOfCompares;
    public int _currentNumberOfCompares = 1;

    public compareWorker(String fileName, int numberOfCompares){
        _fileName = fileName;
        _numberOfCompares = numberOfCompares;
    }

    @Override
    public Void doInBackground(){
        String cmd = "ffplay -nodisp " + _fileName + " -autoexit";
        System.out.println(cmd);
        ProcessBuilder builder = new ProcessBuilder("bash", "-c",
                cmd);
        try{
            Process process = builder.start();
            process.waitFor();

            String cmdDatabase = "ffplay -nodisp output.wav -autoexit";
            ProcessBuilder datebaseBuilder = new ProcessBuilder("bash","-c",cmdDatabase);
            Process databaseProcess = datebaseBuilder.start();
            databaseProcess.waitFor();


        }catch (Exception e){
            System.out.println("failed to play file");
        }
        return null;
    }

    @Override
    public void done(){
        if(_currentNumberOfCompares<_numberOfCompares){
            _currentNumberOfCompares++;
            new compareWorker(_fileName,_numberOfCompares-1).execute();
        }
    }

}
