package UIscenes;

import com.jfoenix.controls.JFXProgressBar;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.TargetDataLine;

public class MicrophoneLevel {

    private JFXProgressBar _progBar;
    private Thread _dataLine;
    private boolean _started;
    private TargetDataLine _line;

    public MicrophoneLevel(JFXProgressBar progBar) {
        _progBar = progBar;
        getLineData();
    }
    /**
     * This Method to get a dataline for the mic level was found at: "http://proteo.me.uk/2009/10/sound-level-monitoring-in-java/"
     */
    public void getLineData() {
         _dataLine = new Thread () {
            @Override
            public void run () {
                _line = null;
                AudioFormat format = new AudioFormat(8000.0F, 8, 1, true, false);
                try {
                    _line = (TargetDataLine) AudioSystem.getTargetDataLine(format);
                    _line.open();
                    _line.start();
                } catch (
                        LineUnavailableException ex) {}

                byte [] buffer = new byte[2000];
                while (true) {
                    _line.read(buffer, 0, buffer.length);
                    double progress = (double) getRMS(buffer)/50;
                    _progBar.setProgress(progress);
                    if (!_line.isRunning() || !_line.isActive()) {
                        break;
                    }
                }
            }
        };
         _dataLine.start();
    }

    public void closeLine() {
        if (_line.isActive()) {
            _line.close();
        }
    }


    /**
     * The method getRMS implementation was found here: "https://stackoverflow.com/questions/3899585/microphone-level-in-java"
     * @param audioData
     * @return
     */

    protected static int getRMS(byte[] audioData) {
        long lSum = 0;
        for(int i=0; i<audioData.length; i++)
            lSum = lSum + audioData[i];

        double dAvg = lSum / audioData.length;

        double sumMeanSquare = 0d;
        for(int j=0; j<audioData.length; j++)
            sumMeanSquare = sumMeanSquare + Math.pow(audioData[j] - dAvg, 2d);

        double averageMeanSquare = sumMeanSquare / audioData.length;
        return (int)(Math.pow(averageMeanSquare,0.5d) + 0.5);
    }
}
