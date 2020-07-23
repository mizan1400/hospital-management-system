package hms;

import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import javax.swing.*;

class Room extends JFrame implements ActionListener {

    static Connection con = null;
    Statement st = null;
    ResultSet rs = null;

    JLabel lcpass, lpass, lfname, lmain, llname, luserName;
    JButton bsub, broom, bback;
    JPasswordField pass, confirmPass;

    Room() {
        super("Room");
        setSize(1000, 720);
        setVisible(true);
        setLayout(null);

        try {
            Class.forName("oracle.jdbc.driver.OracleDriver");
            con = DriverManager.getConnection("jdbc:oracle:thin:@Mizan:1521:XE", "mizan", "123456");

            PreparedStatement stmt = con.prepareStatement("select room from patient");

            ResultSet roomList = stmt.executeQuery();
            int rooms[] = new int[200];

            while (roomList.next()) {
                rooms[roomList.getInt(1)] = 1;
            }

            int roomNo, x = 10, y = 110;

            for (roomNo = 1; roomNo <= 100; roomNo++) {
                String no = "Room " + roomNo;

                broom = new JButton(no);
                broom.setBounds(x, y, 90, 30);

                if (rooms[roomNo] == 1) {
                    broom.setBackground(Color.RED);
                } else {
                    broom.setBackground(Color.GREEN);
                }

                add(broom);
                x = (x + 97);
                if (x >= 980) {
                    x = 10;
                    y = (y + 40);
                }
            }
        } catch (Exception e) {
            System.out.println(e);
        }

        lmain = new JLabel("Room");
        lmain.setBounds(420, 20, 160, 50);
        lmain.setFont(new Font("Serif", Font.BOLD, 30));
        lmain.setForeground(Color.BLUE);
        add(lmain);

        bback = new JButton("BACK", new ImageIcon("images/restore.png"));
        bback.setBounds(440, 600, 110, 30);
        add(bback);
        
        JButton bindicator1 = new JButton("Available");
        bindicator1.setBounds(380, 550, 90, 30);
        bindicator1.setBackground(Color.GREEN);
        add(bindicator1);
        
        JButton bindicator2 = new JButton("Booked");
        bindicator2.setBounds(500, 550, 90, 30);
        bindicator2.setBackground(Color.RED);
        add(bindicator2);

        bback.addActionListener(new Back());
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
        new Room();
    }
}
