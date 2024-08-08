import java.util.ArrayList;

/**
 * The HotelReservationModel class acts as a data layer for the system. 
 * It fetch and store the model state in the database.
 */
public class HotelReservationModel{
    /** 
     * A list to store Hotel objects. 
     */
    private ArrayList<Hotel> hotelList = new ArrayList<Hotel>();

    /**
     * Gets the list of hotels in the system.
     * 
     * @return the list of hotels
     */
    public ArrayList<Hotel> getHotelList() {
        return hotelList;
    }

    /**
     * Creates a new hotel and adds it to the hotel list.
     *
     * @param hotelName The name of the hotel to be added.
     */
    public void createHotel(String hotelName){
        hotelList.add(new Hotel(hotelName));
    }

    /**
     * Removes a hotel from the hotel list.
     */
    public void removeHotel(Hotel hotel){
        hotelList.remove(hotel);
    }
}
