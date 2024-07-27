package com.coursework.admin;

import com.coursework.domains.Doctor;

import java.util.Comparator;

public class SurnameComparator implements Comparator<Doctor> {

    /**
     * This method is used to compare two object with respect to their specific property.
     * @param doc1 the first object to be compared.
     * @param doc2 the second object to be compared.
     * @return the name that comes first
     */
    @Override
    public int compare(Doctor doc1, Doctor doc2) {
        if ((doc1.getSurname().equals(doc2.getSurname()))){
            return doc1.getName().compareTo(doc2.getName());
        }
        return doc1.getSurname().compareTo(doc2.getSurname());
    }
}
