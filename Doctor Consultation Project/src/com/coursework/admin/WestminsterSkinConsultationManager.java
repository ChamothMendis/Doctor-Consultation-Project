package com.coursework.admin;

import com.coursework.domains.Consultation;
import com.coursework.domains.Doctor;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class WestminsterSkinConsultationManager implements SkinConsultationManager {
    private static final List <Doctor> doctors = new ArrayList<>(10);

    /**
     * This method is used to get the List of doctors.
     * @return doctors List
     */
    public static List<Doctor> getDoctors() {
        return doctors;
    }

    /**
     * This method is used to add a new Doctor to the centre.
     */
    @Override
    public void addNewDoctor(Doctor doctor) {
        int count = 0;
        for (Doctor doctor1 : doctors){
            if (doctor.getLicenceNumber().equals(doctor1.getLicenceNumber())){
                count++;
            }
        }
        if (count > 0){
            System.out.println("Doctor already exists !");
        }else {
            doctors.add(doctor);
            System.out.println("Doctor added successfully !");
            System.out.println("\n\n");
        }
    }

    /**
     * This method is used to Delete a specific Doctor according to the licence number
     */
    @Override
    public void deleteDoctor(String licenceNumber) {
            boolean isSuchADoctor = false;
            for (Doctor doc : doctors){
                if (doc.getLicenceNumber().equals(licenceNumber)){
                    doctors.remove(doc);
                    System.out.println("Doctor has been removed successfully !");
                    System.out.println("Number of Doctors in the centre is : " + doctors.size());
                    isSuchADoctor = true;
                    break;
                }
            }
            if (!isSuchADoctor){
                System.out.println("No such a Doctor matches to the licence number");
            }
        }


    /**
     * This method is used to print the list of Doctors and information about each doctor,
     * that is in the consultation centre and Doctors should be in alphabetical order according to the surname
     */
    @Override
    public void listOfDoctors() {
        if (SystemUtility.isEmptyCentre(doctors)){
            System.out.println("No Doctors in the centre");
        }else {
            doctors.sort(new SurnameComparator());
            for (Doctor doc : doctors){
                System.out.println("-------------------------------------------------------");
                System.out.println("...Doctor Information..." +
                        "\n-------------------------------------------------------" +
                        "\nFirst Name : " + doc.getName()+
                        "\nSurName : " + doc.getSurname()+
                        "\nDate of birth : " + doc.getDateOfBirth()+
                        "\nMobile Number : " + doc.getMobileNumber()+
                        "\nLicence Number : " + doc.getLicenceNumber()+
                        "\nSpecialisation : " + doc.getSpecialisation()+
                        "\n--------------------------------------------------------");
            }
        }
    }


    /**
     * This method is used to get the list of Consultations.
     * Manager can see patient ID, patient name, Doctor name, consultation date, consultation time, and cost.
     */
    public static void checkConsultations(){
        ArrayList<Consultation> consultationList = SystemUtility.readConsultationFile();

        for (Consultation consultation : consultationList){
            System.out.println("---------------------------------------------------------------");
            System.out.println("|Patient ID : " + consultation.getPatient().getPatientId() + "\n" +
                               "|Patient Name : " + consultation.getPatient().getName() + " " + consultation.getPatient().getSurname() + "\n" +
                               "|Doctor Name : " + consultation.getDoctor().getName() + " " + consultation.getDoctor().getSurname() + " " + "(" + consultation.getDoctor().getSpecialisation() + ")" + "\n" +
                               "|Date : " + consultation.getDate() + "\n" +
                               "|Time : " + consultation.getTime() + "\n" +
                               "|Cost : " + consultation.getCost() + "\n" +
                               "----------------------------------------------------------------"
            );
            System.out.println();
        }
    }


    /**
     *This method is used Save the information to a File that user has entered so far.
     * It saves Doctor Name, Surname, date of birth, mobile Number, Doctor License Number, specialization details to a file.
     * File Name - doctorDetails.txt
     */
    @Override
    public void saveToFile(){
        try {
            FileWriter fileWriter = new FileWriter("doctorDetails.txt",false);
            PrintWriter printWriter = new PrintWriter(fileWriter);
            if (SystemUtility.isEmptyCentre(doctors)){
                System.out.println("Nothing to save !");
            }else {
                for (Doctor doc : doctors){
                    String details = doc.getName() + "|" + doc.getSurname() +"|" + doc.getDateOfBirth() + "|" + doc.getMobileNumber() + "|" +
                            doc.getLicenceNumber() + "|" + doc.getSpecialisation();
                    printWriter.println(details);
                }
                System.out.println("Save successfully");
                printWriter.close();
            }


        } catch (IOException e) {
            System.out.println("File not exists");
        }
    }


    /**
     * This method is used to read back / load information that have been stored in the file and stores data to a
     * doctor List at the time of programme get started
     * File name - doctorDetails.txt
     */
    @Override
    public void readStoredInformation() {
        try {
            if (!SystemUtility.isEmptyCentre(doctors)){
                System.out.println("Not allowed this time ! ");
            }else {
                FileReader fileReader = new FileReader("doctorDetails.txt");
                BufferedReader bufferedReader = new BufferedReader(fileReader);
                String data = bufferedReader.readLine();
                if (!data.equals("")){
                    while (data != null){
                        String [] tempData = data.split("\\|");
                        String name = tempData[0];
                        String surname = tempData[1];
                        String dateOfBirth = tempData[2];
                        String mobileNumber = tempData[3];
                        String licenceNumber = tempData[4];
                        String specialisation = tempData[5];

                        doctors.add(new Doctor(name,surname,dateOfBirth,mobileNumber,licenceNumber,specialisation));
                        data = bufferedReader.readLine();
                    }
                }

            }
        } catch (FileNotFoundException e) {
            System.out.println("File not exist !");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}

