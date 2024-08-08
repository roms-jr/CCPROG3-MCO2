import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.Arrays;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.Icon;
import javax.swing.ImageIcon;

/**
 * The HotelReservationController class manages a list of hotels and provides methods
 * to create, view, manage, and remove hotels, as well as simulate and perform bookings.
 * This class also calls the model which it gathers the requested data. Then the
 * controller transfers the data retrieved to the view layer.
 */
public class HotelReservationController{
    private HotelReservationModel model;
    private HotelReservationView view;

    /**
     * Constructor for the HotelReservationController class.
     */
    public HotelReservationController(HotelReservationModel model, HotelReservationView view){
        this.model = model;
        this.view = view;
    }

    /**
     * Gets the list of hotels in the system.
     * 
     * @return the list of hotels
     */
    public ArrayList<Hotel> getHotelList() {
        return model.getHotelList();
    }

    /**
     * Creates a new hotel and adds it to the hotel list.
     *
     * @param hotelName The name of the hotel to be added.
     */
    public void createHotel(String hotelName){
        model.createHotel(hotelName);
    }

    /**
     * Shows a menu of features to manage a hotel.
     *
     * @param hotel The selected hotel to be managed.
     */
    public void showManageHotelMenu(Hotel hotel) {
        JDialog dialog = new JDialog();
        dialog.setTitle("Manage Student Records");
        dialog.setModal(true);
        dialog.setLayout(new BorderLayout());
        dialog.setSize(500, 400);

        String[] options = {"Change the name of the hotel", "Add room(s)", "Remove room(s)", "Update the base price for a room", "Remove reservation", "Remove hotel", "Date Price Modifier"};
        JButton button1 = new JButton(options[0]);
        JButton button2 = new JButton(options[1]);
        JButton button3 = new JButton(options[2]);
        JButton button4 = new JButton(options[3]);
        JButton button5 = new JButton(options[4]);
        JButton button6 = new JButton(options[5]);
        JButton button7 = new JButton(options[6]);
        JButton backButton = new JButton("Back");

        button1.addActionListener(e -> {
            askNewHotelName(hotel);
        });
        button2.addActionListener(e -> {
            askNumberOfAdditionalRooms(hotel);
        });
        button3.addActionListener(e -> {
            showRemoveRoomOption(hotel);
        });
        
        button4.addActionListener(e -> {
            showUpdatePriceRoomOption(hotel);
        });

        button5.addActionListener(e -> {
            showRemoveReservationOption(hotel);
        });

        button6.addActionListener(e -> {
            dialog.dispose();
            int confirmModification;
            confirmModification = JOptionPane.showConfirmDialog(null, "Do you want to confirm your configuration?", "Remove Hotel Confirmation", JOptionPane.YES_NO_OPTION);
            if (confirmModification == JOptionPane.YES_OPTION) {
                String[] removeHotelMessage = {"Hotel " + hotel.getHotelName() + " is successfully removed."};
                removeHotel(hotel);
                JOptionPane.showMessageDialog(null, removeHotelMessage);
            }
        });

        button7.addActionListener(e -> {
            showDatePriceModifierOption(hotel);
        });

        backButton.addActionListener(e -> dialog.dispose());

        JPanel panel = new JPanel();
        panel.add(button1);
        panel.add(button2);
        panel.add(button3);
        panel.add(button4);
        panel.add(button5);
        panel.add(button6);
        panel.add(button7);

        JPanel backPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        panel.setLayout(new GridLayout(4, 2, 10, 10));
        backPanel.add(backButton);

        dialog.add(panel, BorderLayout.CENTER);
        dialog.add(backPanel, BorderLayout.SOUTH);
        dialog.setLocationRelativeTo(null);
        dialog.setVisible(true);
    }

    /** 
     * Updates the hotel name.
     * 
     * @param hotel The selected hotel to be modified.
     */
    public void askNewHotelName(Hotel hotel) {
        JTextField hotelNameField = new JTextField();

        int option; 
        int confirmModification;
        String hotelName;
        boolean validInputs;

        do {
            Object[] message = {"New Hotel Name:", hotelNameField};

            option = JOptionPane.showConfirmDialog(null, message, "Change Hotel Name Feature", JOptionPane.OK_CANCEL_OPTION);
            hotelName = hotelNameField.getText();
            validInputs = true;

            /* For Input Checking: */
            if (option == JOptionPane.OK_OPTION) {
                String[] warning = {
                    "Invalid Input.",
                    "Hotel name '" + hotelName + "' is already used."
                };
            
                if (!this.compareHotelName(hotelName, this.getHotelList())) {
                    validInputs = false;
                    JOptionPane.showMessageDialog(null, warning);
                }
            }

        } while (!validInputs && option == JOptionPane.OK_OPTION);
        
        if (option == JOptionPane.OK_OPTION) {
            String[] hotelNameChanged = {"The " + hotel.getHotelName() + " is successfully changed into " + hotelName};

            confirmModification = JOptionPane.showConfirmDialog(null, "Do you want to confirm your configuration?", "Change Hotel Name Confirmation", JOptionPane.YES_NO_OPTION);
            if (confirmModification == JOptionPane.YES_OPTION) {
                hotel.changeHotelName(hotelName);
                JOptionPane.showMessageDialog(null, hotelNameChanged);
            }
        }
    }

    /**
     * Compares a given hotel name with the names of hotels in the provided hotel list.
     *
     * @param name The name to compare.
     * @param hotelList The list of hotels to compare with.
     * @return false if a hotel with the given name exists in the list, false otherwise.
     */
    public boolean compareHotelName(String name, ArrayList<Hotel> hotelList) {
        int x;
        for (x = 0; x < hotelList.size(); x++) {
            if (hotelList.get(x).getHotelName().equals(name)) {
                return false;
            }
        }
        return true;
    }

