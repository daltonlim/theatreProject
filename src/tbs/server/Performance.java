package tbs.server;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

public class Performance extends ClassesWithGetID {
    private String _ID;
    private String _theatreID;
    private String _startTime;
    private String _premiumPrice;
    private String _cheapPrice;
    private int _premiumPriceInt;
    private int _cheapPriceInt;
    private int _seatDimension;
    private List<Seat> _seatList;

    Performance(String actID, String theatreID, String startTime, String premiumPrice, String cheapPrice, int performanceID, HashMap<String, Theatre> theatreHashMap) {
        _theatreID = theatreID;
        _startTime = startTime;
        _premiumPrice = premiumPrice;
        _cheapPrice = cheapPrice;
        _ID = actID + "-" + performanceID;
        _seatList = new ArrayList<>();
        theatreCheck(theatreHashMap);
    }

    public String get_ID() {
        return _ID;
    }

    private void theatreCheck(HashMap<String, Theatre> theatreHashMap) {

        //Find the theatre and the seat dimension of the theatre. If the theatre does not exist, the exception will be
        // caught and the seat dimension will be set to -1. This will be caught during the checkAndAdd method.
        try {
            _seatDimension = theatreHashMap.get(_theatreID).get_seatDimension();
        } catch (Exception e) {
            _seatDimension = -1;
        }
        
    }

    public String checkAndAdd(List<Performance> performanceList, HashMap<String, Performance> performanceHashMap) {

        //Check if all strings have the correct characters and integers in the right places.
        if (!stringCheck(_startTime, new char[]{'-', '-', 'T', ':'}, new int[]{4, 7, 10, 13}, new int[]{0, 1, 2, 3, 5, 6, 8, 9, 11, 12, 14, 15})) {
            return "ERROR: Time format is incorrect";
        } else if (!stringCheck(_premiumPrice, new char[]{'$'}, new int[]{0}, arrayGen(_premiumPrice.length()))) {
            return "ERROR: Premium price format is incorrect";
        } else if (!stringCheck(_cheapPrice, new char[]{'$'}, new int[]{0}, arrayGen(_cheapPrice.length()))) {
            return "ERROR: Cheap price format is incorrect";
        } else if (_seatDimension == -1) {
            return "ERROR: Theatre not found";
        } else {

            //Find values of the cheap and expensive price in int form
            _cheapPriceInt = getPrice(_cheapPrice);
            _premiumPriceInt = getPrice(_premiumPrice);

            //Set up the seats.
            initialiseSeats();
            //Add performance to given list
            performanceList.add(this);
            //Add performance to hash map for global access to this performance object
            performanceHashMap.put(_ID, this);
            return _ID;

        }
    }

    private Boolean stringCheck(String string, char[] charArray, int[] integerArray, int[] others) {

        //Ensure a value has been provided in the string but onl run if the string is of length 2 or greater.
        //This will catch empty inputs.
        if (string.length() > 1) {
            return intCheck(string, others) && charCheck(string, charArray, integerArray);
        } else {
            return false;
        }

    }

    private Boolean charCheck(String string, char[] charArray, int[] integerArray) {

        //Runs though all characters of a string to check if the characters are correct.
        int index;
        char currentChar;

        //Check if non-integers are correct.
        for (int i = 0; i < integerArray.length; i++) {
            index = integerArray[i];
            currentChar = string.charAt(index);
            if (currentChar != charArray[i]) {
                return false;
            }
        }

        return true;
    }

    private Boolean intCheck(String string, int[] indexToCheck) {

        //Check if indexes to check are all integers.
        for (int i = 0; i < indexToCheck.length; i++) {
            int index = indexToCheck[i];
            char currentChar = string.charAt(index);

            if (currentChar > '9' || currentChar < '0') {
                return false;
            }

        }
        return true;
    }

    private int[] arrayGen(int end) {

        //Ensure that a non empty string is provided.
        if (end <= 1) {
            return null;
        } else {

            //Generate an array consisting of index one to the length of the array.
            int[] array = new int[end - 1];
            for (int i = 0; i < end - 1; i++) {
                array[i] = i + 1;
            }

            return array;
        }
    }

    private void initialiseSeats() {

        //Generate all possible seats for a performance.
        for (int seatRow = 1; seatRow <= _seatDimension; seatRow++) {
            for (int seatNumber = 1; seatNumber <= _seatDimension; seatNumber++) {
                int index = indexFinder(seatRow, seatNumber);
                Seat toAdd = new Seat(seatRow, seatNumber, _ID, index);
                _seatList.add(toAdd);
            }
        }

    }

    private int indexFinder(int seatRow, int seatNumber) {

        //Return the index of a ticket in a ticket list.
        return (seatNumber - 1) + (seatRow - 1) * _seatDimension;

    }

    private int getPrice(String priceString) {

        //Remove the dollar sign using the substring command then convert to an integer.
        String string = priceString.substring(1);
        return Integer.valueOf(string);

    }

    public String ticketCount() {

        //Initialise counters
        int ticketsSold = 0;
        int sales = 0;
        int cutoff = _seatDimension / 2;

        //Run through all sold tickets in a list, and count and add ticket prices.
        for (Seat seat : _seatList) {
            if (!seat.get_seatAvailability()) {
                ticketsSold++;

                //Add cheap or premium price depending on seatRow
                if (seat.get_seatRow() <= cutoff) {
                    sales += _premiumPriceInt;
                } else {
                    sales += _cheapPriceInt;
                }

            }
        }

        //Concatenate string and return it
        return _ID + "\t" + _startTime + "\t" + ticketsSold + "\t$" + sales;

    }

    public String issueTicket(int seatRow, int seatNumber) {
        //Check if seat is possible.
        if (seatRow < 1 || seatRow > _seatDimension) {
            return "ERROR: Row number does not exist";
        } else if (seatNumber < 1 || seatNumber > _seatDimension) {
            return "ERROR: Seat number does not exist";
        }

        //Check if seat has been taken. If it hasn't issue the ticket.
        int index = indexFinder(seatRow, seatNumber);
        Seat wanted = _seatList.get(index);

        //Check seat availability and either issue a ticker or return the error.
        if (wanted.get_seatAvailability()) {
            wanted.set_seatAvailability(false);
            return wanted.get_ticketNumber();
        } else {
            return "ERROR: Seat has already been taken.";
        }
    }

    public List<String> seatsAvailable() {

        List<String> ticketIDList = new ArrayList<>();

        //Run through list of seats and check for available seats.
        for (Seat seat : _seatList) {
            if (seat.get_seatAvailability()) {
                ticketIDList.add(seat.getSeatString());
            }
        }

        return ticketIDList;

    }

    public List<String> getTicketIDs() {

        List<String> ticketList = new ArrayList<>();

        //Run though list of seats and get the ticket ID's for available seats
        for (Seat seat : _seatList) {
            if (!seat.get_seatAvailability()) {
                ticketList.add(seat.get_ticketNumber());
            }
        }

        Collections.sort(ticketList);
        return ticketList;

    }
}
