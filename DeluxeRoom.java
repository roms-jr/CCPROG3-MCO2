/**
 * This class extends the Room class. It has a new room price with an additional 20% amount.
 * 
 */
public class DeluxeRoom extends Room{
    public DeluxeRoom(String roomName) {
        super(roomName);
    }

    @Override
    public double getPrice() {
        return (super.price * 0.20) + super.price;
    }
}
