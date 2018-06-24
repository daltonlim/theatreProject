package tbs.server;

public class Seat {
    private int _seatRow;
    private int _seatNumber;
    private String _ticketNumber;
    private Boolean _seatAvailability;

    Seat(int seatRow, int seatNumber, String performanceID, int seatIndex) {
        _seatNumber = seatNumber;
        _seatRow = seatRow;
        _seatAvailability = true;
        _ticketNumber = performanceID + "-" + seatIndex;
    }

    public String get_ticketNumber() {
        return _ticketNumber;
    }

    public String getSeatString() {
        //Returns a string containing the seat row and seat col
        return _seatRow + "\t" + _seatNumber;
    }

    public Boolean get_seatAvailability() {
        return _seatAvailability;
    }

    public void set_seatAvailability(Boolean _seatAvailability) {
        this._seatAvailability = _seatAvailability;
    }

    public int get_seatRow() {
        return _seatRow;
    }

}

