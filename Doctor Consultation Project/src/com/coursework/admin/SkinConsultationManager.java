package com.coursework.admin;

import com.coursework.domains.Doctor;

public interface SkinConsultationManager {
    void addNewDoctor(Doctor doctor);
    void deleteDoctor(String licenceNumber);
    void listOfDoctors();
    void saveToFile();
    void readStoredInformation();
}
