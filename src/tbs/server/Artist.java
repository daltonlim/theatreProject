package tbs.server;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Artist extends ClassesWithGetID {

    private String _name;
    private String _ID;
    private List<Act> _actList;

    Artist(String name) {
        _name = name;
        _actList = new ArrayList<>();
    }

    public String get_ID() {
        return _ID;
    }

    public List<Act> get_actList() {
        return _actList;
    }

    public String get_name() {
        return _name;
    }

    public String checkAndAdd(List<Artist> artistIDList, HashMap<String, Artist> artistHashMap) {

        //Check to ensure the format of the given variables are correct.
        if (duplicateCheck(artistIDList)) {
            return "ERROR: Name is already present in database";
        } else if (_name.equals("")) {
            return "ERROR: No name was specified";
        } else {

            //Generate ID
            _ID = "ID" + "-" + artistIDList.size();
            //Add artist to artist list provided
            artistIDList.add(this);
            //Add key and value to the hash Map
            artistHashMap.put(_ID, this);
            return _ID;

        }
    }

    private boolean duplicateCheck(List<Artist> artistIDList) {

        //Check to ensure a case-insensitive name is not already in the database by running through all names
        // and comparing
        for (Artist artist : artistIDList) {
            if (_name.toLowerCase().equals(artist.get_name().toLowerCase())) {
                return true;
            }
        }
        return false;

    }
}
