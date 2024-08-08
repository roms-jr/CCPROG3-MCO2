/**
 * This class extends the Room class. It has a new room price with an additional 35% amount.
 * 
 */
public class ExecutiveRoom extends Room{
    public ExecutiveRoom(String roomName) {
        super(roomName);
    }

    @Override
    public double getPrice() {
        return (super.price * 0.35) + super.price;
    }
}