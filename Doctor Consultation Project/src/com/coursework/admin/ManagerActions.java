package com.coursework.admin;

import com.coursework.domains.Doctor;
import com.coursework.domains.Manager;
import com.coursework.ui.LoginFrame;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;

public class ManagerActions {

    private static boolean isManagerAction = false;

    public static boolean isManagerAction() {
        return isManagerAction;
    }

    public static void main(String[] args) throws IOException {
        Scanner scanner = new Scanner(System.in);
        WestminsterSkinConsultationManager westManager = new WestminsterSkinConsultationManager();
        westManager.readStoredInformation();
        ArrayList<Manager> managers = Manager.readManagers();

        while (true) {
            System.out.println("""
                    Which perspective you you want to proceed ?
                    1. Manager
                    2. User
                    """);

            String perspective = String.valueOf(scanner.nextLine().toUpperCase().charAt(0));

            if (perspective.equals("1")) {
                if (SystemUtility.isValidManager(managers)) {
                    boolean isNext = true;
                    boolean isAgain = true;
                    while (isAgain) {
                        System.out.println("""
                                ----------------------------------|
                                What do you want to do ?          |
                                A. Add a Doctor                   |
                                D. Delete a Doctor                |
                                L. Show List Of Doctors           |
                                S. Save Data to the File          |
                                R. Read Stored Information        |
                                C. Check Consultations            |
                                B. Book for a patient             |
                                E. Exit;                          |
                                ----------------------------------|
                                                                  |
                                Additional Functions              |
                                M.Add Manager                     |
                                N.Delete Manager                  |
                                                                  |
                                ----------------------------------|
                                """);

                        String selection = String.valueOf(scanner.nextLine().toUpperCase().charAt(0));

                        switch (selection) {
                            case "A" -> {
                                boolean next = true;
                                if (SystemUtility.isFullOfDoctors(WestminsterSkinConsultationManager.getDoctors()))
                                    System.out.println("Centre is full of Doctors. Cannot add new Doctors");
                                else {
                                    while (next) {

                                        System.out.println("Enter the First Name : ");
                                        String firstName = scanner.nextLine().toUpperCase();
                                        while (!(SystemUtility.isWord(firstName))) {
                                            System.out.println("Enter a valid name !");
                                            firstName = scanner.nextLine().toUpperCase();
                                        }

                                        System.out.println("Enter the Surname :");
                                        String surName = scanner.nextLine().toUpperCase();
                                        while (!(SystemUtility.isWord(surName))) {
                                            System.out.println("Enter a valid Surname !");
                                            surName = scanner.nextLine().toUpperCase();
                                        }

                                        System.out.println("Enter the date of birth :");
                                        String dateOfBirth = scanner.nextLine();
                                        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
                                        while (true) {
                                            if (!SystemUtility.isDateValid(dateOfBirth)) {
                                                System.out.println("please enter a valid date");
                                                dateOfBirth = scanner.nextLine();
                                            } else {
                                                Date date = null;
                                                try {
                                                    date = sdf.parse(dateOfBirth);
                                                } catch (ParseException e) {
                                                    System.out.println("Not in a format !");
                                                }
                                                dateOfBirth = sdf.format(date);
                                                break;
                                            }
                                        }

                                        System.out.println("Enter a mobile number : ");
                                        String mobileNumber = scanner.nextLine();
                                        while (!(SystemUtility.isValidMobileNumber(mobileNumber))) {
                                            System.out.println("Enter a valid Mobile Number !");
                                            mobileNumber = scanner.nextLine();
                                        }

                                        System.out.println("Enter a licence number (AB1234) : ");
                                        String temp = scanner.nextLine();
                                        if (SystemUtility.isDoctorAlreadyExist(temp, WestminsterSkinConsultationManager.getDoctors())) {
                                            System.out.println("Doctor is already exist in the centre !\n\n");
                                            break;
                                        }

                                        String licenceNumber = temp;
                                        while (!SystemUtility.isValidLicenceNumber(licenceNumber)) {
                                            System.out.println("Enter a valid licence Number !");
                                            licenceNumber = scanner.nextLine();
                                        }

                                        System.out.println("Enter the specialisation : ");
                                        String specialisation = scanner.nextLine().toUpperCase();
                                        while (!SystemUtility.isValidSpecialisation(specialisation)) {
                                            System.out.println("Enter a valid input");
                                            specialisation = scanner.nextLine().toUpperCase();
                                        }

                                        westManager.addNewDoctor(new Doctor(firstName,surName,dateOfBirth,mobileNumber,licenceNumber,specialisation));
                                        next = false;
                                    }
                                }
                            }
                            case "D" -> {

                                if (SystemUtility.isEmptyCentre(WestminsterSkinConsultationManager.getDoctors())){
                                    System.out.println("No doctors in the centre.");
                                }else {
                                    System.out.println("Enter the doctor's licence number that you want to delete : ");
                                    String licenceNumber = scanner.nextLine();
                                    while (!SystemUtility.isValidLicenceNumber(licenceNumber)) {
                                        System.out.println("Enter a valid licence Number !");
                                        licenceNumber = scanner.nextLine();
                                    }

                                    westManager.deleteDoctor(licenceNumber);
                                }
                            }
                            case "L" -> westManager.listOfDoctors();
                            case "S" -> westManager.saveToFile();
                            case "R" -> westManager.readStoredInformation();
                            case "C" -> WestminsterSkinConsultationManager.checkConsultations();
                            case "M" -> Manager.addManager(managers);
                            case "N" -> Manager.deleteManager(managers);
                            case "B" -> {
                                isManagerAction = true;
                                LoginFrame loginFrame = new LoginFrame();
                                loginFrame.getRegister().setVisible(false);
                                loginFrame.getLogin().setVisible(false);
                                loginFrame.getGuest().setVisible(true);
                                loginFrame.getGuest().setBounds(300,100,275,40);
                                loginFrame.getMyBookings().setBounds(600,100,275,40);
                            }
                            case "E" -> isAgain = false;
                        }
                    }
                } else {
                    System.out.println("No such a manager");
                }

            }
            if (perspective.equals("2")) {
                LoginFrame loginFrame = new LoginFrame();
                loginFrame.getGuest().setVisible(false);
                loginFrame.getLogin().setBounds(150,100,275,40);
                loginFrame.getRegister().setBounds(480,100,275,40);
                loginFrame.getMyBookings().setBounds(800,100,275,40);
            }
        }
    }
}
