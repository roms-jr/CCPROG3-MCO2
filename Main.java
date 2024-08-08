import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * This Main class is the driver file. 
 */
public class Main {
    private static HotelReservationModel model = new HotelReservationModel();
    private static HotelReservationView view = new HotelReservationView();
    private static HotelReservationController HRS = new HotelReservationController(model, view);
    
    public static void main(String[] args) {
        JFrame frame = new JFrame("Hotel Reservation System");
        JPanel panel = new JPanel(new GridLayout(6, 1, 10, 10)); // Changed to 6 rows and 1 column

        JButton createButton = new JButton("Create Hotel");
        JButton viewButton = new JButton("View Hotel");
        JButton manageButton = new JButton("Manage Hotel");
        JButton simulateButton = new JButton("Simulate Booking");
        JButton exitButton = new JButton("Exit");
        JButton otherButton = new JButton("Other Features");

        Font buttonFont = new Font("Comic Sans", Font.BOLD, 18);
        
        JLabel imageLabel = new JLabel();
        ImageIcon icon = new ImageIcon("image1.png");
        imageLabel.setIcon(icon);

        createButton.addActionListener(e -> showCreateHotelDialog());
        viewButton.addActionListener(e -> showViewHotelDialog());
        manageButton.addActionListener(e -> showManageHotelDialog());
        simulateButton.addActionListener(e -> showSimulateBookingDialog());
        exitButton.addActionListener(e -> System.exit(0));
        otherButton.addActionListener(e -> showOtherFeaturesDialog());

        createButton.setFont(buttonFont);
        viewButton.setFont(buttonFont);
        manageButton.setFont(buttonFont);
        simulateButton.setFont(buttonFont);
        exitButton.setFont(buttonFont);
        otherButton.setFont(buttonFont);

        panel.add(createButton);
        panel.add(viewButton);
        panel.add(manageButton);
        panel.add(simulateButton);
        panel.add(otherButton);
        panel.add(exitButton);

        frame.setLayout(new BorderLayout());
        frame.add(imageLabel, BorderLayout.WEST);
        frame.add(panel, BorderLayout.EAST);
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH); 
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.setVisible(true);
    }

    public static void showCreateHotelDialog() {
        JTextField hotelNameField = new JTextField();
        JTextField numStandardRoomsField = new JTextField();
        JTextField numDeluxeRoomsField = new JTextField();
        JTextField numExecutiveRoomsField = new JTextField();
        JTextField namingSchemeField = new JTextField();

        int option; 
        int numStandardRooms = 0;
        int numDeluxeRooms = 0;
        int numExecutiveRooms = 0;
        String hotelName;
        String namingScheme;
        boolean validInputs;    

        do {
            Object[] message = {
                "Hotel Name:", hotelNameField,
                "Number of Standard Rooms:", numStandardRoomsField,
                "Number of Deluxe Rooms:", numDeluxeRoomsField,
                "Number of Executive Rooms:", numExecutiveRoomsField,
                "Room Naming Scheme:", namingSchemeField
            };

            option = JOptionPane.showConfirmDialog(null, message, "Create Hotel", JOptionPane.OK_CANCEL_OPTION);
            hotelName = hotelNameField.getText();
            numStandardRooms = Integer.parseInt(numStandardRoomsField.getText());
            numDeluxeRooms = Integer.parseInt(numDeluxeRoomsField.getText());
            numExecutiveRooms = Integer.parseInt(numExecutiveRoomsField.getText());
            namingScheme = namingSchemeField.getText();
            validInputs = true;

            /* For Input Checking: */
            if (option == JOptionPane.OK_OPTION) {
                String[] warning = {
                    "Invalid Input/s."
                };
            
                if (!HRS.compareHotelName(hotelName, HRS.getHotelList())) {
                    validInputs = false;
                    warning = Arrays.copyOf(warning, warning.length + 1);
                    warning[warning.length - 1] = "Hotel name '" + hotelName + "' is not unique.";
                }
            
                if (numStandardRooms + numDeluxeRooms + numExecutiveRooms < 1 || 50 < numStandardRooms + numDeluxeRooms + numExecutiveRooms) {
                    validInputs = false;
                    warning = Arrays.copyOf(warning, warning.length + 1);
                    warning[warning.length - 1] = "The total number of rooms must be less than or equal to 50.";
                }
                
                if (!validInputs){
                    JOptionPane.showMessageDialog(null, warning);
                }
            }

        } while (!validInputs && option == JOptionPane.OK_OPTION);
        
        if (option == JOptionPane.OK_OPTION) {
            HRS.createHotel(hotelName);
            HRS.getHotelList().get(HRS.getHotelList().size() - 1).createRooms(numStandardRooms, numDeluxeRooms, numExecutiveRooms, namingScheme);
            
            JDialog dialog = new JDialog();
            dialog.setTitle(hotelName + " - Hotel Information");
            dialog.setModal(true);
            dialog.setSize(1090, 690);
            dialog.setLocationRelativeTo(null);
    
            ImageIcon backgroundImage = new ImageIcon("image2.png");
            JLabel backgroundLabel = new JLabel(backgroundImage);
    
            dialog.setLayout(new BorderLayout());
            dialog.add(backgroundLabel, BorderLayout.CENTER);
    
            JPanel labelPanel = new JPanel();
            labelPanel.setLayout(null);
            labelPanel.setOpaque(false); 
    
            JLabel label1 = new JLabel("" + numStandardRooms);
            label1.setBounds(195, 470, 100, 30);
            label1.setFont(new Font("Comic Sans", Font.BOLD, 21));
            label1.setForeground(Color.BLACK); 
            labelPanel.add(label1);
            JLabel label2 = new JLabel("" + numDeluxeRooms);
            label2.setBounds(510, 470, 100, 30);
            label2.setFont(new Font("Comic Sans", Font.BOLD, 21));
            label2.setForeground(Color.BLACK); 
            labelPanel.add(label2);
            JLabel label3 = new JLabel("" + numExecutiveRooms);
            label3.setBounds(835, 470, 100, 30);
            label3.setFont(new Font("Comic Sans", Font.BOLD, 21));
            label3.setForeground(Color.BLACK); 
            labelPanel.add(label3);

            JLabel label4 = new JLabel(hotelName);
            label4.setBounds(150, 590, 600, 30);
            label4.setFont(new Font("Comic Sans", Font.BOLD, 20));
            label4.setForeground(Color. WHITE); 
            labelPanel.add(label4);
    
            JButton backButton = new JButton("Okay");
            backButton.addActionListener(e -> dialog.dispose());
            backButton.setBounds(795, 580, 100, 50);
            backButton.setFont(new Font("Comic Sans", Font.BOLD, 21));
            backButton.setForeground(Color.BLACK);
            backButton.setBackground(Color.WHITE);
            labelPanel.add(backButton);
    
            backgroundLabel.setLayout(new BorderLayout());
            backgroundLabel.add(labelPanel, BorderLayout.CENTER);
    
            dialog.setVisible(true);
        }
    }

    public static void showViewHotelDialog() {
        JDialog dialog = new JDialog();
        dialog.setTitle("Choose a Hotel");
        dialog.setModal(true);
        dialog.setLayout(new BorderLayout());
        dialog.setSize(500, 400);

        JButton backButton = new JButton("Back");
        backButton.addActionListener(e -> dialog.dispose());
        
        JPanel panel = new JPanel();
        JPanel backPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        panel.setLayout(new GridLayout(HRS.getHotelList().size(), 1));
        backPanel.add(backButton);

        if(HRS.getHotelList().isEmpty()){
            String[] noHotel = {"There are no hotels in the system."};
            JOptionPane.showMessageDialog(null, noHotel);
        } else {
            for (Hotel hotel : HRS.getHotelList()) {
                JButton hotelButton = new JButton(hotel.getHotelName());
                hotelButton.addActionListener(e -> {
                    showViewHotelMenu(hotel);
                    dialog.dispose();
                });
                panel.add(hotelButton);
            }
            dialog.add(panel, BorderLayout.CENTER);
            dialog.add(backPanel, BorderLayout.SOUTH);
            dialog.setLocationRelativeTo(null);
            dialog.setVisible(true);
        }
    }
    
    public static void showViewHotelMenu(Hotel hotel) {
        JDialog dialog = new JDialog();
        dialog.setTitle("Choose a Level of Information");
        dialog.setModal(true);
        dialog.setLayout(new BorderLayout());
        dialog.setSize(500, 400);

        JButton backButton = new JButton("Back");
        backButton.addActionListener(e -> dialog.dispose());
        
        JPanel panel = new JPanel();
        JPanel backPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        panel.setLayout(new GridLayout(4, 1));
        backPanel.add(backButton);

        JButton highInfoButton = new JButton("High-level Hotel Information");
        JButton lowInfo1Button = new JButton("Total number of rooms for a selected date (Low-level Hotel Information)");
        JButton lowInfo2Button = new JButton("Room Low-level Information");
        JButton lowInfo3Button = new JButton("Reservation Low-level Information");

        panel.add(highInfoButton);
        panel.add(lowInfo1Button);
        panel.add(lowInfo2Button);
        panel.add(lowInfo3Button);

        highInfoButton.addActionListener(e -> HRS.updateViewHotelInfo(hotel));
        lowInfo1Button.addActionListener(e -> HRS.updateViewNumberOfRoomsInfo(hotel));
        lowInfo2Button.addActionListener(e -> showRoomList(hotel));
        lowInfo3Button.addActionListener(e -> showReservationList(hotel));
        backButton.addActionListener(e -> dialog.dispose());
        
        dialog.add(panel, BorderLayout.CENTER);
        dialog.add(backPanel, BorderLayout.SOUTH);
        dialog.setLocationRelativeTo(null);
        dialog.setVisible(true);
    }

    public static void showRoomList(Hotel hotel) {
        JDialog dialog = new JDialog();
        dialog.setTitle("Choose a room below");
        dialog.setModal(true);
        dialog.setLayout(new BorderLayout());
        dialog.setSize(1200, 700);

        JPanel roomPanel = new JPanel(new GridLayout(5, 10, 10, 10));
        JPanel backPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));

        JButton backButton = new JButton("Back");
        backButton.addActionListener(e -> dialog.dispose());
        backPanel.add(backButton);

        if(hotel.getRoomList().isEmpty()){
            String[] noRooms = {"There are no rooms in the hotel."};
            JOptionPane.showMessageDialog(null, noRooms);
        } else {        
            for (Room room : hotel.getRoomList()) {
                JButton roomButton = new JButton(room.getRoomName());
                roomButton.addActionListener(e -> {
                    HRS.updateViewRoomInfo(room);
                    hotel.showRoomAvailability(room);
                });
                roomPanel.add(roomButton);
            }
            dialog.add(roomPanel, BorderLayout.CENTER);
            dialog.add(backPanel, BorderLayout.SOUTH);
            dialog.setLocationRelativeTo(null);
            dialog.setVisible(true);
        }
    }

    public static void showReservationList(Hotel hotel) {
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

        if (hotel.getReservationList().size() == 0){
            String[] noReservation = {
                hotel.getHotelName() + " has no reservations yet."
            };
            JOptionPane.showMessageDialog(null, noReservation);
        } else {
            for (Reservation reservation : hotel.getReservationList()) {
                JButton reservationButton = new JButton(reservation.getRoom().getRoomName() + " reserved by " + reservation.getGuestName());
                reservationButton.addActionListener(e -> {
                    HRS.updateViewReservationInfo(reservation);
                    HRS.updateViewBreakdownCostInfo(hotel, reservation);
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
     * Manages a specific hotel based on the chosen configuration.
     */
    public static void showManageHotelDialog() {
        JDialog dialog = new JDialog();
        dialog.setTitle("Manage Hotel");
        dialog.setModal(true);
        dialog.setLayout(new BorderLayout());
        dialog.setSize(500, 400);

        JButton backButton = new JButton("Back");
        backButton.addActionListener(e -> dialog.dispose());
        
        JPanel panel = new JPanel();
        JPanel backPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        panel.setLayout(new GridLayout(HRS.getHotelList().size(), 1));
        backPanel.add(backButton);

        if(HRS.getHotelList().isEmpty()){
            String[] noHotel = {"There are no hotels in the system."};
            JOptionPane.showMessageDialog(null, noHotel);
        } else {
            for (Hotel hotel : HRS.getHotelList()) {
                JButton hotelButton = new JButton(hotel.getHotelName());
                hotelButton.addActionListener(e -> {
                    HRS.showManageHotelMenu(hotel);
                    dialog.dispose();
                });
                panel.add(hotelButton);
            }
    
            dialog.add(panel, BorderLayout.CENTER);
            dialog.add(backPanel, BorderLayout.SOUTH);
            dialog.setLocationRelativeTo(null);
            dialog.setVisible(true);
        }
    }

    /**
     * Simulates a booking for a specific hotel.
     */
    public static void showSimulateBookingDialog(){
        JDialog dialog = new JDialog();
        dialog.setTitle("Choose a Hotel");
        dialog.setModal(true);
        dialog.setLayout(new BorderLayout());
        dialog.setSize(500, 400);

        JButton hotelButton;
        JButton backButton = new JButton("Back");
        backButton.addActionListener(e -> dialog.dispose());
        
        JPanel panel = new JPanel();
        JPanel backPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        panel.setLayout(new GridLayout(HRS.getHotelList().size(), 1));
        backPanel.add(backButton);

        if(HRS.getHotelList().isEmpty()){
            String[] noHotel = {"There are no hotels in the system."};
            JOptionPane.showMessageDialog(null, noHotel);
        } else {
            for (Hotel hotel : HRS.getHotelList()) {
                hotelButton = new JButton(hotel.getHotelName());
                hotelButton.addActionListener(e -> {
                    HRS.askReservationDetails(hotel);
                    dialog.dispose();
                });
                panel.add(hotelButton);
            }

            dialog.add(panel, BorderLayout.CENTER);
            dialog.add(backPanel, BorderLayout.SOUTH);
            dialog.setLocationRelativeTo(null);
            dialog.setVisible(true);
        }
    }

    public static void showOtherFeaturesDialog(){
        JDialog dialog = new JDialog();
        dialog.setTitle("Other Features");
        dialog.setModal(true);
        dialog.setLayout(new BorderLayout());
        dialog.setSize(500, 400);

        JButton backButton = new JButton("Back");
        backButton.addActionListener(e -> dialog.dispose());
        
        JPanel panel = new JPanel();
        JPanel backPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        panel.setLayout(new GridLayout(3, 1));
        backPanel.add(backButton);

        JButton button2 = new JButton("Customizable Packages");
        button2.addActionListener(e -> {
            dialog.dispose();
            HRS.showAvailableHotel("customize");
        });
        panel.add(button2);
        JButton button3 = new JButton("View Package of a Reservation");
        button3.addActionListener(e -> {
            dialog.dispose();
            HRS.showAvailableHotel("view");
        });
        panel.add(button3);
        JButton button1 = new JButton("Send Request/Inquiry");
        button1.addActionListener(e -> {
            dialog.dispose();
            showSendRequestDialog(selectGuest(selectHotel()));
        });
        panel.add(button1);

        dialog.add(panel, BorderLayout.CENTER);
        dialog.add(backPanel, BorderLayout.SOUTH);
        dialog.setLocationRelativeTo(null);
        dialog.setVisible(true);
    }

    public static Hotel selectHotel(){
        ArrayList<Hotel> selectedHotel = new ArrayList<Hotel>();

        JDialog dialog = new JDialog();
        dialog.setTitle("Choose a Hotel");
        dialog.setModal(true);
        dialog.setLayout(new BorderLayout());
        dialog.setSize(500, 400);

        JButton hotelButton;
        JButton backButton = new JButton("Back");
        backButton.addActionListener(e -> dialog.dispose());
        
        JPanel panel = new JPanel();
        JPanel backPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        panel.setLayout(new GridLayout(HRS.getHotelList().size(), 1));
        backPanel.add(backButton);

        if(HRS.getHotelList().isEmpty()){
            String[] noHotel = {"There are no hotels in the system."};
            JOptionPane.showMessageDialog(null, noHotel);
        } else {
            for (Hotel hotel : HRS.getHotelList()) {
                hotelButton = new JButton(hotel.getHotelName());
                hotelButton.addActionListener(e -> {
                    selectedHotel.add(hotel);
                    dialog.dispose();
                });
                panel.add(hotelButton);
            }

            dialog.add(panel, BorderLayout.CENTER);
            dialog.add(backPanel, BorderLayout.SOUTH);
            dialog.setLocationRelativeTo(null);
            dialog.setVisible(true);
        }

        return selectedHotel.get(0);
    }

    public static String selectGuest(Hotel hotel){
        ArrayList<String> guestName = new ArrayList<String>();

        JDialog dialog = new JDialog();
        dialog.setTitle("Choose a Reservation");
        dialog.setModal(true);
        dialog.setLayout(new BorderLayout());
        dialog.setSize(1090, 690);

        JButton hotelButton;
        JButton backButton = new JButton("Back");
        backButton.addActionListener(e -> dialog.dispose());
        
        JPanel panel = new JPanel();
        JPanel backPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        panel.setLayout(new GridLayout(5, hotel.getReservationList().size()));
        backPanel.add(backButton);

        if(hotel.getReservationList().isEmpty()){
            String[] noHotel = {"There are no reservations in the hotel."};
            JOptionPane.showMessageDialog(null, noHotel);
        } else {
            for (Reservation reservation : hotel.getReservationList()) {
                hotelButton = new JButton(reservation.getRoom().getRoomName() + " - " + reservation.getGuestName());
                hotelButton.addActionListener(e -> {
                    guestName.add(reservation.getGuestName());
                    dialog.dispose();
                });
                panel.add(hotelButton);
            }

            dialog.add(panel, BorderLayout.CENTER);
            dialog.add(backPanel, BorderLayout.SOUTH);
            dialog.setLocationRelativeTo(null);
            dialog.setVisible(true);
        }

        return guestName.get(0);
    }

    public static void showSendRequestDialog(String guestName) {
        JDialog dialog = new JDialog();
        dialog.setTitle("Select Type of Request/s or Concern/s");
        dialog.setSize(1090, 690);
        dialog.setLayout(new GridLayout(4, 1, 10, 10));

        JPanel APanel = new JPanel();
        APanel.setBorder(BorderFactory.createTitledBorder("Room Services"));
        JCheckBox aa = new JCheckBox("Room Cleaning");
        JCheckBox ab = new JCheckBox("Extra Towels");
        JCheckBox ac = new JCheckBox("Report Malfunctioning Air Conditioner");
        APanel.add(aa);
        APanel.add(ab);
        APanel.add(ac);

        JPanel BPanel = new JPanel();
        BPanel.setBorder(BorderFactory.createTitledBorder("Concierge Services"));
        JCheckBox ba = new JCheckBox("Inquire about Tourist Attractions");
        JCheckBox bb = new JCheckBox("Book Tickets for Local Events");
        JCheckBox bc = new JCheckBox("Make a Reservation at a Restaurant");
        BPanel.add(ba);
        BPanel.add(bb);
        BPanel.add(bc);

        JPanel CPanel = new JPanel();
        CPanel.setBorder(BorderFactory.createTitledBorder("Facilities"));
        JCheckBox ca = new JCheckBox("Ask Gym Hours");
        JCheckBox cb = new JCheckBox("Sign up for Hotel-Organized Activities");
        JCheckBox cc = new JCheckBox("Inquire Pool Availability");
        CPanel.add(ca);
        CPanel.add(cb);
        CPanel.add(cc);

        JPanel buttonPanel = new JPanel();
        JButton okButton = new JButton("OK");
        okButton.addActionListener(e -> {
            ArrayList<String> selectedOptions = new ArrayList<>();
            if (aa.isSelected()){
                selectedOptions.add("Room Cleaning");
            } 
            if (ab.isSelected()){
                selectedOptions.add("Extra Towels");
            } 
            if (ac.isSelected()){
                selectedOptions.add("Report Malfunctioning Air Conditioner");
            } 
            if (ba.isSelected()){
                selectedOptions.add("Inquire about Tourist Attractions");
            } 
            if (bb.isSelected()){
                selectedOptions.add("Book Tickets for Local Events");
            } 
            if (bc.isSelected()){
                selectedOptions.add("Make a Reservation at a Restaurant");
            } 
            if (ca.isSelected()){
                selectedOptions.add("Ask Gym Hours");
            } 
            if (cb.isSelected()){
                selectedOptions.add("Sign up for Hotel-Organized Activities");
            }
            if (cc.isSelected()){
                selectedOptions.add("Inquire Pool Availability");
            } 

            String message = "Dear " + guestName + ",\n\n"
                       + "Thank you for reaching out to us. We have received your request/concern and want to assure you that it is our top priority. Our team is already \nworking on addressing it to ensure your stay with us remains exceptional.\n\n"
                       + "We appreciate your patience and understanding. Should you need further assistance or have additional requests, please do not hesitate to contact us.\n\n"
                       + "Warm regards,\n"
                       + "Hotel Management Team";

            if(!selectedOptions.isEmpty()){
                JOptionPane.showMessageDialog(dialog, message);
            }

            dialog.dispose();
        });
        buttonPanel.add(okButton);

        dialog.add(APanel);
        dialog.add(BPanel);
        dialog.add(CPanel);
        dialog.add(buttonPanel);
        dialog.setVisible(true);
    }
}