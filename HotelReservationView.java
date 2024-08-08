import java.util.ArrayList;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

/**
 * The HotelReservationView class represents the visualization of data received from the model.
 */
public class HotelReservationView {
    /**
     * Displays information about a specific hotel.
     */
    public void viewHotelInfo(String hotelName, int numRooms, int numStandardRooms, int numDeluxeRooms, int numExecutiveRooms, double totalEarnings) {
        StringBuilder details = new StringBuilder("Hotel name: " + hotelName + "\nTotal Number of Rooms: " + numRooms + "\n   - Number of Standard Rooms: " + numStandardRooms + "\n   - Number of Deluxe Rooms: " + numDeluxeRooms + "\n   - Number of Executive Rooms: " + numExecutiveRooms + "\nEstimate earnings for the month: " + totalEarnings);

        JOptionPane.showMessageDialog(null, details.toString(), "Hotel Information", JOptionPane.PLAIN_MESSAGE);
    }

    /*
     * Displays the number of available rooms and booked rooms. 
     */
    public void viewNumberOfRoomsInfo(int date, int numAvailableRooms, int numBookedRooms) {
        StringBuilder details = new StringBuilder("Total number of available rooms for Day #" + date + " of the month: " + numAvailableRooms + "\nTotal number of booked rooms for Day #" + date + " of the month: " + numBookedRooms);

        JOptionPane.showMessageDialog(null, details.toString(), "Total Number of Rooms Information", JOptionPane.PLAIN_MESSAGE);
    }

    /*
     * Displays the room information.
     */
    public void viewRoomInfo(String roomName, String roomType, double roomPrice) {
        StringBuilder details = new StringBuilder(
            "Room's Name: " + roomName + "\nType of Room: " + roomType + "\nPrice per night: " + roomPrice
        );

        JOptionPane.showMessageDialog(null, details.toString(), "Room Information", JOptionPane.PLAIN_MESSAGE);
    }

    /*
     * Displays the reservation information.
     */
    public void viewReservationInfo(String roomType, String guestName, String roomName, int checkInDate, int checkOutDate, double totalPrice) {       
        Object[] details = {
            "Guest Name: " + guestName,
            "Room Name: " + roomName,
            "Type of Room: " + roomType,
            "Check-In Date: " + checkInDate,
            "Check-Out Date: " + checkOutDate,
            "Total Price for the Booking: " + totalPrice,
        }; 
        JOptionPane.showMessageDialog(null, details, "Reservation Information", JOptionPane.PLAIN_MESSAGE);
    }

    /**
     * Displays the breakdown cost of a reservation per night.
     */
    public void viewBreakdownCostInfo(ArrayList<Integer> dateModifyPriceList, ArrayList<Double> priceRateList, int checkInDate, int checkOutDate, double totalPrice, double roomPrice, String discountStatus) {
        int nightsOfStay;
        int night = 0;
        double totalDiscount = 0;
        DefaultTableModel model = new DefaultTableModel();
        JTable table;
        JScrollPane scrollPane;
        String displayDiscountStatus = "";

        model.addColumn("Night");
        model.addColumn("Date");
        model.addColumn("Price");

        Object[] costInfo = {"", "", ""};
        for(nightsOfStay = checkInDate; nightsOfStay < checkOutDate; nightsOfStay++) {
            night++;
            costInfo[costInfo.length - 3] = night;
            costInfo[costInfo.length - 2] = nightsOfStay + " - " + (nightsOfStay + 1);
            costInfo[costInfo.length - 1] = roomPrice;
            for(int i = 0; i < dateModifyPriceList.size(); i++){
                if(nightsOfStay == dateModifyPriceList.get(i)){
                    costInfo[costInfo.length - 1] = roomPrice * priceRateList.get(i);
                }
            }
            
            if (night == 1 && checkOutDate - checkInDate >= 5 && discountStatus.equals("STAY4_GET1")) {
                costInfo[2] = "FREE";
                displayDiscountStatus = discountStatus;
            }
            model.addRow(costInfo);
        }

        if(discountStatus.equals("I_WORK_HERE")){
            displayDiscountStatus = discountStatus + " (10% OFF)";
        } else if(discountStatus.equals("PAYDAY")){
            displayDiscountStatus = discountStatus + " (7% OFF)";
        }

        costInfo[costInfo.length - 3] = "";
        costInfo[costInfo.length - 2] = "";
        costInfo[costInfo.length - 1] = "";
        model.addRow(costInfo);
        costInfo[costInfo.length - 3] = "";
        costInfo[costInfo.length - 2] = "Discount Code Used: ";
        costInfo[costInfo.length - 1] = displayDiscountStatus;
        model.addRow(costInfo);
        costInfo[costInfo.length - 3] = "";
        costInfo[costInfo.length - 2] = "Total Discount (including rates): ";
        totalDiscount = Math.round((roomPrice * (checkOutDate - checkInDate) - totalPrice) * 100.0) / 100.0;
        if(totalDiscount < 0) {
           costInfo[costInfo.length - 1] = 0;
        } else {
           costInfo[costInfo.length - 1] = totalDiscount;
        } 
        model.addRow(costInfo);
        costInfo[costInfo.length - 3] = "";
        costInfo[costInfo.length - 2] = "Total Price: ";
        costInfo[costInfo.length - 1] = totalPrice;
        model.addRow(costInfo);

        table = new JTable(model);
        table.getColumnModel().getColumn(0).setPreferredWidth(7);
        table.getColumnModel().getColumn(1).setPreferredWidth(100);
        table.getColumnModel().getColumn(2).setPreferredWidth(60);

        scrollPane = new JScrollPane(table);
        
        JOptionPane.showMessageDialog(null, scrollPane, "Breakdown of the Cost per Night", JOptionPane.PLAIN_MESSAGE);
    }
}
