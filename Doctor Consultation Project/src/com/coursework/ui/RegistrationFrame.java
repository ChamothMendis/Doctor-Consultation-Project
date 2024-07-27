package com.coursework.ui;


import com.coursework.admin.SystemUtility;
import com.coursework.domains.Consultation;
import com.coursework.domains.Patient;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.regex.Pattern;

public class RegistrationFrame extends MyWindowAdapter implements ActionListener {
    private static Patient patient;
    private JFrame jFrame;
    private JTextField nameTf, surnameTf, dateOfBirthTf, mobileNumberTf, patientIdTf;
    private JPasswordField passwordPF;
    private JButton register,back;
    public RegistrationFrame() {
        initialize();
    }
    private void initialize(){
        jFrame = new JFrame();
        jFrame.getContentPane().setBackground(new Color(0, 0, 0));
        jFrame.setBounds(100, 100, 1200, 570);
        jFrame.setVisible(true);
        jFrame.setResizable(false);
        jFrame.setTitle("Registration Frame");
        jFrame.setLayout(null);
        jFrame.addWindowListener(this);

        Font f = new Font("arial",Font.PLAIN,18);
        Font b = new Font("arial",Font.PLAIN,15);

        ImageIcon logo = new ImageIcon("logo.png");
        JLabel logoLabel = new JLabel("");
        logoLabel.setIcon(logo);
        logoLabel.setBounds(600,130,500,200);

        JLabel name = new JLabel("First Name :");
        name.setForeground(Color.white);
        name.setFont(f);
        name.setBounds(20,50,130,20);

        nameTf = new JTextField();
        nameTf.setFont(b);
        nameTf.setBounds(240,50,250,30);

        JLabel surname = new JLabel("Surname :");
        surname.setForeground(Color.white);
        surname.setFont(f);
        surname.setBounds(20,120,130,20);

        surnameTf = new JTextField();
        surnameTf.setFont(b);
        surnameTf.setBounds(240,120,250,30);

        JLabel dateOfBirth = new JLabel("Date of Birth :");
        dateOfBirth.setForeground(Color.white);
        dateOfBirth.setFont(f);
        dateOfBirth.setBounds(20,190,130,20);

        dateOfBirthTf = new JTextField();
        dateOfBirthTf.setFont(b);
        dateOfBirthTf.setBounds(240,190,250,30);

        JLabel mobileNumber = new JLabel("Mobile Number :");
        mobileNumber.setForeground(Color.white);
        mobileNumber.setFont(f);
        mobileNumber.setBounds(20,260,160,20);

        mobileNumberTf = new JTextField();
        mobileNumberTf.setFont(b);
        mobileNumberTf.setBounds(240,260,250,30);

        JLabel password = new JLabel("Password :");
        password.setForeground(Color.white);
        password.setFont(f);
        password.setBounds(20,330,130,20);

        passwordPF = new JPasswordField();
        passwordPF.setFont(new Font("arial",Font.ITALIC,40));
        passwordPF.setBounds(240,330,250,30);

        JLabel patientId = new JLabel("Patient ID :");
        patientId.setForeground(Color.white);
        patientId.setFont(f);
        patientId.setBounds(20,400,130,20);

        JLabel instruction = new JLabel("(Auto generating field )"); //enter "-" if you are here first time
        instruction.setBounds(20,425,210,20);

        patientIdTf = new JTextField();
        patientIdTf.setFont(b);
        patientIdTf.setBounds(240,400,250,30);
        patientIdTf.setEditable(false);

        register = new JButton("Register");
        register.setBounds(300,460,120,40);
        register.addActionListener(this);
        register.setBackground(Color.cyan);

        back = new JButton("Back");
        back.setBounds(1000,475,100,30);
        back.addActionListener(this);
        back.setBackground(Color.cyan);

        jFrame.getContentPane().add(name);jFrame.getContentPane().add(nameTf);jFrame.getContentPane().add(surname);jFrame.getContentPane().add(surnameTf);
        jFrame.getContentPane().add(dateOfBirth);jFrame.getContentPane().add(dateOfBirthTf);jFrame.getContentPane().add(mobileNumber);
        jFrame.getContentPane().add(mobileNumberTf);jFrame.getContentPane().add(patientId);jFrame.getContentPane().add(patientIdTf);
        jFrame.getContentPane().add(instruction);jFrame.getContentPane().add(password);jFrame.getContentPane().add(passwordPF);
        jFrame.getContentPane().add(register);jFrame.getContentPane().add(logoLabel);jFrame.getContentPane().add(back);
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
        RegistrationFrame.patient = patient;
    }