    /**
     * This method will ask the user for the number of rooms to add and confirms the configuration.
     */
    public void askNumberOfAdditionalRooms(Hotel hotel) {
        JTextField numStandardRoomsField = new JTextField();
        JTextField numDeluxeRoomsField = new JTextField();
        JTextField numExecutiveRoomsField = new JTextField();

        int option; 
        int confirmModification;
        int numStandardRooms;
        int numDeluxeRooms;
        int numExecutiveRooms;
        boolean validInputs;

        int currentNumRooms = hotel.getRoomList().size();
        int availableRooms = 50 - currentNumRooms;

        if (availableRooms == 0){
            String[] fullCapacity = {"The hotel is already full of 50 rooms."};
            JOptionPane.showMessageDialog(null, fullCapacity);
        } else {
            do {
                Object[] message = {
                    "Number of Standard Rooms:", numStandardRoomsField,
                    "Number of Deluxe Rooms:", numDeluxeRoomsField,
                    "Number of Executive Rooms:", numExecutiveRoomsField
                };
    
                option = JOptionPane.showConfirmDialog(null, message, "Add Room(s) Feature", JOptionPane.OK_CANCEL_OPTION);
                numStandardRooms = Integer.parseInt(numStandardRoomsField.getText());
                numDeluxeRooms = Integer.parseInt(numDeluxeRoomsField.getText());
                numExecutiveRooms = Integer.parseInt(numExecutiveRoomsField.getText());
                validInputs = true;
    
                if (option == JOptionPane.OK_OPTION) {
                    /* For Input Checking: */
                    String[] invalidNumRooms = {
                        "Failed to add rooms to " + hotel.getHotelName() + ".",
                        "The number of additional rooms should be " + availableRooms + " or below."
                    };
                
                    if (numStandardRooms + numDeluxeRooms + numExecutiveRooms > availableRooms || numStandardRooms + numDeluxeRooms + numExecutiveRooms <= 0) {
                        validInputs = false;
                        JOptionPane.showMessageDialog(null, invalidNumRooms);
                    }
                }
            } while (!validInputs && option == JOptionPane.OK_OPTION);
            
            if (option == JOptionPane.OK_OPTION) {
                String[] hotelNameChanged = {(numStandardRooms + numDeluxeRooms + numExecutiveRooms) + " room/s added successfully to " + hotel.getHotelName() + "."};
    
                confirmModification = JOptionPane.showConfirmDialog(null, "Do you want to confirm your configuration?", "Add Room/s Confirmation", JOptionPane.YES_NO_OPTION);
                if (confirmModification == JOptionPane.YES_OPTION) {
                    hotel.addRoom(numStandardRooms, numDeluxeRooms, numExecutiveRooms);
                    JOptionPane.showMessageDialog(null, hotelNameChanged);
                }
            }
        }
    }

    
    /** 
     * Shows a list of available rooms that can be removed.
     * 
     * @param hotel The selected hotel to be modified.
     */
    public void showRemoveRoomOption(Hotel hotel) {
        JPanel panel = new JPanel(new GridLayout(5, 10, 10, 10));

        ArrayList<Room> roomsWithoutReservation = new ArrayList<Room>();
        ArrayList<Room> selectedRoomsToBeRemoved = new ArrayList<Room>();
        ArrayList<JCheckBox> checkBoxes = new ArrayList<>();
        int i, j;
        int option;
        int confirmModification;
        boolean reservedRoom = false;

        for(i = 0; i < hotel.getRoomList().size(); i++){
            for(j = 0; j < hotel.getReservationList().size() && !reservedRoom; j++){
                if(hotel.getRoomList().get(i).getRoomName().equals(hotel.getReservationList().get(j).getRoom().getRoomName())){
                    reservedRoom = true;
                }
            }
            
            if(reservedRoom == false) {
                JCheckBox checkBox = new JCheckBox(hotel.getRoomList().get(i).getRoomName());
                checkBoxes.add(checkBox);
                panel.add(checkBox);
                roomsWithoutReservation.add(hotel.getRoomList().get(i));
            }
            reservedRoom = false;
        }

        option = JOptionPane.showConfirmDialog(null, panel, 
                 "Rooms with No Reservations", JOptionPane.OK_CANCEL_OPTION);
        if (option == JOptionPane.OK_OPTION) {
            for (i = 0; i < checkBoxes.size(); i++) {
                if (checkBoxes.get(i).isSelected()) {
                    selectedRoomsToBeRemoved.add(roomsWithoutReservation.get(i));
                }
            }

            String[] roomsRemoved = {"The selected room/s removed successfully."};

            confirmModification = JOptionPane.showConfirmDialog(null, "Do you want to confirm your configuration?", "Remove Room/s Confirmation", JOptionPane.YES_NO_OPTION);
            if (confirmModification == JOptionPane.YES_OPTION) {
                for (i = 0; i < selectedRoomsToBeRemoved.size(); i++) {
                    hotel.removeRoom(selectedRoomsToBeRemoved.get(i));
                }
                JOptionPane.showMessageDialog(null, roomsRemoved);
            }
        }
    }

    
    /** 
     * Shows an option to the user to modify the base price of the rooms in a hotel.  
     * 
     * @param hotel The selected hotel to be modified.
     */
    public void showUpdatePriceRoomOption(Hotel hotel) {
        JTextField newPriceField = new JTextField();
 
        int option, confirmModification;
        int roomIndex;
        boolean askPrice = true;
        double price = 0;
        int numReservation = hotel.getReservationList().size();
 
        if(numReservation == 0){
            Object[] message = {"Enter new price: ", newPriceField};
 
            option = JOptionPane.showConfirmDialog(null, message, "Update Price Room Feature", JOptionPane.OK_CANCEL_OPTION);
            price = Integer.parseInt(newPriceField.getText());
 
            do{
                if((option == JOptionPane.OK_OPTION) && (price >= 100.0)) {
                    String[] hotelPriceChanged = {"New base price per room of " + price + " has been updated successfully to " + hotel.getHotelName() + "."};
 
 
                    confirmModification = JOptionPane.showConfirmDialog(null, "Do you want to confirm your configuration?", "Update Price Room Feature", JOptionPane.YES_NO_OPTION);
                    if (confirmModification == JOptionPane.YES_OPTION) {
                        for(roomIndex = 0; roomIndex < hotel.getRoomList().size(); roomIndex++) {
                            hotel.updatePriceRoom(roomIndex, price);
                        }
                        JOptionPane.showMessageDialog(null, hotelPriceChanged);
                    }
                    askPrice = false;
                } else {
                    Object[] messageInvalid = {"New price should be higher or equal to 100.0. Enter new price: ", newPriceField};
 
 
                    option = JOptionPane.showConfirmDialog(null, messageInvalid, "Update Price Room Feature", JOptionPane.OK_CANCEL_OPTION);
                    price = Integer.parseInt(newPriceField.getText());
                }
                if(option != JOptionPane.OK_OPTION) {
                    askPrice = false;
                }
            } while(askPrice);
        } else {
            String[] hotelPriceCannotChange = {"The Hotel room base price cannot be updated because there are reservations."};
 
            JOptionPane.showMessageDialog(null, hotelPriceCannotChange);
        }
    }

