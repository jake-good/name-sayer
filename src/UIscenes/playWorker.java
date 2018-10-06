package UIscenes;

import javax.swing.*;

public class playWorker extends SwingWorker<Void,Void> {

    private String _fileName;

    private NameModel _nameInList;

    public playWorker(String fileName){
        _fileName = fileName;
    }

    @Override
    public Void doInBackground(){

        int numberOfNames = 0;

        String name = "";
        for(NameModel names : _nameInList._Names){
            name = name + " -i " + names._FileName;
            numberOfNames++;
        }
        String command = "ffmpeg " + name + " -filter_complex '[0:0]concat=n=" + numberOfNames + ":v=0:a=1[out]' -map '[out]' " + name +".wav";
        ProcessBuilder concatBuilder = new ProcessBuilder("bash", "-c", command);
        try{

            Process concatProcess = concatBuilder.start();
            concatProcess.waitFor();

            String cmd = "ffplay -nodisp " + name + ".wav" + " -autoexit";
            ProcessBuilder builder = new ProcessBuilder("bash", "-c",
                    cmd);
            Process process = builder.start();
            process.waitFor();

            ProcessBuilder deleterBuilder = new ProcessBuilder("bash", "-c", "rm " + name + ".wav");
            Process deleterProcess = deleterBuilder.start();
            deleterProcess.waitFor();


        }catch (Exception e){
            System.out.println("failed to play file");
        }
        return null;
    }


}
