package hms;

import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import javax.swing.*;

import hms.Registration.Back;
import hms.Registration.Clear;
import hms.Registration.Submit;

class DoctorReg extends JFrame {

    static Connection con = null;
    Statement st = null;
    ResultSet rs = null;
    PreparedStatement stmt = null;

    JLabel lmain, lname, ladd, lcon, lspecial, ldid, lquali, lwork, lworkfrom, lworkto;
    JTextField tfname, tfcon, tfdid, tfworkFrom, tfworkTo;
    TextArea taadd, taspecial, taquali;
    JButton bsub, bclr, bback;

    DoctorReg() {
        super("Doctor Information");
        setSize(1000, 720);
        setVisible(true);
        setLayout(null);

        lmain = new JLabel("Doctor Information");
        lmain.setBounds(350, 40, 300, 30);
        lmain.setFont(new Font("Serif", Font.BOLD, 30));
        lmain.setForeground(Color.BLUE);
        add(lmain);

        lname = new JLabel("Name :");
        lname.setBounds(100, 100, 70, 25);
        add(lname);

        tfname = new JTextField(30);
        tfname.setBounds(270, 100, 250, 20);
        add(tfname);

        ldid = new JLabel("Doctor ID:");
        ldid.setBounds(570, 100, 70, 25);
        add(ldid);

        tfdid = new JTextField(30);
        tfdid.setBounds(640, 100, 50, 20);
        add(tfdid);

        ladd = new JLabel("Address :");
        ladd.setBounds(100, 138, 70, 15);
        add(ladd);

        taadd = new TextArea();
        taadd.setBounds(270, 138, 250, 100);
        add(taadd);

        lcon = new JLabel("Contact :");
        lcon.setBounds(570, 140, 50, 25);
        add(lcon);

        tfcon = new JTextField(30);
        tfcon.setBounds(640, 140, 200, 20);
        add(tfcon);

        lwork = new JLabel("Working hours :");
        lwork.setBounds(570, 180, 100, 15);
        add(lwork);

        lworkfrom = new JLabel("From :");
        lworkfrom.setBounds(670, 180, 37, 15);
        add(lworkfrom);

        tfworkFrom = new JTextField(30);
        tfworkFrom.setBounds(710, 180, 40, 20);
        add(tfworkFrom);

        lworkto = new JLabel("to :");
        lworkto.setBounds(760, 180, 20, 15);
        add(lworkto);

        tfworkTo = new JTextField(30);
        tfworkTo.setBounds(790, 180, 40, 20);
        add(tfworkTo);

        lspecial = new JLabel("Specialization :");
        lspecial.setBounds(100, 310, 100, 25);
        add(lspecial);

        taspecial = new TextArea();
        taspecial.setBounds(270, 310, 600, 100);
        add(taspecial);

        lquali = new JLabel("Qualification :");
        lquali.setBounds(100, 440, 100, 25);
        add(lquali);

        taquali = new TextArea();
        taquali.setBounds(270, 440, 600, 100);
        add(taquali);

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
                String id = tfdid.getText();
                String address = taadd.getText();
                String contact = tfcon.getText();
                String whFrom = tfworkFrom.getText();
                String whTo = tfworkTo.getText();
                String specialization = taspecial.getText();
                String qualification = taquali.getText();

                stmt = con.prepareStatement("insert into doctor values(?,?,?,?,?,?,?,?)");

                stmt.setString(1, name);
                stmt.setString(2, id);
                stmt.setString(3, address);
                stmt.setString(4, contact);
                stmt.setString(5, whFrom);
                stmt.setString(6, whTo);
                stmt.setString(7, specialization);
                stmt.setString(8, qualification);

                stmt.executeUpdate();

                String message = "Doctor successfully registered!";
                JOptionPane.showMessageDialog(new JFrame(), message, "Done!", JOptionPane.INFORMATION_MESSAGE);

                new Info();
                setVisible(false);
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
            tfdid.setText("");
            taadd.setText("");
            tfname.setText("");
            tfcon.setText("");
            tfworkFrom.setText("");
            tfworkTo.setText("");
            taspecial.setText("");
            taquali.setText("");
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
        new DoctorReg();
    }
}
