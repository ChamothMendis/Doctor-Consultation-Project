package com.coursework.ui;

import com.coursework.admin.ManagerActions;
import com.coursework.admin.SystemUtility;
import com.coursework.domains.Consultation;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class BookingSearchFrame extends MyWindowAdapter implements ActionListener {
    private JFrame jFrame;
    private JTextField patientIDTF;
    private final ArrayList<Consultation> consultations;
    private static String patientIdVal;
    private JButton view;
    private JButton back;
    public BookingSearchFrame(){
        initializer();
        consultations = SystemUtility.readConsultationFile();
    }
    private void initializer(){
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

        view = new JButton("view");
        view.setBounds(320,230,120,40);
        view.addActionListener(this);
        view.setBackground(Color.cyan);

        back = new JButton("Back");
        back.setBounds(1000,475,100,30);
        back.addActionListener(this);
        back.setBackground(Color.cyan);

        jFrame.add(patientId);jFrame.add(patientIDTF);jFrame.add(view);jFrame.add(logoLabel);jFrame.add(back);
    }


    /**
     * This method is used to get the frame
     * @return jFrame
     */
    public JFrame getjFrame() {
        return jFrame;
    }


    /**
     * This method is used to get the patientId
     * @return patientId
     */
    public static String getPatientId() {
        return patientIdVal;
    }

    /**
     * @param e the event to be processed
     */
    @Override
    public void actionPerformed(ActionEvent e) {
            if (e.getSource() == view){
                if (!SystemUtility.isValidPatientId(patientIDTF.getText())){
                    patientIDTF.setText("");
                    JOptionPane.showMessageDialog(null,"Valid patientId Required");
                    patientIDTF.requestFocus();
                }else {
                    patientIdVal = patientIDTF.getText();
                    int count = 0;
                    for(Consultation consultation1 : consultations){
                        if (consultation1.getPatient().getPatientId().equals(patientIdVal)){
                            count = count + 1;
                        }
                    }
                    if (count == 0){
                        JOptionPane.showMessageDialog(null,"No such a patient");
                    }else {
                        jFrame.setVisible(false);
                        ConsultationListFrame consultationListFrame = new ConsultationListFrame();
                        consultationListFrame.getjFrame().setVisible(true);
                        jFrame.dispose();
                    }
                }
            }

        if (e.getSource() == back){
            if (ManagerActions.isManagerAction()){
               ViewBookingFrame.exitNavigationToManager(jFrame);
            }else {
                ViewBookingFrame.exitNavigationToPatient(jFrame);
            }
        }
    }
}


