package com.coursework.ui;

import com.coursework.admin.WestminsterSkinConsultationManager;
import com.coursework.domains.Consultation;
import com.coursework.domains.Doctor;
import com.coursework.encryption.AESEncryptionDecryption;
import org.jdatepicker.impl.DateComponentFormatter;
import org.jdatepicker.impl.JDatePanelImpl;
import org.jdatepicker.impl.JDatePickerImpl;
import org.jdatepicker.impl.UtilDateModel;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Properties;
import java.util.Random;

public class DoctorsListFrame extends MyWindowAdapter {
    private JFrame jFrame;
    private JTable table;
    private static JButton book;
    private static JButton bookingDone;
    private JButton fileButton;
    private JTextField costTf;
    private JTextArea notesTa;
    private JComboBox comboBox,comboBox1;
    final String secretKey = "secrete";
    private final DefaultTableModel dtm;
    private static Consultation consultation;
    private static Doctor selectedDoctor;
    private static String strDate;
    private ArrayList<String> imageUrl;
    private static String selectedTime;
    private static int costValue;
    private int row;
    private static ArrayList<Doctor> doctorList;
    private static String finalText;
    private final AESEncryptionDecryption encryption = new AESEncryptionDecryption();

    /**
     * This method is used to display all the doctors in a table
     */
    public void displayDoctors() {
        dtm.setRowCount(0);
        for (int i = 0; i < doctorList.size(); i++) {
            Object[] obj = {doctorList.get(i).getName(), doctorList.get(i).getSurname(), doctorList.get(i).getSpecialisation(), doctorList.get(i).getLicenceNumber()};
            dtm.addRow(obj);
        }
    }

    /**
     * No args constructor
     * @throws ParseException exception
     */
    public DoctorsListFrame() throws ParseException {
        initialize();
        doctorList = (ArrayList<Doctor>) WestminsterSkinConsultationManager.getDoctors();
        String[] header = new String[]{"Name", "Surname", "Specialization", "License Number"};
        dtm = new DefaultTableModel(header, 0);
        table.setModel(dtm);
        displayDoctors();
    }

