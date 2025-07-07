package com.coursera.scms.service;

import com.coursera.scms.model.Prescription;
import com.coursera.scms.repository.PrescriptionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PrescriptionService {

    @Autowired
    private PrescriptionRepository prescriptionRepository;

    public Prescription savePrescription(Prescription prescription) {
        return prescriptionRepository.save(prescription);
    }
}
