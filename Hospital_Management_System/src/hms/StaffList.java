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
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class StaffList extends JFrame {

    static Connection con = null;
    Statement st = null;
    ResultSet rs = null;
    PreparedStatement stmt = null;

    static JFrame frame;
    private static JPanel panel;

    JButton bstaff[] = new JButton[100];
    String staff[] = new String[100];
    JLabel lmain, lname, ladd, lcon, lid, lwork, lworkfrom, lworkto, ltitle;
    JTextField tfname, tfcon, tfid, tfworkFrom, tfworkTo;
    TextArea taadd;
    JButton bsub, bclr, bback, bback2, bupdate, bdelete;
    String name;
    int i, k, row;

    StaffList() {
        super("Staff List");
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

            stmt = con.prepareStatement("select name from staff");

            ResultSet staffList = stmt.executeQuery();
            int x = 100;
            i = 1;

            ltitle = new JLabel("Staff List");
            ltitle.setBounds(430, 40, 150, 30);
            ltitle.setFont(new Font("Serif", Font.BOLD, 30));
            ltitle.setForeground(Color.BLUE);
            add(ltitle);

            while (staffList.next()) {
                row = staffList.getRow();
                staff[i] = staffList.getString(1);
                System.out.println(staff[i]);
                bstaff[i] = new JButton(staff[i]);
                bstaff[i].setBounds(400, x, 200, 20);
                add(bstaff[i]);

                x += 25;
                i++;
            }

            for (k = 1; k <= row; k++) {

                bstaff[k].addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent event) {

                        try {
                            if (event.getSource() instanceof JButton) {
                                setVisible(false);
                                name = ((JButton) event.getSource()).getText();
                                System.out.println(name);

                                PreparedStatement stmt2 = con.prepareStatement("select * from staff where name=?");
                                stmt2.setString(1, name);

                                ResultSet staffInfo = stmt2.executeQuery();

                                staffInfo.next();
                                panel = new JPanel();
                                panel.setLayout(null);
                                JLabel ltitle = new JLabel(name);
                                ltitle.setBounds(350, 20, 300, 50);
                                ltitle.setFont(new Font("Serif", Font.BOLD, 30));
                                ltitle.setForeground(Color.BLUE);
                                panel.add(ltitle);

                                lid = new JLabel("Staff ID:");
                                lid.setBounds(300, 140, 70, 25);
                                panel.add(lid);

                                tfid = new JTextField(30);
                                tfid.setBounds(470, 140, 50, 20);
                                tfid.setText(staffInfo.getString("id"));
                                panel.add(tfid);

                                lcon = new JLabel("Contact :");
                                lcon.setBounds(300, 180, 70, 25);
                                panel.add(lcon);

                                tfcon = new JTextField(30);
                                tfcon.setBounds(470, 180, 250, 20);
                                tfcon.setText(staffInfo.getString("contact"));
                                panel.add(tfcon);

                                ladd = new JLabel("Address :");
                                ladd.setBounds(300, 220, 70, 25);
                                panel.add(ladd);

                                taadd = new TextArea();
                                taadd.setBounds(470, 220, 250, 100);
                                taadd.setText(staffInfo.getString("address"));
                                panel.add(taadd);

                                lwork = new JLabel("Working hours :");
                                lwork.setBounds(300, 360, 100, 15);
                                panel.add(lwork);

                                lworkfrom = new JLabel("From :");
                                lworkfrom.setBounds(470, 360, 40, 15);
                                panel.add(lworkfrom);

                                tfworkFrom = new JTextField(30);
                                tfworkFrom.setBounds(530, 360, 40, 20);
                                tfworkFrom.setText(staffInfo.getString("wh_from"));
                                panel.add(tfworkFrom);

                                lworkto = new JLabel("to :");
                                lworkto.setBounds(590, 360, 20, 15);
                                panel.add(lworkto);

                                tfworkTo = new JTextField(30);
                                tfworkTo.setBounds(630, 360, 40, 20);
                                tfworkTo.setText(staffInfo.getString("wh_to"));
                                panel.add(tfworkTo);

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
                                            PreparedStatement stmtUpdate = con.prepareStatement(
                                                    "update staff set CONTACT=?,	ADDRESS=?,	WH_FROM=?,	WH_TO=? where name=?");

                                            // stmtUpdate.setString(1,tfname.getText());
                                            stmtUpdate.setString(1, tfcon.getText());
                                            stmtUpdate.setString(2, taadd.getText());
                                            stmtUpdate.setString(3, tfworkFrom.getText());
                                            stmtUpdate.setString(4, tfworkTo.getText());
                                            stmtUpdate.setString(5, name);

                                            stmtUpdate.executeUpdate();

                                            String message = "Succesfully Updated!";
                                            JOptionPane.showMessageDialog(new JFrame(), message, "Done!",
                                                    JOptionPane.INFORMATION_MESSAGE);
                                            new StaffList();
                                            frame.setVisible(false);
                                        } catch (SQLException sq) {
                                            System.out.println("Staff Update:" + sq);
                                        } catch (Exception e) {
                                            System.out.println("Staff Update:" + e);
                                        }
                                    }
                                });

                                bdelete = new JButton("DELETE", new ImageIcon("images/delete.png"));
                                bdelete.setBounds(460, 600, 100, 30);
                                panel.add(bdelete);

                                bdelete.addActionListener(new ActionListener() {
                                    public void actionPerformed(ActionEvent event) {

                                        try {
                                            PreparedStatement stmtDelete = con
                                                    .prepareStatement("delete from staff where name=?");
                                            stmtDelete.setString(1, name);

                                            stmtDelete.executeUpdate();

                                            String message = "Successfully Deleted!";
                                            JOptionPane.showMessageDialog(new JFrame(), message, "Done!",
                                                    JOptionPane.INFORMATION_MESSAGE);
                                            new StaffList();
                                            frame.setVisible(false);
                                        } catch (SQLException sq) {
                                            System.out.println("staff Delete:" + sq);
                                        } catch (Exception e) {
                                            System.out.println("staff Delete:" + e);
                                        }
                                    }
                                });

                                frame = new JFrame("Staff Information");
                                frame.setSize(1000, 720);

                                Container pane = frame.getContentPane();
                                pane.setLayout(new BoxLayout(pane, BoxLayout.Y_AXIS));
                                pane.add(panel);

                                frame.setVisible(true);

                                //System.out.println("Address: " + staffInfo.getString("address"));
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
            new StaffList();
            frame.setVisible(false);
        }
    }

    public void actionPerformed(ActionEvent ae) {
    }

    public static void main(String[] args) {

        new StaffList();
    }
}
