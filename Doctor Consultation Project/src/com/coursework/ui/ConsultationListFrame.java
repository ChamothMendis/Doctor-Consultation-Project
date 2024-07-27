package com.coursework.ui;

import com.coursework.admin.ManagerActions;
import com.coursework.admin.SystemUtility;
import com.coursework.domains.Consultation;
import com.coursework.encryption.AESEncryptionDecryption;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class ConsultationListFrame extends MyWindowAdapter implements ActionListener {
    private JFrame jFrame;
    private JTable table;
    private JButton back,viewImg;
    private final DefaultTableModel dtm;
    private final ArrayList<Consultation> consultations;
    private int row;
    private static Consultation selectedConsultation;
    private BufferedImage [] allImages;
    private final AESEncryptionDecryption encryption = new AESEncryptionDecryption();
    private final String secretKey = "secrete";

    /**
     * This method is used to display specific consultations in the table
     */
    public void displayConsultations() {
        dtm.setRowCount(0);
        for (int i = 0; i < consultations.size(); i++) {
            if (consultations.get(i).getPatient().getPatientId().equals(BookingSearchFrame.getPatientId())){
                if (consultations.get(i).getImageUrl() == null || consultations.get(i).getImageUrl().size() == 0){
                    Object[] obj = {consultations.get(i).getPatient().getName()+ " " + consultations.get(i).getPatient().getSurname(), consultations.get(i).getDoctor().getName()+ " " + consultations.get(i).getDoctor().getSurname(), consultations.get(i).getDate(), consultations.get(i).getTime(),
                            consultations.get(i).getCost(),consultations.get(i).getNotes(),""};
                    dtm.addRow(obj);
                }else {
                    String test = encryption.decrypt(consultations.get(i).getImageUrl().get(0),secretKey);
                    Object[] obj = {consultations.get(i).getPatient().getName()+ " " + consultations.get(i).getPatient().getSurname(), consultations.get(i).getDoctor().getName()+ " " + consultations.get(i).getDoctor().getSurname(), consultations.get(i).getDate(), consultations.get(i).getTime(),
                            consultations.get(i).getCost(),consultations.get(i).getNotes(),test};
                    dtm.addRow(obj);
                }
            }
        }
    }

    /**
     * Constructor of the Frame
     */
    public ConsultationListFrame(){
        consultations = SystemUtility.readConsultationFile();
        initialize();
        String[] header = new String[]{"Patient", "Doctor", "Date", "Time","Cost","Notes","Images"};
        dtm = new DefaultTableModel(header, 0);
        table.setModel(dtm);
        displayConsultations();
    }

    /**
     * This method is used to initialise all the gui objects that is in the frame
     */
    private void initialize(){
        jFrame = new JFrame();
        jFrame.getContentPane().setBackground(new Color(0, 0, 0));
        jFrame.setBounds(100, 100, 1200, 570);
        jFrame.addWindowListener(this);
        jFrame.setResizable(false);
        jFrame.getContentPane().setLayout(null);
        jFrame.setVisible(true);
        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setBounds(220, 75, 800, 339);
        jFrame.getContentPane().add(scrollPane);

        table = new JTable();
        table.setRowHeight(30);
        table.setBackground(Color.YELLOW);
        scrollPane.setViewportView(table);
        table.setVisible(true);

        viewImg = new JButton("view Images");
        viewImg.setBounds(500,450,150,30);
        viewImg.addActionListener(this);
        viewImg.setBackground(Color.cyan);
        jFrame.add(viewImg);

        back = new JButton("Back");
        back.setBounds(1000,475,100,30);
        back.addActionListener(this);
        back.setBackground(Color.cyan);
        jFrame.add(back);

        table.addMouseListener(new MouseAdapter() {
            /**
             * @param e the event to be processed
             */
            @Override
            public void mouseClicked(MouseEvent e) {
                row = table.getSelectedRow();
                selectedConsultation = consultations.get(row);

            }
        });
    }

    /**
     * This method is used to get the frame
     * @return jFrame
     */
    public JFrame getjFrame() {
        return jFrame;
    }

    /**
     * @param e the event to be processed
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == viewImg){
            if (selectedConsultation == null){
                JOptionPane.showMessageDialog(null,"Please select a Consultation");
                table.requestFocus();
            }else {
                if (selectedConsultation.getImageUrl() != null || selectedConsultation.getImageUrl().size() != 0){
                    File [] allFiles = new File[selectedConsultation.getImageUrl().size()];
                    for (int i = 0 ; i < selectedConsultation.getImageUrl().size() ; i++){
                        String url = encryption.decrypt(selectedConsultation.getImageUrl().get(i),secretKey);
                        allFiles[i] = new File(url);
                    }
                    allImages = new BufferedImage[allFiles.length];
                    JFrame window  = new JFrame();
                    window.dispose();
                    window.setLayout(new GridLayout(3,3));

                    JLabel[] label = new JLabel[allFiles.length];

                    for (int i = 0; i < allFiles.length; i++){
                        try {
                            allImages[i] = ImageIO.read(allFiles[i]);
                            label[i] = new JLabel();
                            ImageIcon icon = new ImageIcon(allImages[i]);
                            label[i].setIcon(icon);
                            window.add(label[i]);
                        } catch (IOException ex) {
                            throw new RuntimeException(ex);
                        }
                    }
                    window.pack();
                    window.setVisible(true);
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
