import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class homepage {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                createAndShowGUI();
                
            }
        });
    }

    private static void createAndShowGUI() {
        // Create the main frame
        JFrame frame = new JFrame("Java Front End");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
       
        
        // Create the menu bar
        JMenuBar menuBar = new JMenuBar();
        
        // Create the "File" menu
        JMenu fileMenu = new JMenu("File");
        
        // Create the "Users" menu item and its action
        JMenuItem usersMenuItem = new JMenuItem("Users");
        usersMenuItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Perform the action related to the "Users" menu item
                UsersClass.performAction();
                frame.dispose();
                new users();
            }
        });
        
        // Create the "BMI" menu item and its action
        JMenuItem bmiMenuItem = new JMenuItem("BMI");
        bmiMenuItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Perform the action related to the "BMI" menu item
                BMIClass.performAction();
                frame.dispose();
                new bmi();
                
            }
        });
        
        // Create the "HealthCon" menu item and its action
        JMenuItem healthConMenuItem = new JMenuItem("HealthCon");
        healthConMenuItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Perform the action related to the "HealthCon" menu item
                HealthConClass.performAction();
                frame.dispose();
                new healthcon();
            }
        });
        
        // Create the "Diet" menu item and its action
        JMenuItem dietMenuItem = new JMenuItem("Diet");
        dietMenuItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Perform the action related to the "Diet" menu item
                DietClass.performAction();
                frame.dispose();
                new diet();
            }
        });
        
        // Create the "Exercise" menu item and its action
        JMenuItem exerciseMenuItem = new JMenuItem("Exercise");
        exerciseMenuItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Perform the action related to the "Exercise" menu item
                ExerciseClass.performAction();
                frame.dispose();
                new exercise();
            }
        });
        
        // Add the menu items to the "File" menu
        fileMenu.add(usersMenuItem);
        fileMenu.add(bmiMenuItem);
        fileMenu.add(healthConMenuItem);
        fileMenu.add(dietMenuItem);
        fileMenu.add(exerciseMenuItem);
        
        // Add the "File" menu to the menu bar
        menuBar.add(fileMenu);
        
        // Set the menu bar on the frame
        frame.setJMenuBar(menuBar);
        
        // Display the frame
        frame.pack();
        frame.setVisible(true);
    }
}

// Example classes for performing actions related to each menu item
class UsersClass {
    public static void performAction() {
        System.out.println("Performing action for 'Users' menu item.");
        
        // Add your code here to perform the desired action
    }
}

class BMIClass {
    public static void performAction() {
        System.out.println("Performing action for 'BMI' menu item.");
        // Add your code here to perform the desired action
    }
}

class HealthConClass {
    public static void performAction() {
        System.out.println("Performing action for 'HealthCon' menu item.");
        // Add your code here to perform the desired action
    }
}

class DietClass {
    public static void performAction() {
        System.out.println("Performing action for 'Diet' menu item.");
        // Add your code here to perform the desired action
    }
}

class ExerciseClass {
    public static void performAction() {
        System.out.println("Performing action for 'Exercise' menu item.");
        // Add your code here to perform the desired action
    }
}
