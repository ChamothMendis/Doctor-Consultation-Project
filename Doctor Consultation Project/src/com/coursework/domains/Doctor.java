package com.coursework.domains;

import java.util.ArrayList;
import java.util.List;

public class Doctor extends Person {
    private String licenceNumber;
    private String specialisation;

    private List<Patient> patients = new ArrayList<Patient>(8);

    public Doctor(String name, String surname, String dateOfBirth, String mobileNumber, String licenceNumber, String specialisation) {
        super(name, surname, dateOfBirth, mobileNumber);
        this.licenceNumber = licenceNumber;
        this.specialisation = specialisation;
    }

    public String getLicenceNumber() {
        return licenceNumber;
    }

    public void setLicenceNumber(String licenceNumber) {
        this.licenceNumber = licenceNumber;
    }

    public String getSpecialisation() {
        return specialisation;
    }

    public void setSpecialisation(String specialisation) {
        this.specialisation = specialisation;
    }

    public List<Patient> getPatients() {
        return patients;
    }

    public void setPatients(List<Patient> patients) {
        this.patients = patients;
    }

    @Override
    public String toString() {
        return "Doctor{" +
                super.toString() +
                " licenceNumber='" + licenceNumber + '\'' +
                ", specialisation='" + specialisation + '\'' +
                '}';
    }
}
