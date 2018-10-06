package UIscenes;

import javafx.collections.ObservableList;

import java.util.ArrayList;
import java.util.List;

public class NameModel {
    public String _Name;
    public String _FileName;
    public int _Attempts;
    public static List<NameModel> _Names;
    private String _rating;

    public NameModel(String Name, String FileName) {
        if (_Names == null) {
            _Names = new ArrayList<NameModel>();
        }
        _Name = Name;
        _FileName = FileName;
        _Attempts = 0;
        _rating = "null";
        _Names.add(this);
    }

    public static List<NameModel> getNames() {
        return _Names;
    }

    public void addAttempt() {
        _Attempts++;
    }

    public String getName() {
        return _Name;
    }

    public String getFileName() {
        return _FileName;
    }
}