    /**
     * Shows a list of reservations that can be removed.
     * 
     * @param hotel The selected hotel to be modified.
     */
    public void showRemoveReservationOption(Hotel hotel) {
        JDialog dialog = new JDialog();
        dialog.setTitle("List of Reservations");
        dialog.setModal(true);
        dialog.setLayout(new BorderLayout());
        dialog.setSize(1090, 690);

        JButton backButton = new JButton("Back");
        backButton.addActionListener(e -> dialog.dispose());

        JPanel panel = new JPanel();
        JPanel backPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        panel.setLayout(new GridLayout(5, hotel.getReservationList().size()));
        backPanel.add(backButton);

        if (hotel.getReservationList().isEmpty()){
            String[] noReservation = {
                hotel.getHotelName() + " has no reservations yet."
            };
            JOptionPane.showMessageDialog(null, noReservation);
        } else {
            for (Reservation reservation : hotel.getReservationList()) {
                JButton reservationButton = new JButton(reservation.getRoom().getRoomName() + " reserved by " + reservation.getGuestName());
                reservationButton.addActionListener(e -> {
                    int confirmModification;
                    String[] reservationRemoved = {reservation.getRoom().getRoomName() + " reserved by " + reservation.getGuestName() + " in " + hotel.getHotelName() + " has been successfully removed."};


                    JOptionPane.getRootFrame().dispose();
                    confirmModification = JOptionPane.showConfirmDialog(null, "Do you want to confirm your configuration?", "Remove Reservation Feature", JOptionPane.YES_NO_OPTION);
                    if (confirmModification == JOptionPane.YES_OPTION) {
                        JOptionPane.showMessageDialog(null, reservationRemoved);
                        hotel.removeReservation(reservation);
                    }
                });
                panel.add(reservationButton);
            }

            dialog.add(panel, BorderLayout.CENTER);
            dialog.add(backPanel, BorderLayout.SOUTH);
            dialog.setLocationRelativeTo(null);
            dialog.setVisible(true);
        }
    }

    /**
     * Removes a hotel from the hotel list.
     */
    public void removeHotel(Hotel hotel){
        model.removeHotel(hotel);
    }

    /**
     * Shows a list of dates which each price rate can be modified.
     * 
     * @param hotel The selected hotel to be modified.
     */
    public void showDatePriceModifierOption(Hotel hotel) {
        JPanel panel = new JPanel(new GridLayout(5, 8, 10, 10));
        JTextField priceRateField = new JTextField();
 
        ArrayList<JCheckBox> checkBoxes = new ArrayList<>();
        int i, j;
        int option;
        int date;
        int confirmModification;
        double rate = 0.0;
        boolean askRate = true;
 
        if (!hotel.getReservationList().isEmpty()){
            String[] thereIsReservation = {
                "Price cannot be modified as " + hotel.getHotelName() + " has an existing reservation/s."
            };
            JOptionPane.showMessageDialog(null, thereIsReservation);
        } else {
            for(i = 1; i < 31; i++){
                JCheckBox checkBox = new JCheckBox(String.valueOf(i));
                checkBoxes.add(checkBox);
                panel.add(checkBox);
            }
            option = JOptionPane.showConfirmDialog(null, panel,
                    "Select Date/s you want to modify \n", JOptionPane.OK_CANCEL_OPTION);
            if (option == JOptionPane.OK_OPTION) {
                Object[] message = {"Enter price rate from 0.5 (50%) to 1.5 (150%): ", priceRateField};
                String[] priceModified = {"The " + hotel.getHotelName() + " base price for specific day/s has been successfully modified!"};
 
                do {
                    option = JOptionPane.showConfirmDialog(null, message, "Update Price Room Feature", JOptionPane.OK_CANCEL_OPTION);
                    rate = Double.parseDouble(priceRateField.getText());
                    if (option == JOptionPane.OK_OPTION && (rate >= 0.5 && rate <= 1.5)) {
                        confirmModification = JOptionPane.showConfirmDialog(null, "Do you want to confirm your configuration?", "Date Price Modified Confirmation", JOptionPane.YES_NO_OPTION);
                        if (confirmModification == JOptionPane.YES_OPTION) {
                            for (i = 0; i < checkBoxes.size(); i++) {
                                if (checkBoxes.get(i).isSelected()) {
                                    date = i + 1;
                                    for(j = 0; j < hotel.getDateModifiedPriceList().size(); j++){
                                        if(date == hotel.getDateModifiedPriceList().get(j)){
                                            hotel.getDateModifiedPriceList().remove(j);
                                            hotel.getPriceRateList().remove(j);
                                        }
                                    }
                                    hotel.addDateModifiedPrice(i+1);
                                    hotel.addPriceRate(rate);
                                }
                            }
                            JOptionPane.showMessageDialog(null, priceModified);
                        }
                        askRate = false;
                    } else {
                        Object[] invalidMessage = {"Price rate should be from 0.5 (50%) to 1.5 (150%)."};
 
                        JOptionPane.showMessageDialog(null, invalidMessage);
                    }
                    if (option != JOptionPane.OK_OPTION) {
                        askRate = false;
                    }
                } while(askRate);
            }
        }
    }

    /**
     * Confirms a modification.
     *
     * @return A boolean representing the confirmation status.
     */
    public boolean confirmModification(int choice) {
        if (choice == 0){
            return false;
        }
        return true;
    }

    /**
     * Shows two types of booking that can be selected.
     * 
     * @param hotel The selected hotel for booking.
     * @param guestName The guest name who wants to reserve a room.
     * @param checkInDate The date when the guest decided to check-in.
     * @param checkOutDate The date when the guest decided to check-out.
     */
    public void showSimulateBookingMenu(Hotel hotel, String guestName, int checkInDate, int checkOutDate){
        JDialog dialog = new JDialog();
        dialog.setTitle("Choose a Type of Booking");
        dialog.setModal(true);
        dialog.setLayout(new BorderLayout());
        dialog.setSize(500, 400);

        JButton automatedButton = new JButton("Automated Booking");
        JButton manualButton = new JButton("Manual Booking");
        JButton backButton = new JButton("Back");
        backButton.addActionListener(e -> dialog.dispose());

        JPanel panel = new JPanel();
        JPanel backPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        panel.setLayout(new GridLayout(2, 1));
        backPanel.add(backButton);

        automatedButton.addActionListener(e -> {
            if(automatedBooking(hotel, guestName, checkInDate, checkOutDate)){
                dialog.dispose();
            }
        });
        manualButton.addActionListener(e -> {
            if(manualBooking(hotel, guestName, checkInDate, checkOutDate)){
                dialog.dispose();
            };
        });
        backButton.addActionListener(e -> dialog.dispose());
        panel.add(automatedButton);
        panel.add(manualButton);

        dialog.add(panel, BorderLayout.CENTER);
        dialog.add(backPanel, BorderLayout.SOUTH);
        dialog.setLocationRelativeTo(null);
        dialog.setVisible(true);
    }

