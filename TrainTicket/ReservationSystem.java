import javax.swing.*;
import java.awt.*;
import java.util.HashMap;
import java.util.Map;

class ReservationSystem extends JFrame {
    JComboBox<String> sourceDropdown, destinationDropdown, trainDropdown;
    JTextField dateField;
    JComboBox<String> classType;
    JButton bookButton, cancelButton;

    
    Map<String, String[]> trainRoutes;

    public ReservationSystem() {
        setTitle("Reservation System");
        setSize(500, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridLayout(7, 2, 5, 5)); 

        
        initializeTrainRoutes();

        
        add(new JLabel("Source Station:"));
        String[] cities = {"Delhi", "Mumbai", "Chennai", "Kolkata", "Bangalore", "Hyderabad", "Pune"};
        sourceDropdown = new JComboBox<>(cities);
        sourceDropdown.setPreferredSize(new Dimension(150, 20)); 
        add(sourceDropdown);

        
        add(new JLabel("Destination Station:"));
        destinationDropdown = new JComboBox<>(cities);
        destinationDropdown.setPreferredSize(new Dimension(150, 20)); 
        add(destinationDropdown);

        
        add(new JLabel("Available Trains:"));
        trainDropdown = new JComboBox<>();
        trainDropdown.setEnabled(false);
        trainDropdown.setPreferredSize(new Dimension(150, 20)); 
        add(trainDropdown);

        
        add(new JLabel("Date of Journey:"));
        dateField = new JTextField("YYYY-MM-DD");
        dateField.setPreferredSize(new Dimension(150, 20)); 
        add(dateField);

        
        add(new JLabel("Class Type:"));
        classType = new JComboBox<>(new String[]{"Sleeper", "AC 3-Tier", "AC 2-Tier", "AC 1-Tier"});
        classType.setPreferredSize(new Dimension(150, 20)); 
        add(classType);

        
        bookButton = new JButton("Book Ticket");
        bookButton.setPreferredSize(new Dimension(120, 25)); 
        add(bookButton);

        cancelButton = new JButton("Cancel Ticket");
        cancelButton.setPreferredSize(new Dimension(120, 25));
        cancelButton.setEnabled(false); 
        add(cancelButton);

        
        sourceDropdown.addActionListener(e -> updateTrainList());
        destinationDropdown.addActionListener(e -> updateTrainList());
        bookButton.addActionListener(e -> bookTicket());
        cancelButton.addActionListener(e -> cancelTicket());

        setVisible(true); 
    }

    private void initializeTrainRoutes() {
        trainRoutes = new HashMap<>();
        trainRoutes.put("Delhi-Mumbai", new String[]{"Rajdhani Express", "Duronto Express"});
        trainRoutes.put("Mumbai-Chennai", new String[]{"Shatabdi Express", "Garib Rath Express"});
        trainRoutes.put("Chennai-Kolkata", new String[]{"Coromandel Express", "Howrah Express"});
        trainRoutes.put("Kolkata-Bangalore", new String[]{"Humsafar Express", "Yeshwantpur Express"});
        trainRoutes.put("Hyderabad-Pune", new String[]{"Deccan Queen", "Shiv Ganga Express"});
        
    }

    private void updateTrainList() {
        String source = (String) sourceDropdown.getSelectedItem();
        String destination = (String) destinationDropdown.getSelectedItem();

        if (source != null && destination != null && !source.equals(destination)) {
            String routeKey = source + "-" + destination;
            String reverseRouteKey = destination + "-" + source;

            // Clear previous train list
            trainDropdown.removeAllItems();

            if (trainRoutes.containsKey(routeKey)) {
                for (String train : trainRoutes.get(routeKey)) {
                    trainDropdown.addItem(train);
                }
            } else if (trainRoutes.containsKey(reverseRouteKey)) {
                for (String train : trainRoutes.get(reverseRouteKey)) {
                    trainDropdown.addItem(train);
                }
            } else {
                trainDropdown.addItem("No Trains Available");
            }

            trainDropdown.setEnabled(true);
        } else {
            trainDropdown.removeAllItems();
            trainDropdown.addItem("Invalid Selection");
            trainDropdown.setEnabled(false);
        }
    }

    private void bookTicket() {
        String source = (String) sourceDropdown.getSelectedItem();
        String destination = (String) destinationDropdown.getSelectedItem();
        String trainName = (String) trainDropdown.getSelectedItem();
        String date = dateField.getText();
        String selectedClass = (String) classType.getSelectedItem();

        if (source == null || destination == null || trainName == null || trainName.equals("No Trains Available") ||
            date.isEmpty() || source.equals(destination)) {
            JOptionPane.showMessageDialog(this, "Please fill all fields correctly!");
            return;
        }

        JOptionPane.showMessageDialog(this,
            "Ticket Booked Successfully!\n\nDetails:\n" +
            "Train Name: " + trainName + "\n" +
            "Source: " + source + "\n" +
            "Destination: " + destination + "\n" +
            "Date: " + date + "\n" +
            "Class: " + selectedClass
        );

        cancelButton.setEnabled(true); 
    }

    private void cancelTicket() {
        JOptionPane.showMessageDialog(this, "Ticket Cancelled Successfully!");
        cancelButton.setEnabled(false);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(ReservationSystem::new);
    }
}
