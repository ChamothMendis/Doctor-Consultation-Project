package com.coursework.admin;

import com.coursework.domains.Consultation;
import com.coursework.domains.Doctor;
import com.coursework.domains.Manager;
import com.coursework.domains.Patient;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Pattern;

public final class SystemUtility {

    private SystemUtility() {
    }

    /**
     * This method is used to check the specialization that user has entered is a word or not that matches to the Pattern.
     * @param specialisation is passing to the method
     * @return true if specialisation is a valid word
     */
    public static boolean isValidSpecialisation(String specialisation) {
        return Pattern.matches("[a-zA-Z ]+",specialisation);
    }

    /**
     * @param licenceNumber is passing to the method
     * This method is used to check whether licence number is valid and in the correct format.
     * @return true if Licence number is in the correct format Otherwise returns false
     */
    public static boolean isValidLicenceNumber(String licenceNumber) {
        return Pattern.matches("[A-Z]{2}[0-9]{4}",licenceNumber);
    }

    /**
     * This method is used to check whether mobile number is valid and in the correct format
     * @return true if mobile number is valid otherwise return false
     */
    public static boolean isValidMobileNumber(String mobileNumber) {
        return Pattern.matches("[0-9]{10}",mobileNumber);
    }

    /**
     * This method is used to check whether date is in correct format
     * @return true if date format is valid otherwise returns false
     */
    public static boolean isDateValid(String date) {
        return Pattern.matches("[0-9]{2}/[0-9]{2}/[0-9]{4}",date);
    }

    /**
     * This method is used to check whether word is in  the correct format or not
     * @param word is passing as an argument to check whether name is a word
     * @return true if the word is only contains characters otherwise returns false.
     */
    public static boolean isWord(String word) {
        return Pattern.matches("[a-zA-Z]+",word);
    }


    /**
     * This method is used to check whether doctors List is empty or not.
     * @return true there is no any doctor in the centre otherwise returns false
     */
    public static boolean isEmptyCentre(List<Doctor> doctors) {
        return doctors.size() == 0;
    }


    /**
     * This method is used to check there is any doctor presents in the doctors List that matches to the licence number
     * that has been passed as a parameter.
     * @param tempLicenceNumber is passing to the method
     * @return true if Doctor already exist in the centre otherwise returns false
     */
    public static boolean isDoctorAlreadyExist(String tempLicenceNumber,List<Doctor> doctors) {
        for (Doctor doc : doctors){
            if (doc.getLicenceNumber().equals(tempLicenceNumber))
                return true;
        }
        return false;
    }

    /**
     * This method is used to check if there is  any manager presents in the managers List that matches to the
     * manager ID that user has input.
     * @return true if there is any match , otherwise return false
     */
    public static boolean isValidManager(ArrayList<Manager> managers){
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter the Manager ID : ");
        String managerID = scanner.nextLine();
        while (!Manager.isValidId(managerID)){
            System.out.println("Enter a valid ID !");
            managerID = scanner.nextLine();
        }

        for (Manager manager : managers){
            if (managerID.equals(manager.getManagerId())){
                return true;
            }
        }
        return false;
    }


    /**
     * This method is used to read the "consultations file" and retrieve back the stored consultation objects from the file
     * and stores the objects in the "consultationList" ArrayList
     */
    public static ArrayList<Consultation> readConsultationFile(){
        ArrayList<Consultation> consultationList = new ArrayList<>();
        boolean cont = true;
        try {
            FileInputStream fis = new FileInputStream("consultations.txt");
            while (cont) {
                try {
                    ObjectInputStream ois = new ObjectInputStream(fis);
                    Consultation consultation = (Consultation) ois.readObject();

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
            System.out.println();
        }
        return consultationList;
    }

    public static ArrayList<Patient> readPatientFile(){
        ArrayList<Patient> patients = new ArrayList<>();
        boolean cont = true;
        try {
            FileInputStream fis = new FileInputStream("patientsObj.txt");
            while (cont) {
                try {
                    ObjectInputStream ois = new ObjectInputStream(fis);
                    Patient patient = (Patient) ois.readObject();

                    if (patient != null) {
                        patients.add(patient);
                    } else {
                        cont = false;
                    }
                } catch (IOException | ClassNotFoundException ex) {
                    throw new RuntimeException(ex);
                }
            }
        } catch (Exception ex) {
            System.out.println();
        }
        return patients;
    }

    /**
     * This method is used to check whether doctors List is full of 10 doctors or not.
     * @return true if centre is full of maximum 10 Doctors
     */
    public static boolean isFullOfDoctors(List<Doctor> doctors){
        return doctors.size() == 10;
    }

    public static boolean isValidPatientId(String patientId){
        return Pattern.matches("[0-9]{4}",patientId);
    }


}
