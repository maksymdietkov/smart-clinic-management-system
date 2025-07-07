package com.coursera.scms.service;

import com.coursera.scms.model.Patient;
import com.coursera.scms.repository.PatientRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PatientService {

    private final PatientRepository patientRepository;

    public PatientService(PatientRepository patientRepository) {
        this.patientRepository = patientRepository;
    }

    public Optional<Patient> findByEmail(String email) {
        return patientRepository.findByEmail(email);
    }

    public Optional<Patient> findByEmailOrPhoneNumber(String email, String phoneNumber) {
        return patientRepository.findByEmailOrPhoneNumber(email, phoneNumber);
    }

    // Другие методы сервиса можно добавить здесь
}
