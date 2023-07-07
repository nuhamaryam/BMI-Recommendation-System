import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class users extends JFrame {
    private JTextField userIdField, firstNameField, lastNameField, ageField, genderField, emailField, phoneField;
    private JButton insertButton, modifyButton, deleteButton, displayButton;

    public users() {
        setTitle("users");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
        // Create components
        userIdField = new JTextField(10);
        firstNameField = new JTextField(10);
        lastNameField = new JTextField(10);
        ageField = new JTextField(10);
        genderField = new JTextField(10);
        emailField = new JTextField(10);
        phoneField = new JTextField(10);
        insertButton = new JButton("Insert");
        modifyButton = new JButton("Modify");
        deleteButton = new JButton("Delete");
        displayButton = new JButton("Display");

        // Set layout
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);

        // Add components to the layout
        gbc.gridx = 0;
        gbc.gridy = 0;
        add(new JLabel("User ID:"), gbc);

        gbc.gridx = 1;
        add(userIdField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        add(new JLabel("First Name:"), gbc);

        gbc.gridx = 1;
        add(firstNameField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        add(new JLabel("Last Name:"), gbc);

        gbc.gridx = 1;
        add(lastNameField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        add(new JLabel("Age:"), gbc);

        gbc.gridx = 1;
        add(ageField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 4;
        add(new JLabel("Gender:"), gbc);

        gbc.gridx = 1;
        add(genderField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 5;
        add(new JLabel("Email:"), gbc);

        gbc.gridx = 1;
        add(emailField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 6;
        add(new JLabel("Phone:"), gbc);

        gbc.gridx = 1;
        add(phoneField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 7;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        add(insertButton, gbc);

        gbc.gridx = 0;
        gbc.gridy = 8;
        add(modifyButton, gbc);

        gbc.gridx = 1;
        add(deleteButton, gbc);

        gbc.gridx = 0;
        gbc.gridy = 9;
        gbc.gridwidth = 2;
        add(displayButton, gbc);

        // Add action listeners to buttons
        insertButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                insertUser();
            }
        });

        modifyButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                modifyUser();
            }
        });

        deleteButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                deleteUser();
            }
        });

        displayButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                displayUsers();
            }
        });

        pack();
        setLocationRelativeTo(null);
    }

    private void insertUser() {
        String userId = userIdField.getText();
        String firstName = firstNameField.getText();
        String lastName = lastNameField.getText();
        String age = ageField.getText();
        String gender = genderField.getText();
        String email = emailField.getText();
        String phone = phoneField.getText();

        // Perform the database insertion here using JDBC
        try {
            Connection connection = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe", "srikruth", "srikruth");
            PreparedStatement statement = connection.prepareStatement("INSERT INTO users (user_id, first_name, last_name, age, gender, email_address, phone_number) VALUES (?, ?, ?, ?, ?, ?, ?)");
            statement.setString(1, userId);
            statement.setString(2, firstName);
            statement.setString(3, lastName);
            statement.setString(4, age);
            statement.setString(5, gender);
            statement.setString(6, email);
            statement.setString(7, phone);
            statement.executeUpdate();
            JOptionPane.showMessageDialog(this, "User inserted successfully!");
            clearFields();
            connection.close();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Error inserting user: " + ex.getMessage());
        }
    }

    private void modifyUser() {
        String userId = userIdField.getText();
        String firstName = firstNameField.getText();
        String lastName = lastNameField.getText();
        String age = ageField.getText();
        String gender = genderField.getText();
        String email = emailField.getText();
        String phone = phoneField.getText();

        // Perform the database modification here using JDBC
        try {
            Connection connection = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe", "srikruth", "srikruth");
            PreparedStatement statement = connection.prepareStatement("UPDATE users SET first_name = ?, last_name = ?, age = ?, gender = ?, email_address = ?, phone_number = ? WHERE user_id = ?");
            statement.setString(1, firstName);
            statement.setString(2, lastName);
            statement.setString(3, age);
            statement.setString(4, gender);
            statement.setString(5, email);
            statement.setString(6, phone);
            statement.setString(7, userId);
            statement.executeUpdate();
            JOptionPane.showMessageDialog(this, "User modified successfully!");
            clearFields();
            connection.close();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Error modifying user: " + ex.getMessage());
        }
    }

    private void deleteUser() {
        String userId = userIdField.getText();

        // Perform the database deletion here using JDBC
        try {
            Connection connection = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe", "srikruth", "srikruth");
            PreparedStatement statement = connection.prepareStatement("DELETE FROM users WHERE user_id = ?");
            statement.setString(1, userId);
            statement.executeUpdate();
            JOptionPane.showMessageDialog(this, "User deleted successfully!");
            clearFields();
            connection.close();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Error deleting user: " + ex.getMessage());
        }
    }

    private void displayUsers() {
        // Perform the database retrieval here using JDBC
        try {
            Connection connection = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe", "srikruth", "srikruth");
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM users");

            StringBuilder usersText = new StringBuilder();
            while (resultSet.next()) {
                String userId = resultSet.getString("user_id");
                String firstName = resultSet.getString("first_name");
                String lastName = resultSet.getString("last_name");
                String age = resultSet.getString("age");
                String gender = resultSet.getString("gender");
                String email = resultSet.getString("email_address");
                String phone = resultSet.getString("phone_number");

                usersText.append("User ID: ").append(userId).append("\n");
                usersText.append("First Name: ").append(firstName).append("\n");
                usersText.append("Last Name: ").append(lastName).append("\n");
                usersText.append("Age: ").append(age).append("\n");
                usersText.append("Gender: ").append(gender).append("\n");
                usersText.append("Email: ").append(email).append("\n");
                usersText.append("Phone: ").append(phone).append("\n");
                usersText.append("-----------------------\n");
            }

            JOptionPane.showMessageDialog(this, usersText.toString());
            connection.close();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Error displaying users: " + ex.getMessage());
        }
    }

    private void clearFields() {
        userIdField.setText("");
        firstNameField.setText("");
        lastNameField.setText("");
        ageField.setText("");
        genderField.setText("");
        emailField.setText("");
        phoneField.setText("");
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new users().setVisible(true);
            }
        });
    }
}