    /**
     * Ask the user to input the reservation details.
     * 
     * @param hotel The selected hotel for booking.
     */
    public void askReservationDetails(Hotel hotel) {
        JTextField guestNameField = new JTextField();
        JTextField checkInDateField = new JTextField();
        JTextField checkOutDateField = new JTextField();

        int option; 
        String guestName;
        int checkInDate, checkOutDate;
        boolean validInputs;

        do {
            Object[] message = {
                "Guest Name:", guestNameField,
                "Check-In Date:", checkInDateField,
                "Check-Out Date:", checkOutDateField
            };

            option = JOptionPane.showConfirmDialog(null, message, "Enter Reservation Details", JOptionPane.OK_CANCEL_OPTION);
            guestName = guestNameField.getText();
            checkInDate = Integer.parseInt(checkInDateField.getText());
            checkOutDate = Integer.parseInt(checkOutDateField.getText());

            validInputs = true;

            /* For Input Checking: */
            if (option == JOptionPane.OK_OPTION) {
                String[] warning = {"Invalid input/s. "};
            
                if (checkInDate == 31) {
                    validInputs = false;
                    warning = Arrays.copyOf(warning, warning.length + 1);
                    warning[warning.length - 1] = "Invalid check-in date.";
                }

                if (checkOutDate == 1) {
                    validInputs = false;
                    warning = Arrays.copyOf(warning, warning.length + 1);
                    warning[warning.length - 1] = "Invalid check-out date.";
                }

                if (checkInDate > checkOutDate) {
                    validInputs = false;
                    warning = Arrays.copyOf(warning, warning.length + 1);
                    warning[warning.length - 1] = "Check-in date should be less than the check-out date.";
                }

                if (checkInDate == checkOutDate) {
                    validInputs = false;
                    warning = Arrays.copyOf(warning, warning.length + 1);
                    warning[warning.length - 1] = "Check-in and check-out dates should be at least a day apart to account for a single night.";
                }

                if (checkInDate < 1 || checkOutDate < 1 || checkInDate > 31 || checkOutDate > 31) {
                    validInputs = false;
                    warning = Arrays.copyOf(warning, warning.length + 1);
                    warning[warning.length - 1] = "Check-in and check-out dates should be a number between 1 to 31.";
                }

                if (!checkAvailability(hotel, checkInDate, checkOutDate)){
                    validInputs = false;
                    warning = Arrays.copyOf(warning, warning.length + 1);
                    warning[warning.length - 1] = "No available rooms with the check-in date: " + checkInDate + " and check-out date: " + checkOutDate;
                }

                if (!validInputs){
                    JOptionPane.showMessageDialog(null, warning);
                }
            }

        } while (!validInputs && option == JOptionPane.OK_OPTION);
        
        if (option == JOptionPane.OK_OPTION) {
            showSimulateBookingMenu(hotel, guestName, checkInDate, checkOutDate);
        }
    }

    /**
     * Checks the availability of the selected dates for booking.
     * 
     * @param hotel The selected hotel for booking.
     * @param checkInDate The date when the guest decided to check-in.
     * @param checkOutDate The date when the guest decided to check-out.
     * @return true if the selected dates are available for booking. Otherwise, false.
     */
    public boolean checkAvailability(Hotel hotel, int checkInDate, int checkOutDate){
        int roomIndex, reservationIndex;
        boolean isReserved = false;
        int availableRoomIndex = -1;

        for (roomIndex = 0; (roomIndex < hotel.getRoomList().size()) && availableRoomIndex == -1; roomIndex++) {
            for (reservationIndex = 0; reservationIndex < hotel.getReservationList().size(); reservationIndex++){
                if(hotel.getReservationList().get(reservationIndex).getRoom().getRoomName().equals(hotel.getRoomList().get(roomIndex).getRoomName())){
                    if (checkInDate >= hotel.getReservationList().get(reservationIndex).getCheckInDate() && checkInDate < hotel.getReservationList().get(reservationIndex).getCheckOutDate()) {
                        isReserved = true;
                    } else if (checkInDate <= hotel.getReservationList().get(reservationIndex).getCheckInDate() && checkOutDate > hotel.getReservationList().get(reservationIndex).getCheckInDate()) {
                        isReserved = true;
                    }
                }
            }

            if(!isReserved){
                availableRoomIndex = roomIndex;
            }
            isReserved = false;
        }

        if (availableRoomIndex == -1) {
            return false;
        } 
        return true;
    }

    /**
     * Performs an automated booking for a specific hotel.
     *
     * @param hotel The chosen hotel from the hotel list.
     * @param guestName The name of the guest.
     * @param checkIn The check-in date.
     * @param checkOut The check-out date.
     */
    public boolean automatedBooking(Hotel hotel, String guestName, int checkInDate, int checkOutDate){
        int roomIndex, reservationIndex;
        boolean isReserved = false;
        int availableRoomIndex = -1; 

        for (roomIndex = 0; (roomIndex < hotel.getRoomList().size()) && availableRoomIndex == -1; roomIndex++) {
            for (reservationIndex = 0; reservationIndex < hotel.getReservationList().size(); reservationIndex++){
                if(hotel.getReservationList().get(reservationIndex).getRoom().getRoomName().equals(hotel.getRoomList().get(roomIndex).getRoomName())){
                    if (checkInDate >= hotel.getReservationList().get(reservationIndex).getCheckInDate() && checkInDate < hotel.getReservationList().get(reservationIndex).getCheckOutDate()) {
                        isReserved = true;
                    } else if (checkInDate <= hotel.getReservationList().get(reservationIndex).getCheckInDate() && checkOutDate > hotel.getReservationList().get(reservationIndex).getCheckInDate()) {
                        isReserved = true;
                    }
                }
            }

            if(!isReserved){
                createReservation(hotel, guestName, checkInDate, checkOutDate, hotel.getRoomList().get(roomIndex));
                availableRoomIndex = roomIndex;
                return true;
            }
            isReserved = false;
        }
        return false;
    }
    
