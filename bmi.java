import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.sql.*;

public class bmi extends JFrame {
    private JLabel bmiIdLabel, userIdLabel, heightLabel, weightLabel, bmiValLabel;
    private JTextField bmiIdField, userIdField, heightField, weightField, bmiValField;
    private JButton insertButton, modifyButton, deleteButton;
    private JTextArea displayArea;
    private JScrollPane scrollPane;

    public bmi() {
        setTitle("bmi");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridBagLayout());

        initComponents();
        addComponents();
        attachListeners();

        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void initComponents() {
        bmiIdLabel = new JLabel("BMI ID:");
        userIdLabel = new JLabel("User ID:");
        heightLabel = new JLabel("Height (cm):");
        weightLabel = new JLabel("Weight (kg):");
        bmiValLabel = new JLabel("BMI Value:");

        bmiIdField = new JTextField(10);
        userIdField = new JTextField(10);
        heightField = new JTextField(10);
        weightField = new JTextField(10);
        bmiValField = new JTextField(10);
        bmiValField.setEditable(false);

        insertButton = new JButton("Insert");
        modifyButton = new JButton("Modify");
        deleteButton = new JButton("Delete");

        displayArea = new JTextArea(10, 30);
        displayArea.setEditable(false);
        scrollPane = new JScrollPane(displayArea);
    }

    private void addComponents() {
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.gridx = 0;
        constraints.gridy = 0;
        constraints.anchor = GridBagConstraints.LINE_END;
        constraints.insets = new Insets(5, 5, 5, 5);
        add(bmiIdLabel, constraints);

        constraints.gridx = 1;
        constraints.gridy = 0;
        constraints.anchor = GridBagConstraints.LINE_START;
        add(bmiIdField, constraints);

        constraints.gridx = 0;
        constraints.gridy = 1;
        constraints.anchor = GridBagConstraints.LINE_END;
        add(userIdLabel, constraints);

        constraints.gridx = 1;
        constraints.gridy = 1;
        constraints.anchor = GridBagConstraints.LINE_START;
        add(userIdField, constraints);

        constraints.gridx = 0;
        constraints.gridy = 2;
        constraints.anchor = GridBagConstraints.LINE_END;
        add(heightLabel, constraints);

        constraints.gridx = 1;
        constraints.gridy = 2;
        constraints.anchor = GridBagConstraints.LINE_START;
        add(heightField, constraints);

        constraints.gridx = 0;
        constraints.gridy = 3;
        constraints.anchor = GridBagConstraints.LINE_END;
        add(weightLabel, constraints);

        constraints.gridx = 1;
        constraints.gridy = 3;
        constraints.anchor = GridBagConstraints.LINE_START;
        add(weightField, constraints);

        constraints.gridx = 0;
        constraints.gridy = 4;
        constraints.anchor = GridBagConstraints.LINE_END;
        add(bmiValLabel, constraints);

        constraints.gridx = 1;
        constraints.gridy = 4;
        constraints.anchor = GridBagConstraints.LINE_START;
        add(bmiValField, constraints);

        constraints.gridx = 0;
        constraints.gridy = 5;
        constraints.gridwidth = 2;
        constraints.anchor = GridBagConstraints.CENTER;
        add(insertButton, constraints);

        constraints.gridx = 0;
        constraints.gridy = 6;
        constraints.anchor = GridBagConstraints.LINE_END;
        add(modifyButton, constraints);

        constraints.gridx = 1;
        constraints.gridy = 6;
        constraints.anchor = GridBagConstraints.LINE_START;
        add(deleteButton, constraints);

        constraints.gridx = 0;
        constraints.gridy = 7;
        constraints.gridwidth = 2;
        constraints.anchor = GridBagConstraints.CENTER;
        constraints.insets = new Insets(20, 5, 5, 5);
        add(scrollPane, constraints);
    }

    private void attachListeners() {
        insertButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                insertData();
            }
        });

        modifyButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                modifyData();
            }
        });

        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                deleteData();
            }
        });
    }

    private void insertData() {
        try {
            Connection connection = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe", "srikruth", "srikruth");

            int bmiId = Integer.parseInt(bmiIdField.getText());
            int userId = Integer.parseInt(userIdField.getText());
            double height = Double.parseDouble(heightField.getText());
            double weight = Double.parseDouble(weightField.getText());

            double bmiVal = calculateBMI(height, weight);

            String query = "INSERT INTO bmi (bmi_id, user_id, height, weight, bmi_val) VALUES (?, ?, ?, ?, ?)";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, bmiId);
            statement.setInt(2, userId);
            statement.setDouble(3, height);
            statement.setDouble(4, weight);
            statement.setDouble(5, bmiVal);

            int rowsAffected = statement.executeUpdate();

            if (rowsAffected > 0) {
                JOptionPane.showMessageDialog(null, "Data inserted successfully");
                clearFields();
            } else {
                JOptionPane.showMessageDialog(null, "Failed to insert data");
            }

            connection.close();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error: " + ex.getMessage());
        }
    }

    private void modifyData() {
        try {
            Connection connection = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe", "srikruth", "srikruth");

            int bmiId = Integer.parseInt(bmiIdField.getText());
            int userId = Integer.parseInt(userIdField.getText());
            double height = Double.parseDouble(heightField.getText());
            double weight = Double.parseDouble(weightField.getText());

            double bmiVal = calculateBMI(height, weight);

            String query = "UPDATE bmi SET user_id = ?, height = ?, weight = ?, bmi_val = ? WHERE bmi_id = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, userId);
            statement.setDouble(2, height);
            statement.setDouble(3, weight);
            statement.setDouble(4, bmiVal);
            statement.setInt(5, bmiId);

            int rowsAffected = statement.executeUpdate();

            if (rowsAffected > 0) {
                JOptionPane.showMessageDialog(null, "Data modified successfully");
                clearFields();
            } else {
                JOptionPane.showMessageDialog(null, "Failed to modify data");
            }

            connection.close();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error: " + ex.getMessage());
        }
    }

    private void deleteData() {
        try {
            Connection connection = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe", "srikruth", "srikruth");

            int bmiId = Integer.parseInt(bmiIdField.getText());

            String query = "DELETE FROM bmi WHERE bmi_id = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, bmiId);

            int rowsAffected = statement.executeUpdate();

            if (rowsAffected > 0) {
                JOptionPane.showMessageDialog(null, "Data deleted successfully");
                clearFields();
            } else {
                JOptionPane.showMessageDialog(null, "Failed to delete data");
            }

            connection.close();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error: " + ex.getMessage());
        }
    }

    private void clearFields() {
        bmiIdField.setText("");
        userIdField.setText("");
        heightField.setText("");
        weightField.setText("");
        bmiValField.setText("");
    }

    private double calculateBMI(double height, double weight) {
        double heightInMeters = height / 100.0;
        return weight / (heightInMeters * heightInMeters);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new bmi();
            }
        });
    }
}
