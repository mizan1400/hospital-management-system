package hms;

import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import javax.swing.*;

import hms.Registration.Clear;

import java.io.*;
import java.util.*;
import java.text.*;

class Login extends JFrame implements ActionListener {

    static Connection con = null;
    Statement st = null;
    ResultSet rs = null;
    PreparedStatement stmt = null;

    JLabel lpass, lmain, llname, luserName, lregister;
    JTextField tfuserName;
    JButton blogin, bregister;
    JPasswordField pass;

    Login() {

        super("Login");
        setSize(1000, 720);
        setVisible(true);
        setLayout(null);

        lmain = new JLabel("Login");
        lmain.setBounds(450, 120, 100, 40);
        lmain.setFont(new Font("Serif", Font.BOLD, 30));
        lmain.setForeground(Color.BLUE);
        add(lmain);

        luserName = new JLabel("User Name :");
        luserName.setBounds(400, 200, 100, 15);
        add(luserName);

        tfuserName = new JTextField(30);
        tfuserName.setBounds(510, 200, 100, 20);
        add(tfuserName);

        lpass = new JLabel("Password:");
        lpass.setBounds(400, 250, 100, 15);
        add(lpass);

        pass = new JPasswordField(30);
        pass.setBounds(510, 250, 100, 20);
        add(pass);

        blogin = new JButton("Login", new ImageIcon("images/Key.gif"));
        blogin.setBounds(450, 300, 110, 30);
        add(blogin);

        lregister = new JLabel("Not a registerd user!");
        lregister.setBounds(435, 470, 150, 15);
        add(lregister);

        bregister = new JButton("Register here", new ImageIcon("images/Keys.gif"));
        bregister.setBounds(420, 500, 150, 30);
        add(bregister);

        try {

            Class.forName("oracle.jdbc.driver.OracleDriver");
            con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe", "mizan", "123456");
        } catch (Exception e) {
            System.out.println(e);
        }

        blogin.addActionListener(new BLogin());
        bregister.addActionListener(new Reg());

    }

    class BLogin implements ActionListener {

        public void actionPerformed(ActionEvent ae) {

            try {

                String userName = tfuserName.getText();
                String password = new String(pass.getPassword());

                if (userName.equals(null) || pass.equals(null)) {
                    System.out.println("user name/pass");
                    throw new BlankException();
                }

                stmt = con.prepareStatement("select USER_NAME,PASS from admin");
                rs = stmt.executeQuery();

                int a = 0;
                while (rs.next()) {
                    if (userName.equals(rs.getString("USER_NAME")) && password.equals(rs.getString("PASS"))) {
                        String message = "Login Successful!";
                        JOptionPane.showMessageDialog(new JFrame(), message, "Done!", JOptionPane.INFORMATION_MESSAGE);
                        a = 1;
                        new Info();
                        setVisible(false);
                        break;
                    }
                }

                if (a == 0) {
                    String message = "Wrong User Name or Password!";
                    JOptionPane.showMessageDialog(new JFrame(), message, "ERROR!", JOptionPane.ERROR_MESSAGE);
                }
            } catch (SQLException sq) {
                String message = "Fill up all the field.";
                JOptionPane.showMessageDialog(new JFrame(), message, "ERROR!", JOptionPane.ERROR_MESSAGE);
                System.out.println(sq);
            } catch (BlankException be) {
                String message = "Please Enter All The Fields.";
                JOptionPane.showMessageDialog(new JFrame(), message, "ERROR!", JOptionPane.ERROR_MESSAGE);
            } catch (Exception e) {
                System.out.println(e);
            }

        }
    }

    class Reg implements ActionListener {

        public void actionPerformed(ActionEvent ae) {
            new Registration();
            setVisible(false);
        }
    }

    public void actionPerformed(ActionEvent ae) {
    }

    public static void main(String[] args) {
        new Login();
    }

}