    /**
     * Performs an manual booking for a specific hotel.
     *
     * @param hotel The chosen hotel from the hotel list.
     * @param guestName The name of the guest.
     * @param checkIn The check-in date.
     * @param checkOut The check-out date.
     */
    public boolean manualBooking(Hotel hotel, String guestName, int checkInDate, int checkOutDate){
        JDialog dialog = new JDialog();
        dialog.setModal(true);
        dialog.setLayout(new BorderLayout());
        dialog.setSize(500, 400);

        JPanel roomPanel = new JPanel(new GridLayout(5, 10, 10, 10));
        JPanel backPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));

        JButton roomButton;
        JButton backButton = new JButton("Back");
        backButton.addActionListener(e -> dialog.dispose());
        
        int roomIndex, reservationIndex, i, numAvailableRooms;
        boolean isReserved;
        boolean reserveSuccessfully[] = {false};
        String roomType = selectRoomTypeOption(hotel);

        ArrayList<Room> availableRoomList = new ArrayList<Room>();
        ArrayList<Room> selectedRoomList = new ArrayList<Room>();

        if (roomType == "Standard Room"){
            selectedRoomList.addAll(hotel.getStandardRoomList());
            dialog.setTitle("Choose an Available Standard Room");
        }
        else if (roomType == "Deluxe Room"){
            selectedRoomList.addAll(hotel.getDeluxeRoomList());    
            dialog.setTitle("Choose an Available Deluxe Room");
        }
        else if (roomType == "Executive Room"){
            selectedRoomList.addAll(hotel.getExecutiveRoomList());
            dialog.setTitle("Choose an Available Executive Room");
        }

        for (roomIndex = 0; roomIndex < selectedRoomList.size(); roomIndex++) {
            isReserved = false;
            for (reservationIndex = 0; reservationIndex < hotel.getReservationList().size(); reservationIndex++){
                if(hotel.getReservationList().get(reservationIndex).getRoom().getRoomName().equals(selectedRoomList.get(roomIndex).getRoomName())){
                    if (checkInDate >= hotel.getReservationList().get(reservationIndex).getCheckInDate() && checkInDate < hotel.getReservationList().get(reservationIndex).getCheckOutDate()) {
                        isReserved = true;
                    } else if (checkInDate <= hotel.getReservationList().get(reservationIndex).getCheckInDate() && checkOutDate > hotel.getReservationList().get(reservationIndex).getCheckInDate()) {
                        isReserved = true;
                    }
                }
            }

            if(!isReserved){
                availableRoomList.add(selectedRoomList.get(roomIndex));
            }
        }

        numAvailableRooms = availableRoomList.size();

        for (i = 0; i < numAvailableRooms; i++) {
            Room room = availableRoomList.get(i);
            roomButton = new JButton(room.getRoomName());
            roomButton.addActionListener(e -> {
                createReservation(hotel, guestName, checkInDate, checkOutDate, room);
                dialog.dispose();
                reserveSuccessfully[0] = true;
            });
            roomPanel.add(roomButton);
        }    
        backPanel.add(backButton);

        if(numAvailableRooms == 0){
            JOptionPane.showMessageDialog(null, "No Available Rooms");
        } else if(roomType != ""){
            dialog.add(roomPanel, BorderLayout.CENTER);
            dialog.add(backPanel, BorderLayout.SOUTH);
            dialog.setLocationRelativeTo(null);
            dialog.setVisible(true);
        }

        return reserveSuccessfully[0];
    }

    /**
     * Ask the user to select a type of room to be booked.
     * 
     * @param hotel The selected hotel for booking.
     * @return the name of the selected type of room to be booked.
     */
    public String selectRoomTypeOption(Hotel hotel){
        JDialog dialog = new JDialog();
        dialog.setTitle("Choose a Type of Room Below");
        dialog.setModal(true);
        dialog.setLayout(new BorderLayout());
        dialog.setSize(500, 400);

        JPanel roomPanel = new JPanel(new GridLayout(3, 1, 10, 10));
        JPanel backPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));

        JButton standardButton = new JButton("Standard Rooms");
        JButton deluxeButton = new JButton("Deluxe Rooms");
        JButton executiveButton = new JButton("Executive Rooms");
        JButton backButton = new JButton("Back");  
         
        String[] selectedRoomType = {""};
        String message = "No Available ";

        standardButton.addActionListener(e -> {
            dialog.dispose();
            if(hotel.getStandardRoomList().isEmpty()){
                JOptionPane.showMessageDialog(null, message + "Standard Rooms");
            } else {
                selectedRoomType[0] = "Standard Room";
            }
        });

        deluxeButton.addActionListener(e -> {
            dialog.dispose();
            if(hotel.getDeluxeRoomList().isEmpty()){
                JOptionPane.showMessageDialog(null, message + "Deluxe Rooms");
            } else {
                selectedRoomType[0] = "Deluxe Room";
            }
        });

        executiveButton.addActionListener(e -> {
            dialog.dispose();
            if(hotel.getExecutiveRoomList().isEmpty()){
                JOptionPane.showMessageDialog(null, message + "Executive Rooms");
            } else {
                selectedRoomType[0] = "Executive Room";
            }
        });

        backButton.addActionListener(e -> dialog.dispose());
        roomPanel.add(standardButton);
        roomPanel.add(deluxeButton);
        roomPanel.add(executiveButton);
        backPanel.add(backButton);

        dialog.add(roomPanel, BorderLayout.CENTER);
        dialog.add(backPanel, BorderLayout.SOUTH);
        dialog.setLocationRelativeTo(null);
        dialog.setVisible(true);

        return selectedRoomType[0];
    }

    /**
     * Ask the user to input a valid discount code.
     * 
     * @param hotel The selected hotel for booking.
     * @param reservation The reservation made by the user.
     */
    public void askDiscountCode(Hotel hotel, Reservation reservation){
        JTextField discountCodeField = new JTextField();

        int option; 
        int daysOfStay = reservation.getCheckOutDate() - reservation.getCheckInDate();
        double newPrice = 0;
        double freeRoomPrice = reservation.getRoom().getPrice();
        String discountCode;
        boolean validInputs;

        Object[] message = {
            "Discount Code: ", discountCodeField
        };

        String[] discountApplied = {
            "The discount is successfully applied."
        };

        option = JOptionPane.showConfirmDialog(null, "Do you have a Discount Code?", "Discount Code for Reservations", JOptionPane.YES_NO_OPTION);
        if (option == JOptionPane.YES_OPTION) {
            do {
                option = JOptionPane.showConfirmDialog(null, message, "Discount Code for Reservations", JOptionPane.OK_CANCEL_OPTION);
                discountCode = discountCodeField.getText();
                validInputs = false;
    
                /* For Input Checking: */
                if (option == JOptionPane.OK_OPTION) {
                    String[] warning = {
                        "Invalid Discount Code."
                    };
                
                    if (discountCode.equals("I_WORK_HERE")) {
                        validInputs = true;
                        newPrice = reservation.getTotalPrice() - (reservation.getTotalPrice() * 0.10);
                        reservation.updateTotalPrice(newPrice);
                        reservation.applyDiscountCode("I_WORK_HERE");
                        discountApplied = Arrays.copyOf(discountApplied, discountApplied.length + 1);
                        discountApplied[discountApplied.length - 1] = reservation.getGuestName() + " received 10% discount to the overall price of the reservation.";
                    } else if (discountCode.equals("STAY4_GET1")) {
                        if (daysOfStay >= 5){
                            validInputs = true;
                            for(int x = 0; x < hotel.getDateModifiedPriceList().size(); x++){
                                if(hotel.getDateModifiedPriceList().get(x) == reservation.getCheckInDate()){
                                    freeRoomPrice = reservation.getRoom().getPrice() * hotel.getPriceRateList().get(x);
                                }
                            }
                            newPrice = reservation.getTotalPrice() - freeRoomPrice;
                            reservation.updateTotalPrice(newPrice);
                            reservation.applyDiscountCode("STAY4_GET1");
                            discountApplied = Arrays.copyOf(discountApplied, discountApplied.length + 1);
                            discountApplied[discountApplied.length - 1] = "The first day of " + reservation.getGuestName() + "'s reservation is given for free.";
                        } else {
                            warning = Arrays.copyOf(warning, warning.length + 1);
                            warning[warning.length - 1] = "This code is only applicable to the guests who have a reservation with 5 or more days.";
                        }
                    } else if (discountCode.equals("PAYDAY")) {
                        if ((reservation.getCheckInDate() <= 15 && 15 < reservation.getCheckOutDate()) || (reservation.getCheckInDate() <= 30 && reservation.getCheckOutDate() == 31)){
                            validInputs = true;
                            newPrice = reservation.getTotalPrice() - (reservation.getTotalPrice() * 0.07);
                            reservation.updateTotalPrice(newPrice);
                            reservation.applyDiscountCode("PAYDAY");
                            discountApplied = Arrays.copyOf(discountApplied, discountApplied.length + 1);
                            discountApplied[discountApplied.length - 1] = reservation.getGuestName() + " received 7% discount to the overall price of the reservation.";
                        } else {
                            warning = Arrays.copyOf(warning, warning.length + 1);
                            warning[warning.length - 1] = "This code is only applicable to the guests who have a reservation that covers either day 15 or 30 (but not as a checkout).";
                        }
                    }
    
                    if (!validInputs){
                        JOptionPane.showMessageDialog(null, warning);
                    }
                }
    
            } while (!validInputs && option == JOptionPane.OK_OPTION);
            
            if (option == JOptionPane.OK_OPTION && validInputs) {
                JOptionPane.showMessageDialog(null, discountApplied);
            }
        }
    }

    /**
     * Create a reservation made by the user.
     * 
     * @param hotel The selected hotel for booking.
     * @param guestName The name of the guest who booked a reservation.
     * @param checkInDate The date when the guest decided to check-in.
     * @param checkOutDate The date when the guest decided to check-out.
     * @param chosenRoom The room chosen by the guest.
     */
    public void createReservation(Hotel hotel, String guestName, int checkInDate, int checkOutDate, Room chosenRoom) {
        Reservation reservation;
        Object[] message = {
            guestName + " successfully reserved a room.",
            "The room name is: " + chosenRoom.getRoomName()
        };
        double totalPrice = 0.0;
        int daysOfStay = 0;

        reservation = new Reservation(guestName, checkInDate, checkOutDate, chosenRoom);
        daysOfStay = checkOutDate - checkInDate;
        totalPrice = daysOfStay * chosenRoom.getPrice();
        reservation.updateTotalPrice(totalPrice);
        hotel.datePriceModifier(reservation);

        askDiscountCode(hotel, reservation);
        hotel.getReservationList().add(reservation);
        for(Room room : hotel.getRoomList()){
            if(chosenRoom.equals(room)){
                room.updateStatus("booked");
            }
        }
        JOptionPane.showMessageDialog(null, message);
    }

    /**
     * Updates the information about a specific hotel for display.
     */
    public void updateViewHotelInfo(Hotel hotel) {
        int x;
        double totalEarnings = 0.0;
        for (x = 0; x < hotel.getReservationList().size(); x++) {
            totalEarnings += hotel.getReservationList().get(x).getTotalPrice();
        }
        view.viewHotelInfo(hotel.getHotelName(), hotel.getRoomList().size(), hotel.getStandardRoomList().size(), hotel.getDeluxeRoomList().size(), hotel.getExecutiveRoomList().size(), totalEarnings);
    }

    /*
     * Updates the number of available rooms and booked rooms for display.
     */
    public void updateViewNumberOfRoomsInfo(Hotel hotel) {
        JTextField dateField = new JTextField();
        int option, date, x;
        int availableRooms = hotel.getRoomList().size();
        int bookedRooms = 0;
        boolean validInputs;

        do {
            Object[] message = {
                "Preferred Date (1 - 31):", dateField
            };
            
            option = JOptionPane.showConfirmDialog(null, message, "Selection of Date", JOptionPane.OK_CANCEL_OPTION);
            date = Integer.parseInt(dateField.getText());
            validInputs = true;

            /* For Input Checking: */
            if (option == JOptionPane.OK_OPTION) {
                String[] warning = {
                    "Invalid Input.",
                    "The date must be a number between 1 and 31 only."
                };
            
                if (date < 1 || 31 < date) {
                    validInputs = false;
                }

                if (!validInputs){
                    JOptionPane.showMessageDialog(null, warning);
                }
            }

        } while (!validInputs && option == JOptionPane.OK_OPTION);

        for(x = 0; x < hotel.getReservationList().size(); x++){
            if(hotel.getReservationList().get(x).getCheckInDate() == date){
                availableRooms--;
                bookedRooms++;
            } else if (hotel.getReservationList().get(x).getCheckInDate() <= date && date < hotel.getReservationList().get(x).getCheckOutDate()){
                availableRooms--;
                bookedRooms++;
            }
        }
        
        view.viewNumberOfRoomsInfo(date, availableRooms, bookedRooms);
    }

    /*
     * Updates the room information for display.
     */
    public void updateViewRoomInfo(Room room) {
        String roomType;
        if(room instanceof DeluxeRoom)
            roomType = "Deluxe Room";
        else if(room instanceof ExecutiveRoom)
            roomType = "Executive Room";
        else
            roomType = "Standard Room";

        view.viewRoomInfo(room.getRoomName(), roomType, room.getPrice());
    }

    /*
     * Updates the reservation information for display.
     */
    public void updateViewReservationInfo(Reservation reservation) {
        String roomType;
        if(reservation.getRoom() instanceof DeluxeRoom)
            roomType = "Deluxe Room";
        else if(reservation.getRoom() instanceof ExecutiveRoom)
            roomType = "Executive Room";
        else
            roomType = "Standard Room";

        view.viewReservationInfo(roomType, reservation.getGuestName(), reservation.getRoom().getRoomName(), reservation.getCheckInDate(), reservation.getCheckOutDate(), reservation.getTotalPrice());       
    }

    /*
     * Updates the breakdown cost of a reservation per night for display.
     */
    public void updateViewBreakdownCostInfo(Hotel hotel, Reservation reservation) {
        view.viewBreakdownCostInfo(hotel.getDateModifiedPriceList(), hotel.getPriceRateList(), reservation.getCheckInDate(), reservation.getCheckOutDate(), reservation.getTotalPrice(), reservation.getRoom().getPrice(), reservation.getDiscountStatus());       
    }

    public void showAvailableHotel(String task) {
        JDialog dialog = new JDialog();
        dialog.setTitle("Choose a Hotel");
        dialog.setModal(true);
        dialog.setLayout(new BorderLayout());
        dialog.setSize(500, 400);

        JButton backButton = new JButton("Back");
        backButton.addActionListener(e -> dialog.dispose());

        JPanel panel = new JPanel();
        JPanel backPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        panel.setLayout(new GridLayout(getHotelList().size(), 1));
        backPanel.add(backButton);

        if(getHotelList().isEmpty()){
            String[] noHotel = {"There are no hotels in the system."};
            JOptionPane.showMessageDialog(null, noHotel);
        } else {
            for (Hotel hotel : getHotelList()) {
                JButton hotelButton = new JButton(hotel.getHotelName());
                hotelButton.addActionListener(e -> {
                    showReservation(hotel, task);
                });
                panel.add(hotelButton);
            }
            dialog.add(panel, BorderLayout.CENTER);
            dialog.add(backPanel, BorderLayout.SOUTH);
            dialog.setLocationRelativeTo(null);
            dialog.setVisible(true);
        }
    }

    public void showReservation(Hotel hotel, String task) {
        int i;
        int numInvalidReserved = 0;
        int noNumPackage = 0;

        JDialog dialog = new JDialog();
        dialog.setTitle("List of Reservations");
        dialog.setModal(true);
        dialog.setLayout(new BorderLayout());
        dialog.setSize(500, 400);

        JButton backButton = new JButton("Back");
        backButton.addActionListener(e -> dialog.dispose());

        JPanel panel = new JPanel();
        JPanel backPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        panel.setLayout(new GridLayout(hotel.getReservationList().size(), 1));
        backPanel.add(backButton);

        for (i = 0; i < hotel.getReservationList().size(); i++) {
            if (!hotel.getReservationList().get(i).getChosenPackage().equals("No Package")) {
                numInvalidReserved++;
            } else if ((hotel.getReservationList().get(i).getCheckOutDate() - hotel.getReservationList().get(i).getCheckInDate()) < 3) {
                numInvalidReserved++;
            }
            if(hotel.getReservationList().get(i).getChosenPackage().equals("No Package")){
                noNumPackage++;
            }
        }

        if (hotel.getReservationList().size() == 0){
            String[] noReservation = {
                    hotel.getHotelName() + " has no reservations yet."
            };
            JOptionPane.showMessageDialog(null, noReservation);
        } else if (numInvalidReserved == hotel.getReservationList().size() && task.equals("customize")){
            String[] noReservation = {
                    hotel.getHotelName() + " has no reservation with 3 or more nights of stay or all reservation has a package."
            };
            JOptionPane.showMessageDialog(null, noReservation);
        } else if (noNumPackage == hotel.getReservationList().size() && task.equals("view")){
            String[] noReservation = {
                    "Reservation/s in " + hotel.getHotelName() + " does not have a package."
            };
            JOptionPane.showMessageDialog(null, noReservation);
        } else {
            for (Reservation reservation : hotel.getReservationList()) {
                if (reservation.getChosenPackage().equals("No Package") && (reservation.getCheckOutDate() - reservation.getCheckInDate()) >= 3 && task.equals("customize")) {
                    JButton reservationButton = new JButton(reservation.getRoom().getRoomName() + " reserved by " + reservation.getGuestName());
                    reservationButton.addActionListener(e -> {
                        customizablePackages(reservation);
                    });
                    panel.add(reservationButton);
                } else if (!reservation.getChosenPackage().equals("No Package") && task.equals("view")) {
                    JButton reservationButton = new JButton(reservation.getRoom().getRoomName() + " reserved by " + reservation.getGuestName());
                    reservationButton.addActionListener(e -> {
                        viewPackage(reservation);
                    });
                    panel.add(reservationButton);
                }
            }

            dialog.add(panel, BorderLayout.CENTER);
            dialog.add(backPanel, BorderLayout.SOUTH);
            dialog.setLocationRelativeTo(null);
            dialog.setVisible(true);
        }
    }

    public void customizablePackages(Reservation reservation) {
        JDialog dialog = new JDialog();
        dialog.setTitle("Choose a package");
        dialog.setModal(true);
        dialog.setLayout(new BorderLayout());
        dialog.setSize(375, 800);

        JButton backButton = new JButton("Back");
        backButton.addActionListener(e -> dialog.dispose());

        JPanel panel = new JPanel();
        JPanel backPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        panel.setLayout(new GridLayout(3, 1));
        backPanel.add(backButton);

        Icon foodIcon = new ImageIcon("packagepicture/food.png");
        Icon spaIcon = new ImageIcon("packagepicture/spa.png");
        Icon tourIcon = new ImageIcon("packagepicture/tour.png");

        JButton foodButton = new JButton(foodIcon);
        JButton spaButton = new JButton(spaIcon);
        JButton tourButton = new JButton(tourIcon);

        panel.add(foodButton);
        panel.add(spaButton);
        panel.add(tourButton);

        foodButton.addActionListener(e -> choosePackage(reservation, "Choose a food package", "packagepicture/breakfast.png", "packagepicture/lunch.png", "packagepicture/dinner.png",
                                                        "Breakfast Package","Lunch Package", "Dinner Package"));
        spaButton.addActionListener(e -> choosePackage(reservation, "Choose a spa package", "packagepicture/spa1.png", "packagepicture/spa2.png", "packagepicture/spa3.png",
                                                        "Foot Spa Package","Facial Package", "Massage Package"));
        tourButton.addActionListener(e -> choosePackage(reservation, "Choose a spa package", "packagepicture/tour1.png", "packagepicture/tour2.png", "packagepicture/tour3.png",
                                                        "Binondo Food Tour Package","Rizal Park Tour Package", "Intramuros Tour Package"));
        backButton.addActionListener(e -> dialog.dispose());

        dialog.add(panel, BorderLayout.CENTER);
        dialog.add(backPanel, BorderLayout.SOUTH);
        dialog.setLocationRelativeTo(null);
        dialog.setVisible(true);
    }

    public void choosePackage(Reservation reservation, String title, String nameImage1, String nameImage2, String nameImage3, String namePackage1, String namePackage2, String namePackage3) {
        JDialog dialog = new JDialog();
        dialog.setTitle(title);
        dialog.setModal(true);
        dialog.setLayout(new BorderLayout());
        dialog.setSize(375, 800);

        JButton backButton = new JButton("Back");
        backButton.addActionListener(e -> dialog.dispose());

        JPanel panel = new JPanel();
        JPanel backPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        panel.setLayout(new GridLayout(3, 1));
        backPanel.add(backButton);

        Icon icon1 = new ImageIcon(nameImage1);
        Icon icon2 = new ImageIcon(nameImage2);
        Icon icon3 = new ImageIcon(nameImage3);

        JButton button1 = new JButton(icon1);
        JButton button2 = new JButton(icon2);
        JButton button3 = new JButton(icon3);

        panel.add(button1);
        panel.add(button2);
        panel.add(button3);

        button1.addActionListener(e -> {
            int confirmModification;
            String[] packageUpdated = {reservation.getGuestName() + " in " + reservation.getRoom().getRoomName() + " has successfully chosen " + namePackage1};

            JOptionPane.getRootFrame().dispose();
            confirmModification = JOptionPane.showConfirmDialog(null, "Do you want to confirm your configuration?", "Remove Reservation Feature", JOptionPane.YES_NO_OPTION);
            if (confirmModification == JOptionPane.YES_OPTION) {
                JOptionPane.showMessageDialog(null, packageUpdated);
                reservation.updateChosenPackage(namePackage1);
            }
        });
        button2.addActionListener(e -> {
            int confirmModification;
            String[] packageUpdated = {reservation.getGuestName() + " in " + reservation.getRoom().getRoomName() + " has successfully chosen " + namePackage2};

            JOptionPane.getRootFrame().dispose();
            confirmModification = JOptionPane.showConfirmDialog(null, "Do you want to confirm your configuration?", "Remove Reservation Feature", JOptionPane.YES_NO_OPTION);
            if (confirmModification == JOptionPane.YES_OPTION) {
                JOptionPane.showMessageDialog(null, packageUpdated);
                reservation.updateChosenPackage(namePackage2);
            }
        });
        button3.addActionListener(e -> {
            int confirmModification;
            String[] packageUpdated = {reservation.getGuestName() + " in " + reservation.getRoom().getRoomName() + " has successfully chosen " + namePackage3};

            JOptionPane.getRootFrame().dispose();
            confirmModification = JOptionPane.showConfirmDialog(null, "Do you want to confirm your configuration?", "Remove Reservation Feature", JOptionPane.YES_NO_OPTION);
            if (confirmModification == JOptionPane.YES_OPTION) {
                JOptionPane.showMessageDialog(null, packageUpdated);
                reservation.updateChosenPackage(namePackage3);
            }
        });
        backButton.addActionListener(e -> dialog.dispose());

        dialog.add(panel, BorderLayout.CENTER);
        dialog.add(backPanel, BorderLayout.SOUTH);
        dialog.setLocationRelativeTo(null);
        dialog.setVisible(true);
    }

    public void viewPackage(Reservation reservation) {
        ImageIcon icon = new ImageIcon(findImagePackage(reservation.getChosenPackage()));

        JOptionPane.showMessageDialog(
                null,
                reservation.getGuestName() + " in " + reservation.getRoom().getRoomName() + " has a " + reservation.getChosenPackage(),
                "Package", JOptionPane.INFORMATION_MESSAGE,
                icon);
    }

    public String findImagePackage(String chosenPackage) {
        if (chosenPackage.equals("Breakfast Package")){
            return "packagepicture/breakfast.png";
        } else if (chosenPackage.equals("Lunch Package")) {
            return "packagepicture/lunch.png";
        } else if (chosenPackage.equals("Dinner Package")) {
            return "packagepicture/dinner.png";
        } else if (chosenPackage.equals("Foot Spa Package")) {
            return "packagepicture/spa1.png";
        } else if (chosenPackage.equals("Facial Package")) {
            return "packagepicture/spa2.png";
        } else if (chosenPackage.equals("Massage Package")) {
            return "packagepicture/spa3.png";
        } else if (chosenPackage.equals("Binondo Food Tour Package")) {
            return "packagepicture/tour1.png";
        } else if (chosenPackage.equals("Rizal Park Tour Package")) {
            return "packagepicture/tour2.png";
        } else if (chosenPackage.equals("Intramuros Tour Package")) {
            return "packagepicture/tour3.png";
        }
        return null;
    }
}