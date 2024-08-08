/**
 * The Reservation class represents a hotel room reservation.
 * This class contains information about the guest, check-in and check-out dates, 
 * the room, the total price, and a discount status with no discount as default.
 */
public class Reservation {

    private String guestName;
    private int checkInDate;
    private int checkOutDate;
    private Room room;
    private double totalPrice = 0;
    private String discountStatus = "No Discount";
    private String chosenPackage = "No Package";

    /**
     * Constructs a new Reservation with the specified guest name, check-in date, check-out date, and room.
     * 
     * @param guestName is the name of the guest.
     * @param checkInDate is the check-in date.
     * @param checkOutDate is the check-out date.
     * @param room is the room reserved.
     */
    public Reservation(String guestName, int checkInDate, int checkOutDate, Room room) {
        this.guestName = guestName;
        this.checkInDate = checkInDate;
        this.checkOutDate = checkOutDate;
        this.room = room;
    }

    /**
     * Gets the room reserved.
     * 
     * @return the room reserved
     */
    public Room getRoom() {
        return room;
    }

    /**
     * Gets the name of the guest.
     * 
     * @return the guest name
     */
    public String getGuestName() {
        return guestName;
    }

    /**
     * Gets the check-in date.
     * 
     * @return the check-in date
     */
    public int getCheckInDate() {
        return checkInDate;
    }

    /**
     * Gets the check-out date.
     * 
     * @return the check-out date
     */
    public int getCheckOutDate() {
        return checkOutDate;
    }

    /**
     * Gets the total price of the reservation.
     * 
     * @return the total price of the reservation
     */
    public double getTotalPrice() {
        return totalPrice;
    }

    /**
     * Gets the discount status of a reservation.
     *
     * @return the discount status of the reservation
     */
    public String getDiscountStatus() {
        return discountStatus;
    }

    public String getChosenPackage() { 
        return chosenPackage; 
    }
    
    /**
     * Updates the total price of the reservation.
     *
     * @param newTotalPrice is the new total price to set.
     */
    public void updateTotalPrice(double newTotalPrice) {
        this.totalPrice = Math.round(newTotalPrice * 100.0) / 100.0;
    }

    /**
     * Updates the discount status of the reservation by applying the discount code.
     *
     * @param discountStatus is the new discount status to set.
     */
    public void applyDiscountCode(String discountStatus) {
        this.discountStatus = discountStatus;
    }

    public void updateChosenPackage(String chosenPackage) { 
        this.chosenPackage = chosenPackage; 
    }
}