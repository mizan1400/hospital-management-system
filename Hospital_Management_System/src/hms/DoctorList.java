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

import hms.DoctorReg.Back;

public class DoctorList extends JFrame {

    static Connection con = null;
    Statement st = null;
    ResultSet rs = null;
    PreparedStatement stmt = null;

    static JFrame frame;
    private static JPanel panel;

    JButton bdoctor[] = new JButton[100], bdelete;
    String doctor[] = new String[100];
    JLabel lmain, lname, ladd, lcon, lspecial, ldid, lquali, lwork, lworkfrom, lworkto, ltitle;
    JTextField tfname, tfcon, tfdid, tfworkFrom, tfworkTo;
    TextArea taadd, taspecial, taquali;
    JButton bsub, bclr, bback, bback2, bupdate;
    String name;
    int i, k, row;

    DoctorList() {
        super("Doctor List");
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

            stmt = con.prepareStatement("select id,name from doctor");

            ResultSet docList = stmt.executeQuery();
            int x = 100;
            i = 1;

            ltitle = new JLabel("Doctor List");
            ltitle.setBounds(430, 40, 150, 30);
            ltitle.setFont(new Font("Serif", Font.BOLD, 30));
            ltitle.setForeground(Color.BLUE);
            add(ltitle);

            while (docList.next()) {
                row = docList.getRow();
                doctor[i] = docList.getString(2);
                System.out.println(doctor[i]);
                bdoctor[i] = new JButton(doctor[i]);
                bdoctor[i].setBounds(400, x, 200, 20);
                add(bdoctor[i]);

                x += 25;
                i++;
            }

            for (k = 1; k <= row; k++) {

                bdoctor[k].addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent event) {

                        try {
                            if (event.getSource() instanceof JButton) {
                                setVisible(false);
                                name = ((JButton) event.getSource()).getText();

                                PreparedStatement stmt2 = con.prepareStatement("select * from doctor where name=?");
                                stmt2.setString(1, name);

                                ResultSet docInfo = stmt2.executeQuery();

                                docInfo.next();
                                panel = new JPanel();
                                panel.setLayout(null);
                                JLabel ltitle = new JLabel(name);
                                ltitle.setBounds(350, 20, 300, 50);
                                ltitle.setFont(new Font("Serif", Font.BOLD, 30));
                                ltitle.setForeground(Color.BLUE);
                                panel.add(ltitle);

                                ldid = new JLabel("Doctor ID:");
                                ldid.setBounds(100, 100, 70, 25);
                                panel.add(ldid);

                                tfdid = new JTextField(30);
                                tfdid.setBounds(270, 100, 50, 20);
                                tfdid.setText(docInfo.getString("id"));
                                panel.add(tfdid);

                                ladd = new JLabel("Address :");
                                ladd.setBounds(100, 138, 70, 15);
                                panel.add(ladd);

                                taadd = new TextArea();
                                taadd.setBounds(270, 138, 250, 100);
                                taadd.setText(docInfo.getString("ADDRESS"));
                                panel.add(taadd);

                                lcon = new JLabel("Contact :");
                                lcon.setBounds(570, 100, 50, 25);
                                panel.add(lcon);

                                tfcon = new JTextField(30);
                                tfcon.setBounds(640, 100, 200, 20);
                                tfcon.setText(docInfo.getString("contact"));
                                panel.add(tfcon);

                                lwork = new JLabel("Working hours: ");
                                lwork.setBounds(570, 180, 100, 15);
                                panel.add(lwork);

                                lworkfrom = new JLabel("From :");
                                lworkfrom.setBounds(670, 180, 37, 15);
                                panel.add(lworkfrom);

                                tfworkFrom = new JTextField(30);
                                tfworkFrom.setBounds(710, 180, 40, 20);
                                tfworkFrom.setText(docInfo.getString("WH_FROM"));
                                panel.add(tfworkFrom);

                                lworkto = new JLabel("to :");
                                lworkto.setBounds(760, 180, 20, 15);
                                panel.add(lworkto);

                                tfworkTo = new JTextField(30);
                                tfworkTo.setBounds(790, 180, 40, 20);
                                tfworkTo.setText(docInfo.getString("WH_TO"));
                                panel.add(tfworkTo);

                                lspecial = new JLabel("Specialization :");
                                lspecial.setBounds(100, 310, 100, 25);
                                panel.add(lspecial);

                                taspecial = new TextArea();
                                taspecial.setBounds(270, 310, 600, 100);
                                taspecial.setText(docInfo.getString("SPECIALIZATION"));
                                panel.add(taspecial);

                                lquali = new JLabel("Qualification :");
                                lquali.setBounds(100, 440, 100, 25);
                                panel.add(lquali);

                                taquali = new TextArea();
                                taquali.setBounds(270, 440, 600, 100);
                                taquali.setText(docInfo.getString("QUALIFICATION"));
                                panel.add(taquali);

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
                                                    "update doctor set ADDRESS=?,	CONTACT=?,	WH_FROM=?, WH_TO=?,	SPECIALIZATION=?,	QUALIFICATION=? where name=?");

                                            stmtUpdate.setString(1, taadd.getText());
                                            stmtUpdate.setString(2, tfcon.getText());
                                            stmtUpdate.setString(3, tfworkFrom.getText());
                                            stmtUpdate.setString(4, tfworkTo.getText());
                                            stmtUpdate.setString(5, taspecial.getText());
                                            stmtUpdate.setString(6, taquali.getText());
                                            stmtUpdate.setString(7, name);

                                            stmtUpdate.executeUpdate();

                                            String message = "Succesfully Updated!";
                                            JOptionPane.showMessageDialog(new JFrame(), message, "Done!",
                                                    JOptionPane.INFORMATION_MESSAGE);
                                            new DoctorList();
                                            frame.setVisible(false);
                                        } catch (SQLException sq) {
                                            System.out.println("Update:" + sq);
                                        } catch (Exception e) {
                                            System.out.println("Update:" + e);
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
                                                    .prepareStatement("delete from doctor where name=?");
                                            stmtDelete.setString(1, name);

                                            stmtDelete.executeUpdate();

                                            String message = "Successfully Deleted!";
                                            JOptionPane.showMessageDialog(new JFrame(), message, "Done!",
                                                    JOptionPane.INFORMATION_MESSAGE);
                                            new DoctorList();
                                            frame.setVisible(false);
                                        } catch (SQLException sq) {
                                            System.out.println("Delete:" + sq);
                                        } catch (Exception e) {
                                            System.out.println("Delete:" + e);
                                        }
                                    }
                                });

                                frame = new JFrame("Doctor Information");
                                frame.setSize(1000, 720);

                                Container pane = frame.getContentPane();
                                pane.setLayout(new BoxLayout(pane, BoxLayout.Y_AXIS));
                                pane.add(panel);

                                frame.setVisible(true);

								// System.out.println("Address: " +
                                // docInfo.getString("address"));
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
            new DoctorList();
            frame.setVisible(false);
        }
    }

    public void actionPerformed(ActionEvent ae) {
    }

    public static void main(String[] args) {

        new DoctorList();
    }
}
