package tbs.server;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Act extends ClassesWithGetID {
    private String _ID;
    private String _artistID;
    private String _title;
    private int _duration;
    private List<Performance> _performanceList;

    Act(String title, String artistID, int duration) {
        _artistID = artistID;
        _title = title;
        _duration = duration;
        _performanceList = new ArrayList<>();
    }

    public List<Performance> get_performanceList() {
        return _performanceList;
    }

    public String get_ID() {
        return _ID;
    }

    public String get_title() {
        return _title;
    }

    public String checkAndAdd(List<Act> actList, HashMap<String, Act> actMap) {

        //Run through all checks to ensure format is correct.
        if (_title.equals("")) {
            return "ERROR: Title is empty";
        } else if (_duration <= 0) {
            //Ensure duration is larger than 0 and non-negative
            return "ERROR: Duration of the act is too short";
        }else if(duplicateCheck(actList)){
            //Check if an act with the same title is already in the database.
            return "ERROR: An act with the same title is already in the database.";
        } else {

            //Generate ID for the act
            _ID = _artistID + "-" + actList.size();
            //Add the act to the actList given
            actList.add(this);
            //Plash a key and value onto the hash map
            actMap.put(_ID, this);
            return _ID;

        }

    }

    private boolean duplicateCheck(List<Act> actIDList) {

        //Check to ensure a case-insensitive title is not already in the database by running through all titles
        // and comparing
        for (Act act : actIDList) {
            if (_title.toLowerCase().equals(act.get_title().toLowerCase())) {
                return true;
            }
        }
        return false;

    }

    public List<String> salesCount() {
        List<String> salesRep = new ArrayList<>();

        //Run through all performances for this act and get the tickets sold for each.
        for (Performance currentPerformance : _performanceList) {
            String ticketsSold = currentPerformance.ticketCount();
            salesRep.add(ticketsSold);
        }

        return salesRep;
    }
}
