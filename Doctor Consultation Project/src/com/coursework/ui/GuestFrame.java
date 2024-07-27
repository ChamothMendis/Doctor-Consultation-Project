package com.coursework.ui;

import com.coursework.admin.SystemUtility;
import com.coursework.domains.Consultation;
import com.coursework.domains.Patient;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Objects;

public class GuestFrame extends MyWindowAdapter implements ActionListener {
    private JFrame jFrame;
    private JTextField nameTf,surnameTf,dateOfBirthTf,mobileNumberTf,patientIdTf;
    private JButton back,next;

    public static Patient patient;
    private final ArrayList<Consultation> consultations;

    /**
     * No args constructor
     */
    public GuestFrame(){
        initialize();
        consultations = SystemUtility.readConsultationFile();
        System.out.println(consultations);
    }

    /**
     * This method is used initialise all the gui objects/components in the frame
     */
    private void initialize() {
        jFrame = new JFrame();
        jFrame.getContentPane().setBackground(new Color(0, 0, 0));
        jFrame.setBounds(100, 100, 1200, 570);
        jFrame.setVisible(true);
        jFrame.setResizable(false);
        jFrame.setTitle("Guest Frame");
        jFrame.setLayout(null);
        jFrame.addWindowListener(this);

        JLabel label = new JLabel("Patient Details");
        label.setBounds(480,40,400,50);
        label.setForeground(Color.pink);
        label.setFont(new Font("arial",Font.BOLD,40));

        Font f = new Font("arial",Font.PLAIN,18);
        Font b = new Font("arial",Font.PLAIN,15);

        JLabel name = new JLabel("Patient Name :");
        name.setForeground(Color.white);
        name.setFont(f);
        name.setBounds(60,150,130,20);

        nameTf = new JTextField();
        nameTf.setFont(b);
        nameTf.setBounds(300,150,250,30);

        JLabel surname = new JLabel("Surname :");
        surname.setForeground(Color.white);
        surname.setFont(f);
        surname.setBounds(700,150,130,20);

        surnameTf = new JTextField();
        surnameTf.setFont(b);
        surnameTf.setBounds(850,150,250,30);

        JLabel dateOfBirth = new JLabel("Date of Birth :");
        dateOfBirth.setForeground(Color.white);
        dateOfBirth.setFont(f);
        dateOfBirth.setBounds(60,220,130,20);

        dateOfBirthTf = new JTextField();
        dateOfBirthTf.setFont(b);
        dateOfBirthTf.setBounds(300,220,250,30);

        JLabel mobileNumber = new JLabel("Mobile Number :");
        mobileNumber.setForeground(Color.white);
        mobileNumber.setFont(f);
        mobileNumber.setBounds(700,220,160,20);

        mobileNumberTf = new JTextField();
        mobileNumberTf.setFont(b);
        mobileNumberTf.setBounds(850,220,250,30);

        JLabel patientId = new JLabel("Patient ID :");
        patientId.setForeground(Color.white);
        patientId.setFont(f);
        patientId.setBounds(60,290,130,20);

        JLabel instruction = new JLabel("(Auto generating field )"); //enter "-" if you are here first time
        instruction.setBounds(60,310,210,20);

        patientIdTf = new JTextField();
        patientIdTf.setFont(b);
        patientIdTf.setBounds(300,290,250,30);
        patientIdTf.setEditable(false);

        next = new JButton("Next");
        next.setBounds(1000,475,100,30);
        next.setBackground(Color.cyan);
        next.addActionListener(this);

        back = new JButton("Back");
        back.setBounds(60,475,100,30);
        back.setBackground(Color.cyan);
        back.addActionListener(this);

        jFrame.add(name);jFrame.add(nameTf);jFrame.add(surname);jFrame.add(surnameTf);
        jFrame.add(dateOfBirth);jFrame.add(dateOfBirthTf);jFrame.add(mobileNumber);jFrame.add(mobileNumberTf);
        jFrame.add(patientId);jFrame.add(patientIdTf);jFrame.add(next);jFrame.add(back);jFrame.add(label);

    }

    /**
     * This method is used to get the frame
     * @return jFrame
     */
    public JFrame getJFrame() {
        return jFrame;
    }


    /**
     * This method is used to get the patient
     * @return patient
     */
    public static Patient getPatient() {
        return patient;
    }


    /**
     * This method is used to set the patient
     * @param patient passing
     */
    public static void setPatient(Patient patient) {
        GuestFrame.patient = patient;
    }

    /**
     * @param e the event to be processed
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == back){
            ViewBookingFrame.exitNavigationToManager(jFrame);
        }

        if (e.getSource() == next){
            int genPatientId = 0;
            if (!SystemUtility.isWord(nameTf.getText())) {
                nameTf.setText("");
                JOptionPane.showMessageDialog(null, "Valid name Required !");
                nameTf.requestFocus();

            } else if (!SystemUtility.isWord(surnameTf.getText())) {
                surnameTf.setText("");
                JOptionPane.showMessageDialog(null, "Valid Surname Required !");
                surnameTf.requestFocus();

            } else if (!SystemUtility.isDateValid(dateOfBirthTf.getText())) {
                dateOfBirthTf.setText("");
                JOptionPane.showMessageDialog(null, "Valid Date Required !");
                dateOfBirthTf.requestFocus();

            } else if (!SystemUtility.isValidMobileNumber(mobileNumberTf.getText())) {
                mobileNumberTf.setText("");
                JOptionPane.showMessageDialog(null, "Valid Mobile Number Required");
                mobileNumberTf.requestFocus();
            }else {
                int count = 0;
                for (Consultation consultation : consultations){
                    if ((Objects.equals(consultation.getPatient().getMobileNumber(), mobileNumberTf.getText()) && consultation.getPatient().getDateOfBirth().equals(dateOfBirthTf.getText())) && consultation.getPatient().getName().equals(nameTf.getText())){
                        if (consultation.getPatient().getSurname().equals(surnameTf.getText())){
                            patientIdTf.setText(consultation.getPatient().getPatientId());
                            count = count + 1;
                            break;
                        }
                    }
                }
                if (count == 0){
                    int min = 1001;
                    int max = 9999;
                    genPatientId = (int) (Math.random() * (max - min + 1) + min);
                    while (true) {
                        try {
                            if (!(RegistrationFrame.isIdAlreadyAssigned(String.valueOf(genPatientId)) == 1)) break;
                            genPatientId = (int) (Math.random() * (max - min + 1) + min);
                        } catch (IOException | ClassNotFoundException ex) {
                            throw new RuntimeException(ex);
                        }
                    }
                    patientIdTf.setText(String.valueOf(genPatientId));
                }
            }

            String nameVal = nameTf.getText();
            String surnameVal = surnameTf.getText();
            String dateOfBirthVal = dateOfBirthTf.getText();
            String mobileNumberVal = mobileNumberTf.getText();
            String patientIdVal = patientIdTf.getText();

            if ((!nameVal.equals("") && (!surnameVal.equals("") && (!dateOfBirthVal.equals("") && (!mobileNumberVal.equals("")))))){
                patient = new Patient(nameVal,surnameVal,dateOfBirthVal,mobileNumberVal,patientIdVal,"");
                JOptionPane.showMessageDialog(null,"You have registered with id " + genPatientId);
                jFrame.setVisible(false);
                try {
                    DoctorsListFrame doctorsListFrame = new DoctorsListFrame();
                    doctorsListFrame.getJFrame().setVisible(true);
                    jFrame.dispose();
                } catch (ParseException ex) {
                    throw new RuntimeException(ex);
                }
            }
        }
    }
}
