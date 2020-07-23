package hms;

import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import javax.swing.*;

class Search extends JFrame implements ActionListener {

    static Connection con = null;
    Statement st = null;
    ResultSet rs = null;
    PreparedStatement stmt = null;

    JButton bsearchDoctor, bsearchPatient, bsearchStaff, bback, bsearch, bback2;
    JLabel lmain, lname, ladd, lcon, lid, lwork, lworkfrom, lworkto, ltitle;
    JTextField tfsearchBox, tfname, tfcon, tfid, tfworkFrom, tfworkTo;
    TextArea taadd;
    JLabel lpmain, lppersonalInfo, lpid, lpname, lpadd, lpcon, lpmedicalInfo, lpbloodGroup, lpdob, lproom,
            lpadmissionDate, lpgender, lproomType, lpdtf, lpdtf2, lpproblem, lpdocName, lptitle;
    JTextField tfpname, tfpcon, tfpdob, tfproom, tfpadmissionDate, tfpdocName, tfpid, tfpbloodGroup, tfpgender,
            tfproomType;
    TextArea tapadd, tapproblem;

    JLabel ldmain, ldname, ldadd, ldcon, ldspecial, ldid, ldquali, ldwork, ldworkfrom, ldworkto, ldtitle, ltext;
    JTextField tfdname, tfdcon, tfdid, tfdworkFrom, tfdworkTo;
    TextArea tadadd, tadspecial, tadquali;
    String name;

    static JFrame frame;
    private static JPanel panel;

