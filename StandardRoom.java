/**
 * This class extends the Room class with the same price of 1,299.0.
 * 
 */
public class StandardRoom extends Room{
    public StandardRoom(String roomName) {
        super(roomName);
    }

    @Override
    public double getPrice() {
        return super.price;
    }
}
