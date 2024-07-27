package com.coursework.domains;

import com.coursework.admin.SystemUtility;

import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;
import java.util.regex.Pattern;

public class Manager extends Person{
    private String managerId;

    public Manager(String name, String surname, String dateOfBirth, String mobileNumber, String managerId) {
        super(name, surname, dateOfBirth, mobileNumber);
        this.managerId = managerId;
    }

    public String getManagerId() {
        return managerId;
    }

    public void setManagerId(String managerId) {
        this.managerId = managerId;
    }

    public static Manager addManagerDetails(){
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter the Manager Name : ");
        String firstName = scanner.nextLine().toUpperCase();
        while (!(SystemUtility.isWord(firstName))){
            System.out.println("Enter a valid name !");
            firstName = scanner.nextLine().toUpperCase();
        }

        System.out.println("Enter the Manager Surname :");
        String surName = scanner.nextLine().toUpperCase();
        while (!(SystemUtility.isWord(surName))){
            System.out.println("Enter a valid Surname !");
            surName = scanner.nextLine().toUpperCase();
        }

        System.out.println("Enter the Manager's mobile number : ");
        String mobileNumber = scanner.nextLine();
        while (!(SystemUtility.isValidMobileNumber(mobileNumber))){
            System.out.println("Enter a valid Mobile Number !");
            mobileNumber = scanner.nextLine();
        }

        System.out.println("Enter the date of birth :");
        String dateOfBirth = scanner.nextLine();
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        while (true){
            if (!SystemUtility.isDateValid(dateOfBirth)){
                System.out.println("please enter a valid date");
                dateOfBirth = scanner.nextLine();
            }else {
                Date date = null;
                try {
                    date = sdf.parse(dateOfBirth);
                } catch (ParseException e) {
                    System.out.println("Not in a format !");
                }
                dateOfBirth = sdf.format(date);
                System.out.println(dateOfBirth);
                break;
            }
        }

        System.out.println("Assign a manager ID : ");
        String managerId = scanner.nextLine();
        while (!(isValidId(managerId))){
            System.out.println("Enter a valid ID");
            managerId = scanner.nextLine();
        }


        return new Manager(firstName,surName,mobileNumber,dateOfBirth,managerId);

    }

    public static void writeManagers(ArrayList <Manager> managerArrayList) {

        try{
            FileWriter fileWriter = new FileWriter("managers.txt",false);
            PrintWriter printWriter = new PrintWriter(fileWriter);

            for (Manager manager : managerArrayList){
                String details = manager.getName() + "|" + manager.getSurname() + "|" + manager.getDateOfBirth() + "|" + manager.getMobileNumber()
                        + "|" + manager.getManagerId();
                printWriter.println(details);
            }
            printWriter.close();
        }catch (IOException e){
            System.out.println("File not exists");
        }
    }

    public static ArrayList<Manager> readBackManagerInformation(){
        ArrayList<Manager> managers = new ArrayList<>();
        try {
            FileReader fileReader = new FileReader("managers.txt");
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            String data = bufferedReader.readLine();

            while (data != null){
                String [] tempData = data.split("\\|");
                String name = tempData[0];
                String surName = tempData[1];
                String dateOfBirth = tempData[2];
                String mobileNumber = tempData[3];
                String managerId = tempData[4];

                managers.add(new Manager(name,surName,dateOfBirth,mobileNumber,managerId));
                data = bufferedReader.readLine();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return managers;
    }


    /**
     * This method is used to call the readBackManagerInformation method which is present in the Manager class and
     * read back managers details from the file at the time of programme gets started.
     * File name - managers.txt
     */
    public static ArrayList<Manager> readManagers(){
        return Manager.readBackManagerInformation();
    }

    public static ArrayList<Manager> deleteManagerFromList(ArrayList <Manager> managerArrayList){
        ArrayList <Manager> newManagerList = new ArrayList<>();
        int count = 0;
        if (managerArrayList.size() == 1){
            System.out.println("System should have at least one Manager. Cannot Delete !");
            newManagerList = managerArrayList;
        } else if (managerArrayList.size() > 1) {
            Scanner scanner = new Scanner(System.in);
            System.out.println("ID of the manager: ");
            String managerId = scanner.nextLine();
            while (!(isValidId(managerId))){
                System.out.println("Enter a valid ID");
                managerId = scanner.nextLine();
            }

            for (Manager manager : managerArrayList){
                if (manager.getManagerId().equals(managerId)){
                    System.out.println("Successfully Deleted !");
                    count = count + 1;
                    continue;
                }else {
                    newManagerList.add(manager);
                }
            }
            if (count == 0){
                System.out.println("No such a Manager !");
            }
        }
        return newManagerList;
    }


    /***
     * This method is used to call the deleteManager method which is present in the Manager class and
     * delete specific manager from the manager List and update the file.
     */
    public static void deleteManager(ArrayList <Manager> managers) {
        managers = Manager.deleteManagerFromList(managers);
        Manager.saveManagers(managers);
    }



    /**
     * This method is used to call the addManager method which is present in the Manager class and add a new Manager
     * if managers List doesn't have more than two Managers and save to a file.
     */
    public static void addManager(ArrayList<Manager>managers) {
        if (managers.size() < 2){
            Manager manager = Manager.addManagerDetails();
            managers.add(manager);
            saveManagers(managers);
        }
        else {
            System.out.println("Can have maximum two managers !");
        }
    }


    /**
     * This method is used to call the saveManager method which is present in the Manager class and save managers to a file.
     * File Name - managers.txt
     */
    public static void saveManagers(ArrayList<Manager>managers) {
        writeManagers(managers);
    }

    public static boolean isValidId(String managerId){
        return Pattern.matches("[a-z]{2}[0-9]{2}",managerId);
    }

    @Override
    public String toString() {
        return "Manager{" +
                "managerId='" + managerId + '\'' +
                '}';
    }
}