    Search() {
        super("Search Options");
        setSize(1000, 720);
        setVisible(true);
        setLayout(null);

        try {
            Class.forName("oracle.jdbc.driver.OracleDriver");
            Connection con = DriverManager.getConnection("jdbc:oracle:thin:@Mizan:1521:XE", "mizan", "123456");

            lmain = new JLabel("Search");
            lmain.setBounds(450, 70, 100, 30);
            lmain.setFont(new Font("Serif", Font.BOLD, 30));
            lmain.setForeground(Color.BLUE);
            add(lmain);

            ltext = new JLabel("Enter Name (You want to search)");
            ltext.setBounds(400, 160, 200, 10);
            add(ltext);

            tfsearchBox = new JTextField(30);
            tfsearchBox.setBounds(350, 180, 300, 40);
            add(tfsearchBox);

            bsearchDoctor = new JButton("Doctor Search");
            bsearchDoctor.setBounds(250, 270, 160, 40);
            add(bsearchDoctor);

            bsearchPatient = new JButton("Patient Search");
            bsearchPatient.setBounds(420, 270, 160, 40);
            add(bsearchPatient);

            bsearchStaff = new JButton("Staff Search");
            bsearchStaff.setBounds(590, 270, 160, 40);
            add(bsearchStaff);

            bback = new JButton("BACK");
            bback.setBounds(450, 600, 110, 30);
            add(bback);
            bback.addActionListener(new Back());

            bsearchStaff.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent event) {

                    try {
                        name = tfsearchBox.getText();
                        System.out.println(name.toLowerCase());
                        PreparedStatement staff = con.prepareStatement("select * from staff where lower(name)=?");
                        staff.setString(1, name.toLowerCase());

                        ResultSet staffResult = staff.executeQuery();

                        if (staffResult.next()) {
                            setVisible(false);
                            panel = new JPanel();
                            panel.setLayout(null);
                            JLabel ltitle = new JLabel(staffResult.getString("name"));
                            ltitle.setBounds(350, 20, 300, 50);
                            ltitle.setFont(new Font("Serif", Font.BOLD, 30));
                            ltitle.setForeground(Color.BLUE);
                            panel.add(ltitle);

                            lid = new JLabel("Staff ID:");
                            lid.setBounds(300, 140, 70, 25);
                            panel.add(lid);

                            tfid = new JTextField(30);
                            tfid.setBounds(470, 140, 50, 20);
                            tfid.setText(staffResult.getString("id"));
                            panel.add(tfid);

                            lcon = new JLabel("Contact :");
                            lcon.setBounds(300, 180, 70, 25);
                            panel.add(lcon);

                            tfcon = new JTextField(30);
                            tfcon.setBounds(470, 180, 250, 20);
                            tfcon.setText(staffResult.getString("contact"));
                            panel.add(tfcon);

                            ladd = new JLabel("Address :");
                            ladd.setBounds(300, 220, 70, 25);
                            panel.add(ladd);

                            taadd = new TextArea();
                            taadd.setBounds(470, 220, 250, 100);
                            taadd.setText(staffResult.getString("address"));
                            panel.add(taadd);

                            lwork = new JLabel("Working hours :");
                            lwork.setBounds(300, 360, 100, 15);
                            panel.add(lwork);

                            lworkfrom = new JLabel("From :");
                            lworkfrom.setBounds(470, 360, 40, 15);
                            panel.add(lworkfrom);

                            tfworkFrom = new JTextField(30);
                            tfworkFrom.setBounds(530, 360, 40, 20);
                            tfworkFrom.setText(staffResult.getString("wh_from"));
                            panel.add(tfworkFrom);

                            lworkto = new JLabel("to :");
                            lworkto.setBounds(590, 360, 20, 15);
                            panel.add(lworkto);

                            tfworkTo = new JTextField(30);
                            tfworkTo.setBounds(630, 360, 40, 20);
                            tfworkTo.setText(staffResult.getString("wh_to"));
                            panel.add(tfworkTo);

                            frame = new JFrame("staff Information");
                            frame.setSize(1000, 720);

                            Container pane = frame.getContentPane();
                            pane.setLayout(new BoxLayout(pane, BoxLayout.Y_AXIS));
                            pane.add(panel);

                            frame.setVisible(true);

                        } else {
                            String message = "No Record Found!";
                            JOptionPane.showMessageDialog(new JFrame(), message, "ERROR!", JOptionPane.ERROR_MESSAGE);
                        }

                        bback2 = new JButton("BACK");
                        bback2.setBounds(460, 600, 110, 30);
                        panel.add(bback2);

                        bback2.addActionListener(new ActionListener() {
                            public void actionPerformed(ActionEvent event) {
                                try {
                                    new Search();
                                    frame.setVisible(false);
                                } catch (Exception e) {
                                    System.out.println("search:" + e);
                                }
                            }
                        });

                    } catch (SQLException sq) {
                        System.out.println("search:" + sq);
                    } catch (Exception e) {
                        System.out.println("Search:" + e);
                    }
                }
            });

            bsearchPatient.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent event) {
                    try {
                        name = tfsearchBox.getText();
                        System.out.println(name);
                        PreparedStatement patient = con.prepareStatement("select * from patient where lower(name)=?");
                        patient.setString(1, name.toLowerCase());

                        ResultSet patientResult = patient.executeQuery();

                        if (patientResult.next()) {
                            setVisible(false);
                            panel = new JPanel();
                            panel.setLayout(null);
                            JLabel lptitle = new JLabel(patientResult.getString("name"));
                            lptitle.setBounds(350, 20, 300, 50);
                            lptitle.setFont(new Font("Serif", Font.BOLD, 30));
                            lptitle.setForeground(Color.BLUE);
                            panel.add(lptitle);

                            lppersonalInfo = new JLabel("Personal Information");
                            lppersonalInfo.setBounds(40, 70, 150, 15);
                            panel.add(lppersonalInfo);

                            lpadd = new JLabel("Address :");
                            lpadd.setBounds(104, 138, 70, 15);
                            panel.add(lpadd);

                            tapadd = new TextArea();
                            tapadd.setBounds(270, 138, 250, 100);
                            tapadd.setText(patientResult.getString("address"));
                            panel.add(tapadd);

                            lpid = new JLabel("Patient No.:");
                            lpid.setBounds(104, 97, 70, 15);
                            panel.add(lpid);

                            tfpid = new JTextField(30);
                            tfpid.setBounds(270, 97, 250, 20);
                            tfpid.setText(patientResult.getString("id"));
                            panel.add(tfpid);

                            lproom = new JLabel("Room No.:");
                            lproom.setBounds(570, 97, 70, 25);
                            panel.add(lproom);

                            tfproom = new JTextField(30);
                            tfproom.setBounds(700, 97, 50, 20);
                            tfproom.setText(patientResult.getString("room"));
                            panel.add(tfproom);

                            lpcon = new JLabel("Contact No.:");
                            lpcon.setBounds(570, 138, 75, 25);
                            panel.add(lpcon);

                            tfpcon = new JTextField(30);
                            tfpcon.setBounds(700, 138, 150, 20);
                            tfpcon.setText(patientResult.getString("contact"));
                            panel.add(tfpcon);

                            lpdob = new JLabel("Date of Birth :");
                            lpdob.setBounds(570, 180, 120, 25);
                            panel.add(lpdob);

                            tfpdob = new JTextField(15);
                            tfpdob.setBounds(700, 180, 80, 20);
                            tfpdob.setText(patientResult.getString("birth_date"));
                            panel.add(tfpdob);

                            lpgender = new JLabel("Gender :");
                            lpgender.setBounds(570, 223, 50, 20);
                            panel.add(lpgender);

                            tfpgender = new JTextField(30);
                            tfpgender.setBounds(700, 223, 60, 20);
                            tfpgender.setText(patientResult.getString("gender"));
                            panel.add(tfpgender);

                            lpmedicalInfo = new JLabel("Medical Information");
                            lpmedicalInfo.setBounds(40, 268, 120, 15);
                            panel.add(lpmedicalInfo);

                            lpbloodGroup = new JLabel("Blood Group :");
                            lpbloodGroup.setBounds(104, 306, 79, 15);
                            panel.add(lpbloodGroup);

                            tfpbloodGroup = new JTextField(30);
                            tfpbloodGroup.setBounds(270, 306, 70, 20);
                            tfpbloodGroup.setText(patientResult.getString("blood_group"));
                            panel.add(tfpbloodGroup);

                            lpadmissionDate = new JLabel("Date Of Admission :");
                            lpadmissionDate.setBounds(575, 306, 120, 15);
                            panel.add(lpadmissionDate);

                            tfpadmissionDate = new JTextField(40);
                            tfpadmissionDate.setBounds(700, 305, 80, 20);
                            tfpadmissionDate.setText(patientResult.getString("admission_date"));
                            panel.add(tfpadmissionDate);

                            lpproblem = new JLabel("Problem Description :");
                            lpproblem.setBounds(104, 365, 150, 15);
                            panel.add(lpproblem);

                            tapproblem = new TextArea();
                            tapproblem.setBounds(270, 365, 600, 100);
                            tapproblem.setText(patientResult.getString("problem"));
                            panel.add(tapproblem);

                            lproomType = new JLabel("Type Of Room : ");
                            lproomType.setBounds(104, 510, 120, 25);
                            panel.add(lproomType);

                            tfproomType = new JTextField(30);
                            tfproomType.setBounds(270, 510, 80, 20);
                            tfproomType.setText(patientResult.getString("room_type"));
                            panel.add(tfproomType);

                            lpdocName = new JLabel("Doctor Name :");
                            lpdocName.setBounds(575, 510, 130, 15);
                            panel.add(lpdocName);

                            tfpdocName = new JTextField(100);
                            tfpdocName.setBounds(700, 510, 170, 20);
                            tfpdocName.setText(patientResult.getString("doctor_name"));
                            panel.add(tfpdocName);

                            frame = new JFrame("Patient Information");
                            frame.setSize(1000, 720);

                            Container pane = frame.getContentPane();
                            pane.setLayout(new BoxLayout(pane, BoxLayout.Y_AXIS));
                            pane.add(panel);

                            frame.setVisible(true);

                        } else {
                            String message = "No Record Found!";
                            JOptionPane.showMessageDialog(new JFrame(), message, "ERROR!", JOptionPane.ERROR_MESSAGE);
                            // System.out.println("No Record found");
                        }

                        bback2 = new JButton("BACK", new ImageIcon("images/restore.png"));
                        bback2.setBounds(460, 600, 110, 30);
                        panel.add(bback2);

                        bback2.addActionListener(new ActionListener() {
                            public void actionPerformed(ActionEvent event) {

                                try {

                                    new Search();
                                    frame.setVisible(false);
                                } catch (Exception e) {
                                    System.out.println("staff:" + e);
                                }
                            }
                        });

                    } catch (SQLException sq) {
                        System.out.println("Staff:" + sq);
                    } catch (Exception e) {
                        System.out.println("Staff:" + e);
                    }
                }
            });

            bsearchDoctor.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent event) {

                    try {

                        name = tfsearchBox.getText();
                        System.out.println(name);
                        PreparedStatement doctor = con.prepareStatement("select * from doctor where lower(name)=?");
                        doctor.setString(1, name.toLowerCase());

                        ResultSet doctorResult = doctor.executeQuery();

                        if (doctorResult.next()) {
                            setVisible(false);
                            panel = new JPanel();
                            panel.setLayout(null);
                            JLabel ldtitle = new JLabel(doctorResult.getString("name"));
                            ldtitle.setBounds(350, 20, 300, 50);
                            ldtitle.setFont(new Font("Serif", Font.BOLD, 30));
                            ldtitle.setForeground(Color.BLUE);
                            panel.add(ldtitle);

                            ldid = new JLabel("Doctor ID:");
                            ldid.setBounds(100, 100, 70, 25);
                            panel.add(ldid);

                            tfdid = new JTextField(30);
                            tfdid.setBounds(270, 100, 50, 20);
                            tfdid.setText(doctorResult.getString("id"));
                            panel.add(tfdid);

                            ldadd = new JLabel("Address :");
                            ldadd.setBounds(100, 138, 70, 15);
                            panel.add(ldadd);

                            tadadd = new TextArea();
                            tadadd.setBounds(270, 138, 250, 100);
                            tadadd.setText(doctorResult.getString("ADDRESS"));
                            panel.add(tadadd);

                            ldcon = new JLabel("Contact :");
                            ldcon.setBounds(570, 100, 50, 25);
                            panel.add(ldcon);

                            tfdcon = new JTextField(30);
                            tfdcon.setBounds(640, 100, 200, 20);
                            tfdcon.setText(doctorResult.getString("contact"));
                            panel.add(tfdcon);

                            ldwork = new JLabel("Working hours: ");
                            ldwork.setBounds(570, 180, 100, 15);
                            panel.add(ldwork);

                            ldworkfrom = new JLabel("From :");
                            ldworkfrom.setBounds(670, 180, 37, 15);
                            panel.add(ldworkfrom);

                            tfdworkFrom = new JTextField(30);
                            tfdworkFrom.setBounds(710, 180, 40, 20);
                            tfdworkFrom.setText(doctorResult.getString("WH_FROM"));
                            panel.add(tfdworkFrom);

                            ldworkto = new JLabel("to :");
                            ldworkto.setBounds(760, 180, 20, 15);
                            panel.add(ldworkto);

                            tfdworkTo = new JTextField(30);
                            tfdworkTo.setBounds(790, 180, 40, 20);
                            tfdworkTo.setText(doctorResult.getString("WH_TO"));
                            panel.add(tfdworkTo);

                            ldspecial = new JLabel("Specialization :");
                            ldspecial.setBounds(100, 310, 100, 25);
                            panel.add(ldspecial);

                            tadspecial = new TextArea();
                            tadspecial.setBounds(270, 310, 600, 100);
                            tadspecial.setText(doctorResult.getString("SPECIALIZATION"));
                            panel.add(tadspecial);

                            ldquali = new JLabel("Qualification :");
                            ldquali.setBounds(100, 440, 100, 25);
                            panel.add(ldquali);

                            tadquali = new TextArea();
                            tadquali.setBounds(270, 440, 600, 100);
                            tadquali.setText(doctorResult.getString("QUALIFICATION"));
                            panel.add(tadquali);

                            frame = new JFrame("Doctor Information");
                            frame.setSize(1000, 720);

                            Container pane = frame.getContentPane();
                            pane.setLayout(new BoxLayout(pane, BoxLayout.Y_AXIS));
                            pane.add(panel);

                            frame.setVisible(true);

                        } else {
                            String message = "No Record Found!";
                            JOptionPane.showMessageDialog(new JFrame(), message, "ERROR!", JOptionPane.ERROR_MESSAGE);
                            // System.out.println("No Record found");
                        }

                        bback2 = new JButton("BACK", new ImageIcon("images/restore.png"));
                        bback2.setBounds(460, 600, 110, 30);
                        panel.add(bback2);

                        bback2.addActionListener(new ActionListener() {
                            public void actionPerformed(ActionEvent event) {

                                try {

                                    new Search();
                                    frame.setVisible(false);
                                } catch (Exception e) {
                                    System.out.println("Doctor:" + e);
                                }
                            }
                        });

                    } catch (SQLException sq) {
                        System.out.println("Doctor:" + sq);
                    } catch (Exception e) {
                        System.out.println("Doctor:" + e);
                    }
                }
            });

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

    public void actionPerformed(ActionEvent ae) {
    }

    public static void main(String[] args) {
        new Search();
    }

}
