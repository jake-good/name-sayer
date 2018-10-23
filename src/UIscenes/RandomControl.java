package UIscenes;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

import java.io.File;
import java.util.Random;

public class RandomControl {

    @FXML private Label _nameLabel;


    public void back() {

    }

    public void playRandom() {
        File dir = new File("DataBase-VoNZ-word");
        String[] dataBase = dir.list();
        Random random = new Random();

        String  randomChoice = dataBase[random.nextInt(dataBase.length)];
        System.out.println(randomChoice);
        new playWorker(randomChoice);
    }
}
