package com.coursera.scms.controller;

import com.coursera.scms.model.Prescription;
import com.coursera.scms.service.PrescriptionService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/prescriptions")
public class PrescriptionController {

    @Autowired
    private PrescriptionService prescriptionService;

    @PostMapping
    public ResponseEntity<?> savePrescription(
            @RequestHeader("Authorization") String token,
            @Valid @RequestBody Prescription prescription) {

        if (token == null || !token.startsWith("Bearer ")) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid or missing token");
        }

        try {
            Prescription saved = prescriptionService.savePrescription(prescription);
            return ResponseEntity.ok("Prescription saved successfully with id: " + saved.getId());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Failed to save prescription: " + e.getMessage());
        }
    }
}
