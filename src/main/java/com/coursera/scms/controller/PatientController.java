package com.coursera.scms.controller;

import com.coursera.scms.model.Patient;
import com.coursera.scms.service.PatientService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/test")
public class PatientController {

    private final PatientService patientService;

    public PatientController(PatientService patientService) {
        this.patientService = patientService;
    }

    // Тестовый метод для поиска пациента по email или телефону
    @GetMapping("/patient")
    public ResponseEntity<?> testFindPatient(@RequestParam String email, @RequestParam String phone) {
        Optional<Patient> patient = patientService.findByEmailOrPhoneNumber(email, phone);
        return patient.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}
