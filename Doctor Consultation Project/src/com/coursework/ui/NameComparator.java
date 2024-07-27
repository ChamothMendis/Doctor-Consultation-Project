package com.coursework.ui;

import com.coursework.domains.Doctor;

import java.util.Comparator;

public class NameComparator implements Comparator<Doctor> {
    /**
     * @param doc1 the first object to be compared.
     * @param doc2 the second object to be compared.
     * @return
     */
    @Override
    public int compare(Doctor doc1, Doctor doc2) {
        if (doc1.getName().equals(doc2.getName())){
            return doc1.getSurname().compareTo(doc2.getSurname());
        }
        return doc1.getName().compareTo(doc2.getName());
    }
}
