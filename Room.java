/**
 * This abstract class represents a room with a name, a default price of 1,299.0, and a status.
 * 
 */
public abstract class Room {

    private String roomName;
    protected double price = 1299.0;
    private String status = "available";

    /**
     * This constructs a new Room object with the specified room name.
     *
     * @param roomName has the name of the room.
     */
    public Room(String roomName) {
        this.roomName = roomName;
    }

    /**
     * Gets the name of the room.
     *
     * @return the name of the room.
     */
    public String getRoomName() {
        return roomName;
    }

    /**
     * An abstract method for getting the price of a room.
     */
    abstract public double getPrice();

    /**
     * Gets the current status of the room.
     *
     * @return the status of the room.
     */
    public String getStatus() {
        return status;
    }
    
    /**
     * Updates the price of the room.
     *
     * @param price is the new price to set.
     */
    public void updatePrice(double price) {
        this.price = price;
    }

    /**
     * Updates the status of the room.
     *
     * @param status is the new status to set for a room if it is reserved or not.
     */
    public void updateStatus(String status) {
        this.status = status;
    }
}