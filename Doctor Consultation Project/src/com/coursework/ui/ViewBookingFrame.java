package com.coursework.ui;

import com.coursework.domains.Consultation;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ViewBookingFrame extends MyWindowAdapter implements ActionListener {
    private JFrame jFrame;
    private JButton exit;
    private Consultation consultation = DoctorsListFrame.getConsultation();
    public ViewBookingFrame(){
        initialize();
    }

    /**
     * This method is used to initialise all the gui objects/components that is in the frame
     */
    private void initialize(){
        jFrame = new JFrame();
        jFrame.getContentPane().setBackground(new Color(0, 0, 0));
        jFrame.setBounds(100, 100, 1200, 570);
        jFrame.addWindowListener(this);
        jFrame.setResizable(false);
        jFrame.getContentPane().setLayout(null);
        jFrame.setVisible(true);

        Font f = new Font(Font.SANS_SERIF, Font.PLAIN, 20);

        ImageIcon logo = new ImageIcon("done-logo.png");
        JLabel logoLabel = new JLabel("");
        logoLabel.setIcon(logo);
        logoLabel.setBounds(550,150,600,200);

        JLabel patient = new JLabel("Patient :");
        patient.setBounds(20,73,170,30);
        patient.setForeground(Color.white);
        patient.setFont(f);

        JLabel patientDetails = new JLabel();
        patientDetails.setBounds(130,73,400,30);
        patientDetails.setForeground(Color.white);
        patientDetails.setText(consultation.getPatient().getName() + " " + consultation.getPatient().getSurname() + "( ID :"
        + consultation.getPatient().getPatientId() + ")");
        patientDetails.setFont(f);

        JLabel doctor = new JLabel("Doctor :");
        doctor.setBounds(20,150,170,30);
        doctor.setForeground(Color.white);
        doctor.setFont(f);

        JLabel doctorDetails = new JLabel();
        doctorDetails.setBounds(130,150,700,30);
        doctorDetails.setForeground(Color.white);
        doctorDetails.setText(consultation.getDoctor().getName() + " " +  consultation.getDoctor().getSurname() + " (" + consultation.getDoctor().getSpecialisation() + ")");
        doctorDetails.setFont(f);

        JLabel date = new JLabel("Date :");
        date.setBounds(20,227,170,30);
        date.setForeground(Color.white);
        date.setFont(f);

        JLabel dateDetails = new JLabel();
        dateDetails.setBounds(130,227,200,30);
        dateDetails.setForeground(Color.white);
        dateDetails.setText(consultation.getDate());
        dateDetails.setFont(f);

        JLabel time = new JLabel("Time :");
        time.setBounds(20,304,170,30);
        time.setForeground(Color.white);
        time.setFont(f);

        JLabel timeDetails = new JLabel();
        timeDetails.setBounds(130,304,200,30);
        timeDetails.setForeground(Color.white);
        timeDetails.setText(consultation.getTime());
        timeDetails.setFont(f);

        JLabel cost = new JLabel("Cost :");
        cost.setBounds(20,381,170,30);
        cost.setForeground(Color.white);
        cost.setFont(f);

        JLabel costDetails = new JLabel();
        costDetails.setBounds(130,381,200,30);
        costDetails.setForeground(Color.white);
        costDetails.setText(String.valueOf(consultation.getCost()));
        costDetails.setFont(f);

        exit = new JButton("Exit");
        exit.setBounds(1000,475,100,30);
        exit.addActionListener(this);
        exit.setBackground(Color.cyan);

        jFrame.add(patient);jFrame.add(patientDetails);jFrame.add(doctor);jFrame.add(doctorDetails);jFrame.add(date);
        jFrame.add(dateDetails);jFrame.add(time);jFrame.add(timeDetails);jFrame.add(cost);jFrame.add(costDetails);jFrame.add(exit);
        jFrame.add(logoLabel);
    }

    /**
     * This method is used to get the consultation object
     * @return consultation
     */
    public Consultation getConsultation() {
        return consultation;
    }

    /**
     * This method is used to set the consultation object
     * @param consultation passing
     */
    public void setConsultation(Consultation consultation) {
        this.consultation = consultation;
    }

    /**
     * This method is used to get the frame object
     * @return jFrame
     */
    public JFrame getJFrame() {
        return jFrame;
    }

    /**
     * @param e the event to be processed
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == exit){
            if (LoginDetailsFrame.getPatient() == null && RegistrationFrame.getPatient() == null){
                exitNavigationToManager(jFrame);
            }else {
                exitNavigationToPatient(jFrame);
            }
        }
    }

    public static void exitNavigationToManager(JFrame jFrame){
        jFrame.dispose();
        LoginFrame loginFrame = new LoginFrame();
        loginFrame.getRegister().setVisible(false);
        loginFrame.getLogin().setVisible(false);
        loginFrame.getGuest().setVisible(true);
        loginFrame.getGuest().setBounds(300,100,275,40);
        loginFrame.getMyBookings().setBounds(600,100,275,40);
        LoginDetailsFrame.setPatient(null);
        RegistrationFrame.setPatient(null);
        GuestFrame.setPatient(null);
    }

    public static void exitNavigationToPatient(JFrame jFrame){
        jFrame.dispose();
        LoginFrame loginFrame = new LoginFrame();
        loginFrame.getGuest().setVisible(false);
        loginFrame.getLogin().setBounds(150,100,275,40);
        loginFrame.getRegister().setBounds(480,100,275,40);
        loginFrame.getMyBookings().setBounds(800,100,275,40);
        loginFrame.getJFrame().setVisible(true);
        LoginDetailsFrame.setPatient(null);
        RegistrationFrame.setPatient(null);
        GuestFrame.setPatient(null);
    }
}
