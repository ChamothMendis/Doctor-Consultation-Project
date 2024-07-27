package com.coursework.domains;

public class Patient extends Person{
    private String patientId;
    private String password;

    public Patient() {
        super();

    }

    /**
     *
     * @param name of the patient
     * @param surname of the patient
     * @param date of consultation
     * @param mobileNumber of the patient
     * @param patientId of the patient
     * @param password of the patient
     */
    public Patient(String name, String surname, String date, String mobileNumber, String patientId, String password) {
        super(name, surname, date, mobileNumber);
        this.patientId = patientId;
        this.password = password;
    }



    /**
     * This method is used to get the patientId
     * @return patientId as a String
     */
    public String getPatientId() {
        return patientId;
    }

    /**
     * This method is used to set the patientId
     * @param patientId of the patient passing as a parameter
     */
    public void setPatientId(String patientId) {
        this.patientId = patientId;
    }

    /**
     * This method is used to return the password of the patient
     * @return password as a String
     */
    public String getPassword() {
        return password;
    }

    /**
     * This method is used to get the details of the patient object as a String
     * @return details of the patient
     */
    @Override
    public String toString() {
        return super.toString() + "Patient{" +
                "patientId='" + patientId + '\'' + "password=" + password +
                '}';
    }
}
