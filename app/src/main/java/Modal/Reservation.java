package Modal;

public class Reservation {

    private String checkInDate;
    private String checkInTime;
    private String checkOutDate;
    private String checkOutTime;
    private int noOfAdults;
    private int noOfChild;
    private int noOfRooms;

    public Reservation() { }

    public Reservation(String checkInDate, String checkInTime, String checkOutDate, String checkOutTime, int noOfAdults, int noOfChild, int noOfRooms) {
        this.checkInDate = checkInDate;
        this.checkInTime = checkInTime;
        this.checkOutDate = checkOutDate;
        this.checkOutTime = checkOutTime;
        this.noOfAdults = noOfAdults;
        this.noOfChild = noOfChild;
        this.noOfRooms = noOfRooms;
    }

    public String getCheckInDate() {
        return checkInDate;
    }

    public void setCheckInDate(String checkInDate) {
        this.checkInDate = checkInDate;
    }

    public String getCheckInTime() {
        return checkInTime;
    }

    public void setCheckInTime(String checkInTime) {
        this.checkInTime = checkInTime;
    }

    public String getCheckOutDate() {
        return checkOutDate;
    }

    public void setCheckOutDate(String checkOutDate) {
        this.checkOutDate = checkOutDate;
    }

    public String getCheckOutTime() {
        return checkOutTime;
    }

    public void setCheckOutTime(String checkOutTime) {
        this.checkOutTime = checkOutTime;
    }

    public int getNoOfAdults() {
        return noOfAdults;
    }

    public void setNoOfAdults(int noOfAdults) {
        this.noOfAdults = noOfAdults;
    }

    public int getNoOfChild() {
        return noOfChild;
    }

    public void setNoOfChild(int noOfChild) {
        this.noOfChild = noOfChild;
    }

    public int getNoOfRooms() {
        return noOfRooms;
    }

    public void setNoOfRooms(int noOfRooms) {
        this.noOfRooms = noOfRooms;
    }

    @Override
    public String toString() {
        return "Reservation{" +
                "checkInDate='" + checkInDate + '\'' +
                ", checkInTime='" + checkInTime + '\'' +
                ", checkOutDate='" + checkOutDate + '\'' +
                ", checkOutTime='" + checkOutTime + '\'' +
                ", noOfAdults=" + noOfAdults +
                ", noOfChild=" + noOfChild +
                ", noOfRooms=" + noOfRooms +
                '}';
    }
}