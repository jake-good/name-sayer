package UIscenes;

import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.Window;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.Arrays;
import java.util.List;

public class TextFileUpload {

    private List<String> _dataBaseNames;
    private SelectControl _selectControl;

    public TextFileUpload(List<String> dataBaseNames, SelectControl selectControl){
        _dataBaseNames = dataBaseNames;
        _selectControl = selectControl;
    }

    /**
     * Loads a text file that the user has chosen to upload.
     */
    public void uploadFile() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open Resource File");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Audio Files", "*.txt", "*.mp3", "*.aac"));
        Window stage = new Stage();
        File selectedFile = fileChooser.showOpenDialog(stage);
        String fileName = "";
        if (selectedFile != null) {
            fileName = selectedFile.getName();
            try {
                getList(fileName);
            } catch (Exception e) {}
        }
    }

    /**
     * Uses the uploaded text file to load all the names into the application.
     * @param fileName
     * @throws Exception
     */
    public void getList(String fileName) throws Exception {
        File namesList = new File(fileName);
        try (BufferedReader br = new BufferedReader(new FileReader(namesList))) {
            String line;
            while ((line = br.readLine()) != null) {
                //This checks if all the names are valid, if not, an error dialogue pops up informing the user that
                //There is an error.
                String [] individualWords = line.split(" ");
                int completion = 0;
                for(int i =0; i<individualWords.length;i++) {
                    if (_dataBaseNames.contains(individualWords[i])) {
                        completion++;
                    }else {
                        _selectControl.errorPopUp("Invalid Input Error", "ERROR! The input is invalid",
                                "Please double check that all the names are \ncorrect and are in the database!");
                        return;
                    }
                }
                if(completion==individualWords.length){
                    new NameModel(line, Arrays.asList(individualWords));
                    completion = 0;
                }
            }
        }
        new SceneChange("LISTEN", "SELECT");
    }

}
