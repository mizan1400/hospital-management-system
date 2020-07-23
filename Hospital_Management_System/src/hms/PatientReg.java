package hms;

import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import javax.swing.*;

class PatientReg extends JFrame {

    static Connection con = null;
    Statement st = null;
    ResultSet rs = null;
    PreparedStatement stmt = null;

    JLabel lmain, lpersonalInfo, lid, lname, ladd, lcon, lmedicalInfo, lbloodGroup, ldob, lroom, ladmissionDate,
            lgender, lroomType, ldtf, ldtf2, lproblem, ldocName;
    JTextField tfname, tfcon, tfdob, tfroom, tfadmissionDate, tfdocName, tfid;
    TextArea taadd, taproblem;
    JComboBox cbbloodGroup, cbroomType;
    ButtonGroup gender;
    JRadioButton male, female;
    JButton bsub, bclr, bback;

    PatientReg() {
        super("Patient Information");
        setSize(1000, 720);
        setVisible(true);
        setLayout(null);

        lmain = new JLabel("Patient Information");
        lmain.setBounds(350, 40, 300, 30);
        lmain.setFont(new Font("Serif", Font.BOLD, 30));
        lmain.setForeground(Color.BLUE);
        add(lmain);

        lpersonalInfo = new JLabel("Personal Information");
        lpersonalInfo.setBounds(40, 70, 150, 15);
        add(lpersonalInfo);

        lname = new JLabel("Name :");
        lname.setBounds(104, 97, 70, 15);
        add(lname);

        tfname = new JTextField(30);
        tfname.setBounds(270, 97, 250, 20);
        add(tfname);

        ladd = new JLabel("Address :");
        ladd.setBounds(104, 138, 70, 15);
        add(ladd);

        taadd = new TextArea();
        taadd.setBounds(270, 138, 250, 100);
        add(taadd);

        lid = new JLabel("Patient No.:");
        lid.setBounds(570, 97, 70, 25);
        add(lid);

        tfid = new JTextField(30);
        tfid.setBounds(643, 97, 50, 20);
        add(tfid);

        lroom = new JLabel("Room No.:");
        lroom.setBounds(720, 97, 60, 20);
        add(lroom);

        tfroom = new JTextField(30);
        tfroom.setBounds(788, 97, 60, 20);
        add(tfroom);

        lcon = new JLabel("Contact No.:");
        lcon.setBounds(570, 138, 75, 25);
        add(lcon);

        tfcon = new JTextField(30);
        tfcon.setBounds(700, 138, 150, 20);
        add(tfcon);

        ldob = new JLabel("Date of Birth :");
        ldob.setBounds(570, 180, 120, 25);
        add(ldob);

        tfdob = new JTextField(15);
        tfdob.setBounds(700, 180, 80, 20);
        add(tfdob);

        ldtf2 = new JLabel("(dd-mm-yyyy)");
        ldtf2.setBounds(782, 180, 100, 20);
        add(ldtf2);

        lgender = new JLabel("Gender :");
        lgender.setBounds(570, 223, 50, 15);
        add(lgender);

        male = new JRadioButton("Male");
        female = new JRadioButton("Female");
        male.setBounds(700, 223, 60, 15);
        female.setBounds(770, 223, 80, 15);
        gender = new ButtonGroup();
        gender.add(male);

        gender.add(female);
        add(male);
        add(female);

        lmedicalInfo = new JLabel("Medical Information");
        lmedicalInfo.setBounds(40, 268, 120, 15);
        add(lmedicalInfo);

        lbloodGroup = new JLabel("Blood Group :");
        lbloodGroup.setBounds(104, 306, 79, 15);
        add(lbloodGroup);

        String bg[] = {"A +ve", "A -ve", "B +ve", "B -ve", "AB +ve", "AB -ve", "O +ve", "O -ve"};
        cbbloodGroup = new JComboBox(bg);
        cbbloodGroup.setBounds(270, 306, 70, 20);
        add(cbbloodGroup);

        ladmissionDate = new JLabel("Date Of Admission :");
        ladmissionDate.setBounds(575, 306, 120, 15);
        add(ladmissionDate);

        tfadmissionDate = new JTextField(40);
        tfadmissionDate.setBounds(700, 305, 80, 20);
        add(tfadmissionDate);

        ldtf = new JLabel("(dd-mm-yyyy)");
        ldtf.setBounds(782, 305, 100, 20);
        add(ldtf);

        lproblem = new JLabel("Problem Description :");
        lproblem.setBounds(104, 365, 150, 15);
        add(lproblem);

        taproblem = new TextArea();
        taproblem.setBounds(270, 365, 600, 100);
        add(taproblem);

        lroomType = new JLabel("Type Of Room : ");
        lroomType.setBounds(104, 510, 120, 25);
        add(lroomType);

        String room[] = {"Deluxe", "Private", "General"};
        cbroomType = new JComboBox(room);
        cbroomType.setBounds(270, 510, 80, 20);
        add(cbroomType);

        ldocName = new JLabel("Doctor Name :");
        ldocName.setBounds(575, 510, 130, 15);
        add(ldocName);

        tfdocName = new JTextField(100);
        tfdocName.setBounds(700, 510, 170, 20);
        add(tfdocName);

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
                String room = tfroom.getText();
                String address = taadd.getText();
                String contact = tfcon.getText();
                String dob = tfdob.getText();
                String gender = null;
                if (male.isSelected()) {
                    gender = "Male";
                } else if (female.isSelected()) {
                    gender = "Female";
                }

                String bloodGroup = (String) cbbloodGroup.getItemAt(cbbloodGroup.getSelectedIndex());
                String admissionDate = tfadmissionDate.getText();
                String problem = taproblem.getText();
                String roomType = (String) cbroomType.getItemAt(cbroomType.getSelectedIndex());
                String doctorName = tfdocName.getText();

                stmt = con.prepareStatement("insert into patient values(?,?,?,?,?,?,?,?,?,?,?,?)");

                stmt.setString(1, name);
                stmt.setString(2, id);
                stmt.setString(3, room);
                stmt.setString(4, address);
                stmt.setString(5, contact);
                stmt.setString(6, dob);
                stmt.setString(7, gender);
                stmt.setString(8, bloodGroup);
                stmt.setString(9, admissionDate);
                stmt.setString(10, problem);
                stmt.setString(11, roomType);
                stmt.setString(12, doctorName);

                stmt.executeUpdate();

                String message = "Patient successfully registered!";
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
            taadd.setText("");
            tfname.setText("");
            tfcon.setText("");
            tfroom.setText("");
            tfadmissionDate.setText("");
            tfdocName.setText("");
            tfdob.setText("");
            taproblem.setText("");
            tfid.setText("");
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

        new PatientReg();
    }

}