    /**
     * This methos is used to initialise all the gui objects/ components and actions in the frame
     * @throws ParseException exception
     */
    private void initialize() throws ParseException {
        jFrame = new JFrame();
        jFrame.getContentPane().setBackground(new Color(0, 0, 0));
        jFrame.setBounds(100, 100, 1200, 570);
        jFrame.addWindowListener(this);
        jFrame.setResizable(false);
        jFrame.getContentPane().setLayout(null);
        jFrame.setVisible(true);
        imageUrl = new ArrayList<>();

        Font f = new Font("arial", Font.PLAIN, 18);

        JButton sort = new JButton("Sort");
        sort.setBounds(960, 40, 150, 30);
        sort.setBackground(Color.cyan);
        jFrame.add(sort);

        sort.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                doctorList.sort(new NameComparator());
                displayDoctors();
            }
        });

        JButton bookForOthers = new JButton("Book for others");
        bookForOthers.setBounds(19, 40, 150, 30);

        JLabel date = new JLabel("Date");
        date.setBounds(20, 73, 150, 30);
        date.setForeground(Color.white);
        date.setFont(f);

        UtilDateModel model = new UtilDateModel();
        Properties p = new Properties();
        JDatePanelImpl datePanel = new JDatePanelImpl(model, p);
        JDatePickerImpl datePicker = new JDatePickerImpl(datePanel, new DateComponentFormatter());
        datePicker.setBounds(90, 73, 200, 30);
        model.setSelected(false);
        datePicker.setVisible(true);

        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setBounds(375, 75, 737, 339);
        jFrame.getContentPane().add(scrollPane);

        Integer[] timeSlots = {8,9,10,11,12,1,2,3,4};
        Integer[] endTimeSlots = {9,10,11,12,1,2,3,4,5};

        JLabel time = new JLabel("Time");
        time.setBounds(20, 150, 150, 30);
        time.setFont(f);
        time.setForeground(Color.white);
        jFrame.add(time);

        comboBox = new JComboBox<>(timeSlots);
        comboBox.setBounds(90, 150, 75, 30);
        jFrame.add(comboBox);

        comboBox1 = new JComboBox<>(endTimeSlots);
        comboBox1.setBounds(170, 150, 75, 30);
        jFrame.add(comboBox1);

        JLabel cost = new JLabel("Cost");
        cost.setBounds(20, 227, 150, 30);
        cost.setFont(f);
        cost.setForeground(Color.white);
        jFrame.add(cost);

        costTf = new JTextField();
        costTf.setBounds(90, 227, 200, 30);
        costTf.setFont(new Font("arial", Font.ITALIC, 15));
        costTf.setEditable(false);
        jFrame.add(costTf);

        JLabel notes = new JLabel("Notes");
        notes.setBounds(20, 314, 150, 30);
        notes.setFont(f);
        notes.setForeground(Color.white);
        jFrame.add(notes);

        notesTa = new JTextArea();
        notesTa.setBounds(90, 314, 200, 100);
        notesTa.setFont(new Font("arial", Font.ITALIC, 15));
        jFrame.add(notesTa);

        fileButton = new JButton("upload");
        fileButton.setBounds(90,420,100,35);
        fileButton.setVisible(true);
        fileButton.setFont(f);
        fileButton.setBackground(Color.cyan);
        jFrame.add(fileButton);

        table = new JTable();
        table.setRowHeight(30);
        table.setBackground(Color.YELLOW);
        scrollPane.setViewportView(table);
        jFrame.add(datePicker);
        jFrame.add(date);

        table.addMouseListener(new MouseAdapter() {
            /**
             * @param arg0 the event to be processed
             */
            @Override
            public void mouseClicked(MouseEvent arg0) {
                row = table.getSelectedRow();
                selectedDoctor = doctorList.get(row);
            }
        });

        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
        Date date1 = new Date();
        String defaultDate = format.format(date1);
        Date defDate = format.parse(defaultDate);

        datePicker.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Date selDate;
                Date selectedDate = (Date) datePicker.getModel().getValue();
                SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
                strDate = formatter.format(selectedDate);
                try {
                    selDate = formatter.parse(strDate);
                } catch (ParseException ex) {
                    throw new RuntimeException(ex);
                }
                if (selDate.compareTo(defDate) < 0){
                    JOptionPane.showMessageDialog(null,"Please select the valid date");
                    strDate = null;
                }
            }
        });

        book = new JButton("Book");
        book.setBounds(600, 450, 100, 40);
        book.setBackground(Color.cyan);
        jFrame.add(book);
        book.setFont(f);

        bookingDone = new JButton("View");
        bookingDone.setBounds(800,450,100,40);
        bookingDone.setBackground(Color.cyan);
        jFrame.add(bookingDone);
        bookingDone.setEnabled(false);
        bookingDone.setFont(f);

        fileButton.addActionListener(e -> {
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.addChoosableFileFilter(new ImageFilter());
            fileChooser.setAcceptAllFileFilterUsed(false);

            int option = fileChooser.showOpenDialog(this.getJFrame());
            if(option == JFileChooser.APPROVE_OPTION){
                File file = fileChooser.getSelectedFile();
                String filePath = file.getAbsolutePath();
                String filePathEncrypt = encryption.encrypt(filePath, secretKey);
                System.out.println(filePathEncrypt);
                imageUrl.add(filePathEncrypt);


            }else{
                System.out.println("No file selected");
            }});


        book.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                int selStartTimeValid = (int) comboBox.getItemAt(comboBox.getSelectedIndex());
                int selEndTimeValid = (int) comboBox1.getItemAt(comboBox1.getSelectedIndex());

                if (((selStartTimeValid >= 8 && selStartTimeValid <= 12) && (selEndTimeValid <= selStartTimeValid && (selEndTimeValid >= 9))) ||
                        (((selStartTimeValid >= 12) || (selStartTimeValid >= 1 && selStartTimeValid <= 4)) && ((selEndTimeValid <= selStartTimeValid && selStartTimeValid <= 4) || (selEndTimeValid > 5 )))){
                    JOptionPane.showMessageDialog(null,"Valid time required");
                    selectedTime = null;
                }else {
                    selectedTime = String.valueOf(comboBox.getItemAt(comboBox.getSelectedIndex())) + "-" + String.valueOf(comboBox1.getItemAt(comboBox1.getSelectedIndex()));
                }

                if ((strDate != null) && (selectedTime != null) && (selectedDoctor != null)) {
                    int count = 0;
                    ArrayList<Consultation> consultationList = new ArrayList<>();
                    boolean cont = true;
                    try {
                        FileInputStream fis = new FileInputStream("consultations.txt");
                        while (cont) {
                            try {
                                ObjectInputStream ois = new ObjectInputStream(fis);
                                consultation = (Consultation) ois.readObject();
                                if (consultation != null) {
                                    consultationList.add(consultation);
                                } else {
                                    cont = false;
                                }
                            } catch (IOException | ClassNotFoundException ex) {
                                throw new RuntimeException(ex);
                            }
                        }
                    } catch (Exception ex) {
//                        System.out.println("End of the file");
                    }

                    for (Consultation consultation1 : consultationList) {
                        if (consultation1 != null) {
                            String[] time = consultation1.getTime().split("-");
                            int startTime = Integer.parseInt(time[0]);
                            int endTime = Integer.parseInt(time[1]);
                            int selStartTime = (int) comboBox.getItemAt(comboBox.getSelectedIndex());
                            if ((consultation1.getDoctor().getLicenceNumber().equals(selectedDoctor.getLicenceNumber()) && consultation1.getDate().equals(
                                    strDate)) && (((selStartTime >= startTime) && (startTime >= 8 && startTime <= 12 && (endTime > selStartTime))) || ((selStartTime >= startTime) && (selStartTime <= 5 && (endTime < selStartTime || selStartTime < endTime)))
                                    || ((startTime >= 8 && startTime <= 12) && ((selStartTime >= 8 && selStartTime <= 12) && (endTime > selStartTime || (endTime <= 5)))) || ((startTime == 12 || startTime <= 5) && (endTime > selStartTime || selStartTime == startTime))
                                    || ((endTime <= 5 && selStartTime < endTime))) ) {
                                count = count + 1;

                                boolean isAgain = true;
                                while (isAgain) {
                                    Random random = new Random();
                                    int x = random.nextInt(doctorList.size());
                                    Doctor doctor = doctorList.get(x);
                                    for (Consultation consultation2 : consultationList) {
                                        if ((consultation2.getDoctor().getLicenceNumber().equals(doctor.getLicenceNumber())) && (consultation2.getDate().equals(strDate))) {
                                            isAgain = true;
                                        } else {
                                            selectedDoctor = doctor;
                                            isAgain = false;
                                        }
                                    }
                                }
                            }
                        }
                    }
                    if (count >= 1) {
                        JOptionPane.showMessageDialog(null, "Selected doctor is not available.We have assigned " +
                                "you to a available doctor");
                    }

                    int selStartTime = (int) comboBox.getItemAt(comboBox.getSelectedIndex());
                    int selEndTime = (int) comboBox1.getItemAt(comboBox1.getSelectedIndex());

                    String t = String.valueOf(25 * ((12 - selStartTime) + selEndTime));
                    String t1 = String.valueOf(15 * ((12 - selStartTime) + selEndTime));
                    if (LoginDetailsFrame.getPatient() != null){
                        for (Consultation consultation1 : consultationList){
                            if (consultation1.getPatient().getPatientId().equals(LoginDetailsFrame.getPatient().getPatientId())){
                                CostCal(selStartTime, selEndTime, t);
                            }
                        }
                    } else if (RegistrationFrame.getPatient() != null) {
                        for (Consultation consultation1 : consultationList){
                            if (consultation1.getPatient().getPatientId().equals(RegistrationFrame.getPatient().getPatientId())){
                                CostCal(selStartTime, selEndTime, t);
                            }
                        }
                    } else if (GuestFrame.getPatient() != null) {
                        for (Consultation consultation1 : consultationList){
                            if (consultation1.getPatient().getPatientId().equals(GuestFrame.getPatient().getPatientId())){
                                CostCal(selStartTime, selEndTime, t);
                            }
                        }
                    }
                    if (costTf.getText().equals("")){
                        if ((selStartTime >= 8 && selStartTime < 12) && (selEndTime >= 1 && selEndTime <= 5)){
                            costTf.setText(t1);
                            costValue = Integer.parseInt(costTf.getText());
                        } else if (selStartTime == 12 && (selEndTime >= 1 && selEndTime <= 5)) {
                            costTf.setText(String.valueOf(15 * selEndTime));
                            costValue = Integer.parseInt(costTf.getText());
                        } else if ((selStartTime >= 8 && selStartTime < 12) && selEndTime == 12) {
                            costTf.setText(String.valueOf(15 * (selEndTime - selStartTime)));
                            costValue = Integer.parseInt(costTf.getText());
                        } else if (selEndTime < selStartTime) {
                            costTf.setText(String.valueOf(15 * (selEndTime - selStartTime)));
                            costValue = Integer.parseInt(costTf.getText());
                        } else if (selStartTime < selEndTime && selStartTime <= 12) {
                            costTf.setText(String.valueOf(15 * (selEndTime - selStartTime)));
                            costValue = Integer.parseInt(costTf.getText());
                        }
                    }


                    finalText = notesTa.getText();

                    FileOutputStream fos;
                    try {
                        fos = new FileOutputStream("consultations.txt", true);
                    } catch (FileNotFoundException ex) {
                        throw new RuntimeException(ex);
                    }
                    ObjectOutputStream oos;
                    try {
                        oos = new ObjectOutputStream(fos);
                    } catch (IOException ex) {
                        throw new RuntimeException(ex);
                    }

                    if (LoginDetailsFrame.getPatient() != null) {
                        try {
                            consultation = new Consultation(LoginDetailsFrame.getPatient(), selectedDoctor, strDate, selectedTime,
                                    costValue, finalText,imageUrl);
                           bookingSuccess(oos);
                        } catch (IOException ex) {
                            throw new RuntimeException(ex);
                        }

                    } else if (GuestFrame.getPatient() != null) {
                        try {
                            consultation = new Consultation(GuestFrame.getPatient(), selectedDoctor, strDate, selectedTime,
                                    costValue, finalText,imageUrl);
                            bookingSuccess(oos);
                        } catch (IOException ex) {
                            throw new RuntimeException(ex);
                        }
                    } else {
                        try {
                            consultation = new Consultation(RegistrationFrame.getPatient(), selectedDoctor, strDate, selectedTime, costValue, finalText,imageUrl);
                            bookingSuccess(oos);
                        } catch (IOException ex) {
                            throw new RuntimeException(ex);
                        }
                    }
                }
            }
            public static void bookingSuccess(ObjectOutputStream oos) throws IOException {
                oos.writeObject(consultation);
                book.setEnabled(false);
                bookingDone.setEnabled(true);
                JOptionPane.showMessageDialog(null,"Booking Successful !");
            }
        });

        bookingDone.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                jFrame.setVisible(false);
                ViewBookingFrame viewBookingFrame = new ViewBookingFrame();
                viewBookingFrame.getJFrame().setVisible(true);
                jFrame.dispose();
            }
        });
    }


    /**
     * This method is used to get the consultation object
     * @return consultation
     */
    public static Consultation getConsultation() {
        return consultation;
    }

    /**
     * This method is used to get the frame
     * @return jFrame
     */
    public JFrame getJFrame() {
        return jFrame;
    }

    /**
     * This method is used to calculate the cost of the consultation
     * @param selStartTime of the consultation
     * @param selEndTime of the consultation
     * @param t passing
     */
    private void CostCal(int selStartTime, int selEndTime, String t) {
        if ((selStartTime >= 8 && selStartTime < 12) && (selEndTime >= 1 && selEndTime <= 5)){
            costTf.setText(t);
            costValue = Integer.parseInt(costTf.getText());
        } else if (selStartTime == 12 && (selEndTime >= 1 && selEndTime <= 5)) {
            costTf.setText(String.valueOf(25 * selEndTime));
            costValue = Integer.parseInt(costTf.getText());
        } else if ((selStartTime >= 8 && selStartTime < 12) && selEndTime == 12) {
            costTf.setText(String.valueOf(25 * (selEndTime - selStartTime)));
            costValue = Integer.parseInt(costTf.getText());
        } else if (selEndTime < selStartTime) {
            costTf.setText(String.valueOf(25 * (selEndTime - selStartTime)));
            costValue = Integer.parseInt(costTf.getText());
        }else if (selStartTime < selEndTime && selStartTime <= 12) {
            costTf.setText(String.valueOf(25 * (selEndTime - selStartTime)));
            costValue = Integer.parseInt(costTf.getText());
        }
    }
}

































































































