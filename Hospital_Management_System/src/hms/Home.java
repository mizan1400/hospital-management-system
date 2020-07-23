package hms;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class Home extends JFrame implements ActionListener {

    static JFrame frame;
    private static JPanel panel;
    private JButton reg, img;
    private JButton signIn;
    private JLabel ltitle;

    Home() {
        panel = new JPanel();
        panel.setLayout(null);

        ltitle = new JLabel("Hospital Managment System");
        ltitle.setBounds(317, 80, 365, 50);
        ltitle.setFont(new Font("Serif", Font.BOLD, 30));
        ltitle.setForeground(Color.BLUE);

        //ImageIcon img = new ImageIcon("images/Hospital1.png");
        img = new JButton("", new ImageIcon("images/Hospital1.png"));
        img.setBounds(350, 150, 300, 300);

        reg = new JButton("Register", new ImageIcon("images/keys.gif"));
        reg.setBounds(350, 500, 150, 30);

        signIn = new JButton("Sign in", new ImageIcon("images/Key.gif"));
        signIn.setBounds(550, 500, 110, 30);

        panel.add(ltitle);
        panel.setOpaque(true);

        panel.add(img);
        panel.add(reg);
        panel.add(signIn);

        frame = new JFrame("Hospital Managment System");
        frame.setSize(1000, 720);

        Container pane = frame.getContentPane();
        pane.setLayout(new BoxLayout(pane, BoxLayout.Y_AXIS));
        pane.add(panel);
        frame.setVisible(true);

        reg.addActionListener(new Reg());
        signIn.addActionListener(new SignIn());
    }

    class Reg implements ActionListener {
        public void actionPerformed(ActionEvent ae) {
            new Registration();
            frame.setVisible(false);
        }
    }

    class SignIn implements ActionListener {
        public void actionPerformed(ActionEvent ae) {
            new Login();
            frame.setVisible(false);
        }
    }

    public void actionPerformed(ActionEvent ae) {
    }

    public static void main(String[] args) {
        new Home();
    }
}
