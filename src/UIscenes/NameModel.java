package UIscenes;

import javafx.collections.ObservableList;

import java.util.ArrayList;
import java.util.List;

public class NameModel {
    public String _Name;
    public String _FileName;
    public String _recFileName;
    public String _recName;
    public int _Attempts;
    public static List<NameModel> _Names;
    public static int _currentName;
    private String _rating;

    public NameModel(String Name, String FileName) {
        if (_Names == null) {
            _Names = new ArrayList<NameModel>();
            _currentName = 0;
        }
        _Name = Name;
        _FileName = FileName;
        _Attempts = 0;
        _rating = "null";
        _Names.add(this);
    }

    public static void next() {
        if (_currentName < NameModel._Names.size() - 1) {
            _currentName++;
        }
    }

    public static void prev() {
        if (_currentName > 0) {
            _currentName--;
        }
    }


    public void addAttempt() {
        _Attempts++;
    }

    public String getName() {
        return _Name;
    }


    public void set_recFileName(String _recFileName) {
        this._recFileName = _recFileName;
    }

    public void set_recName(String _recName) {
        this._recName = _recName;
    }

    public String get_recFileName() {
        return _recFileName;
    }

    public String get_recName() {
        return _recName;
    }

    public String getFileName() {
        return _FileName;
    }

    public void Rate() {
        _rating = "Rated";
    }
}
