package tbs.server;

import java.util.HashMap;

public class Theatre extends ClassesWithGetID {
    private String _ID;
    private int _seatDimension;
    private int _floorSpace;

    Theatre(String[] toAdd, HashMap<String, Theatre> theatreHashMap) {
        _ID = toAdd[1];
        _seatDimension = Integer.valueOf(toAdd[2]);
        _floorSpace = Integer.valueOf(toAdd[3]);
        theatreHashMap.put(_ID, this);
    }

    public int get_floorSpace() {
        return _floorSpace;
    }

    public String get_ID() {
        return _ID;
    }

    public int get_seatDimension() {
        return _seatDimension;
    }


}
