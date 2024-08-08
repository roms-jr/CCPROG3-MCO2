import java.util.ArrayList;
import javax.swing.JOptionPane;

/**
 * The Hotel class represents a hotel with rooms and reservations.
 * This class provides methods to manage rooms and reservations, and to display hotel information.
 */
public class Hotel {

    private String hotelName;
    private String namingScheme;
    private ArrayList<Room> roomList = new ArrayList<Room>();
    private ArrayList<Reservation> reservationList = new ArrayList<Reservation>();
    private ArrayList<Integer> dateModifiedPriceList = new ArrayList<Integer>();
    private ArrayList<Double> priceRateList = new ArrayList<Double>();

    /**
     * Constructs a new Hotel with the specified name.
     * 
     * @param hotelName the name of the hotel
     */
    public Hotel(String hotelName) {
        this.hotelName = hotelName;
    }

    /**
     * Gets the name of the hotel.
     * 
     * @return the hotel name
     */
    public String getHotelName() {
        return hotelName;
    }

    /**
     * Gets the list of rooms in the hotel.
     * 
     * @return the list of rooms
     */
    public ArrayList<Room> getRoomList() {
        return roomList;
    }

    /**
     * Gets the list of standard rooms in the hotel.
     * 
     * @return the list of standard rooms
     */
    public ArrayList<Room> getStandardRoomList() {
        ArrayList<Room> standardRoomList = new ArrayList<Room>();
        for (Room room : roomList){
            if(room instanceof StandardRoom){
                standardRoomList.add(room);
            }
        }

        return standardRoomList;
    }

    /**
     * Gets the list of deluxe rooms in the hotel.
     * 
     * @return the list of deluxe rooms
     */
    public ArrayList<Room> getDeluxeRoomList() {
        ArrayList<Room> deluxeRoomList = new ArrayList<Room>();
        for (Room room : roomList){
            if(room instanceof DeluxeRoom){
                deluxeRoomList.add(room);
            }
        }

        return deluxeRoomList;
    }

    /**
     * Gets the list of executive rooms in the hotel.
     * 
     * @return the list of executive rooms
     */
    public ArrayList<Room> getExecutiveRoomList() {
        ArrayList<Room> executiveRoomList = new ArrayList<Room>();
        for (Room room : roomList){
            if(room instanceof ExecutiveRoom){
                executiveRoomList.add(room);
            }
        }

        return executiveRoomList;
    }

    /**
     * Gets the list of reservations in the hotel.
     * 
     * @return the list of reservations
     */
    public ArrayList<Reservation> getReservationList() {
        return reservationList;
    }

    /**
     * Gets the list date/s for date price modifier in the hotel.
     *
     * @return the list of integers
     */
    public ArrayList<Integer> getDateModifiedPriceList() {
       return dateModifiedPriceList;
    }


    /**
     * Gets the list of rate/s for date price modifier in the hotel.
     *
     * @return the list of integers
     */
    public ArrayList<Double> getPriceRateList() {
       return priceRateList;
    }

    /**
     * Adds the date with modified price.
     *
     */
    public void addDateModifiedPrice(int date){
        dateModifiedPriceList.add(date);
    }

    /**
     * Adds the rate to modify the price.
     *
     */
    public void addPriceRate(double rate){
        priceRateList.add(rate);
    }

    /**
     * Generates a room name based on the naming scheme and room number extension.
     * 
     * @param namingScheme the naming scheme for room names
     * @param roomNumberExtension the room number extension
     * @return the generated room name
     */
    public String generateRoomName(String namingScheme, int roomNumberExtension) {
        if(roomNumberExtension < 10){
            namingScheme += "0";
        }
        return namingScheme + roomNumberExtension;
    }

    /**
     * Compares a room name to the names of existing rooms to ensure uniqueness.
     * 
     * @param name the room name to compare
     * @return true if the name is unique, false otherwise
     */
    public boolean compareRoomName(String name) {
        int x;
        int numRooms = roomList.size();

        for (x = 0; x < numRooms; x++) {
            if (roomList.get(x).getRoomName().equals(name)) {
                return false;
            }
        }
        return true;
    }

    /**
     * Creates the specified number of rooms using the given naming scheme.
     * 
     * @param numRooms the number of rooms to create
     * @param namingScheme the naming scheme for the rooms
     */
    public void createRooms(int numStandardRooms, int numDeluxeRooms, int numExecutiveRooms, String namingScheme) {
        int i = 1;
        String roomName;
        Room room;
        this.namingScheme = namingScheme;

        while(i <= numStandardRooms){
            roomName = generateRoomName(namingScheme, i);
            room = new StandardRoom(roomName);
            roomList.add(room);
            i++;
        }

        while(i <= numStandardRooms + numDeluxeRooms){
            roomName = generateRoomName(namingScheme, i);
            room = new DeluxeRoom(roomName);
            roomList.add(room);
            i++;
        }

        while(i <= numStandardRooms + numDeluxeRooms + numExecutiveRooms){
            roomName = generateRoomName(namingScheme, i);
            room = new ExecutiveRoom(roomName);
            roomList.add(room);
            i++;
        }
    }

