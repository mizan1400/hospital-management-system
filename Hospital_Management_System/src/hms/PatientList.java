package hms;

import java.awt.Color;
import java.awt.Container;
import java.awt.Font;
import java.awt.TextArea;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;

import hms.DoctorReg.Back;

public class PatientList extends JFrame {

    static Connection con = null;
    Statement st = null;
    ResultSet rs = null;
    PreparedStatement stmt = null;

    static JFrame frame;
    private static JPanel panel;

    JButton bpatient[] = new JButton[100];
    String patient[] = new String[100];
    JLabel lmain, lpersonalInfo, lid, lname, ladd, lcon, lmedicalInfo, lbloodGroup, ldob, lroom, ladmissionDate,
            lgender, lroomType, ldtf, ldtf2, lproblem, ldocName, ltitle;
    JTextField tfname, tfcon, tfdob, tfroom, tfadmissionDate, tfdocName, tfid, tfbloodGroup, tfgender, tfroomType;
    TextArea taadd, taproblem;
    JButton bsub, bclr, bback, bback2, bdelete, bupdate;
    String name;
    int i, k, row;

    PatientList() {
        super("Patient List");
        setSize(1000, 720);
        setVisible(true);
        setLayout(null);

        try {
            bback = new JButton("BACK", new ImageIcon("images/restore.png"));
            bback.setBounds(450, 600, 110, 30);
            add(bback);
            bback.addActionListener(new Back());

            Class.forName("oracle.jdbc.driver.OracleDriver");
            Connection con = DriverManager.getConnection("jdbc:oracle:thin:@Mizan:1521:XE", "mizan", "123456");

            stmt = con.prepareStatement("select name from patient");

            ResultSet patientList = stmt.executeQuery();
            int x = 100;
            i = 1;

            ltitle = new JLabel("Patient List");
            ltitle.setBounds(430, 40, 150, 30);
            ltitle.setFont(new Font("Serif", Font.BOLD, 30));
            ltitle.setForeground(Color.BLUE);
            add(ltitle);

            while (patientList.next()) {
                row = patientList.getRow();
                patient[i] = patientList.getString(1);
                System.out.println(patient[i]);
                bpatient[i] = new JButton(patient[i]);
                bpatient[i].setBounds(400, x, 200, 20);
                add(bpatient[i]);

                x += 25;
                i++;
            }

            for (k = 1; k <= row; k++) {

                bpatient[k].addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent event) {

                        try {
                            if (event.getSource() instanceof JButton) {
                                setVisible(false);
                                name = ((JButton) event.getSource()).getText();

                                PreparedStatement stmt2 = con.prepareStatement("select * from patient where name=?");
                                stmt2.setString(1, name);

                                ResultSet patientInfo = stmt2.executeQuery();

                                patientInfo.next();
                                //System.out.println("name:"+patientInfo.getString("name"));
                                panel = new JPanel();
                                panel.setLayout(null);
                                JLabel ltitle = new JLabel(name);
                                ltitle.setBounds(350, 20, 300, 50);
                                ltitle.setFont(new Font("Serif", Font.BOLD, 30));
                                ltitle.setForeground(Color.BLUE);
                                panel.add(ltitle);

                                lpersonalInfo = new JLabel("Personal Information");
                                lpersonalInfo.setBounds(40, 70, 150, 15);
                                panel.add(lpersonalInfo);

                                ladd = new JLabel("Address :");
                                ladd.setBounds(104, 138, 70, 15);
                                panel.add(ladd);

                                taadd = new TextArea();
                                taadd.setBounds(270, 138, 250, 100);
                                taadd.setText(patientInfo.getString("address"));
                                panel.add(taadd);

                                lid = new JLabel("Patient No.:");
                                lid.setBounds(104, 97, 70, 15);
                                panel.add(lid);

                                tfid = new JTextField(30);
                                tfid.setBounds(270, 97, 250, 20);
                                tfid.setText(patientInfo.getString("id"));
                                panel.add(tfid);

                                lroom = new JLabel("Room No.:");
                                lroom.setBounds(570, 97, 70, 25);
                                panel.add(lroom);

                                tfroom = new JTextField(30);
                                tfroom.setBounds(700, 97, 50, 20);
                                tfroom.setText(patientInfo.getString("room"));
                                panel.add(tfroom);

                                lcon = new JLabel("Contact No.:");
                                lcon.setBounds(570, 138, 75, 25);
                                panel.add(lcon);

                                tfcon = new JTextField(30);
                                tfcon.setBounds(700, 138, 150, 20);
                                tfcon.setText(patientInfo.getString("contact"));
                                panel.add(tfcon);

                                ldob = new JLabel("Date of Birth :");
                                ldob.setBounds(570, 180, 120, 25);
                                panel.add(ldob);

                                tfdob = new JTextField(15);
                                tfdob.setBounds(700, 180, 80, 20);
                                tfdob.setText(patientInfo.getString("birth_date"));
                                panel.add(tfdob);

                                lgender = new JLabel("Gender :");
                                lgender.setBounds(570, 223, 50, 20);
                                panel.add(lgender);

                                tfgender = new JTextField(30);
                                tfgender.setBounds(700, 223, 60, 20);
                                tfgender.setText(patientInfo.getString("gender"));
                                panel.add(tfgender);

                                lmedicalInfo = new JLabel("Medical Information");
                                lmedicalInfo.setBounds(40, 268, 120, 15);
                                panel.add(lmedicalInfo);

                                lbloodGroup = new JLabel("Blood Group :");
                                lbloodGroup.setBounds(104, 306, 79, 15);
                                panel.add(lbloodGroup);

                                tfbloodGroup = new JTextField(30);
                                tfbloodGroup.setBounds(270, 306, 70, 20);
                                tfbloodGroup.setText(patientInfo.getString("blood_group"));
                                panel.add(tfbloodGroup);

                                ladmissionDate = new JLabel("Date Of Admission :");
                                ladmissionDate.setBounds(575, 306, 120, 15);
                                panel.add(ladmissionDate);

                                tfadmissionDate = new JTextField(40);
                                tfadmissionDate.setBounds(700, 305, 80, 20);
                                tfadmissionDate.setText(patientInfo.getString("admission_date"));
                                panel.add(tfadmissionDate);

                                lproblem = new JLabel("Problem Description :");
                                lproblem.setBounds(104, 365, 150, 15);
                                panel.add(lproblem);

                                taproblem = new TextArea();
                                taproblem.setBounds(270, 365, 600, 100);
                                taproblem.setText(patientInfo.getString("problem"));
                                panel.add(taproblem);

                                lroomType = new JLabel("Type Of Room : ");
                                lroomType.setBounds(104, 510, 120, 25);
                                panel.add(lroomType);

                                tfroomType = new JTextField(30);
                                tfroomType.setBounds(270, 510, 80, 20);
                                tfroomType.setText(patientInfo.getString("room_type"));
                                panel.add(tfroomType);

                                ldocName = new JLabel("Doctor Name :");
                                ldocName.setBounds(575, 510, 130, 15);
                                panel.add(ldocName);

                                tfdocName = new JTextField(100);
                                tfdocName.setBounds(700, 510, 170, 20);
                                tfdocName.setText(patientInfo.getString("doctor_name"));
                                panel.add(tfdocName);

                                bback2 = new JButton("BACK", new ImageIcon("images/restore.png"));
                                bback2.setBounds(580, 600, 110, 30);
                                panel.add(bback2);
                                bback2.addActionListener(new Back2());

                                bupdate = new JButton("UPDATE", new ImageIcon("images/update.png"));
                                bupdate.setBounds(330, 600, 110, 30);
                                panel.add(bupdate);

                                bupdate.addActionListener(new ActionListener() {
                                    public void actionPerformed(ActionEvent event) {

                                        try {
                                            PreparedStatement stmtUpdate = con.prepareStatement("update patient set ROOM=?,	ADDRESS=?,	CONTACT=?,	GENDER=?,	BLOOD_GROUP=?,	PROBLEM=?,	ROOM_TYPE=?,	DOCTOR_NAME=? where name=?");

                                            stmtUpdate.setString(1, tfroom.getText());
                                            stmtUpdate.setString(2, taadd.getText());
                                            stmtUpdate.setString(3, tfcon.getText());                                           
                                            stmtUpdate.setString(4, tfgender.getText());
                                            stmtUpdate.setString(5, tfbloodGroup.getText());
                                            stmtUpdate.setString(6, taproblem.getText());
                                            stmtUpdate.setString(7, tfroom.getText());
                                            stmtUpdate.setString(8, tfdocName.getText());
                                            stmtUpdate.setString(9, name);

                                            stmtUpdate.executeUpdate();

                                            String message = "Succesfully Updated!";
                                            JOptionPane.showMessageDialog(new JFrame(), message, "Done!",
                                                    JOptionPane.INFORMATION_MESSAGE);
                                            new PatientList();
                                            frame.setVisible(false);
                                        } catch (SQLException sq) {
                                            System.out.println("Patient Update:" + sq);
                                        } catch (Exception e) {
                                            System.out.println("Patient Update:" + e);
                                        }
                                    }
                                });

                                bdelete = new JButton("DELETE", new ImageIcon("images/delete.png"));
                                bdelete.setBounds(460, 600, 100, 30);
                                panel.add(bdelete);

                                bdelete.addActionListener(new ActionListener() {
                                    public void actionPerformed(ActionEvent event) {

                                        try {
                                            PreparedStatement stmtDelete = con.prepareStatement("delete from patient where name=?");
                                            stmtDelete.setString(1, name);

                                            stmtDelete.executeUpdate();

                                            String message = "Successfully Deleted!";
                                            JOptionPane.showMessageDialog(new JFrame(), message, "Done!",
                                                    JOptionPane.INFORMATION_MESSAGE);
                                            new PatientList();
                                            frame.setVisible(false);
                                        } catch (SQLException sq) {
                                            System.out.println("Patient Delete:" + sq);
                                        } catch (Exception e) {
                                            System.out.println("Patient Delete:" + e);
                                        }
                                    }
                                });

                                frame = new JFrame("Patient Information");
                                frame.setSize(1000, 720);

                                Container pane = frame.getContentPane();
                                pane.setLayout(new BoxLayout(pane, BoxLayout.Y_AXIS));
                                pane.add(panel);

                                frame.setVisible(true);
                            }
                        } catch (SQLException sq) {
                            System.out.println("Inside Loop:" + sq);
                        } catch (Exception e) {
                            System.out.println("Inside Loop:" + e);
                        }
                    }
                });
            }

        } catch (SQLException sq) {
            System.out.println(sq);
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    class Back implements ActionListener {

        public void actionPerformed(ActionEvent ae) {
            new Info();
            setVisible(false);
        }
    }

    class Back2 implements ActionListener {

        public void actionPerformed(ActionEvent ae) {
            new PatientList();
            frame.setVisible(false);
        }
    }

    public void actionPerformed(ActionEvent ae) {
    }

    public static void main(String[] args) {

        new PatientList();
    }
}
