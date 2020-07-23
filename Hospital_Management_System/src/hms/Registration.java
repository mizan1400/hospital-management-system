package hms;

import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import javax.swing.*;

class Registration extends JFrame implements ActionListener {

    static Connection con = null;
    Statement st = null;
    ResultSet rs = null;

    JLabel lcpass, lpass, lfname, lmain, llname, luserName;
    JTextField tfuserName, tffname, tflname;
    JButton bsub, bclr, bback;
    JPasswordField pass, confirmPass;

    Registration() {
        super("Registration");
        setSize(1000, 720);
        setVisible(true);
        setLayout(null);

        lmain = new JLabel("Registration");
        lmain.setBounds(420, 80, 160, 50);
        lmain.setFont(new Font("Serif", Font.BOLD, 30));
        lmain.setForeground(Color.BLUE);
        add(lmain);

        lfname = new JLabel("First Name: ");
        lfname.setBounds(300, 180, 70, 15);
        add(lfname);

        tffname = new JTextField(30);
        tffname.setBounds(450, 180, 250, 20);
        add(tffname);

        llname = new JLabel("Last Name :");
        llname.setBounds(300, 230, 70, 15);
        add(llname);

        tflname = new JTextField(30);
        tflname.setBounds(450, 230, 250, 20);
        add(tflname);

        luserName = new JLabel("User Name :");
        luserName.setBounds(300, 280, 100, 15);
        add(luserName);

        tfuserName = new JTextField(30);
        tfuserName.setBounds(450, 280, 250, 20);
        add(tfuserName);

        lpass = new JLabel("Password:");
        lpass.setBounds(300, 330, 100, 15);
        add(lpass);

        pass = new JPasswordField(30);
        pass.setBounds(450, 330, 250, 20);
        add(pass);

        lcpass = new JLabel("Confirm Password:");
        lcpass.setBounds(300, 380, 150, 15);
        add(lcpass);

        confirmPass = new JPasswordField(30);
        confirmPass.setBounds(450, 380, 250, 20);
        add(confirmPass);

        bsub = new JButton("SUBMIT", new ImageIcon("images/add.gif"));
        bsub.setBounds(330, 500, 110, 30);
        add(bsub);

        bclr = new JButton("CLEAR", new ImageIcon("images/LOGGOFF.PNG"));
        bclr.setBounds(460, 500, 100, 30);
        add(bclr);

        bback = new JButton("BACK", new ImageIcon("images/restore.png"));
        bback.setBounds(580, 500, 110, 30);
        add(bback);

        try {

            Class.forName("oracle.jdbc.driver.OracleDriver");
            con = DriverManager.getConnection("jdbc:oracle:thin:@Mizan:1521:XE", "mizan", "123456");
        } catch (Exception e) {
            System.out.println(e);
        }

        bclr.addActionListener(new Clear());
        bsub.addActionListener(new Submit());
        bback.addActionListener(new Back());

    }

    class Submit implements ActionListener {

        public void actionPerformed(ActionEvent ae) {

            try {

                String fname = tffname.getText();
                String lname = tflname.getText();
                String userName = tfuserName.getText();
                String password = new String(pass.getPassword());
                String confirmPassword = new String(confirmPass.getPassword());

                if (userName.trim().isEmpty() || password.trim().isEmpty()) {
                    throw new BlankException();
                }
                if (password.equals(confirmPassword)) {

                    PreparedStatement stmt = con.prepareStatement("insert into admin values(?,?,?,?)");

                    stmt.setString(1, fname);
                    stmt.setString(2, lname);
                    stmt.setString(3, userName);
                    stmt.setString(4, password);

                    stmt.executeUpdate();

                    String message = "Registration Successful!";
                    JOptionPane.showMessageDialog(new JFrame(), message, "Done!", JOptionPane.INFORMATION_MESSAGE);
                    new Login();
                    setVisible(false);
                } else {
                    String message = "Password didn't match!";
                    JOptionPane.showMessageDialog(new JFrame(), message, "ERROR!", JOptionPane.ERROR_MESSAGE);
                }

            } catch (SQLException sq) {
                String message = "Fill up the information properly";
                JOptionPane.showMessageDialog(new JFrame(), message, "ERROR!", JOptionPane.ERROR_MESSAGE);
                System.out.println(sq);
            } catch (BlankException be) {
                String message = "Please fill up all the fields.";
                JOptionPane.showMessageDialog(new JFrame(), message, "ERROR!", JOptionPane.ERROR_MESSAGE);
            } catch (Exception e) {
                System.out.println(e);
            }

        }
    }

    class Clear implements ActionListener {

        public void actionPerformed(ActionEvent ae) {

            confirmPass.setText("");
            pass.setText("");
            tfuserName.setText("");
            tffname.setText("");
            tflname.setText("");
        }
    }

    class Back implements ActionListener {

        public void actionPerformed(ActionEvent ae) {
            new Home();
            setVisible(false);
        }
    }

    public void actionPerformed(ActionEvent ae) {
    }

    public static void main(String[] args) {

        new Registration();
    }

}
