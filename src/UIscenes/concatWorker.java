package UIscenes;

import javax.swing.*;
import java.io.BufferedReader;
import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class concatWorker extends SwingWorker<Void,Void> {

    private List<String> _files = new ArrayList<String>();
    private String _input;

    public concatWorker(String input){
        _input = input;
    }

    @Override
    public Void doInBackground(){
        String[] names  = _input.split(" |-");
        List<String> files = new ArrayList<String>();
        for (String name : names) {
            String cmd = "ls DataBase-VoNZ-word/*" + name + ".wav";
            System.out.println(cmd);
            ProcessBuilder builder = new ProcessBuilder("/bin/bash", "-c", cmd);
            try {
                Process process = builder.start();
                InputStream stdout = process.getInputStream();
                BufferedReader stdoutBuffered = new BufferedReader(new InputStreamReader(stdout));
                String line = null;
                while ((line = stdoutBuffered.readLine()) != null) {
                    files.add(line);
                }
            } catch (Exception e) {
                System.out.println("error");
            }
            // Concatenate the file names to make the new wav
        }
        if (new File("output.wav").exists()) {
            new File("output.wav").delete();
        }

        int numberOfNames = 0;

        String concatNames = "";
        for(String currentName : files){
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
