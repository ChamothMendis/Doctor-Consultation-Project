package com.coursework.ui;

import com.coursework.domains.Patient;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.text.ParseException;
import java.util.ArrayList;

public class LoginDetailsFrame extends MyWindowAdapter implements ActionListener {

    private static Patient patient;
    private JFrame jFrame;
    private JTextField patientIDTF;
    private JPasswordField passwordPF;
    private JButton login,back;

    /**
     * No args Constructor
     */
    public LoginDetailsFrame(){
        initialize();
    }

    /**
     * This method is used to initialise all the gui objects in the frame
     */
    private void initialize(){
        jFrame = new JFrame();
        jFrame.getContentPane().setBackground(new Color(0, 0, 0));
        jFrame.setBounds(100, 100, 1200, 570);
        jFrame.addWindowListener(this);
        jFrame.setResizable(false);
        jFrame.getContentPane().setLayout(null);
        jFrame.setVisible(true);

        Font f = new Font("arial",Font.PLAIN,18);

        ImageIcon logo = new ImageIcon("logo.png");
        JLabel logoLabel = new JLabel("");
        logoLabel.setIcon(logo);
        logoLabel.setBounds(600,110,500,200);

        JLabel patientId = new JLabel("Patient ID");
        patientId.setForeground(Color.white);
        patientId.setFont(f);
        patientId.setBounds(80,150,100,20);

        patientIDTF = new JTextField();
        patientIDTF.setFont(new Font("arial",Font.ITALIC,15));
        patientIDTF.setBounds(270,150,250,30);

        JLabel password = new JLabel("Password");
        password.setForeground(Color.white);
        password.setFont(f);
        password.setBounds(80,230,100,20);

        passwordPF = new JPasswordField();
        passwordPF.setFont(new Font("arial",Font.ITALIC,40));
        passwordPF.setBounds(270,230,250,30);

        login = new JButton("Login");
        login.setBounds(320,325,120,40);
        login.addActionListener(this);
        login.setBackground(Color.cyan);

        back = new JButton("Back");
        back.setBounds(1000,475,100,30);
        back.addActionListener(this);
        back.setBackground(Color.cyan);

        jFrame.getContentPane().add(patientId);jFrame.getContentPane().add(patientIDTF);jFrame.getContentPane().add(password);
        jFrame.getContentPane().add(passwordPF);jFrame.getContentPane().add(login);jFrame.getContentPane().add(logoLabel);
        jFrame.getContentPane().add(back);

    }

    /**
     * This method is used to get the frame
     * @return jFrame
     */
    public JFrame getJFrame() {
        return jFrame;
    }

    /**
     * This method is used to get the patient object that has logged into the system
     * @return patient
     */
    public static Patient getPatient() {
        return patient;
    }

    /**
     * This method is used to set the aptient
     * @param patient passing
     */
    public static void setPatient(Patient patient) {
        LoginDetailsFrame.patient = patient;
    }

    /**
     * This method is used to do a specific action when user press the login button or back button
     * @param e the event to be processed
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == login){
            boolean isExistingUser = false;
            if (patientIDTF.getText().equals("") || String.valueOf(passwordPF.getPassword()).equals("")){
                JOptionPane.showMessageDialog(null,"Patient ID and Password must be required !.");
            }else {
                ArrayList<Patient> patientsObjList = new ArrayList<>();
                boolean cont = true;
                try {
                    FileInputStream fis = new FileInputStream("patientsObj.txt");

                    while (cont){
                        try{
                            ObjectInputStream ois = new ObjectInputStream(fis);
                            patient = (Patient) ois.readObject();

                            if (patient != null){
                                patientsObjList.add(patient);
                            }else {
                                cont = false;
                            }
                        } catch (IOException | ClassNotFoundException ex) {
                            throw new RuntimeException(ex);
                        }
                    }
                } catch (Exception ex) {
//                    System.out.println("End of the file");
                }

                for (Patient patient1 : patientsObjList){
                    if (patient1.getPatientId().equals(patientIDTF.getText()) && patient1.getPassword().equals(String.valueOf(passwordPF.getPassword()))){
//                        System.out.println("equal");
//                        System.out.println(patient1);
//                        System.out.println(patientsObjList);
                        patient = patient1;
//                        System.out.println(patient);
                        jFrame.setVisible(false);
                        DoctorsListFrame doctorsListFrame;
                        try {
                            doctorsListFrame = new DoctorsListFrame();
                        } catch (ParseException ex) {
                            throw new RuntimeException(ex);
                        }
                        doctorsListFrame.getJFrame().setVisible(true);
                        jFrame.dispose();
                        isExistingUser = true;
                        break;
                    }
                }
                if (!isExistingUser){
                    JOptionPane.showMessageDialog(null,"Patient doesn't exists !. ");
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
}
