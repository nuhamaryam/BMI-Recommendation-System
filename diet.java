import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class diet extends JFrame {
    private JTextField dietIdTextField, healthIdTextField, foodsTextField, calorieIntakeTextField;
    private JButton insertButton, modifyButton, deleteButton, displayButton;

    public diet() {
        setTitle("diet");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridBagLayout());

        GridBagConstraints constraints = new GridBagConstraints();
        constraints.gridx = 0;
        constraints.gridy = 0;
        constraints.insets = new Insets(5, 5, 5, 5);

        JLabel dietIdLabel = new JLabel("Diet ID:");
        add(dietIdLabel, constraints);

        dietIdTextField = new JTextField(10);
        constraints.gridx = 1;
        add(dietIdTextField, constraints);

        JLabel healthIdLabel = new JLabel("Health ID:");
        constraints.gridx = 0;
        constraints.gridy = 1;
        add(healthIdLabel, constraints);

        healthIdTextField = new JTextField(10);
        constraints.gridx = 1;
        add(healthIdTextField, constraints);

        JLabel foodsLabel = new JLabel("Recommended Foods:");
        constraints.gridx = 0;
        constraints.gridy = 2;
        add(foodsLabel, constraints);

        foodsTextField = new JTextField(20);
        constraints.gridx = 1;
        add(foodsTextField, constraints);

        JLabel calorieIntakeLabel = new JLabel("Calorie Intake:");
        constraints.gridx = 0;
        constraints.gridy = 3;
        add(calorieIntakeLabel, constraints);

        calorieIntakeTextField = new JTextField(10);
        constraints.gridx = 1;
        add(calorieIntakeTextField, constraints);

        insertButton = new JButton("Insert");
        constraints.gridx = 0;
        constraints.gridy = 4;
        constraints.gridwidth = 2;
        add(insertButton, constraints);

        modifyButton = new JButton("Modify");
        constraints.gridy = 5;
        add(modifyButton, constraints);

        deleteButton = new JButton("Delete");
        constraints.gridy = 6;
        add(deleteButton, constraints);

        displayButton = new JButton("Display");
        constraints.gridy = 7;
        add(displayButton, constraints);

        pack();
        setLocationRelativeTo(null);

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

        displayButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                displayData();
            }
        });
    }

    private void insertData() {
        try {
            String dietId = dietIdTextField.getText();
            String healthId = healthIdTextField.getText();
            String foods = foodsTextField.getText();
            int calorieIntake = Integer.parseInt(calorieIntakeTextField.getText());

            // Perform JDBC insert operation using the input values
            // Code to connect to the database, prepare the statement, and execute the insert query

            // Example code for MySQL database
            Connection connection = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe", "srikruth", "srikruth");
            String insertQuery = "INSERT INTO diet (diet_id, health_id, recommended_foods, calorie_intake) VALUES (?, ?, ?, ?)";
            PreparedStatement preparedStatement = connection.prepareStatement(insertQuery);
            preparedStatement.setString(1, dietId);
            preparedStatement.setString(2, healthId);
            preparedStatement.setString(3, foods);
            preparedStatement.setInt(4, calorieIntake);
            preparedStatement.executeUpdate();

            JOptionPane.showMessageDialog(this, "Data inserted successfully.");

            preparedStatement.close();
            connection.close();
        } catch (SQLException | NumberFormatException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error inserting data: " + ex.getMessage());
        }
    }

    private void modifyData() {
        // Similar to insertData(), implement modification logic here
    }

    private void deleteData() {
        // Similar to insertData(), implement deletion logic here
    }

    private void displayData() {
        // Retrieve and display the data from the database using JDBC
        // Code to connect to the database, execute the select query, and display the results

        try {
            // Example code for MySQL database
            Connection connection = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe", "srikruth", "srikruth");
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM diet");

            StringBuilder data = new StringBuilder();

            while (resultSet.next()) {
                String dietId = resultSet.getString("diet_id");
                String healthId = resultSet.getString("health_id");
                String foods = resultSet.getString("recommended_foods");
                int calorieIntake = resultSet.getInt("calorie_intake");

                data.append("Diet ID: ").append(dietId).append(", Health ID: ").append(healthId)
                        .append(", Recommended Foods: ").append(foods).append(", Calorie Intake: ").append(calorieIntake)
                        .append("\n");
            }

            if (data.length() > 0) {
                JOptionPane.showMessageDialog(this, data.toString());
            } else {
                JOptionPane.showMessageDialog(this, "No data found.");
            }

            resultSet.close();
            statement.close();
            connection.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error displaying data: " + ex.getMessage());
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new diet().setVisible(true);
            }
        });
    }
}
