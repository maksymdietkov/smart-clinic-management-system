package com.coursera.scms.service;

import com.coursera.scms.model.Patient;
import com.coursera.scms.repository.PatientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PatientService {

    @Autowired
    private PatientRepository patientRepository;

    public boolean validateLogin(String email, String password) {
        Optional<Patient> patientOpt = patientRepository.findByEmail(email);
        return patientOpt.map(patient -> patient.getPassword().equals(password)).orElse(false);
    }

    public Optional<Patient> findByEmail(String email) {
        return patientRepository.findByEmail(email);
    }

}
