package com.coursework.admin;

import com.coursework.domains.Doctor;
import org.junit.jupiter.api.*;

import java.io.File;
import java.text.ParseException;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class WestminsterSkinConsultationManagerTest {

    static WestminsterSkinConsultationManager westminsterSkinConsultationManager = new WestminsterSkinConsultationManager();

    static Doctor doctor = new Doctor("Peter","Perera","12/02/2001","0704400241","PP6677","Surgeon");

    @BeforeAll
    static void setUp() throws ParseException {
        westminsterSkinConsultationManager.addNewDoctor(doctor);
    }

    @AfterEach
    void tearDown() {
    }

    @Order(1)
    @Test
    void isWord(){
        String firstName = doctor.getName();
        String surName = doctor.getSurname();
        boolean expectedResult = true;
        boolean actualResult = SystemUtility.isWord(firstName);
        boolean actualResultSurName = SystemUtility.isWord(surName);
        Assertions.assertEquals(expectedResult,actualResult);
        Assertions.assertEquals(expectedResult,actualResultSurName);
    }

    @Order(2)
    @Test
    void isValidDate(){
        String date = doctor.getDateOfBirth();
        boolean expectedResult = true;
        boolean actualResult = SystemUtility.isDateValid(date);
        Assertions.assertEquals(expectedResult,actualResult);
    }

    @Order(3)
    @Test
     void isValidMobileNumber(){
        String mobileNumber = doctor.getMobileNumber();
        boolean expectedResult = true;
        boolean actualResult = SystemUtility.isValidMobileNumber(mobileNumber);
        Assertions.assertEquals(expectedResult,actualResult);
     }

    @Order(4)
     @Test
     void isValidLicenceNumber(){
        String licenceNumber = doctor.getLicenceNumber();
        boolean expectedResult = true;
        boolean actualResult = SystemUtility.isValidLicenceNumber(licenceNumber);
        Assertions.assertEquals(expectedResult,actualResult);
     }

    @Order(5)
    @Test
    void addNewDoctor() {
        Doctor actualObject = WestminsterSkinConsultationManager.getDoctors().get(0);
        Assertions.assertTrue(WestminsterSkinConsultationManager.getDoctors().size() > 0);
        Assertions.assertEquals(doctor,actualObject);
    }

    @Order(7)
    @Test
    void deleteDoctor() {
        String licenceNumberToBeRemoved = "PP6677";
        westminsterSkinConsultationManager.deleteDoctor(licenceNumberToBeRemoved);
        Assertions.assertTrue(WestminsterSkinConsultationManager.getDoctors().isEmpty());

    }

    @Order(6)
    @Test
    void saveToFile() {
        westminsterSkinConsultationManager.saveToFile();
        File saveFile = new File("doctorDetails.txt");
        Assertions.assertTrue(saveFile.exists());
        Assertions.assertEquals(saveFile.length(),51);
    }

    @Order(8)
    @Test
    void readStoredInformation(){
        WestminsterSkinConsultationManager.getDoctors().clear();
        westminsterSkinConsultationManager.readStoredInformation();
        Assertions.assertTrue(WestminsterSkinConsultationManager.getDoctors().size() > 0);
    }
}