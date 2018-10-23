package UIscenes;

import javax.swing.*;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class DataBaseWorker {

    protected List<String> process() {
        List<String> listName = new ArrayList<>();
        ProcessBuilder listBuilder = new ProcessBuilder("/bin/bash", "-c", "ls DataBase-VoNZ-word -1 | sed 's/.*_//' | sed -n 's/\\.wav$//p'");
        try{
            Process listProcess = listBuilder.start();

            InputStream stdout = listProcess.getInputStream();

            BufferedReader stdoutBuffered = new BufferedReader(new InputStreamReader(stdout));
            String line = null;
            int i = 1;
            while((line = stdoutBuffered.readLine())!= null){
                if(listName.contains(line)){
                    for(String name : listName){
                        if(name == line){
                            i++;
                        }else if (name.contains("(")){
                            if(name.substring(0,name.length()-3).equals(line)){
                                i++;
                            }
                        }
                    }
                    //Adds an indicator that there are multiple names with the same name. eg Li, Li(1), Li(2)...
                    listName.add(line + "(" + Integer.toString(i) + ")");
                    i=1;
                }else {
                    listName.add(line);
                    i=1;
                }
            }
        }catch(Exception e){e.printStackTrace();}

        return listName;
    }
}
