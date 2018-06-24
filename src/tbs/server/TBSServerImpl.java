package tbs.server;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

public class TBSServerImpl implements TBSServer {

    private List<Artist> _artistList;
    private List<Theatre> _theatreList;
    private HashMap<String, Artist> _artistHashMap;
    private HashMap<String, Performance> _performanceHashMap;
    private HashMap<String, Act> _actHashMap;
    private HashMap<String, Theatre> _theatreHashMap;

    TBSServerImpl() {
        _artistList = new ArrayList<>();
        _theatreList = new ArrayList<>();
        _artistHashMap = new HashMap<>();
        _performanceHashMap = new HashMap<>();
        _actHashMap = new HashMap<>();
        _theatreHashMap = new HashMap<>();
    }

    public String addAct(String title, String artistID, int minutesDuration) {

        //Ensure the artist ID is not empty before moving onto checking and adding artist.
        if (artistID.length() == 0) {
            return "ERROR: Artist ID is empty";
        } else {

            try {
                //Generate a new act and let the method within act dictate whether its viable or not.
                Act act = new Act(title, artistID, minutesDuration);
                List<Act> actList = _artistHashMap.get(artistID).get_actList();
                return act.checkAndAdd(actList, _actHashMap);
            } catch (NullPointerException e) {
                //Catch the exception from finding the actList if no artist ID is found.
                return "ERROR: Artist ID not found";
            }

        }

    }

    public String addArtist(String name) {
        //Ensure the name is not empty before moving onto checking and adding artist.
        if (name.length() == 0) {
            return "ERROR: Name is empty";
        } else {
            //Create a new artist object and check if all are fulfilled.
            Artist artist = new Artist(name);
            return artist.checkAndAdd(_artistList, _artistHashMap);
        }
    }

    public List<String> dump() {
        return null;
    }

    public List<String> getActIDsForArtist(String artistID) {

        List<String> actIDs = new ArrayList<>();

        //Ensure artistID is not empty before finding the act id's for an artist
        if (artistID.length() == 0) {
            actIDs.add("ERROR: Artist ID is empty");
        } else {

            try {
                //Try to retrieve act list for artist and return an error if it cant be found.
                List<Act> actList = _artistHashMap.get(artistID).get_actList();
                //Sort the id's within the list
                return ClassesWithGetID.getAndSort(actList);
            } catch (NullPointerException e) {
                actIDs.add("ERROR: Artist ID does not exist");
            }

        }
        return actIDs;
    }

    public List<String> getArtistIDs() {
        //Sort the ID's within the list.
        return ClassesWithGetID.getAndSort(_artistList);
    }

    public List<String> getArtistNames() {

        List<String> artists = new ArrayList<>();

        //Run through all artist to get their nam before sorting
        for (Artist artist : _artistList) {
            artists.add(artist.get_name());
        }

        //Sort the list alphabetically
        Collections.sort(artists);
        return artists;
    }

    public List<String> getPeformanceIDsForAct(String actID) {

        List<String> performanceStringList = new ArrayList<>();

        //Ensure actID is not empty
        if (actID.length() == 0) {
            performanceStringList.add("ERROR: Act ID is empty");
        } else {

            try {
                //Get the performance list for an act id.
                List<Performance> performanceList = _actHashMap.get(actID).get_performanceList();
                //Sort the list.
                performanceStringList = ClassesWithGetID.getAndSort(performanceList);
            } catch (NullPointerException e) {
                //Catch the exception that performance list will throw if no act id is found
                performanceStringList.add("ERROR: Act ID does not exist");
            }

        }
        return performanceStringList;
    }

    public List<String> getTheatreIDs() {
        return ClassesWithGetID.getAndSort(_theatreList);
    }

    public List<String> getTicketIDsForPerformance(String performanceID) {

        List<String> ticketList = new ArrayList<>();

        //Ensure the provided performance ID is not empty.
        if (performanceID.length() == 0) {
            ticketList.add("ERROR: Performance ID is empty");
        } else {

            try {
                //Try to retrieve current performance and if it is successful, get the ticket ids.
                Performance currentPerformance = _performanceHashMap.get(performanceID);
                ticketList = currentPerformance.getTicketIDs();
            } catch (NullPointerException e) {
                //Catch the exception if no performance id exists in the hash map
                ticketList.add("ERROR: PerformanceID does not exist");
            }

        }
        return ticketList;
    }

    public String initialise(String path) {
        //Generate a new file management object.
        FileManagement file = new FileManagement(path);
        //Use the method within file management to check the file format and add to the list if valid.
        return file.checkAndConvert(_theatreList, _theatreHashMap);

    }

    public String issueTicket(String performanceID, int rowNumber, int seatNumber) {

        //Ensure the provided performance ID is not empty.
        if (performanceID.length() == 0) {
            return "ERROR: Performance ID is empty";
        } else {

            try {
                //Try to get current performance and try to issue a ticket which if fails returns an error.
                Performance currentPerformance = _performanceHashMap.get(performanceID);
                //Try to issue ticket and return the result.
                return currentPerformance.issueTicket(rowNumber, seatNumber);
            } catch (NullPointerException e) {
                return "ERROR: Performance ID does not exist";
            }

        }
    }

    public List<String> salesReport(String actID) {
        List<String> salesRep = new ArrayList<>();

        //Ensure provided act id is not empty
        if (actID.length() == 0) {
            salesRep.add("ERROR: Act ID is empty");
        } else {

            try {
                //Try to get current act and find the sales count
                Act currentAct = _actHashMap.get(actID);
                //get a sales report for object
                salesRep = currentAct.salesCount();
            } catch (NullPointerException e) {
                //The only reason for this call to fail is if the act id does not exist.
                salesRep.add("ERROR: Act ID does not exist");
            }

        }
        return salesRep;
    }

    public String schedulePerformance(String actID, String theatreID, String startTimeStr, String premiumPriceStr, String cheapSeatsStr) {

        //Ensure the act id is not empty
        if (actID.length() == 0) {
            return "ERROR: Act ID is empty";
        } else {

            try {
                //Try to get performance list and return error if the actid is not found which will throw an error
                List<Performance> performanceList = _actHashMap.get(actID).get_performanceList();
                //Set up a new performance with the new variables
                Performance performance = new Performance(actID, theatreID, startTimeStr, premiumPriceStr, cheapSeatsStr, performanceList.size(), _theatreHashMap);
                //Run a check on the new object and add if it passes
                return performance.checkAndAdd(performanceList, _performanceHashMap);
            } catch (NullPointerException e) {
                return "ERROR: ActID not Found";
            }

        }

    }

    public List<String> seatsAvailable(String performanceID) {
        List<String> ticketIDList = new ArrayList<>();

        //Ensure performance id is not empty.
        if (performanceID.length() == 0) {
            ticketIDList.add("ERROR: Performance ID is empty");
        } else {

            try {
                //Try to retrieve the the wanted performance and throw error if it doesn't exist.
                Performance currentPerformance = _performanceHashMap.get(performanceID);
                //Grab available seats.
                ticketIDList = currentPerformance.seatsAvailable();
            } catch (NullPointerException e) {
                ticketIDList.add("ERROR: PerformanceID does not exist");
            }

        }
        return ticketIDList;
    }
}
