import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class healthcon extends JFrame {
    private JLabel healthIdLabel, userIdLabel, bmiValLabel;
    private JTextField healthIdField, userIdField, bmiValField;
    private JButton insertButton, modifyButton, deleteButton;

    public healthcon() {
        setTitle("HealthCon Recommendation System");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new GridBagLayout());

        GridBagConstraints constraints = new GridBagConstraints();
        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.insets = new Insets(5, 5, 5, 5);

        healthIdLabel = new JLabel("Health ID:");
        constraints.gridx = 0;
        constraints.gridy = 0;
        add(healthIdLabel, constraints);

        healthIdField = new JTextField(10);
        constraints.gridx = 1;
        constraints.gridy = 0;
        add(healthIdField, constraints);

        userIdLabel = new JLabel("User ID:");
        constraints.gridx = 0;
        constraints.gridy = 1;
        add(userIdLabel, constraints);

        userIdField = new JTextField(10);
        constraints.gridx = 1;
        constraints.gridy = 1;
        add(userIdField, constraints);

        bmiValLabel = new JLabel("BMI Value:");
        constraints.gridx = 0;
        constraints.gridy = 2;
        add(bmiValLabel, constraints);

        bmiValField = new JTextField(10);
        constraints.gridx = 1;
        constraints.gridy = 2;
        add(bmiValField, constraints);

        insertButton = new JButton("Insert");
        constraints.gridx = 0;
        constraints.gridy = 3;
        constraints.gridwidth = 2;
        constraints.anchor = GridBagConstraints.CENTER;
        add(insertButton, constraints);

        modifyButton = new JButton("Modify");
        constraints.gridy = 4;
        add(modifyButton, constraints);

        deleteButton = new JButton("Delete");
        constraints.gridy = 5;
        add(deleteButton, constraints);

        attachListeners();

        setVisible(true);
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

            int healthId = Integer.parseInt(healthIdField.getText());
            int userId = Integer.parseInt(userIdField.getText());

            String bmiQuery = "SELECT bmi_val FROM bmi WHERE user_id = ?";
            PreparedStatement bmiStatement = connection.prepareStatement(bmiQuery);
            bmiStatement.setInt(1, userId);
            ResultSet bmiResult = bmiStatement.executeQuery();

            double bmiVal = 0.0;
            if (bmiResult.next()) {
                bmiVal = bmiResult.getDouble("bmi_val");
            } else {
                JOptionPane.showMessageDialog(null, "No BMI value found for the provided user ID");
                return;
            }

            String query = "INSERT INTO healthcon (health_id, user_id, bmi_val) VALUES (?, ?, ?)";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, healthId);
            statement.setInt(2, userId);
            statement.setDouble(3, bmiVal);

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
        // Implement code to modify data in the healthcon table
    }

    private void deleteData() {
        // Implement code to delete data from the healthcon table
    }

    private void clearFields() {
        healthIdField.setText("");
        userIdField.setText("");
        bmiValField.setText("");
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new healthcon();
            }
        });
    }
}
