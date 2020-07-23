package hms;

import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import javax.swing.*;

class StaffReg extends JFrame {

    static Connection con = null;
    Statement st = null;
    ResultSet rs = null;
    PreparedStatement stmt = null;

    JLabel lmain, lname, ladd, lcon, lid, lwork, lworkfrom, lworkto;
    JTextField tfname, tfcon, tfid, tfworkFrom, tfworkTo;
    TextArea taadd;
    JButton bsub, bclr, bback;

    StaffReg() {
        super("Staff Information");
        setSize(1000, 720);
        setVisible(true);
        setLayout(null);

        lmain = new JLabel("Staff Information");
        lmain.setBounds(350, 40, 300, 30);
        lmain.setFont(new Font("Serif", Font.BOLD, 30));
        lmain.setForeground(Color.BLUE);
        add(lmain);

        lname = new JLabel("Name :");
        lname.setBounds(300, 100, 70, 25);
        add(lname);

        tfname = new JTextField(30);
        tfname.setBounds(470, 100, 250, 20);
        add(tfname);

        lid = new JLabel("Staff ID:");
        lid.setBounds(300, 140, 70, 25);
        add(lid);

        tfid = new JTextField(30);
        tfid.setBounds(470, 140, 50, 20);
        add(tfid);

        lcon = new JLabel("Contact :");
        lcon.setBounds(300, 180, 70, 25);
        add(lcon);

        tfcon = new JTextField(30);
        tfcon.setBounds(470, 180, 250, 20);
        add(tfcon);

        ladd = new JLabel("Address :");
        ladd.setBounds(300, 220, 70, 25);
        add(ladd);

        taadd = new TextArea();
        taadd.setBounds(470, 220, 250, 100);
        add(taadd);

        lwork = new JLabel("Working hours :");
        lwork.setBounds(300, 360, 100, 15);
        add(lwork);

        lworkfrom = new JLabel("From :");
        lworkfrom.setBounds(470, 360, 40, 15);
        add(lworkfrom);

        tfworkFrom = new JTextField(30);
        tfworkFrom.setBounds(530, 360, 40, 20);
        add(tfworkFrom);

        lworkto = new JLabel("to :");
        lworkto.setBounds(590, 360, 20, 15);
        add(lworkto);

        tfworkTo = new JTextField(30);
        tfworkTo.setBounds(630, 360, 40, 20);
        add(tfworkTo);

        bsub = new JButton("SUBMIT", new ImageIcon("images/add.gif"));
        bsub.setBounds(330, 600, 110, 30);
        add(bsub);

        bclr = new JButton("CLEAR", new ImageIcon("images/LOGGOFF.PNG"));
        bclr.setBounds(460, 600, 100, 30);
        add(bclr);

        bback = new JButton("BACK", new ImageIcon("images/restore.png"));
        bback.setBounds(580, 600, 110, 30);
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
                String name = tfname.getText();
                String id = tfid.getText();
                String contact = tfcon.getText();
                String address = taadd.getText();
                String whFrom = tfworkFrom.getText();
                String whTo = tfworkTo.getText();

                stmt = con.prepareStatement("insert into staff values(?,?,?,?,?,?)");

                stmt.setString(1, name);
                stmt.setString(2, id);
                stmt.setString(3, contact);
                stmt.setString(4, address);
                stmt.setString(5, whFrom);
                stmt.setString(6, whTo);

                stmt.executeUpdate();

                String message = "Stuff successfully registered!";
                JOptionPane.showMessageDialog(new JFrame(), message, "Done!", JOptionPane.INFORMATION_MESSAGE);

                // new Info();
                // setVisible(false);
            } catch (SQLException sq) {
                String message = "Fill up all the field.";
                JOptionPane.showMessageDialog(new JFrame(), message, "ERROR!", JOptionPane.ERROR_MESSAGE);
                System.out.println(sq);
            } catch (Exception e) {
                System.out.println(e);
            }

        }
    }

    class Clear implements ActionListener {

        public void actionPerformed(ActionEvent ae) {

            tfname.setText("");
            tfid.setText("");
            taadd.setText("");
            tfcon.setText("");
            tfworkFrom.setText("");
            tfworkTo.setText("");
        }
    }

    class Back implements ActionListener {

        public void actionPerformed(ActionEvent ae) {
            new Info();
            setVisible(false);
        }
    }

    public void actionPerformed(ActionEvent ae) {
    }

    public static void main(String[] args) {
        new StaffReg();
    }
}
