import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class exercise extends JFrame {
    private JLabel lblExerciseId, lblHealthId, lblExerciseType, lblDuration;
    private JTextField txtExerciseId, txtHealthId, txtExerciseType, txtDuration;
    private JButton btnInsert, btnModify, btnDelete, btnDisplay;
    private GridBagLayout layout;
    private GridBagConstraints constraints;

    // Database connection parameters
    private String url = "jdbc:oracle:thin:@localhost:1521:xe";
    private String username = "srikruth";
    private String password = "srikruth";

    public exercise() {
        setTitle("exercise");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Initialize components
        lblExerciseId = new JLabel("Exercise ID:");
        lblHealthId = new JLabel("Health ID:");
        lblExerciseType = new JLabel("Exercise Type:");
        lblDuration = new JLabel("Duration(min):");

        txtExerciseId = new JTextField(10);
        txtHealthId = new JTextField(10);
        txtExerciseType = new JTextField(10);
        txtDuration = new JTextField(10);

        btnInsert = new JButton("Insert");
        btnModify = new JButton("Modify");
        btnDelete = new JButton("Delete");
        btnDisplay = new JButton("Display");

        // Set layout and constraints
        layout = new GridBagLayout();
        setLayout(layout);
        constraints = new GridBagConstraints();
        constraints.insets = new Insets(5, 5, 5, 5);

        // Add components to the frame
        addComponent(lblExerciseId, 0, 0, 1, 1);
        addComponent(txtExerciseId, 1, 0, 2, 1);
        addComponent(lblHealthId, 0, 1, 1, 1);
        addComponent(txtHealthId, 1, 1, 2, 1);
        addComponent(lblExerciseType, 0, 2, 1, 1);
        addComponent(txtExerciseType, 1, 2, 2, 1);
        addComponent(lblDuration, 0, 3, 1, 1);
        addComponent(txtDuration, 1, 3, 2, 1);

        addComponent(btnInsert, 0, 4, 1, 1);
        addComponent(btnModify, 1, 4, 1, 1);
        addComponent(btnDelete, 0, 5, 1, 1);
        addComponent(btnDisplay, 1, 5, 1, 1);

        // Button event listeners
        btnInsert.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                insertRecord();
            }
        });

        btnModify.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                modifyRecord();
            }
        });

        btnDelete.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                deleteRecord();
            }
        });

        btnDisplay.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                displayRecords();
            }
        });

        pack();
        setVisible(true);
    }

    private void addComponent(Component component, int gridx, int gridy, int gridwidth, int gridheight) {
        constraints.gridx = gridx;
        constraints.gridy = gridy;
        constraints.gridwidth = gridwidth;
        constraints.gridheight = gridheight;
        layout.setConstraints(component, constraints);
        add(component);
    }

    private void insertRecord() {
        int exerciseId = Integer.parseInt(txtExerciseId.getText());
        int healthId = Integer.parseInt(txtHealthId.getText());
        String exerciseType = txtExerciseType.getText();
        int duration = Integer.parseInt(txtDuration.getText());

        try (Connection conn = DriverManager.getConnection(url, username, password)) {
            String query = "INSERT INTO exercise (exercise_id, health_id, exercise_type, duration) VALUES (?, ?, ?, ?)";
            PreparedStatement statement = conn.prepareStatement(query);
            statement.setInt(1, exerciseId);
            statement.setInt(2, healthId);
            statement.setString(3, exerciseType);
            statement.setInt(4, duration);
            statement.executeUpdate();
            JOptionPane.showMessageDialog(this, "Record inserted successfully!");
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Error inserting record: " + ex.getMessage());
        }
    }

    private void modifyRecord() {
        int exerciseId = Integer.parseInt(txtExerciseId.getText());
        int healthId = Integer.parseInt(txtHealthId.getText());
        String exerciseType = txtExerciseType.getText();
        int duration = Integer.parseInt(txtDuration.getText());

        try (Connection conn = DriverManager.getConnection(url, username, password)) {
            String query = "UPDATE exercise SET health_id = ?, exercise_type = ?, duration = ? WHERE exercise_id = ?";
            PreparedStatement statement = conn.prepareStatement(query);
            statement.setInt(1, healthId);
            statement.setString(2, exerciseType);
            statement.setInt(3, duration);
            statement.setInt(4, exerciseId);
            statement.executeUpdate();
            JOptionPane.showMessageDialog(this, "Record modified successfully!");
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Error modifying record: " + ex.getMessage());
        }
    }

    private void deleteRecord() {
        int exerciseId = Integer.parseInt(txtExerciseId.getText());

        try (Connection conn = DriverManager.getConnection(url, username, password)) {
            String query = "DELETE FROM exercise WHERE exercise_id = ?";
            PreparedStatement statement = conn.prepareStatement(query);
            statement.setInt(1, exerciseId);
            statement.executeUpdate();
            JOptionPane.showMessageDialog(this, "Record deleted successfully!");
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Error deleting record: " + ex.getMessage());
        }
    }

    private void displayRecords() {
        try (Connection conn = DriverManager.getConnection(url, username, password)) {
            String query = "SELECT * FROM exercise";
            Statement statement = conn.createStatement();
            ResultSet resultSet = statement.executeQuery(query);

            StringBuilder output = new StringBuilder();
            output.append("Exercise ID\tHealth ID\tExercise Type\tDuration\n");
            while (resultSet.next()) {
                int exerciseId = resultSet.getInt("exercise_id");
                int healthId = resultSet.getInt("health_id");
                String exerciseType = resultSet.getString("exercise_type");
                int duration = resultSet.getInt("duration");
                output.append(exerciseId).append("\t\t").append(healthId).append("\t\t").append(exerciseType)
                        .append("\t\t").append(duration).append("\n");
            }

            JOptionPane.showMessageDialog(this, output.toString());
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Error displaying records: " + ex.getMessage());
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new exercise();
            }
        });
    }
}
