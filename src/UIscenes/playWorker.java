package UIscenes;

import sun.util.resources.ca.CalendarData_ca;

import javax.swing.*;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

public class playWorker extends SwingWorker<Void,Void> {

    private String _fileName;

    private NameModel _nameInList;

    public playWorker(String fileName){
        _fileName = fileName;
    }

    @Override
    public Void doInBackground(){
        String cmd = "ffplay -nodisp " + _fileName + " -autoexit";
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
