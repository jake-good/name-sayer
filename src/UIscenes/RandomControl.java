package UIscenes;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;

import javax.xml.crypto.Data;
import java.io.File;
import java.net.URL;
import java.util.List;
import java.util.Random;
import java.util.ResourceBundle;

public class RandomControl implements Initializable {

    @FXML private Label _nameLabel;
    private List<String> _names;
    private String[] _dataBase;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        _names = new DataBaseWorker().process();
        File dir = new File("DataBase-VoNZ-word");
        _dataBase = dir.list();
    }

    public void back() {

    }

    public void playRandom() { ;
        Random random = new Random();
        int choice = random.nextInt(_dataBase.length);


        String  randomChoice = _dataBase[choice];
        _nameLabel.setText(_names.get(choice));
        System.out.println(randomChoice);
        new playWorker(randomChoice);
    }


}
