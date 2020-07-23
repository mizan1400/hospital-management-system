package hms;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

class Info extends JFrame implements ActionListener {

    JLabel lpass, lmain, llname, luserName;
    JTextField tfpass, tfuserName;
    JButton baddDoctor, bviewDoctor, baddPatient, bviewPatient, bviewStuff, baddStuff, bsub, bclr, bback, bsearch, broom, bbill, bviewBill;

    Info() {
        super("Information");
        setSize(1000, 720);
        setVisible(true);
        setLayout(null);

        lmain = new JLabel("Hospital Management System");
        lmain.setBounds(300, 50, 400, 40);
        lmain.setFont(new Font("Serif", Font.BOLD, 30));
        lmain.setForeground(Color.BLUE);
        add(lmain);

        baddDoctor = new JButton("Add New Doctor", new ImageIcon("images/add_doctor.png"));
        baddDoctor.setBounds(175, 130, 300, 60);
        add(baddDoctor);

        bviewDoctor = new JButton("View Doctor List", new ImageIcon("images/doctor.png"));
        bviewDoctor.setBounds(525, 130, 300, 60);
        add(bviewDoctor);

        baddPatient = new JButton("Patient Registration", new ImageIcon("images/add_doctor.png"));
        baddPatient.setBounds(175, 220, 300, 60);
        add(baddPatient);

        bviewPatient = new JButton("View Patient", new ImageIcon("images/patient.png"));
        bviewPatient.setBounds(525, 220, 300, 60);
        add(bviewPatient);

        baddStuff = new JButton("Stuff Registration", new ImageIcon("images/add_doctor.png"));
        baddStuff.setBounds(175, 310, 300, 60);
        add(baddStuff);

        bviewStuff = new JButton("View Stuff", new ImageIcon("images/stuff.png"));
        bviewStuff.setBounds(525, 310, 300, 60);
        add(bviewStuff);

        bsearch = new JButton("Search", new ImageIcon("images/Search.png"));
        bsearch.setBounds(175, 400, 300, 60);
        add(bsearch);

        broom = new JButton("Room", new ImageIcon("images/room.png"));
        broom.setBounds(525, 400, 300, 60);
        add(broom);

        baddDoctor.addActionListener(new AddDoctor());
        bviewDoctor.addActionListener(new ViewDoctor());
        baddPatient.addActionListener(new AddPatient());
        bviewPatient.addActionListener(new ViewPatient());
        baddStuff.addActionListener(new AddStuff());
        bviewStuff.addActionListener(new ViewStuff());
        bsearch.addActionListener(new BSearch());
        broom.addActionListener(new BRoom());
    }

    class AddDoctor implements ActionListener {

        public void actionPerformed(ActionEvent ae) {
            new DoctorReg();
            setVisible(false);
        }
    }

    class ViewDoctor implements ActionListener {

        public void actionPerformed(ActionEvent ae) {
            new DoctorList();
            setVisible(false);
        }
    }

    class AddPatient implements ActionListener {

        public void actionPerformed(ActionEvent ae) {
            new PatientReg();
            setVisible(false);
        }
    }

    class ViewPatient implements ActionListener {

        public void actionPerformed(ActionEvent ae) {
            new PatientList();
            setVisible(false);
        }
    }

    class AddStuff implements ActionListener {

        public void actionPerformed(ActionEvent ae) {
            new StaffReg();
            setVisible(false);
        }
    }

    class ViewStuff implements ActionListener {

        public void actionPerformed(ActionEvent ae) {
            new StaffList();
            setVisible(false);
        }
    }

    class BSearch implements ActionListener {

        public void actionPerformed(ActionEvent ae) {
            new Search();
            setVisible(false);
        }
    }

    class BRoom implements ActionListener {

        public void actionPerformed(ActionEvent ae) {
            new Room();
            setVisible(false);
        }
    }

    public void actionPerformed(ActionEvent ae) {
    }

    public static void main(String[] args) {
        new Info();
    }

}