//    public static DoctorsListFrame doctorsListFrame = new DoctorsListFrame();
//
//    public static DoctorsListFrame getDoctorsListFrame() {
//        return doctorsListFrame;
//    }
//
//    private Doctor doctor;
//
//    public Doctor getDoctor() {
//        return doctor;
//    }
//
//    JButton book,sort;
//    JTable table = new JTable();
//
//    public DoctorsListFrame() {
//        addRowToTable();
//        this.setVisible(true);
//        table.setBounds(30,40,200,300);
//        JScrollPane scrollPane = new JScrollPane(table);
//        this.add(scrollPane);
//        book = new JButton("Book");
//        this.add(book);
//        this.add(scrollPane);
//        this.add(table);
//
////        this.setTitle("Doctors List");
////        this.setLayout(null);
////        this.addWindowListener(new WindowAdapter() {
////            /**
////             * @param we the event to be processed
////             */
////            @Override
////            public void windowClosing(WindowEvent we) {
////                System.exit(0);
////            }
////        });
//    }
//
//    public ArrayList ListDoctors(){
//        ArrayList<Doctor> doctorArrayList = (ArrayList<Doctor>) WestminsterSkinConsultationManager.getDoctors();
//        return doctorArrayList;
//    }
//
//    public void addRowToTable(){
//        System.out.println(ListDoctors());
//        DefaultTableModel model = (DefaultTableModel) table.getModel();
//        ArrayList<Doctor> doctors = ListDoctors();
//        Object rowData[] = new Object[4];
//
//        for (int i = 0; i < doctors.size(); i++){
//            rowData[i] = doctors.get(i).getName();
//            rowData[i] = doctors.get(i).getSurname();
//            rowData[i] = doctors.get(i).getSpecialisation();
//            rowData[i] = doctors.get(i).getLicenceNumber();
//            model.addRow(rowData);
//        }
//    }
//
//    /**
//     * @param e the event to be processed
//     */
//    @Override
//    public void actionPerformed(ActionEvent e) {
//
//    }
//}
//
//
///**
// * //Column Headers
// *         String [] columnHeaders = {"Name","SurName","Specialization","Licence Number"};
// *
// *         //Data for the table
// *         Object [][] data = new Object[WestminsterSkinConsultationManager.getDoctors().size()][];
// *         for (int i = 0; i < WestminsterSkinConsultationManager.getDoctors().size(); i++){
// *             String name = WestminsterSkinConsultationManager.getDoctors().get(i).getName();
// *             String surName = WestminsterSkinConsultationManager.getDoctors().get(i).getSurname();
// *             String specialization = WestminsterSkinConsultationManager.getDoctors().get(i).getSpecialisation();
// *             String licenceNumber = WestminsterSkinConsultationManager.getDoctors().get(i).getLicenceNumber();
// *
// *             data = (Object[][]) new Object[]{name,surName,specialization,licenceNumber};
// *         }
// *
// *         //Create the table and set Headers
// *         table = new JTable(data,columnHeaders);
// *
// *         JScrollPane pane = new JScrollPane(table);
// *         getContentPane().add(pane,BorderLayout.CENTER);
// *         setSize(450,100);
// *         setDefaultCloseOperation(EXIT_ON_CLOSE);
// *
// *     }
// *
// *     /**
// *      * @param e the event to be processed
// *      */
// /**@Override
// *public void actionPerformed(ActionEvent e){
//        *
//        *}
//        *
//        *public static void main(String[]args){
//        *DoctorsListFrame doctorsListFrame=new DoctorsListFrame();
//        *doctorsListFrame.setVisible(true);
//        *}
// */