    /**
     * @param e the event to be processed
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == register){
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

            } else if (!isValidPassword(String.valueOf(passwordPF.getPassword())) || passwordPF.getPassword().length < 6) {
                passwordPF.setText("");
                JOptionPane.showMessageDialog(null,"Invalid password");
                passwordPF.requestFocus();

            } else {
                try {
                    if (isIdAlreadyAssigned(patientIdTf.getText()) == 0 ) {
                        int min = 1001;
                        int max = 9999;
                        genPatientId = (int) (Math.random() * (max - min + 1) + min);
                        while (isIdAlreadyAssigned(String.valueOf(genPatientId)) == 1) {
                            genPatientId = (int) (Math.random() * (max - min + 1) + min);
                        }
                        patientIdTf.setText(String.valueOf(genPatientId));

                    }
                } catch (IOException | ClassNotFoundException ex) {
                    throw new RuntimeException(ex);
                }
            }


            String nameValue = nameTf.getText();
            String surnameValue = surnameTf.getText();
            String dateOfBirthValue = dateOfBirthTf.getText();
            String mobileNumberValue = mobileNumberTf.getText();
            String passwordValue = String.valueOf(passwordPF.getPassword());
            String patientIdValue = patientIdTf.getText();

            if (isAlreadyRegisteredMobileNumber(mobileNumberValue) == 1){
                patientIdTf.setText("");
                JOptionPane.showMessageDialog(null,"You already registered with this mobile number. Please proceed " +
                        "with existing account!");
                jFrame.setVisible(false);
                LoginFrame loginFrame = new LoginFrame();
                loginFrame.getGuest().setVisible(false);
                loginFrame.getJFrame().setVisible(true);
                jFrame.dispose();

            }else {
                if ((!nameValue.equals("") && (!surnameValue.equals("") && (!dateOfBirthValue.equals("") && (!mobileNumberValue.equals("") &&
                        (!passwordValue.equals("") || isValidPassword(passwordValue) )))))){ // &&   (!patientIdValue.equals("-"))
                    patient = new Patient(nameValue, surnameValue, dateOfBirthValue, mobileNumberValue, patientIdValue, passwordValue);
                    JOptionPane.showMessageDialog(null,"You have registered with patient ID " + genPatientId);
                    try {
                        FileOutputStream fos = new FileOutputStream("patientsObj.txt",true);
                        ObjectOutputStream oos = new ObjectOutputStream(fos);
                        oos.writeObject(patient);
                        oos.close();
                        fos.close();
                    } catch (IOException ex) {
                        throw new RuntimeException(ex);
                    }
//                    System.out.println(patient);
//                registrationFrame.setVisible(false);
                    jFrame.setVisible(false);
                    DoctorsListFrame doctorsListFrame;
                    try {
                        doctorsListFrame = new DoctorsListFrame();
                    } catch (ParseException ex) {
                        throw new RuntimeException(ex);
                    }
                    doctorsListFrame.getJFrame().setVisible(true);
                    jFrame.dispose();

                }
            }
        }

        if (e.getSource() == back){
            jFrame.setVisible(false);
            LoginFrame loginFrame = new LoginFrame();
            loginFrame.getGuest().setVisible(false);
            loginFrame.getLogin().setBounds(150,100,275,40);
            loginFrame.getRegister().setBounds(480,100,275,40);
            loginFrame.getMyBookings().setBounds(800,100,275,40);
            loginFrame.getJFrame().setVisible(true);
            jFrame.dispose();
        }
    }

    /**
     * @param patientId which user has entered is passing as parameter to check if user is booking doctor in first time.If it is first time
     *                  have to generate patient ID.if it is not first time entered ID keep it as same.
     * @return 0 if patient has not registered with ID. Otherwise, return 1.
     */
    public static int isIdAlreadyAssigned(String patientId) throws IOException, ClassNotFoundException {
        int count = 0;
        ArrayList<Consultation> consultations = SystemUtility.readConsultationFile();
        for (Consultation consultation : consultations){
            if (consultation.getPatient().getPatientId().equals(patientId)){
                count = count + 1;
            }
        }
        return count;
    }

    /**
     *
     * @param password that user has entered is passing as parameter.
     * @return true if pattern format matches to the password what user has entered
     */
    public static boolean isValidPassword(String password){
        return Pattern.matches("[a-zA-Z0-9#-$]+",password);
    }

    /**
     * This method is used to check the mobileNumber is already registered
     * @param mobileNumber passing
     * @return count. return 0 mobile number is not registered Otherwise return 1
     */
    public static int isAlreadyRegisteredMobileNumber(String mobileNumber){
        ArrayList<Patient> patientsList = SystemUtility.readPatientFile();
        int count = 0;
        for (Patient patient2 : patientsList) {
            if (patient2.getMobileNumber().equals(mobileNumber)) {
                count = count + 1;
                break;
            }
        }
        return count;
    }
}