    /*
     * Displays the dates when a room is available.
     */
    public void showRoomAvailability(Room room) {
        int x, date;
        ArrayList<Integer> dayList = new ArrayList<Integer>();

        StringBuilder details = new StringBuilder(
            "Availability across the entire month:" + "\n     Available Day #"
        );

        for(x = 1; x <= 31; x++){
            dayList.add(x);
        }

        for(x = 0; x < getReservationList().size(); x++){
            if(room.getRoomName().equals(getReservationList().get(x).getRoom().getRoomName())){
                if (getReservationList().get(x).getCheckInDate() == getReservationList().get(x).getCheckOutDate()){
                    date = getReservationList().get(x).getCheckInDate();
                    dayList.remove(Integer.valueOf(date));
                }

                for (date = getReservationList().get(x).getCheckInDate(); date < getReservationList().get(x).getCheckOutDate(); date++){
                    dayList.remove(Integer.valueOf(date));
                }
                
                if(getReservationList().get(x).getCheckOutDate() == 31){
                    date = getReservationList().get(x).getCheckOutDate();
                    dayList.remove(Integer.valueOf(date));
                }
            }
        }

        for(x = 0; x < dayList.size(); x++){
            if (x == dayList.size() - 1) {
                details.append(dayList.get(x));
            } else {
                details.append(dayList.get(x) + ", ");
            }
        }

        JOptionPane.showMessageDialog(null, details.toString(), "Room Availability Information", JOptionPane.PLAIN_MESSAGE);
    }

    /**
     * Changes the name of the hotel.
     * 
     * @param newHotelName the new name of the hotel
     */
    public void changeHotelName(String newHotelName) {
        this.hotelName = newHotelName;
    }
    
    /**
     * Adds rooms to the hotel.
     */
    public void addRoom(int numStandardRooms, int numDeluxeRooms, int numExecutiveRooms) {
        Room room;
        String roomName;
        int currentNumRooms = roomList.size();
        int i = 1;

        while(i <= currentNumRooms + numStandardRooms){
            roomName = generateRoomName(namingScheme, i);
            if(compareRoomName(roomName)){
                room = new StandardRoom(roomName);
                roomList.add(room);
            }
            i++;
        }

        while(i <= currentNumRooms + numStandardRooms + numDeluxeRooms){
            roomName = generateRoomName(namingScheme, i);
            if(compareRoomName(roomName)){
                room = new DeluxeRoom(roomName);
                roomList.add(room);
            }
            i++;
        }

        while(i <= currentNumRooms + numStandardRooms + numDeluxeRooms + numExecutiveRooms){
            roomName = generateRoomName(namingScheme, i);
            if(compareRoomName(roomName)){
                room = new ExecutiveRoom(roomName);
                roomList.add(room);
            }
            i++;
        }
    }

    /**
     * Removes rooms from the hotel.
     * 
     */
    public void removeRoom(Room room) {
        roomList.remove(room);
    }

    /**
     * Updates the price of all rooms in the hotel.
     * 
     */
    public void updatePriceRoom(int roomIndex, double newPrice) {
        getRoomList().get(roomIndex).updatePrice(newPrice);
    }

    /**
     * Removes a reservation from the hotel.
     * 
     */
    public void removeReservation(Reservation reservation) {
        reservationList.remove(reservation);
    }

    /**
    * Calculate and modifies the price in a specific date/s.
    */
    public void datePriceModifier(Reservation reservation){
       int nightsOfStay;
       double totalPrice = 0;
       int index = 0;

        for(nightsOfStay = reservation.getCheckInDate(); nightsOfStay < reservation.getCheckOutDate(); nightsOfStay++) {
            if (getDateWithModifiedPriceIndex(nightsOfStay) >= 0 && getDateWithModifiedPriceIndex(nightsOfStay) <= 31) {
               index = getDateWithModifiedPriceIndex(nightsOfStay);
               totalPrice += reservation.getRoom().getPrice() * getPriceRateList().get(index);
            } else if (getDateWithModifiedPriceIndex(nightsOfStay) == -999){
               totalPrice += reservation.getRoom().getPrice();
           }
        }
        reservation.updateTotalPrice(totalPrice);
    }

    /**
    * This method will check if the price in a specific day is modified and return the index of the date with modified price rate.
    *
    * @return the index of the dateModifiedPriceList
    */
    public int getDateWithModifiedPriceIndex(int nightsOfStay){
        int i;
        for (i = 0; i < getDateModifiedPriceList().size(); i++) {
            if (getDateModifiedPriceList().get(i) == nightsOfStay) {
                return i;
            }
        }
        return -999;
    } 
}