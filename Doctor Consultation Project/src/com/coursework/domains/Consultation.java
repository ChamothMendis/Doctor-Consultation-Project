package com.coursework.domains;

import java.io.*;
import java.util.ArrayList;

public class Consultation implements Serializable {
    private Patient patient;
    private Doctor doctor;
    private String date;
    private String time;
    private int cost;
    private transient String notes;
    private ArrayList<String> imageUrl;

    /**
     *
     * @param patient of the consultation
     * @param doctor of the consultation
     * @param date of the consultation
     * @param time of the consultation
     * @param cost of the consultation
     * @param notes of the consultation
     * @param imageUrl of the consultation
     */
    public Consultation(Patient patient, Doctor doctor, String date, String time, int cost, String notes, ArrayList<String> imageUrl) {
        this.patient = patient;
        this.doctor = doctor;
        this.date = date;
        this.time = time;
        this.cost = cost;
        this.notes = notes;
        this.imageUrl = imageUrl;
    }

    /**
     * No args constructor
     */
    public Consultation() {
    }

    /**
     * This method is used to get the image urls of the consultation
     * @return imageUrl Arraylist
     */
    public ArrayList<String> getImageUrl() {
        return imageUrl;
    }

    /**
     * This method is used to set the image urls of the consultation
     * @param imageUrl  Arraylist
     */
    public void setImageUrl(ArrayList<String> imageUrl) {
        this.imageUrl = imageUrl;
    }

    /**
     * This method is used to get the Patient of the consultation
     * @return patient
     */
    public Patient getPatient() {
        return patient;
    }

    /**
     * This method is used to set the patient of the consultation
     * @param patient passing
     */
    public void setPatient(Patient patient) {
        this.patient = patient;
    }

    /**
     * This method is used to get the Date of the consultation
     * @return date
     */
    public String getDate() {
        return date;
    }

    /**
     * This method is used to set the Date of the consultation
     * @param date passing
     */
    public void setDate(String date) {
        this.date = date;
    }

    /**
     * This method is used to get the time of the consultation
     * @return time
     */
    public String getTime() {
        return time;
    }

    /**
     * This method is used to get the Doctor of the consultation
     * @return doctor
     */
    public Doctor getDoctor() {
        return doctor;
    }

    /**
     * This method is used to set the Doctor of the consultation
     * @param doctor passing
     */
    public void setDoctor(Doctor doctor) {
        this.doctor = doctor;
    }

    /**
     * This method is used to get the cost of the consultation
     * @return cost
     */
    public int getCost() {
        return cost;
    }

    /**
     * This method is used to get the notes of the consultation that user has added
     * @return notes
     */
    public String getNotes() {
        return notes;
    }

    @Serial
    private void writeObject(ObjectOutputStream os) throws Exception {
        StringBuilder finalC = new StringBuilder();
        os.defaultWriteObject();
        char [] chars = notes.toCharArray();
        for (char c : chars){
            c += 5;
            finalC.append(c);
        }
        System.out.println(finalC);
        os.writeObject(finalC.toString());
    }

    @Serial
    private void readObject(ObjectInputStream is)throws Exception{
        StringBuilder finalC = new StringBuilder();
        is.defaultReadObject();
        String before = (String) is.readObject();
        char [] chars = before.toCharArray();
        for (char c : chars){
            c -= 5;
            finalC.append(c);
        }
        notes = String.valueOf(finalC);
    }


    @Override
    public String toString() {
        return "Consultation{" +
                "patient=" + patient +
                ", doctor=" + doctor +
                ", date='" + date + '\'' +
                ", time='" + time + '\'' +
                ", cost=" + cost +
                ", notes='" + notes + '\'' +
                ", imageUrl=" + imageUrl +
                '}';
    }
}
